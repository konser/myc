package test.udp.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetSocketAddress;
import java.util.HashMap;

public class UdpServerTest {
	NioDatagramChannel f = new NioDatagramChannel();
	private static final int PORT = Integer.parseInt(System.getProperty("port",
			"2002"));

	public static void main(String[] args) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup(10);
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class)
					.option(ChannelOption.SO_BROADCAST, true)
					.handler(new UDP_Handler());
			ChannelFuture closeFuture = b.bind(PORT).sync().channel().closeFuture();
			System.out.println("UdpServerTest start");
			closeFuture.await();
			System.out.println("UdpServerTest close");
		} finally {
			group.shutdownGracefully();
		}
	}

	public static class UDP_Handler extends
			SimpleChannelInboundHandler<DatagramPacket> {

		HashMap<String, InetSocketAddress> hash = new HashMap<String, InetSocketAddress>();

		ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) {
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			cause.printStackTrace();
			// We don't close the channel because we can keep serving requests.
		}

		@Override
		public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
			System.out.println("write = " + (msg == null ? null : msg.getClass()));
			ctx.write(msg, promise);
		}
		
		@Override
		protected void messageReceived(ChannelHandlerContext ctx,
				DatagramPacket msg) throws Exception {
			// TODO Auto-generated method stub
			ByteBuf content = msg.content();
			int len = content.readableBytes();
			String str = "";
			InetSocketAddress sender = msg.sender();
			if (len > 0) {
				byte[] dst = new byte[len];
				content.readBytes(dst);
				str = new String(dst, "utf-8");
				
//					channels.add(ctx.channel());
//					channels.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(content), sender));
					
				DatagramPacket pack = new DatagramPacket(Unpooled.copiedBuffer(str.getBytes()), sender);
				ChannelFuture future = ctx.writeAndFlush(pack);
				System.out.println("isDone=" + future.isDone() + ", isSuccess=" + future.isSuccess());
			}
			System.out.println("len=" + len + ", str=" + str + ", cur=" + System.currentTimeMillis() + ", sender=" + sender);
		}
	}
}
