package ui;

import contants.NetWorkConstant;
import entity.ChessBoard;
import entity.Player;
import network.MessageSender;
import network.NetworkHandler;
import network.entity.Message;
import ui.impl.GameDialogHandle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Objects;

import static ui.LANCombatPanel.startGame;

public class JoinRoomPanel extends JPanel {
    private JList<String> roomList;
    private DefaultListModel<String> listModel;
    private JButton joinButton;
    private MainFrame mainUI;
    private Socket client;
    private Thread receiverThread;

    public JoinRoomPanel(MainFrame frame) {
        this.mainUI = frame;
        // 初始化布局
        setLayout(new BorderLayout());
        int border = 25;
        setBorder(BorderFactory.createEmptyBorder(border, border, border, border));

        // 创建列表
        listModel = new DefaultListModel<>();
        JList<String> roomList = createJList(listModel);
        // 添加滚动条
        JScrollPane scrollPane = new JScrollPane(roomList);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // 创建按钮
        joinButton = new JButton("加入房间");
        joinButton.setEnabled(false);

        // 将组件添加到布局
        add(scrollPane, BorderLayout.CENTER);
        add(joinButton, BorderLayout.PAGE_END);

        // 设置按钮的最小高度
        joinButton.setPreferredSize(new Dimension(Short.MAX_VALUE, 50));

        joinButton.addActionListener(this::joinRoom);

        receiverThread = new Thread(this::receiveIP);
        receiverThread.start();
    }


    private void joinRoom(ActionEvent actionEvent) {
        String targetIP = roomList.getSelectedValue();
        if(Objects.isNull(targetIP)) {
            return;
        }

        try {
            client = new Socket(targetIP, NetWorkConstant.DEFAULT_PORT);

            GameDialogHandle gameDialogHandle = new GameDialogHandle();
            Player player = new Player(Color.WHITE, false);
            LANCombatPanel.startGame(gameDialogHandle, player, client, mainUI);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void receiveIP() {
        try (DatagramSocket receiver = new DatagramSocket(NetWorkConstant.DEFAULT_PORT)) {
            byte[] buf = new byte[1024];
            while (Objects.isNull(client) || !client.isConnected() || client.isClosed()) {
                DatagramPacket messagePacket = new DatagramPacket(buf, buf.length);
                receiver.receive(messagePacket);
                String ip = messagePacket.getAddress()
                                         .getHostAddress();
                if (!listModel.contains(ip)) {
                    addRoom(ip);
                }
            }
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }
    }


    private JList<String> createJList(ListModel<String> model) {
        roomList = new JList<>(model);
        roomList.setVisibleRowCount(10);
        roomList.setFixedCellHeight(50);
        roomList.setFont(new Font("宋体", Font.BOLD, 20));
        return roomList;
    }
    public void addRoom(String roomName) {
        listModel.addElement(roomName);
        joinButton.setEnabled(true);
    }
}
