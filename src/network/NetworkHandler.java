package network;

import com.sun.istack.internal.NotNull;
import entity.ChessBoard;
import entity.Piece;
import network.entity.Message;
import network.listeners.NetworkMessageListener;
import ui.impl.GameDialogHandle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkHandler implements NetworkMessageListener {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final MessageSender sender;
    private final MessageReceiver receiver;
    private final ChessBoard chessBoard;
    private final GameDialogHandle gameDialogHandle;

    public NetworkHandler(Socket socket, ChessBoard chessBoard, GameDialogHandle gameDialogHandle) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.gameDialogHandle = gameDialogHandle;
        this.sender = new MessageSender(out);
        this.receiver = new MessageReceiver(in, this);
        this.chessBoard = chessBoard;
    }

    public MessageSender getSender() {
        return sender;
    }

    public void start() {
        receiver.start();
    }

    @Override
    public void onPiecePlaced(@NotNull Piece piece) {
        // 检查棋子是否被占用
        if (chessBoard.isOccupied(piece)) {
            return;
        }

        sender.sendMessage(Message.move(piece));
        chessBoard.nextTurn();
        // 棋子放置后检查游戏是否结束
        boolean isGameEnd = chessBoard.placePiece(piece);
        if (!isGameEnd) {
            return;
        }
        // 如果游戏结束，通知对方
        chessBoard.setGameOver();
        sender.sendMessage(Message.win());
        gameDialogHandle.gameEnd(
                "Game Over You Win!",
                chessBoard,
                this::waitRestartGame,
                this::playerQuit);
    }

    @Override
    public void onOpponentQuit() {
        gameDialogHandle.closeWaitingForOpponentRestart();
        gameDialogHandle.showOpponentQuitDialog("Opponent Quit!", chessBoard);
        closeResources();
    }

    @Override
    public void onOpponentWin() {
        chessBoard.setGameOver();
        gameDialogHandle.gameEnd("Game Over! You Lose!", chessBoard, this::waitRestartGame, this::playerQuit);
    }

    private void waitRestartGame() {
        if (socket.isClosed()) {
            return;
        }
        sender.sendMessage(Message.reset());
        gameDialogHandle.showWaitingForOpponentRestart(chessBoard);
    }

    @Override
    public void onQueryResetGame() {
        // 防止对方在游戏结束后请求重置游戏时，游戏结束弹窗仍然存在
        gameDialogHandle.closeGameEndDialog();

        // 弹出确认重置游戏的对话框
        boolean isResetGame = gameDialogHandle.confirmRestart("对方请求重置游戏，是否同意？", chessBoard);
        if (!isResetGame) {
            playerQuit();
            return;
        }

        // 如果同意重置游戏，发送确认消息给对方
        if (!socket.isClosed()) {
            sender.sendMessage(Message.confirm());
            chessBoard.resetBoard();
        }
    }

    private void playerQuit() {
        sender.sendMessage(Message.quit());
        closeResources();
    }

    @Override
    public void onOpponentPlacePiece(Piece piece) {
        chessBoard.placePiece(piece);
        chessBoard.nextTurn();
    }

    @Override
    public void onResetGameConfirmed() {
        gameDialogHandle.closeWaitingForOpponentRestart();
        chessBoard.resetBoard();
    }

    public void closeResources() {
        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException ignored) {
        }
    }
}
