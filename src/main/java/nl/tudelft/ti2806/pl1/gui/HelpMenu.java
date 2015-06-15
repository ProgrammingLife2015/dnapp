package nl.tudelft.ti2806.pl1.gui;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class HelpMenu extends JDialog {

	public HelpMenu(final Window window) {
		super(window);
		this.setTitle("Help");
		this.getContentPane().add(content());
		this.add(content());
	}

	private JLabel content() {
		JLabel ret = new JLabel();
		ret.setText("Hoi");
		return ret;
	}
}
