/**
 * 
 */
package nl.tudelft.ti2806.pl1.gui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * @author Maarten
 *
 */
public class WindowEvents implements ComponentListener {

	/**
	 * The window this listener receives events from.
	 */
	private Window window;

	/**
	 * Initializes a new window events listener.
	 */
	public WindowEvents(final Window w) {
		this.window = w;
	}

	/**
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	public void componentResized(final ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
	 */
	public void componentMoved(final ComponentEvent e) {
	}

	/**
	 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
	 */
	public void componentShown(final ComponentEvent e) {

	}

	/**
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	public void componentHidden(final ComponentEvent e) {

	}

}
