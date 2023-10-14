package interactInterface;

import SenderReceiver.Receiver;
import SenderReceiver.Sender;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ModeSelectUI extends JFrame {
	JFrame ModeSelect;
	public ModeSelectUI(String title, int width, int height) {
		super(title);
		ModeSelect = this;
		ModeSelect.setSize(width, height);
		ModeSelect.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ModeSelect.setLayout(null);
		ModeSelect.setLocationRelativeTo(null);

		// 创建控件
		JLabel head = new JLabel("联机对战");
		JButton creat_house = new JButton("创建房间");
		JButton join_house  = new JButton("加入房间");

		// 相关位置变量
		int controlWidget = (int)(getWidth() * 0.6);
		int left_border = (int)(getWidth() * 0.2);
		int high = 50;

		// 设置控件属性
		head.setHorizontalAlignment(JLabel.CENTER);
		head.setBounds(left_border, 100, controlWidget, high);
		head.setFont(new Font("宋体", Font.BOLD, 30));
		creat_house.setBounds(left_border, head.getY() + 150, controlWidget, high);
		join_house.setBounds(left_border, creat_house.getY() + 100, controlWidget, high);

		// 添加控件点击事件
		// 创建房间
		creat_house.addActionListener(e -> {
			try {
				new WaitConnectUI(new Sender(), this);
			} catch (IOException | InterruptedException ex) {
				throw new RuntimeException(ex);
			}
		});

		// 加入房间
		join_house.addActionListener(e -> {
			ModeSelect.dispose();
			new RoomSearcherUI(new Receiver());
		});

		// 添加控件
		ModeSelect.add(head);
		ModeSelect.add(creat_house);
		ModeSelect.add(join_house);
		ModeSelect.setVisible(true);
	}
}
