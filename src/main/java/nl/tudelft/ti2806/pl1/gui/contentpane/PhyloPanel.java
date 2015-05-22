package nl.tudelft.ti2806.pl1.gui.contentpane;

import javax.swing.JScrollPane;

import nl.tudelft.ti2806.pl1.gui.Window;

/**
 * @author Maarten
 *
 */
public class PhyloPanel extends JScrollPane {

	/** The serial version UID. */
	private static final long serialVersionUID = -1936473122898892804L;

	/** The window this panel is part of. */
	private Window window;

	/**
	 * Initializes the panel.
	 * 
	 * @param w
	 *            The window this panel is part of.
	 */
	public PhyloPanel(final Window w) {
		this.window = w;
	}

}
