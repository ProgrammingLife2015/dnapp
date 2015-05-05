package nl.tudelft.ti2806.pl1.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A panel representing the content of the main window.
 * 
 * @author Maarten
 *
 */
public class Content extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -518196843048978296L;

	/**
	 * The constraints for the layout of the content panel.
	 */
	private GridBagConstraints gbc = new GridBagConstraints();

	/**
	 * 
	 */
	public Content() {
		setLayout(new GridBagLayout());
		setupGBC();
		place(makeButton("button 00", Event.ANOTHER_EVENT, "another tool tip"),
				0, 0);
		gbc.weightx = 1;
		gbc.weighty = 1;
		place(makeButton("Center button print app name", Event.PRINT_APP_NAME,
				"print app name tool tip"), 1, 1);
		gbc.weightx = 0;
		gbc.weighty = 0;
		place(makeButton("Exit", Event.EXIT_APP, "exit the program tool tip"),
				2, 2);

	}

	/**
	 * 
	 */
	private void setupGBC() {
		gbc.fill = GridBagConstraints.BOTH;
	}

	/**
	 * 
	 * @param elem
	 *            The element to place.
	 * @param x
	 *            The x grid coordinate to place the element.
	 * @param y
	 *            The y grid coordinate to place the element.
	 */
	private void place(final Component elem, final int x, final int y) {
		gbc.gridx = x;
		gbc.gridy = y;
		add(elem, gbc);
	}

	/**
	 * Creates a button element.
	 * 
	 * @param text
	 *            File name of the image icon.
	 * @param action
	 *            Action id.
	 * @param toolTipText
	 *            Tool tip text.
	 * 
	 * @see Event
	 * 
	 * @return The button created.
	 */
	private JButton makeButton(final String text, final Event action,
			final String toolTipText) {
		JButton button = new JButton();
		button.setText(text);
		button.setToolTipText(toolTipText);
		// button.setActionCommand(action.toString());
		button.addActionListener(action);
		return button;

	}

}
