package network;

import network.entity.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @Description: TODO
 * @Author: Tomatos
 * @Date: 2025/6/5 09:20
 */

public class MessageSender {
    private final ObjectOutputStream out;

    public MessageSender(ObjectOutputStream out) {
        this.out = out;
    }

    public void sendMessage(Message message)  {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException ignored) {
        }
    }
}
