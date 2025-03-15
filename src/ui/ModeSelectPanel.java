package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelectionPanel extends JPanel {
    // 推荐使用接口回调，而非直接处理逻辑
    public interface ModeSelectionListener {
        void onCreateRoomClicked();
        void onJoinRoomClicked();
    }

    private final ModeSelectionListener listener;

    public ModeSelectionPanel(ModeSelectionListener listener) {
        this.listener = listener;
        initializeUI();
    }

    private void initializeUI() {
        // 使用 BorderLayout 管理布局
        setLayout(new BorderLayout(10, 10)); // 设置组件间距
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 外边距

        // 创建标题
        JLabel header = createHeader();
        add(header, BorderLayout.NORTH);

        // 创建按钮面板
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.CENTER);
    }

    private JLabel createHeader() {
        JLabel header = new JLabel("联机对战");
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setFont(new Font("宋体", Font.BOLD, 30));
        return header;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // 按钮间距
        gbc.anchor = GridBagConstraints.CENTER;

        JButton createBtn = createButton("创建房间", e -> listener.onCreateRoomClicked());
        JButton joinBtn = createButton("加入房间", e -> listener.onJoinRoomClicked());

        // 添加按钮到面板
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(createBtn, gbc);

        gbc.gridy = 1;
        panel.add(joinBtn, gbc);

        return panel;
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("宋体", Font.PLAIN, 18));
        button.setPreferredSize(new Dimension(200, 50)); // 统一按钮大小
        button.addActionListener(listener);
        return button;
    }

    // 主方法测试
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("模式选择");
            ModeSelectionPanel panel = new ModeSelectionPanel(new ModeSelectionListener() {
                @Override
                public void onCreateRoomClicked() {
                    System.out.println("创建房间按钮被点击");
                }

                @Override
                public void onJoinRoomClicked() {
                    System.out.println("加入房间按钮被点击");
                }
            });
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.pack(); // 自动调整窗口大小
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
