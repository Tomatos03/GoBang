package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class MainFrame extends JFrame {
	private final int WIDTH = 1000;
	private final int HEIGHT = 1000;

	private JPanel currentPanel;

	public MainFrame() {
		super("五子棋游戏");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		this.currentPanel = new ModeSelectPanel(this);
		add(currentPanel);
		setVisible(true);
	}

	public void updatePanel(JPanel panel) {
		if (currentPanel != null) {
			remove(currentPanel);
		}

		add(panel, BorderLayout.CENTER);
		currentPanel = panel;

		revalidate();
		repaint();
	}
}
