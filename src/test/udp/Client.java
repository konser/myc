package test.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class Client {
	public static String serverIp = "115.159.6.194";
//	public static String serverIp = "192.168.1.106";
	public static int serverPort = 1111;
	public static void main(String[] args) throws Exception {
		final DatagramSocket datagramSocket = new DatagramSocket();
		
		String message = "client";
		if (args != null && args.length > 0) {
			message = args[0];
		}
		if(args != null && args.length > 1) {
			serverIp = args[1];
		}
		UdpUtil.send(datagramSocket, serverIp, serverPort, "client:"+message);
		
		System.out.println("port = " + datagramSocket.getLocalPort());
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					Client.receive(datagramSocket);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Thread receiveThread = new Thread(runnable);
		receiveThread.start();
		Thread.sleep(Integer.MAX_VALUE);
	}
	
	public static void receive0(DatagramSocket datagramSocket) throws Exception{
		for (;;) {
			System.out.println("receive0");
			byte buf[] = new byte[2048];
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
			datagramSocket.receive(datagramPacket);
			
			String rec = new String(buf, 0, datagramPacket.getLength());
			int port = datagramPacket.getPort();
			InetAddress addr = datagramPacket.getAddress();
			
			System.out.println("receive0 addr=" + addr + ", port=" + port + ", rec=" + rec);
		}
	}
	
	public static void receive(DatagramSocket datagramSocket) throws Exception{
		for (;;) {
			System.out.println("receive");
			byte buf[] = new byte[2048];
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
			datagramSocket.receive(datagramPacket);
			
			String rec = new String(buf, 0, datagramPacket.getLength());
			int port = datagramPacket.getPort();
			InetAddress addr = datagramPacket.getAddress();
			
			System.out.println("addr=" + addr + ", port=" + port + ", rec=" + rec);
			
			if(rec != null && !rec.isEmpty()) {
				if(rec.startsWith("clients")) {
					rec = rec.replace("clients:", "");
					List<ClientInfo> clientInfos = ClientInfo.resolve(rec);
					System.out.println("clientInfos=" + clientInfos);
					if (clientInfos.size() > 1) {
						UdpUtil.send(datagramSocket, serverIp, serverPort, "conn");
					}
				} else if(rec.startsWith("send:")) {
					rec = rec.replace("send:", "");
					List<ClientInfo> clientInfos = ClientInfo.resolve(rec);
					ClientInfo clientInfo1 = clientInfos.get(0);
					ClientInfo clientInfo2 = clientInfos.get(1);
					final DatagramSocket datagramSocket2 = new DatagramSocket();
					UdpUtil.send(datagramSocket2, clientInfo2.addr, clientInfo2.port, "hole:{}");
					UdpUtil.send(datagramSocket2, clientInfo2.addr, clientInfo2.port, "hole:{}");
					
					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							try {
								Client.receive(datagramSocket2);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					Thread receiveThread = new Thread(runnable);
					receiveThread.start();
					
					UdpUtil.send(datagramSocket, serverIp, serverPort, "succ:" + clientInfo1.addr + "|" +  datagramSocket2.getLocalPort());
				} else if(rec.startsWith("send0:")) {
					rec = rec.replace("send0:", "");
					List<ClientInfo> clientInfos = ClientInfo.resolve(rec);
					ClientInfo clientInfo = clientInfos.get(0);
					UdpUtil.send(datagramSocket, clientInfo.addr, clientInfo.port, "succ:{}");
					UdpUtil.send(datagramSocket, clientInfo.addr, clientInfo.port, "succ:{}");
				}if (rec.startsWith("succ:")) {
					System.out.println("succ");
				}
			}
		}
	}
}
