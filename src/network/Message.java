package network;

import entity.Piece;

import java.awt.*;
import java.io.Serializable;

public class Message implements Serializable {
    public static final int TYPE_MOVE = 0; // 落子消息
    public static final int TYPE_WIN = 1;   // 胜利消息
    public static final int TYPE_RESET = 2; // 重置棋盘
    private int type;
    private Piece piece;
    private Point dropPoint;

    public Message(Piece piece, Point dropPoint, int typeMove) {
        this.piece = piece;
        this.type = typeMove;
        this.dropPoint = dropPoint;
    }

    public Message(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public Piece getPiece() {
        return piece;
    }

    public Point getDropPoint() {
        return dropPoint;
    }
}
