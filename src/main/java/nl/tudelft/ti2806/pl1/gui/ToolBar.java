package nl.tudelft.ti2806.pl1.gui;

import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;

import nl.tudelft.ti2806.pl1.gui.contentpane.ContentTab;

/**
 * The main tool bar of the application showing at the top of the window (right
 * below the menu bar), or floating around.
 * 
 * @author Maarten
 */
public class ToolBar extends JToolBar {

	/** The serial version UID. */
	private static final long serialVersionUID = 7980283436568336682L;

	/** The import button. */
	private JButton btnImport = makeButton("Import graph", null,
			Event.IMPORT_FILE,
			"Click to load a graph from .node.graph and .edge.graph files.");

	/** The import phylogenetic tree button. */
	private JButton btnImportPhylo = makeButton("Import phylo", null,
			Event.IMPORT_PHYLO,
			"Click to load a phylogenetic tree from a newick file format.");

	/**
	 * Initializes the tool bar.
	 */
	public ToolBar() {
		super("DN/App toolbar", JToolBar.HORIZONTAL);
		setRollover(true);
		addGeneralButtons();
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
	public static JButton makeButton(final String text, final String imageName,
			final ActionListener action, final String toolTipText) {
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

	/** Adds the control elements to the tool bar. */
	private void addGeneralButtons() {
		this.removeAll();
		add(btnImport);
		add(btnImportPhylo);
		addSeparator();
	}

	/**
	 * @param contentTab
	 *            The content tab which tool bar buttons should now be shown.
	 */
	public void viewContextChanged(final ContentTab contentTab) {
		removeAll();
		addGeneralButtons();
		for (JComponent c : contentTab.getToolBarControls()) {
			add(c);
		}
	}

}
