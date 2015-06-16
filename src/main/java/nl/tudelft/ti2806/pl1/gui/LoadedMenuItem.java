package nl.tudelft.ti2806.pl1.gui;

import javax.swing.JMenuItem;

/**
 * A menu bar item which is only enabled if a graph is loaded into the graph
 * panel of a given window.
 * 
 * @author Maarten
 * @since 16-6-2015
 * 
 * @see JMenuItem
 * @see Window
 */
public class LoadedMenuItem extends JMenuItem {

	/** The serial version UID. */
	private static final long serialVersionUID = 1718870799530320365L;

	/** The window. */
	private Window window;

	/**
	 * Initialize the menu item.
	 * 
	 * @param w
	 *            The window.
	 */
	public LoadedMenuItem(final Window w) {
		super();
		this.window = w;
	}

	// @Override
	// public boolean isEnabled() {
	// if(window == null) {
	// System.out.println("WIN NULL");
	// return super.isEnabled();
	// }
	// return window.getContent().isGraphLoaded();
	// }

	@Override
	public boolean isVisible() {
		if (window == null) {
			System.out.println("WIN NULL");
			return super.isEnabled();
		}
		return window.getContent().isGraphLoaded();
	}
}
