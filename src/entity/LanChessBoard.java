package entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class LanChessBoard extends ChessBoard{
    private boolean isMyTurn;
    private boolean isGameOver = false;
    private boolean isPlacePiece = false;
    private final Color myPieceColor;
    private Piece lastMoviePiece;
    private Point lastMovePoint;

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public LanChessBoard(JFrame frame, Color myPieceColor, boolean isMyTurn) {
        super(frame);
        this.myPieceColor = myPieceColor;
        this.isMyTurn = isMyTurn;
    }

    public Point getLastMovePoint() {
        return lastMovePoint;
    }

    public boolean isPlacePiece() {
        return isPlacePiece;
    }

    public void setPlacePiece(boolean placePiece) {
        isPlacePiece = placePiece;
    }

    public Piece getLastMoviePiece() {
        return lastMoviePiece;
    }


    public boolean isMyTurn() {
        return isMyTurn;
    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    boolean placePiece(Point point) {
        if (Objects.isNull(point) || isPositionOccupied(point)) {
            return false;
        }

        // 创建并放置棋子
        Piece piece = arrayToPieceCoordinate(point);
        piece.setColor(myPieceColor);
        recordPieceMove(piece, point);

        lastMoviePiece = piece;
        lastMovePoint = point;
        return true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!isMyTurn || isGameOver) {
            return;
        }
        Point point = findRecord(e.getX(), e.getY());
        if(!placePiece(point)) {
            return;
        }
        isGameOver = isWinner(point);
        isPlacePiece = true;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}
