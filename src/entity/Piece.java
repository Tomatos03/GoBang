package entity;

import java.awt.*;

public class Piece extends Point {
	private final Color color;

	private Piece(Builder builder) {
		super(builder.x, builder.y);
		this.color = builder.color;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Piece)) {
			return false;
		}
		Piece piece = (Piece) obj;
		return this.x == piece.x && this.y == piece.y && this.color.equals(piece.color);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private int x;
		private int y;
		private Color color = Color.BLACK; // 默认颜色

		public Builder setPoint(Point point) {
			this.x = point.x;
			this.y = point.y;
			return this;
		}

		public Builder setColor(Color color) {
			this.color = color;
			return this;
		}

		public Piece build() {
			return new Piece(this);
		}
    }
}
