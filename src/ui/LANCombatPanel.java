package ui;

import entity.ChessBoard;
import entity.Player;
import network.NetworkHandler;
import network.entity.Message;
import ui.impl.GameDialogHandle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class LANCombatPanel extends JPanel {

    private final MainFrame frame;

    public LANCombatPanel(MainFrame frame) {
        this.frame = frame;
        initializeUI();
    }

    private Box wrapInHorizontalCenterContainer(Component component) {
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(Box.createHorizontalGlue());
        horizontalBox.add(component);
        horizontalBox.add(Box.createHorizontalGlue());
        return horizontalBox;
    }

    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalGlue());

        verticalBox.add(wrapInHorizontalCenterContainer(createHeader()));

        JButton createButton = createButton("创建房间");
        verticalBox.add(Box.createVerticalStrut(20));
        verticalBox.add(wrapInHorizontalCenterContainer(createButton));

        JButton joinButton = createButton("加入房间");
        verticalBox.add(Box.createVerticalStrut(25));
        verticalBox.add(wrapInHorizontalCenterContainer(joinButton));

        verticalBox.add(Box.createVerticalGlue());
        add(verticalBox);

        createButton.addActionListener(e -> frame.updatePanel(new CreateRoomPanel(frame)));
        joinButton.addActionListener(e -> frame.updatePanel(new JoinRoomPanel(frame)));
    }

    public static void startGame(GameDialogHandle gameDialogHandle, Player player, Socket socket,
                                 MainFrame mainUI) throws IOException {
        ChessBoard chessBoard = new ChessBoard(player);
        NetworkHandler networkHandler = new NetworkHandler(socket, chessBoard, gameDialogHandle);

        chessBoard.addListener(networkHandler);
        mainUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Message quitMsg = Message.quit();
                networkHandler.getSender().sendMessage(quitMsg);
                System.exit(0);
            }
        });

        mainUI.updatePanel(chessBoard);
        networkHandler.start();
    }

    private JLabel createHeader() {
        JLabel header = new JLabel("局域网对战");
        header.setFont(new Font("宋体", Font.BOLD, 35));
        return header;
    }

    private JButton createButton(String title) {
        JButton button = new JButton(title);
        Dimension preferredSize = new Dimension(200, 50);
        button.setPreferredSize(preferredSize);
        button.setMinimumSize(preferredSize);
        button.setMaximumSize(preferredSize);
        return button;
    }
}
