import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TerminationWindow extends JDialog {
	public TerminationWindow(JFrame frame, ChessBoard chessBoard)  {
		JDialog terminationWindow = new JDialog(frame, "提示", JDialog.ModalityType.APPLICATION_MODAL);
		// 设置属性
		terminationWindow.setLocationRelativeTo(this);
		terminationWindow.setSize(200, 100);
		terminationWindow.setModal(true);
		terminationWindow.setLayout(new FlowLayout());
		terminationWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// 创建控件
		String user = Referee.getCurrentPieceColor() == Color.BLACK ? "黑" : "白";
		JLabel hintText = new JLabel("游戏结束" + user +"方获得胜利");
		JButton closeButton = new JButton("关闭游戏");
		JButton restartButton = new JButton("重新开局");

		// 添加控件事件
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				terminationWindow.dispose();
			}
		});

		restartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessBoard.restartGame();
				terminationWindow.dispose();
				chessBoard.repaint();
			}
		});

		// 添加控件
		terminationWindow.add(hintText);
		terminationWindow.add(restartButton);
		terminationWindow.add(closeButton);
		terminationWindow.setVisible(true);
	}
}
