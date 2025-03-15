package ui;

import entity.LanChessBoard;
import network.NetworkHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.Objects;

public class CreateRoomPanel extends JPanel {
    private final int DEFAULT_PORT = 4999;
//    private final int CHECK_PORT = 4998;
    private final MainFrame mainUI;
    private Socket server;
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
            server = new ServerSocket(DEFAULT_PORT).accept();

            LanChessBoard chessBoard = new LanChessBoard(mainUI, Color.WHITE,true);
            mainUI.updatePanel(chessBoard);

            NetworkHandler networkHandler = new NetworkHandler(server, chessBoard);
            networkHandler.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendLocalIP() {
        try (DatagramSocket sender = new DatagramSocket()){
            InetAddress address = InetAddress.getByName("255.255.255.255");
            String message = " ";
            DatagramPacket messagePacket = new DatagramPacket(message.getBytes(), message.length(), address, DEFAULT_PORT);
            while (Objects.isNull(server) || !server.isConnected()) {
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
