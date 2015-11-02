package udp.test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;

public class UdpServer {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		final DatagramSocket ds = new DatagramSocket(1111);
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					UdpServer.receive(ds);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
		Thread.sleep(Integer.MAX_VALUE);
	}
	
	public static void receive(DatagramSocket ds) throws Exception{
		while (true) {
			System.out.println("Server while ");
			byte buf[] = new byte[1024];
			DatagramPacket p = new DatagramPacket(buf, buf.length);
			ds.receive(p);
			System.out.println("Server receive:"+new String(buf,0,p.getLength()) +",getAddress()="+p.getAddress() +",getPort()="+p.getPort());
			
			String str = "Server hello world!";
			byte[] buf2 = str.getBytes();
			p.setData(buf2);
			ds.send(p);
			System.out.println("Server send " + str);
		}
	}

}
