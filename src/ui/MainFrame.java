package ui;

import entity.ChessBoard;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {
	private final int DEFAULT_WIDTH = 1000;
	private final int DEFAULT_HEIGHT = 1000;

	private JPanel currentContent;

	public MainUI() {
		super("游戏菜单");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		this.currentContent = new ModeSelectPanel(this);

		add(currentContent);
		setVisible(true);
	}

	public void switchContent(String target) {
		// 移除当前内容
		remove(currentContent);

		// 动态创建新内容
		switch (target) {
			case "modelSelect":
				currentContent = new ModeSelectPanel(this);
				break;
			case "lanCombat":
				currentContent = new LANCombatPanel(this);
				break;
			case "singleGame":
				currentContent = new ChessBoard(this);
				break;
			case "joinRoom":
				currentContent = new JoinRoomPanel(this);
				break;
			case "createRoom":
				currentContent = new CreateRoomPanel(this);
				break;
		}

		// 添加新内容并更新布局
		add(currentContent, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
