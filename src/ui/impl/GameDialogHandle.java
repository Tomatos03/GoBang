package ui.impl;

import ui.IGameDialog;

import javax.swing.*;
import java.awt.*;

/**
 * @Description: TODO
 * @Author: Tomatos
 * @Date: 2025/6/7 13:03
 */
public class GameDialogHandle implements IGameDialog {
    private static final String GAME_OVER_TITLE = "游戏结束";
    private static final String GAME_END_OPTION_RESTART = "再来一局";
    private static final String GAME_END_OPTION_CANCEL = "取消";

    // 等待对方确认再来一局的弹窗实例
    private JDialog waitingDialog = null;

    // 游戏结束弹窗实例
    private JDialog gameEndDialog = null;

    /**
     * 游戏结束弹窗，询问用户是否再来一局（可手动关闭）
     *
     * @param message 结果信息（如“你赢了！”）
     * @param parent  父组件
     * @return true=再来一局，false=退出
     */
    public void gameEnd(String message, Component parent, Runnable onRestart, Runnable onCancel) {
        JButton restartButton = new JButton(GAME_END_OPTION_RESTART);
        JButton cancelButton = new JButton(GAME_END_OPTION_CANCEL);

        JOptionPane optionPane = new JOptionPane(
                message + "\n是否再来一局？",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[]{restartButton, cancelButton},
                restartButton
        );

        gameEndDialog = optionPane.createDialog(parent, GAME_OVER_TITLE);
        gameEndDialog.setModal(false);
        gameEndDialog.setVisible(true);

        restartButton.addActionListener(e -> {
            gameEndDialog.dispose();
            gameEndDialog = null;
            if (onRestart != null) onRestart.run();
        });

        cancelButton.addActionListener(e -> {
            gameEndDialog.dispose();
            gameEndDialog = null;
            if (onCancel != null) onCancel.run();
        });
    }

    /**
     * 手动关闭游戏结束弹窗
     */
    public void closeGameEndDialog() {
        if (gameEndDialog != null) {
            gameEndDialog.setVisible(false);
            gameEndDialog.dispose();
            gameEndDialog = null;
        }
    }

    /**
     * 对手退出，仅弹出“确定”按钮
     * @param message 提示信息（如“对手已退出，你获胜！”）
     * @param parent  父组件
     */
    public void showOpponentQuitDialog(String message, Component parent) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                GAME_OVER_TITLE,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * 询问用户是否确认重开一局
     * @param content 提示信息
     * @return true=确认重开，false=取消
     */
    @Override
    public boolean confirmRestart(String content, Component parent) {
        int result = JOptionPane.showConfirmDialog(
                null,
                content,
                "确认重开",
                JOptionPane.YES_NO_OPTION
        );
        return result == JOptionPane.YES_OPTION;
    }


    /**
     * 显示等待对方确认是否再来一局的弹窗（可手动关闭）
     * @param parent 父组件
     */
    public void showWaitingForOpponentRestart(Component parent) {
        if (waitingDialog != null) {
            return;
        }
        JOptionPane optionPane = new JOptionPane(
                "已发送再来一局请求，等待对方确认...",
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[]{}, // 不显示按钮
                null
        );
        waitingDialog = optionPane.createDialog(parent, "请稍候");
        waitingDialog.setModal(false);
        waitingDialog.setVisible(true);
    }

    /**
     * 关闭等待对方确认再来一局的弹窗
     */
    public void closeWaitingForOpponentRestart() {
        if (waitingDialog != null) {
            waitingDialog.dispose();
            waitingDialog = null;
        }
    }
}
