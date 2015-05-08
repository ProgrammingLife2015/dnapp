package nl.tudelft.ti2806.pl1.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
		// setupGBC();
		Dimension d = new Dimension(10, 20);
		setWeight(0);
		JTextArea jta = new JTextArea("Bla bla bla bla bla.");
		// jta.setMaximumSize(d);
		// jta.setSize(d);
		jta.setPreferredSize(d);
		jta.setColumns(20);
		place(add(jta), 1, 0);
		gbc.anchor = GridBagConstraints.NORTHWEST;

		place(makeButton("button 00", Event.ANOTHER_EVENT, "another tool tip"),
				0, 0);
		// setWeight(1.0);
		place(makeButton("Center button print app name", Event.PRINT_APP_NAME,
				"print app name tool tip"), 1, 1);
		// setWeight(0.0);
		place(makeButton("Exit", Event.EXIT_APP, "exit the program tool tip"),
				2, 2);

	}

	/**
	 * Sets the grid bag constraint weight in both x and y direction.
	 * 
	 * @param weight
	 *            The weight to set.
	 */
	private void setWeight(final double weight) {
		gbc.weightx = weight;
		gbc.weighty = weight;
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
