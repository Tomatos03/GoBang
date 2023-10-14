package Chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class ChessBoard extends JPanel implements MouseListener, ChessAccord{
	private int currentFallingPositionX = -1;
	private int currentFallingPositionY = -1;
	private boolean control;
	private final int widget;
	private final int height;
	private final int INCREMENT = 50;
	private final int INIT_X = 50;
	private final int INIT_Y = 50;
	private final int border = 25;
	private final JFrame frame;
	ArrayList<Piece> pieces;
	private int[][] chessLog;
	private ChessBoard(int widget, int height , JFrame frame) {
		this.widget = widget;
		this.height = height;
		this.frame = frame;
		this.control = true;
		chessLog = new int[height + 1][widget + 1];
		pieces = new ArrayList<>();
		addMouseListener(this);
	}

	public void setCurrentFallingPositionX(int currentFallingPositionX) {
		this.currentFallingPositionX = currentFallingPositionX;
	}

	public void setCurrentFallingPositionY(int currentFallingPositionY) {
		this.currentFallingPositionY = currentFallingPositionY;
	}

	public boolean isControl() {
		return control;
	}

	synchronized public String getCurrentFallingPosition () {
		return getCurrentFallingPositionX() + " " + getCurrentFallingPositionY();
	}

	public int getCurrentFallingPositionX() {
		return currentFallingPositionX;
	}

	public int getCurrentFallingPositionY() {
		return currentFallingPositionY;
	}

	public void setControl(boolean control) {
		this.control = control;
	}

	public int getWidget() {
		return widget;
	}

	public int getINIT_X() {
		return INIT_X;
	}

	public int getINIT_Y() {
		return INIT_Y;
	}

	public int getBorders() {
		return border;
	}

	public int getHeight() {
		return height;
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	public int getINCREMENT() {
		return INCREMENT;
	}

	public int[][] getChessLog() {
		return chessLog;
	}



	public static ChessBoard init () {
		JFrame gameCore = new JFrame("五子棋游戏");
		ChessBoard chessBoard = new ChessBoard(750, 800, gameCore);
		gameCore.add(chessBoard);

		gameCore.setSize(900, 850);
		gameCore.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameCore.setLocationRelativeTo(null);
		gameCore.setVisible(true);
		return chessBoard;
	}

	// 225 325
	@Override
	public void paint(Graphics g) {
		// 调用父类的paint方法，以进行默认的绘制
		super.paint(g);

		// 绘制棋盘背景
		g.setColor(new Color(210,105,30));
		g.fillRect(INIT_X ,  INIT_Y, widget, height);
		// 绘制棋盘线条
		g.setColor(Color.BLACK);
		int endPoint = (widget - 2 * border) / INCREMENT;
		for (int i = 0; i <= endPoint; i++) {
			g.fillRect( border + INIT_X  + i * INCREMENT,  border + INIT_Y, 2, widget - 2 * border);
			g.fillRect( border + INIT_X , border + INIT_Y  + i * INCREMENT, widget - 2 * border, 2);
		}

		// 绘制已有棋子
		for (Piece piece : pieces) {
			g.setColor(piece.getColor());
			g.fillOval(piece.getX(), piece.getY(), 2 * Piece.R, 2 * Piece.R);
		}
	}

	public void restartGame() {
		pieces = new ArrayList<>();
		chessLog = new int[height + 1][widget + 1];
		repaint();
	}

	private void generatePieceAtCoordinate (int x, int y) {
		int endPoint = widget / INCREMENT;
		for (int i = 0; i <= endPoint; i++) {
			for (int j = 0; j <= endPoint; j++) {
				int cur_x = border + INIT_X + j * INCREMENT;
				int cur_y = border + INIT_Y + i * INCREMENT;
				// 以中心点做半径为r的圆
				int r = (int) Math.sqrt(Math.pow(cur_x - x, 2) + Math.pow(cur_y - y, 2));
				if (r <= 25) {
					// 将坐标偏移至原点
					int offset_x = cur_x -  INIT_X - border;
					int offset_y = cur_y -  INIT_Y - border;
					// 检查落点棋子是否已经存在
					if (chessLog[offset_y][offset_x] != Piece.EMPTY_PIECE) {
						return;
					}

					currentFallingPositionX = cur_x;
					currentFallingPositionY = cur_y;
					recordChessMoves(cur_x, cur_y, offset_x, offset_y);
					Referee.winLossVerifier(frame, this, offset_x, offset_y);
					Referee.switchPlayerTurn();
					return;
				}
			}
		}
	}


	public void recordChessMoves (int cur_x, int cur_y, int offset_x , int offset_y) {
		Color currentPieceColor = Referee.getCurrentPieceColor();
		int k = (currentPieceColor == Color.BLACK ? Piece.BLACK_PIECE : Piece.WHITE_PIECE);
		chessLog[offset_y][offset_x] = k;
		pieces.add(new Piece(cur_x - Piece.R, cur_y- Piece.R, currentPieceColor));
		repaint();
	}

	public void endGame() {
		frame.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!control) return;

		int x = e.getX();
		int y = e.getY();
		generatePieceAtCoordinate(x, y);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}