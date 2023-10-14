package interactInterface;

import Chess.ChessAccord;
import SenderReceiver.Receiver;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class RoomSearcherUI extends JFrame implements ChessAccord, Runnable {
	DefaultListModel<String> itemManager;
	ArrayList<String> all_Ip;

	public RoomSearcherUI(Receiver receiver) {
		super("局域网房间");
		// 初始化属性对象
		this.itemManager = new DefaultListModel<>();
		this.all_Ip = receiver.getIp_list();
		// 该接受广播信息
		Thread broadcast = new Thread(receiver);
		broadcast.start();
		// 该线程刷新窗口显示信息
		new Thread(this).start();

		JList<String> list = new JList<>(itemManager);
		JScrollPane scrollPane = new JScrollPane(list);
		// 设置基本属性
		setSize(200, 300);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		Button joinButton = new Button("Join in house");
		add(joinButton, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
		// 添加事件
		joinButton.addActionListener(e -> {
			String ip = list.getSelectedValue();
			if (ip == null) return;

			try {
				receiver.joinHouse(ip);
			} catch (IOException | InterruptedException ex) {
				throw new RuntimeException(ex);
			}
		});
	}

	public void updateAllItem(ArrayList<String> allIp) {
		for (String ip : allIp) {
			if (!itemManager.contains(ip)) itemManager.addElement(ip);
		}
	}


	@Override
	public void run() {
		try {
			while (!Receiver.isConnect()) {
				updateAllItem(all_Ip);
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			this.dispose();
		}
	}
}
