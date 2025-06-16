package network;

import entity.Piece;
import network.entity.RestartRequest;
import network.listeners.NetworkMessageListener;
import network.entity.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @Description: TODO
 * @Author: Tomatos
 * @Date: 2025/6/5 09:20
 */

public class MessageReceiver extends Thread {
    private final ObjectInputStream in;
    private final NetworkMessageListener listener;

    public MessageReceiver(ObjectInputStream in, NetworkMessageListener listener) {
        this.in = in;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = (Message) in.readObject();
                switch (message.getType()) {
                    case WIN:
                        listener.onOpponentWin();
                        break;
                    case QUIT:
                        listener.onOpponentQuit();
                        break;
                    case RESET:
                        listener.onQueryResetGame((RestartRequest) message.getData());
                        break;
                    case MOVE:
                        listener.onOpponentPlacePiece((Piece) message.getData());
                        break;
                    case CONFIRM_RESET:
                        listener.onResetGameConfirmed();
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException ignored) {
            System.out.println(ignored.getMessage());
        }
    }
}
