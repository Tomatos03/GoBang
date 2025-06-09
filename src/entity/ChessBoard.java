package entity;

import network.listeners.NetworkMessageListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class ChessBoard extends JPanel implements MouseListener {
	// 棋盘起始坐标(左上角)
	private static final int START_X = 50;
	private static final int START_Y = 50;

	// 棋盘格子和线宽
	private static final int CELL_SIZE = 50; // 单个格子边长
	private static final int LINE_WIDTH = 6; // 线宽

	// 棋盘行列数
	private static final int ROWS = 15;
	private static final int COLUMNS = 15;

	// 连珠胜利所需棋子数
	private static final int END_GAME_PIECE_COUNT = 5;

	// 点与点之间的间距
	private static final int POINT_GAP = CELL_SIZE + LINE_WIDTH;

	// 棋子半径
	private static final int PIECE_R = POINT_GAP / 2;

	// 棋盘宽高
	private static final int WIDTH = COLUMNS * POINT_GAP;
	private static final int HEIGHT = ROWS * POINT_GAP;

	// 棋盘终点坐标(右下角)
	private static final int END_X = START_X + WIDTH;
	private static final int END_Y = START_Y + HEIGHT;

	// 棋盘背景色（仿木色）
	private static final Color BOARD_BACKGROUND_COLOR = new Color(227, 116, 36);
	// 棋盘线条颜色（黑色）
	private static final Color LINE_COLOR = Color.BLACK;
	// 当前回合能够行动的棋子颜色
	private final Player player;
	// 棋子落子历史记录，用于悔棋、重放等功能
	private final List<Piece> moveHistory = new ArrayList<>();
	// 处理网络消息的监听器
	private NetworkMessageListener networkMessageListener;

	private boolean gameOver = false;

	public ChessBoard(Player player) {
		this.player = player;
        addMouseListener(this);
	}
	public void addListener(NetworkMessageListener networkMessageListener) {
		this.networkMessageListener = networkMessageListener;
	}

	@Override
	public void paint(Graphics g) {
		// 调用父类的paint方法，以进行默认的绘制
		super.paint(g);

		paintChessBoard(g);
		paintPieces(g);
	}

	private void drawSinglePiece(Graphics g, Piece piece) {
		int x = piece.x - PIECE_R, y = piece.y - PIECE_R;
		g.setColor(piece.getColor());
		g.fillOval(x, y, 2 * PIECE_R, 2 * PIECE_R);
	}

	private void paintPieces(Graphics g) {
		for (Piece piece : moveHistory) {
			drawSinglePiece(g, piece);
		}
	}

	private void paintChessBoard(Graphics g) {
		drawBackground(g);
		drawVerticalLines(g);
		drawHorizontalLines(g);
	}

	private void drawBackground(Graphics g) {
		g.setColor(BOARD_BACKGROUND_COLOR);
		g.fillRect(START_X, START_Y, WIDTH, HEIGHT);
	}

	private void drawVerticalLines(Graphics g) {
		g.setColor(LINE_COLOR);
		for (int i = 0; i <= COLUMNS; i++) {
			boolean isEdge = (i == 0 || i == COLUMNS);
			int x0 = calculateX(i, isEdge, true);
			int y0 = calculateY(i, isEdge, false);
			int lineHeight = calculateHeight(isEdge);
			g.fillRect(x0, y0, LINE_WIDTH, lineHeight);
		}
	}

	private void drawHorizontalLines(Graphics g) {
		g.setColor(LINE_COLOR);
		for (int i = 0; i <= ROWS; i++) {
			boolean isEdge = (i == 0 || i == ROWS);
			int x0 = calculateX(i, isEdge, false);
			int y0 = calculateY(i, isEdge, true);
			int lineWidth = calculateWidth(isEdge);
			int lineHeight = LINE_WIDTH;
			g.fillRect(x0, y0, lineWidth, lineHeight);
		}
	}

	// 计算垂直线的起始 x 坐标
	private int calculateX(int columnIndex, boolean isEdge, boolean isVertical) {
		int res = isVertical ? START_X + columnIndex * (CELL_SIZE + LINE_WIDTH) - LINE_WIDTH / 2 : START_X;
		return isEdge && !isVertical ? res - LINE_WIDTH / 2 : res;
	}

	// 计算水平线的起始 y 坐标
	private int calculateY(int rowIndex, boolean isEdge, boolean isHorizontal) {
		int res = isHorizontal ? START_Y + rowIndex * (CELL_SIZE + LINE_WIDTH) - LINE_WIDTH / 2 : START_Y;
		return isEdge && !isHorizontal ? res - LINE_WIDTH / 2 : res;
	}

	// 计算边框线的额外高度（垂直线）
	private int calculateHeight(boolean isEdge) {
		return isEdge ? HEIGHT + LINE_WIDTH : HEIGHT;
	}

	// 计算边框线的额外宽度（水平线）
	private int calculateWidth(boolean isEdge) {
		return isEdge ? WIDTH + LINE_WIDTH : WIDTH;
	}

	Point pixelToBoardPoint(int x, int y) {
		for (int i = 0; i <= ROWS; i++) {
			for (int j = 0; j <= COLUMNS; j++) {
				int x0 = START_X + i * (CELL_SIZE + LINE_WIDTH);
				int y0 = START_Y + j * (CELL_SIZE + LINE_WIDTH);

				// 以中心点做半径为r的圆
				int rr = (int)(Math.pow(x0 - x, 2) + Math.pow(y0 - y, 2));
				if (rr <= PIECE_R * PIECE_R) {
					return new Point(x0, y0);
				}
			}
		}
		return null;
	}

	private boolean isGameEnd(Piece piece) {
		final int[] dx = {-1, 1, 0, 0, 1, 1, -1, -1}, dy = {0, 0, -1, 1, 1, -1, 1, -1};
		int length = dx.length;

        for(int i = 0; i < length; ++i) {
			Piece nextPiece = Piece.builder()
					.setColor(piece.getColor())
					.setPoint(piece)
					.build();

			int samePieceCount = 0;
			while(isInChessBoardRange(nextPiece) && isExistPiece(nextPiece)) {
				nextPiece.x += dx[i] * POINT_GAP;
				nextPiece.y += dy[i] * POINT_GAP;
				++samePieceCount;
			}
			if(samePieceCount >= END_GAME_PIECE_COUNT) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查棋子是否在棋盘范围内
	 *
	 * @param piece 棋子对象
	 * @return 如果棋子在棋盘范围内，返回 true；否则返回 false
	 */
	public boolean isInChessBoardRange(Piece piece) {
		int x = piece.x, y = piece.y;
		return START_X <= x && x <= END_X && START_Y <= y && y <= END_Y;
	}

	/**
	 * 检查棋盘上某个位置是否被占用
	 *
	 * @param point 棋盘上的点
	 * @return 如果该位置已经有棋子，返回 true；否则返回 false
	 */
	public boolean isOccupied(Point point) {
		for (Piece p : moveHistory) {
			if (p.x == point.x && p.y == point.y) {
				return true; // 棋子已存在
			}
		}
		return false;
	}

	/**
	 * 检查棋盘上是否存在某种棋子
	 *
	 * @param piece 棋子对象
	 * @return 如果该棋子已经存在于棋盘上，返回 true；否则返回 false
	 */
	public boolean isExistPiece(Piece piece) {
		return moveHistory.contains(piece);
	}

	public boolean placePiece(Piece piece) {
		moveHistory.add(piece);
		repaint();
		return isGameEnd(piece);
	}

	public void nextTurn() {
		player.setMyTurn(!player.isMyTurn());
	}

	public void resetBoard() {
		gameOver = false;
		moveHistory.clear();
		// 黑色先手
		player.setMyTurn(player.getPieceColor() == Color.BLACK);
		repaint();
	}

	public void setGameOver() {
		this.gameOver = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// 如果不是当前玩家的回合，则不处理点击事件
		if (!player.isMyTurn() || gameOver) {
			return;
		}

		// 将鼠标点击位置转换为棋盘上的点
		Point point = pixelToBoardPoint(e.getX(), e.getY());
		if (point == null) {
			return;
		}

		Piece piece = Piece.builder()
						   .setColor(player.getPieceColor())
						   .setPoint(point)
						   .build();
		networkMessageListener.onPiecePlaced(piece);
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