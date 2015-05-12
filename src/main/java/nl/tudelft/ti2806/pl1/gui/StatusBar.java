package nl.tudelft.ti2806.pl1.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * @author Maarten
 *
 */
public class StatusBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2304279241558941973L;

	/**
	 * The text label of the status bar.
	 */
	private JLabel label;

	/**
	 * Initializes the status bar.
	 */
	public StatusBar() {
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.LEFT);
		add(label);
	}

	/**
	 * 
	 * @param message
	 *            The error message to show.
	 */
	public final void error(final String message) {
		label.setText("Error: " + message);
	}

	/**
	 * 
	 * @param message
	 *            The info message to show.
	 */
	public final void info(final String message) {
		label.setText(message);
	}

}
