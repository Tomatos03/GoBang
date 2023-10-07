import javax.swing.*;

public class Main {
	public static void main(String[] args) {
//		JFrame gameMenu = new JFrame("游戏菜单");
//		gameMenu.setSize(500, 600);
//		gameMenu.setVisible(true);
		
		JFrame gameCore = new JFrame("五子棋游戏");
		gameCore.setSize(1000, 800);
		gameCore.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameCore.setVisible(true);

		ChessBoard chessBoard = new ChessBoard(700, 700, gameCore);
		gameCore.add(chessBoard);
	}
}