package entity;

import java.awt.*;

public class Piece extends Point {
	public Color color;

	public Piece(int x, int y, Color color) {
		super(x, y);
		this.color = color;
	}

	public Piece(Point point, Color color) {
		super(point);
		this.color = color;
	}

	public Piece(int x, int y) {
		super(x, y);
	}

	public Piece(Piece piece) {
		super(piece);
		this.color = piece.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
