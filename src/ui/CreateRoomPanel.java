package ui;

import contants.NetWorkConstant;
import entity.Player;
import ui.impl.GameDialogHandle;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.Objects;

public class CreateRoomPanel extends JPanel {
    private final MainFrame mainUI;
    private Socket socket;
    private Thread senderThread;

    public CreateRoomPanel(MainFrame mainUI){
        this.mainUI = mainUI;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(Box.createHorizontalGlue());

        JLabel prompt = createJLabel();
        horizontalBox.add(prompt);

        horizontalBox.add(Box.createHorizontalGlue());

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalGlue());
        verticalBox.add(horizontalBox);
        verticalBox.add(Box.createVerticalGlue());

        add(verticalBox);

        Thread waiter = new Thread(this::waitJoinRoom);
        waiter.start();

        senderThread = new Thread(this::sendLocalIP);
        senderThread.start();

    }

    private void waitJoinRoom() {
        try {
            socket = new ServerSocket(NetWorkConstant.DEFAULT_PORT).accept();

            GameDialogHandle gameDialogHandle = new GameDialogHandle();
            Player player = new Player(Color.BLACK, true);
            LANCombatPanel.startGame(gameDialogHandle, player, socket, mainUI);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendLocalIP() {
        try (DatagramSocket sender = new DatagramSocket()){
            InetAddress address = InetAddress.getByName(NetWorkConstant.BROADCAST_ADDRESS);
            String message = " ";
            DatagramPacket messagePacket = new DatagramPacket(message.getBytes(),
                                                              message.length(), address,
                                                              NetWorkConstant.DEFAULT_PORT);
            while (Objects.isNull(socket) || !socket.isConnected()) {
                sender.send(messagePacket);
            }
            Thread.sleep(2000);
            sender.send(messagePacket);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private JLabel createJLabel(){
        JLabel prompt = new JLabel("正在等待其他人连接...");
        prompt.setFont(new Font("宋体", Font.BOLD, 35));
        return prompt;
    }
}
