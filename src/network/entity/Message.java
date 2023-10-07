package network.entity;


import entity.Piece;

import java.io.Serializable;


public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Type type;
    private final Piece piece;

    public enum Type {
        MOVE,   // 落子消息
        WIN,    // 胜利消息
        RESET,   // 重置棋盘
        QUIT,    // 退出游戏
        CONFIRM_RESET // 确认重置游戏
    }

    public static Message move(Piece piece) {
        return new Message(Type.MOVE, piece);
    }

    public static Message win() {
        return new Message(Type.WIN, null);
    }

    public static Message reset() {
        return new Message(Type.RESET, null);
    }

    public static Message quit() {
        return new Message(Type.QUIT, null);
    }

    public static Message confirm() {
        return new Message(Type.CONFIRM_RESET, null);
    }

    public Message(Type type, Piece piece) {
        this.type = type;
        this.piece = piece;
    }

    public Type getType() {
        return type;
    }

    public Piece getPiece() {
        return piece;
    }
}