package ui;

import javax.swing.*;
import java.awt.*;

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

        verticalBox.add(wrapInHorizontalCenterContainer(createHeader("局域网对战")));

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

    private JLabel createHeader(String title) {
        JLabel header = new JLabel(title);
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
