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
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 7980283436568336682L;

	/**
	 * 
	 */
	private JButton btnImport = makeButton("Import", null, Event.IMPORT_FILE,
			"Click to load a graph from .node.graph and .edge.graph files.");

	/**
	 * Initializes the tool bar.
	 */
	public ToolBar() {
		super("DN/App toolbar", JToolBar.HORIZONTAL);
		addButtons();
	}

	/**
	 * 
	 */
	private void addButtons() {
		add(btnImport);
		addSeparator();
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

	// /**
	// *
	// * @param enabled
	// */
	// public final void enableBtnLoadGraph(final boolean enabled) {
	// btnLoadGraph.setEnabled(enabled);
	// }

}
