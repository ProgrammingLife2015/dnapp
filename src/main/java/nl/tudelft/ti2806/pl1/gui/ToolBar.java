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
	private JButton btnLoadGraph = makeButton("Load graph", null,
			Event.LOAD_FILE, "Click to load the default graph.");

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
		add(makeButton("example", null, Event.EXAMPLE_EVENT,
				"Example tooltip text."));
		add(makeButton("another button", null, Event.NONE, "boe"));
		addSeparator();
		add(btnLoadGraph);
	}

	/**
	 * 
	 * @param text
	 *            The text to show.
	 * @param imageName
	 *            File name of the image icon.
	 * @param action
	 *            Action id.
	 * @param toolTipText
	 *            Tool tip text.
	 * @return The created button.
	 */
	private JButton makeButton(final String text, final String imageName,
			final Event action, final String toolTipText) {
		JButton button = new JButton();
		button.setText(text);
		button.setToolTipText(toolTipText);
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
		return button;
	}

	/**
	 * 
	 * @param enabled
	 */
	public void enableBtnLoadGraph(final boolean enabled) {
		btnLoadGraph.setEnabled(enabled);
	}

}
