package gui;

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
	 * @param ehIn
	 *            The event handler to use.
	 */
	public StatusBar() {
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		// statusPanel.setPreferredSize(new Dimension(getWidth(), 20));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JLabel statusLabel = new JLabel("status");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(statusLabel);
	}

}
