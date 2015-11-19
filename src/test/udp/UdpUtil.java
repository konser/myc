package test.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpUtil {
	public static void send(final DatagramSocket datagramSocket, String addr, int port, String message)
			throws IOException {
		byte[] buf = message.getBytes();
		DatagramPacket tmp = new DatagramPacket(buf, buf.length);
		tmp.setData(buf);
		InetAddress addr0 = InetAddress.getByName(addr);
		tmp.setAddress(addr0);
		tmp.setPort(port);
		datagramSocket.send(tmp);
	}
}
