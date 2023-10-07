import java.awt.*;

public class Referee {
	static Color currentPieceColor = Color.BLACK;
	static int endCount = 5;

	public static Color getCurrentPieceColor() {
		return currentPieceColor;
	}

	public static void switchPlayerTurn() {
		currentPieceColor = currentPieceColor == Color.BLACK ? Color.WHITE : Color.BLACK;
	}

	private static boolean checkHorizontalAndVertical(int[][] chessLog, int x, int y , int increment) {
		int rowEndPoint = chessLog[0].length;
		int colEndPoint = chessLog.length;
		int blackPiece = 0;
		int whitePiece = 0;
		for (int i = x; i < rowEndPoint; i += increment) {
			Color currentPieceColor = chessLog[y][i] == Piece.BLACK_PIECE ? Color.BLACK : Color.WHITE;
			if (chessLog[y][i] == Piece.EMPTY_PIECE || currentPieceColor != getCurrentPieceColor()) {
				break;
			}

			if (chessLog[y][i] == Piece.BLACK_PIECE) {
				++blackPiece;
			} else {
				++whitePiece;
			}
		}

		if (blackPiece == endCount || whitePiece == endCount) {
			return true;
		}
		for (int i = x - increment; i >= 0; i -= increment) {
			Color currentPieceColor = chessLog[y][i] == Piece.BLACK_PIECE ? Color.BLACK : Color.WHITE;
			if (chessLog[y][i] == Piece.EMPTY_PIECE || currentPieceColor != getCurrentPieceColor()) {
				break;
			}

			if (chessLog[y][i] == Piece.BLACK_PIECE) {
				++blackPiece;
			} else {
				++whitePiece;
			}
		}

		if (blackPiece == endCount || whitePiece == endCount) {
			return true;
		}
		blackPiece = 0;
		whitePiece = 0;
		for (int i = y; i < colEndPoint; i += increment) {
			Color currentPieceColor = chessLog[i][x] == Piece.BLACK_PIECE ? Color.BLACK : Color.WHITE;
			if (chessLog[i][x] == Piece.EMPTY_PIECE || currentPieceColor != getCurrentPieceColor()) {
				break;
			}

			if (chessLog[i][x] == Piece.BLACK_PIECE) {
				++blackPiece;
			} else {
				++whitePiece;
			}
		}

		if (blackPiece == endCount || whitePiece == endCount) {
			return true;
		}
		for (int i = y - increment; i >= 0; i -= increment) {
			Color currentPieceColor = chessLog[i][x] == Piece.BLACK_PIECE ? Color.BLACK : Color.WHITE;
			if (chessLog[i][x] == Piece.EMPTY_PIECE || currentPieceColor != getCurrentPieceColor()) {
				break;
			}

			if (chessLog[i][x] == Piece.BLACK_PIECE) {
				++blackPiece;
			} else {
				++whitePiece;
			}
		}
		if (blackPiece == endCount || whitePiece == endCount) {
			return true;
		}
		return false;
	}


	public static boolean winLossVerifier (int[][] chessLog, int x, int y, int increment) {
		return checkHorizontalAndVertical(chessLog, x, y, increment) || checkDiagonal(chessLog, x, y, increment);
	}

	private static boolean checkDiagonal(int[][] chessLog, int x, int y , int increment) {
		int rowEndPoint = chessLog[0].length;
		int colEndPoint = chessLog.length;
		int blackPiece = 0;
		int whitePiece = 0;
		int cur_x = x - increment;
		int cur_y = y - increment;
		while (cur_x >= 0 && cur_y >= 0) {
			Color currentPieceColor = chessLog[cur_y][cur_x] == Piece.BLACK_PIECE ? Color.BLACK : Color.WHITE;
			if (chessLog[cur_y][cur_x] == Piece.EMPTY_PIECE || currentPieceColor != getCurrentPieceColor()) {
				break;
			}

			if (chessLog[cur_y][cur_x] == Piece.BLACK_PIECE) {
				++blackPiece;
			} else {
				++whitePiece;
			}
			cur_x -= increment;
			cur_y -= increment;
		}

		if (blackPiece == endCount || whitePiece == endCount) {
			return true;
		}
		cur_x = x;
		cur_y = y;
		while (cur_x < rowEndPoint && cur_y < colEndPoint) {
			Color currentPieceColor = chessLog[cur_y][cur_x] == Piece.BLACK_PIECE ? Color.BLACK : Color.WHITE;
			if (chessLog[cur_y][cur_x] == Piece.EMPTY_PIECE || currentPieceColor != getCurrentPieceColor()) {
				break;
			}

			if (chessLog[cur_y][cur_x] == Piece.BLACK_PIECE) {
				++blackPiece;
			} else {
				++whitePiece;
			}
			cur_x += increment;
			cur_y += increment;
		}
		if (blackPiece == endCount || whitePiece == endCount) {
			return true;
		}

		blackPiece = 0;
		whitePiece = 0;
		cur_x = x;
		cur_y = y;
		while (cur_x < rowEndPoint && cur_y >= 0) {
			Color currentPieceColor = chessLog[cur_y][cur_x] == Piece.BLACK_PIECE ? Color.BLACK : Color.WHITE;
			if (chessLog[cur_y][cur_x] == Piece.EMPTY_PIECE || currentPieceColor != getCurrentPieceColor()) {
				break;
			}

			if (chessLog[cur_y][cur_x] == Piece.BLACK_PIECE) {
				++blackPiece;
			} else {
				++whitePiece;
			}

			cur_y -= increment;
			cur_x += increment;
		}

		cur_x = x - increment;
		cur_y = y + increment;
		while (cur_x >= 0 && cur_y < colEndPoint) {
			Color currentPieceColor = chessLog[cur_y][cur_x] == Piece.BLACK_PIECE ? Color.BLACK : Color.WHITE;
			if (chessLog[cur_y][cur_x] == Piece.EMPTY_PIECE || currentPieceColor != getCurrentPieceColor()) {
				break;
			}

			if (chessLog[cur_y][cur_x] == Piece.BLACK_PIECE) {
				++blackPiece;
			} else {
				++whitePiece;
			}

			cur_x -= increment;
			cur_y += increment;
		}

		if (blackPiece == endCount || whitePiece == endCount) {
			return true;
		}

		return false;
	}
}
