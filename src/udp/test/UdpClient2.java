package udp.test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient2 {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		final DatagramSocket ds = new DatagramSocket();
		String str = "hello world!";
		byte[] buf = str.getBytes();
		DatagramPacket p = new DatagramPacket(buf, buf.length,InetAddress.getLocalHost(), 1111);
		ds.send(p);
		DatagramPacket pp = new DatagramPacket(buf, buf.length,InetAddress.getLocalHost(), 1112);
		ds.send(pp);
		System.out.println("Client send "+str);
		byte buf2[] = new byte[1024];
		DatagramPacket p2 = new DatagramPacket(buf2, buf2.length,InetAddress.getLocalHost(), 1111);
		ds.receive(p2);
		System.out.println("Client receive:"+new String(buf2,0,p2.getLength()) +",getSocketAddress()="+p2.getSocketAddress() +",getPort()="+p2.getPort());
	}
}
