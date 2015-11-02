package udp.test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		final DatagramSocket ds = new DatagramSocket();
		ds.connect(InetAddress.getLocalHost(), 1111);
		String str = "hello world!";
		byte[] buf = str.getBytes();
		DatagramPacket p = new DatagramPacket(buf, buf.length);
		ds.send(p);
		System.out.println("Client send "+str);
		byte buf2[] = new byte[1024];
		DatagramPacket p2 = new DatagramPacket(buf2, buf2.length);
		ds.receive(p2);
		System.out.println("Client receive:"+new String(buf2,0,p2.getLength()) +",getSocketAddress()="+p2.getSocketAddress() +",getPort()="+p2.getPort());
	}
}
