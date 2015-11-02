package udp.test;

/*
 *基于UDP广播的留言程序
 *
 */
import java.awt.*;
//向上注释,(程序在前,注释在后)
//awt包,JAVA中支持图形化界面GUI设计工具集
import java.awt.event.*;
//JAVA事件处理包.
import java.net.*;

public class ui extends Frame implements Runnable, ActionListener { // extends
																	// Frame
																	// 继承Frame类
																	// Frame类;JAVA顶级窗口之一(一个窗体...)
																	// 实现多线程需要实现Runnable接口
																	// 事件处理
																	// 需要实现ActionListener接口
	String name;
	// 主机名
	Thread thread;

	TextField field;
	Button button;
	private TextArea textarea;
	Label label;

	public ui() throws Exception {
		name = InetAddress.getLocalHost().getHostName();
		thread = new Thread(this);
		try {
			thread.start();
		} catch (Exception ee) {
		}

		textarea = new TextArea(20, 80);
		textarea.setEditable(false);
		add("Center", textarea);
		button = new Button("send");
		button.addActionListener(this);

		Panel pl = new Panel();
		label = new Label("请输入发送内容:");
		field = new TextField(20);
		field.addActionListener(this); // 响应回车操作
		pl.add(label);
		pl.add(field);
		pl.add(button);
		add("South", pl);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// newline=System.getProperty("line.separator");//获取分割符
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if ((source == button) || (source == field)) {
			if ((field.getText() == null) || field.getText().equals("")) {
				this.label.setText("不能为空");
			} else {
				send(name + " say:" + field.getText() + "\n");
				field.setText(null);
			}
		}

	}

	public void send(String s) {
		// 发送数据
		int port = 5858;
		InetAddress group = null;
		MulticastSocket socket = null;
		try {
			group = InetAddress.getByName("228.5.6.7");
			socket = new MulticastSocket(port);
			socket.setTimeToLive(1);
			socket.joinGroup(group);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error:" + e);
		}
		try {
			DatagramPacket packet = null;
			byte data[] = s.getBytes();
			packet = new DatagramPacket(data, data.length, group, port);
			socket.send(packet);
			// System.out.println("发送前:"+new String (data,0,data.length));
		} catch (Exception e) {
			System.out.println("Error:   " + e);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {// 接收数据
		while (true) {
			InetAddress rec_group = null;
			MulticastSocket rec_socket = null;
			int rec_port = 5858;
			try {
				rec_group = InetAddress.getByName("228.5.6.7");
				rec_socket = new MulticastSocket(rec_port);
				rec_socket.joinGroup(rec_group);
			} catch (Exception e) {
			}
			byte rec_data[] = new byte[1024];
			DatagramPacket rec_packet = new DatagramPacket(rec_data,
					rec_data.length);
			try {
				rec_socket.receive(rec_packet);
				String message = new String(rec_packet.getData(), 0,
						rec_packet.getLength());
				System.out.println("收到的：" + message);
				// String msg=toChinese(message);
				textarea.setForeground(Color.blue);
				textarea.append(message);
				// textarea.setText(message);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) throws Exception {
		ui window = new ui();
		window.setTitle("UDP_MULTICAST _anyway");
		window.pack();
		window.setVisible(true);
		// Thread t = new Thread(window);
		// t.start();
	}
}
