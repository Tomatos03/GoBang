package network;

import entity.LanChessBoard;
import entity.Piece;
import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkHandler extends Thread{
    private final Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final LanChessBoard chessBoard;

    public NetworkHandler(Socket socket, LanChessBoard chessBoard) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("IN OUT 初始异常");
        }
        this.chessBoard = chessBoard;
        MainFrame frame = (MainFrame) chessBoard.getFrame();
        frame.removeWindowListener(frame.getOriginAdapter());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeResources();
                System.exit(0);
            }
        });
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            if (!chessBoard.isMyTurn()) {
                Message message = receiveMessage();
                if (message == null) {
                    showGameOverDialog("对方退出游戏，你赢了！");
                    break;
                }
                handleMessage(message);
                chessBoard.setPlacePiece(false);
                chessBoard.setMyTurn(true);
                if (chessBoard.isGameOver()) {
                    showGameOverDialog("游戏结束，你输了");
                    break;
                }
            } else {
                if (!chessBoard.isPlacePiece()) {
                    continue;
                }
                Piece piece = chessBoard.getLastMoviePiece();
                Point dropPoint = chessBoard.getLastMovePoint();
                final int MESSAGE_TYPE = chessBoard.isGameOver() ? Message.TYPE_WIN : Message.TYPE_MOVE;
                Message message = new Message(piece, dropPoint, MESSAGE_TYPE);
                sendMessage(message);
                chessBoard.setMyTurn(false);
                if (chessBoard.isGameOver()) {
                    showGameOverDialog("游戏结束，你赢了");
                    break;
                }
            }
        }
        closeResources();
    }

    private void handleMessage(Message message) {
        switch (message.getType()) {
            case Message.TYPE_MOVE:
                chessBoard.recordPieceMove(message.getPiece(), message.getDropPoint());
                break;
            case Message.TYPE_WIN:
                chessBoard.recordPieceMove(message.getPiece(), message.getDropPoint());
                chessBoard.setGameOver(true);
                break;
            case Message.TYPE_RESET:
                chessBoard.restartGame();
                break;
        }
    }

    public void sendMessage(Message message) {
        try{
            out.writeObject(message);
            out.flush();
        } catch (IOException ignored) {
        }
    }

    public Message receiveMessage() {
        Message message = null;
        try {
            message = (Message) in.readObject();
        } catch (IOException | ClassNotFoundException ignored) {
        }
        return message;
    }

    private void showGameOverDialog(String content) {
        String title = "游戏结束";
        SwingUtilities.invokeLater(() ->
                JOptionPane.showConfirmDialog(
                null,
                content,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE));
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("关闭资源时出错: " + e.getMessage());
        }
    }
}
