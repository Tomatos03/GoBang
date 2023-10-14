package interactInterface;

import Chess.ChessAccord;

import javax.swing.*;
import java.awt.*;

public class EnquireUI extends JDialog implements ChessAccord {
	int select = -1;

	public int getSelect() {
		return select;
	}

	public EnquireUI(JFrame frame,  String title, String content, String leftButtonTitle, String rightButtonTitle)  {
		JDialog dialog = new JDialog(frame, "提示", JDialog.ModalityType.APPLICATION_MODAL);

		// 创建控件
		JLabel hintText = new JLabel();
		JButton acceptButton = new JButton();
		JButton rejectButton = new JButton();
		initUI(dialog, hintText, acceptButton, rejectButton);

		dialog.setTitle(title);
		hintText.setText(content);
		acceptButton.setText(leftButtonTitle);
		rejectButton.setText(rightButtonTitle);
		dialog.setVisible(true);
	}

	private void initUI(JDialog dialog, JLabel hintText, JButton acceptButton, JButton rejectButton) {
		// 设置属性
		dialog.setSize(200, 100);
		dialog.setModal(true);
		dialog.setLayout(new FlowLayout());
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(null);

		// 添加控件
		dialog.add(hintText);
		dialog.add(acceptButton);
		dialog.add(rejectButton);

		// 添加控件事件
		acceptButton.addActionListener(e -> {
				dialog.dispose();
				select = ACCEPT;
		});

		rejectButton.addActionListener(e -> {
				dialog.dispose();
				select = REJECT;
		});
	}
}
