package interactInterface;

import SenderReceiver.Sender;

import javax.swing.*;
import java.io.IOException;

public class WaitConnectUI extends JDialog implements Runnable{
	Sender sender;
	ModeSelectUI modeSelectUI;
	public WaitConnectUI(Sender sender, ModeSelectUI modeSelectUI) throws IOException, InterruptedException {
		super();
		this.sender = sender;
		this.modeSelectUI = modeSelectUI;
		// 该线程利用广播发送信息
		new Thread(sender).start();
		// 该线程用于关闭窗口
		new Thread(this).start();

		// 设置窗口属性
		setSize(200, 100);
		setTitle("等待连接");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		// 添加控件
		JLabel content = new JLabel("有人接入后自动关闭此窗口");
		add(content);
		// 显示窗口
		setVisible(true);
	}

	@Override
	public void run() {
		try {
			sender.creatHouse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (Sender.isConnect()) {
			this.dispose();
			modeSelectUI.dispose();
		}
	}
}
