import ui.MainFrame;

import javax.swing.*;

public class ApplicationBoot {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainFrame::new);
	}
}