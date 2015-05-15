/**
 * 
 */
package nl.tudelft.ti2806.pl1.gui;

import javax.swing.JScrollPane;

/**
 * @author Maarten
 *
 */
public class PhyloPanel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1936473122898892804L;

	/**
	 * 
	 */
	private Window window;

	/**
	 * @param w
	 *            The window this panel is part of.
	 */
	public PhyloPanel(final Window w) {
		this.window = w;
	}

}
