package interactInterface;

import Chess.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI extends JFrame {
	public MainUI(int width, int height) {
		super("游戏菜单");
		setSize(width, height);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);

		// 创建控件
		JLabel title = new JLabel("GoBang");
		JButton solo_combat_bottom = new JButton("单人对战");
		JButton online_combat_bottom  = new JButton("联机对战");

		// 相关位置变量
		int controlWidget = (int)(getWidth() * 0.6);
		int left_border = (int)(getWidth() * 0.2);
		int high = 50;

		// 设置控件属性
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBounds(left_border, 100, controlWidget, high);
		title.setFont(new Font("Arial", Font.BOLD, 30));
		solo_combat_bottom.setBounds(left_border, title.getY() + 150, controlWidget, high);
		online_combat_bottom.setBounds(left_border, solo_combat_bottom.getY() + 100, controlWidget, high);

		// 添加控件点击事件
		// 单人游戏
		solo_combat_bottom.addActionListener(e -> {
				dispose();
				ChessBoard.init();
		});
		// 联机对战
		online_combat_bottom.addActionListener(e ->{
				dispose();
				ModeSelectUI modeSelectUI = new ModeSelectUI("联机对战", 500, 500);
				modeSelectUI.setSize(500, 600);
				modeSelectUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				modeSelectUI.setLocationRelativeTo(null);
				modeSelectUI.setVisible(true);
		});

		// 添加控件
		add(title);
		add(solo_combat_bottom);
		add(online_combat_bottom);

		setVisible(true);
	}
}
