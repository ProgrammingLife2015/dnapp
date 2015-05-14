package nl.tudelft.ti2806.pl1.gui;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * @author Maarten
 *
 */
public class ToolBar extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7980283436568336682L;

	/**
	 * 
	 */
	public ToolBar() {
		super("NAME", JToolBar.HORIZONTAL);
		addButtons();
	}

	/**
	 * 
	 */
	private void addButtons() {
		addButton("example", null, Event.EXAMPLE_EVENT, "Example tooltip text.");
		addSeparator();
		addButton("another button", null, Event.NONE, "boe");
		addButton("Load graph", null, Event.LOAD_FILE,
				"Click this to load a default graph.");
	}

	/**
	 * Adds a button to the tool bar.
	 * 
	 * @param text
	 *            The text to show.
	 * @param imageName
	 *            File name of the image icon.
	 * @param action
	 *            Action id.
	 * @param toolTipText
	 *            Tool tip text.
	 * 
	 * @see Event
	 */
	private void addButton(final String text, final String imageName,
			final Event action, final String toolTipText) {
		JButton button = new JButton();
		button.setText(text);
		button.setToolTipText(toolTipText);
		// button.setActionCommand(Event.stringify(action));
		button.addActionListener(action);

		if (imageName != null) {
			String imgLocation = "images/" + imageName;
			URL imageURL = ToolBar.class.getResource(imgLocation);
			if (imageURL != null) {
				button.setIcon(new ImageIcon(imageURL, text));
			} else {
				System.err.println("Resource not found: " + imgLocation);
			}
		}

		add(button);
	}

}
