package Chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;


public class ChessBoard extends JPanel implements MouseListener {
	private Color currentPlayer = Color.BLACK;

	private final int INIT_X = 50;
	private final int INIT_Y = 50;

	private final int END_GAME_PIECE_COUNT = 5;

	private final int ROWS = 15;    // 纵向网格数
	private final int COLUMNS = 15; // 横向网格数
	private final int CELL_SIZE = 50; // 单个格子长度和宽度
	private final int LINE_WIDTH = 6;

	private final int PIECE_R = (LINE_WIDTH + CELL_SIZE) / 2;
	private final int WIDTH = COLUMNS * (CELL_SIZE + LINE_WIDTH);
	private final int HEIGHT = ROWS * (CELL_SIZE + LINE_WIDTH);

	private final Color BACKGROUND_COLOR = new Color(210,105,30);
	private final Color LINE_COLOR = Color.BLACK;

	private Piece[][] record = new Piece[ROWS + 1][COLUMNS + 1];

	public ChessBoard() {
		addMouseListener(this);
	}

	@Override
	public void paint(Graphics g) {
		// 调用父类的paint方法，以进行默认的绘制
		super.paint(g);

		paintChessBoard(g);
		paintPieces(g);
	}

	private void paintPieces(Graphics g) {
		int n = record.length, m = record[0].length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				Point point = new Point(j, i);
				if(isPositionOccupied(point)) {
					Piece piece = calculateDrawPosition(record[i][j]);
                    g.setColor(piece.color);
					g.fillOval(piece.x, piece.y, 2 * PIECE_R, 2 * PIECE_R);
				}
			}
		}
	}

	private Piece calculateDrawPosition(Piece piece) {
		Piece res = new Piece(piece);
		res.x -= (LINE_WIDTH + CELL_SIZE) / 2;
		res.y -= (LINE_WIDTH + CELL_SIZE) / 2;
		return res;
	}

	private Piece arrayToPieceCoordinate(int x, int y) {
		x = INIT_X + x * (CELL_SIZE + LINE_WIDTH);
		y = INIT_Y + y * (CELL_SIZE + LINE_WIDTH);
		return new Piece(x, y);
	}

	private Piece arrayToPieceCoordinate(Point point) {
		return arrayToPieceCoordinate(point.x, point.y);
	}

	private void paintChessBoard(Graphics g) {
		drawBackground(g);
		drawVerticalLines(g);
		drawHorizontalLines(g);
	}

	private void drawBackground(Graphics g) {
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(INIT_X, INIT_Y, WIDTH, HEIGHT);
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
        int res = isVertical ? INIT_X + columnIndex * (CELL_SIZE + LINE_WIDTH) - LINE_WIDTH / 2 : INIT_X;
		return isEdge && !isVertical ? res - LINE_WIDTH / 2 : res;
    }

    // 计算水平线的起始 y 坐标
    private int calculateY(int rowIndex, boolean isEdge, boolean isHorizontal) {
		int res = isHorizontal ? INIT_Y + rowIndex * (CELL_SIZE + LINE_WIDTH) - LINE_WIDTH / 2 : INIT_Y;
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

	public void restartGame() {
		record = new Piece[ROWS + 1][COLUMNS + 1];
		repaint();
	}

	private boolean isValid(Point point) {
		if(Objects.isNull(point)) {
			return false;
		}
		return 0 <= point.x && point.x <= COLUMNS && 0 <= point.y && point.y <= ROWS;
	}

	private boolean isColorSame(Piece piece0, Piece piece1) {
		return piece0.color.equals(piece1.color);
	}

	private boolean isPositionOccupied(Point point) {
		if(Objects.isNull(point)) {
			return false;
		}
		return !Objects.isNull(record[point.y][point.x]);
	}

	private boolean isWinner(Point point) {
		Piece origin = record[point.y][point.x];
		final int[] dx = {-1, 1, 0, 0, 1, 1, -1, -1}, dy = {0, 0, -1, 1, 1, -1, 1, -1};
		int length = dx.length, count = 0;
		for(int i = 0; i < length; ++i) {
			Point point0 = new Point(point);
			while(isValid(point0) && isPositionOccupied(point0) && isColorSame(record[point0.y][point0.x], origin)) {
				++count;
				point0.move(point0.x + dx[i], point0.y + dy[i]);
			}
		}
		// 中心位置重复计算了7次
		count -= 7;
		return count >= END_GAME_PIECE_COUNT;
	}

	private void nextPlayerRound() {
		this.currentPlayer = currentPlayer == Color.BLACK ? Color.WHITE : Color.BLACK;
	}

	public Point findRecord(int x, int y) {
		for (int i = 0; i <= ROWS; i++) {
			for (int j = 0; j <= COLUMNS; j++) {
				Point point = arrayToPieceCoordinate(j, i);
				// 以中心点做半径为r的圆
				int r = (int)(Math.pow(point.x - x, 2) + Math.pow(point.y - y, 2));
				if (r <= PIECE_R * PIECE_R) {
					return new Point(j, i);
				}
			}
		}
		return null;
	}

	public boolean placePiece(Point point) {
		if (Objects.isNull(point) || isPositionOccupied(point)) {
			return false; // 位置被占用，无法落子
		}

		// 创建并放置棋子
		Piece piece = arrayToPieceCoordinate(point);
		piece.setColor(currentPlayer);
		recordPieceMove(piece, point);
		return true; // 落子成功
    }

	public void recordPieceMove (Piece piece, Point DropPoint) {
		record[DropPoint.y][DropPoint.x] = piece;
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Point point = findRecord(x, y);
		if(!placePiece(point)) {
			return;
		}
		if(isWinner(point)) {
			System.out.println("Player won!");
		}
		nextPlayerRound();
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