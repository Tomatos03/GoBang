package Chess;

import java.awt.*;

public class Piece {
	private Color color;
	private int x;
	private int y;
	static final int EMPTY_PIECE = 0;

	static final int WHITE_PIECE = 1;
	static final int BLACK_PIECE = 2;

	static final int R = 25;
	public Piece(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
