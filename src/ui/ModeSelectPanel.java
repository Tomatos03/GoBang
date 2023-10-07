package ui;

import entity.ChessBoard;
import ui.impl.GameDialogHandle;

import javax.swing.*;
import java.awt.*;

public class ModeSelectPanel extends JPanel {
    private final MainFrame frame;

    public ModeSelectPanel(MainFrame frame) {
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
        verticalBox.add(Box.createVerticalStrut(20));

        JButton testButton = createButton("测试");
        verticalBox.add(wrapInHorizontalCenterContainer(testButton));

        verticalBox.add(Box.createVerticalStrut(25));

        JButton lanCombatButton = createButton("局域网对战");
        verticalBox.add(wrapInHorizontalCenterContainer(lanCombatButton));

        verticalBox.add(Box.createVerticalGlue());
        add(verticalBox);


        lanCombatButton.addActionListener(e -> frame.updatePanel(new LANCombatPanel(frame)));
    }

    private JLabel createHeader() {
        JLabel header = new JLabel("五子棋游戏");
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
