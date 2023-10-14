package SenderReceiver;

import Chess.ChessBoard;
import Chess.Referee;

import java.awt.*;
import java.io.*;
import java.net.Socket;

public class FirstHead extends Thread {
	Socket socket;
	final ChessBoard chessBoard;

	public FirstHead(Socket socket) {
		this.socket = socket;
		this.chessBoard = ChessBoard.init();
		chessBoard.setControl(true);
	}

	// 225 325

	@Override
	public void run() {
		BufferedWriter out = null;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			int pre_x = chessBoard.getCurrentFallingPositionX();
			int pre_y = chessBoard.getCurrentFallingPositionY();
			while (true) {
				if (Referee.getCurrentPieceColor() == Color.BLACK) {
					synchronized (chessBoard) {
						if (pre_x == chessBoard.getCurrentFallingPositionX() && pre_y == chessBoard.getCurrentFallingPositionY()) {
							continue;
						}

						String data = chessBoard.getCurrentFallingPosition();
						out.write(data + '\n');
						out.flush();

						chessBoard.setControl(!chessBoard.isControl());
						pre_x = chessBoard.getCurrentFallingPositionX();
						pre_y = chessBoard.getCurrentFallingPositionY();
					}
				} else {
					String[] xy = in.readLine().split(" ");
					int cur_x = Integer.parseInt(xy[0]);
					int cur_y = Integer.parseInt(xy[1]);
					synchronized (chessBoard) {
						int offset_x = cur_x - chessBoard.getINIT_X() - chessBoard.getBorders();
						int offset_y = cur_y - chessBoard.getINIT_Y() - chessBoard.getBorders();

						chessBoard.recordChessMoves(cur_x, cur_y, offset_x, offset_y);
						chessBoard.setControl(!chessBoard.isControl());

						pre_x = cur_x;
						pre_y = cur_y;
						Referee.switchPlayerTurn();

						chessBoard.setCurrentFallingPositionY(pre_y);
						chessBoard.setCurrentFallingPositionX(pre_x);
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
