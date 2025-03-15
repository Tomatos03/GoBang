package ui;

import entity.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
	private final int DEFAULT_WIDTH = 1000;
	private final int DEFAULT_HEIGHT = 1000;
	private WindowAdapter originAdapter;

	private JPanel currentContent;

	public WindowAdapter getOriginAdapter() {
		return originAdapter;
	}

	public MainFrame() {
		super("五子棋游戏");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		this.currentContent = new ModeSelectPanel(this);

		originAdapter = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("test");
				System.exit(0);
			}
		};
		addWindowListener(originAdapter);
		add(currentContent);
		setVisible(true);
	}

	public void updatePanel(JPanel panel) {
		if (currentContent != null) {
			remove(currentContent);
		}

		add(panel, BorderLayout.CENTER);
		currentContent = panel;

		revalidate();
		repaint();
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
			case "goBangGame":
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
