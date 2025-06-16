package network.entity;


import java.io.Serializable;


public class Message implements Serializable {
    private static final long serialVersionUID = 1313716282061442688L;
    private final Type type;
    private final Object data;

    public enum Type {
        MOVE,   // 落子消息
        WIN,    // 胜利消息
        RESET,   // 重置棋盘
        QUIT,    // 退出游戏
        CONFIRM_RESET // 确认重置游戏
    }

    public static Message move(Object data) {
        return new Message(Type.MOVE, data);
    }

    public static Message win() {
        return new Message(Type.WIN, null);
    }

    public static Message quit() {
        return new Message(Type.QUIT, null);
    }

    public static Message confirm() {
        return new Message(Type.CONFIRM_RESET, null);
    }

    public Message(Type type, Object data) {
        this.type = type;
        this.data = data;
    }

    public static Message reset(RestartRequest restartRequest) {
        return new Message(Type.RESET, restartRequest);
    }

    public Type getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
