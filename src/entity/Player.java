package entity;

import java.awt.*;

/**
 * @Description: TODO
 * @Author: Tomatos
 * @Date: 2025/6/7 14:14
 */
public class Player {
    private final Color pieceColor;
    private boolean myTurn;

    public Player(Color pieceColor, boolean isFirst) {
        this.pieceColor = pieceColor;
        this.myTurn = isFirst;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public Color getPieceColor() {
        return pieceColor;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }
}
