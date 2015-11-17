package test.udp.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class UdpClientTest {
	static final String HOST = System.getProperty("host", "127.0.0.1");
	static final int PORT = Integer
			.parseInt(System.getProperty("port", "2002"));
	private static ByteBuf byte_buf;

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		InetSocketAddress socket = new InetSocketAddress("127.0.0.1", PORT);

		EventLoopGroup group = new NioEventLoopGroup(20);
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class)
					.option(ChannelOption.SO_BROADCAST, true)
					.handler(new UDP_Client_Handler());

			Channel ch = b.connect(HOST, PORT).sync().channel();

			// Broadcast the QOTM request to port 8080.
			for (int i = 0; i < 10; i++) {
				ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("QOTM?" + i, CharsetUtil.UTF_8), socket)).sync();
			}

			Thread.sleep(1000l);
			// QuoteOfTheMomentClientHandler will close the DatagramChannel when
			// a
			// response is received. If the channel is not closed within 5
			// seconds,
			// print an error message and quit.
			// if (!ch.closeFuture().await(15000)) {
			// System.err.println("QOTM request timed out.");
			// }
		} finally {
			group.shutdownGracefully();
		}
	}

	public static class UDP_Client_Handler extends SimpleChannelInboundHandler<DatagramPacket> {

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			cause.printStackTrace();
			ctx.close();
		}

		@Override
		protected void messageReceived(ChannelHandlerContext ctx,
				DatagramPacket msg) throws Exception {
			// TODO Auto-generated method stub
			String response = msg.content().toString(CharsetUtil.UTF_8);
			System.out.println("Quote of the Moment: " + response);
//			if (response.contains("0")) {
//				System.out.println("0 bye~!!!");
//				ctx.close();
//			}
		}
	}
}
