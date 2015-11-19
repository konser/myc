package test.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Server {
	public static List<ClientInfo> clientInfos = new ArrayList<ClientInfo>();
	public static void main(String[] args) throws Exception {
		final DatagramSocket datagramSocket = new DatagramSocket(1111);
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					Server.receive(datagramSocket);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Thread receiveThread = new Thread(runnable);
		receiveThread.start();
		Thread.sleep(Integer.MAX_VALUE);
	}
	
	public static void receive(DatagramSocket datagramSocket) throws Exception{
		for (;;) {
			byte buf[] = new byte[2048];
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
			datagramSocket.receive(datagramPacket);
			
			String rec = new String(buf, 0, datagramPacket.getLength());
			int port = datagramPacket.getPort();
			InetAddress addr = datagramPacket.getAddress();
			System.out.println("addr=" + addr + ", port=" + port + ", rec=" + rec);
			
			if(rec.startsWith("client")) {
				ClientInfo clientInfo = new ClientInfo(addr.getHostAddress(), port);
				if (!clientInfos.contains(clientInfo)) {
					clientInfos.add(clientInfo);
				}
				String message = "clients:" + ClientInfo.getClientInfos(clientInfos);
				UdpUtil.send(datagramSocket, addr.getHostAddress(), port, message);
			} else if(rec.startsWith("conn")) {
				ClientInfo clientInfo1 = clientInfos.get(0);
				UdpUtil.send(datagramSocket, clientInfo1.addr, clientInfo1.port, "send:" + ClientInfo.getClientInfos(clientInfos));
			} else if(rec.startsWith("succ:")) {
				rec = rec.replace("succ:", "");
				ClientInfo clientInfo2 = clientInfos.get(1);
				UdpUtil.send(datagramSocket, clientInfo2.addr, clientInfo2.port, "send0:" + rec);
			}
		}
	}
}
