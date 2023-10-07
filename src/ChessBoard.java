import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


class ChessBoard extends JPanel implements MouseListener{
	private final int widget;
	private final int height;
	private final int INCREMENT = 50;
	private final int INIT_X = 50;
	private final int INIT_Y = 50;
	private final JFrame frame;


	ArrayList<Piece> pieces;
	private int[][] chessLog;

	public int getINIT_X() {
		return INIT_X;
	}

	public int getINIT_Y() {
		return INIT_Y;
	}

	public int getWidget() {
		return widget;
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

	public ChessBoard(int widget, int height , JFrame frame) {
		this.widget = widget;
		this.height = height;
		this.frame = frame;
		chessLog = new int[height + 1][widget + 1];
		pieces = new ArrayList<>();
		addMouseListener(this);
	}

	@Override
	public void paint(Graphics g) {
		// 调用父类的paint方法，以进行默认的绘制
		super.paint(g);
		// 绘制棋盘背景
		g.setColor(new Color(210,105,30));
		g.fillRect(INIT_X,  INIT_Y, widget, height);
		// 绘制棋盘线条
		g.setColor(Color.BLACK);
		int endPoint = widget / INCREMENT;
		for (int i = 0; i <= endPoint; i++) {
			g.fillRect( INIT_X + i * INCREMENT,  INIT_Y, 2, widget);
			g.fillRect( INIT_X, INIT_Y + i * INCREMENT, widget, 2);
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
	}

	private void generatePieceAtCoordinate (int x, int y) {
		int endPoint = widget / INCREMENT;
		for (int i = 0; i <= endPoint; i++) {
			for (int j = 0; j <= endPoint; j++) {
				int r_x = INIT_X + j * INCREMENT;
				int r_y = INIT_Y + i * INCREMENT;
				// 以中心点做半径为r的圆
				int r = (int) Math.sqrt(Math.pow(r_x - x, 2) + Math.pow(r_y - y, 2));
				if (r <= 25) {
					int cur_x = r_x -  INIT_X;
					int cur_y = r_y -  INIT_Y;
					// 检查落点棋子是否已经存在
					if (chessLog[cur_y][cur_x] != Piece.EMPTY_PIECE) {
						return;
					}

					recordChessMoves(cur_x, cur_y, r_x, r_y);
					repaint();

					if (Referee.winLossVerifier(chessLog, cur_x, cur_y, INCREMENT)) {
						TerminationWindow terminationWindow = new TerminationWindow(frame, this);
					}
					Referee.switchPlayerTurn();
					return;
				}
			}
		}
	}
	public void recordChessMoves (int cur_x, int cur_y, int r_x , int r_y) {
		Color currentPieceColor = Referee.getCurrentPieceColor();
		chessLog[cur_y][cur_x] = (currentPieceColor == Color.BLACK ? Piece.BLACK_PIECE : Piece.WHITE_PIECE);
		pieces.add(new Piece(r_x - Piece.R, r_y - Piece.R, currentPieceColor));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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