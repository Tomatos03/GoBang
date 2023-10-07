package ui;

import java.awt.*;

/**
 * @Description: TODO
 * @Author: Tomatos
 * @Date: 2025/6/7 12:56
 */
public interface IGameDialog {
    void gameEnd(String message, Component parent, Runnable onRestart, Runnable onCancel);

    boolean confirmRestart(String content, Component parent);
}
