/**
 * 
 */
package nl.tudelft.ti2806.pl1.gui.contentpane;

import nl.tudelft.ti2806.pl1.DGraph.DNode;

/**
 * @author Maarten
 *
 */
public class ViewArea {

	/** The left boundary. */
	private final int xLeft;

	/**
	 * @return The left boundary.
	 */
	public final int getLeftBoundary() {
		return xLeft;
	}

	/** The right boundary. */
	private int xRight;

	/**
	 * @return The right boundary.
	 */
	public final int getRightBoundary() {
		return xRight;
	}

	/**
	 * 
	 * @param xl
	 *            The left boundary.
	 * @param xr
	 *            The right boundary.
	 */
	public ViewArea(final int xl, final int xr) {
		this.xLeft = xl;
		this.xRight = xr;
	}

	/**
	 * 
	 * @param xl
	 *            The left boundary.
	 * @param xr
	 *            The right boundary.
	 */
	public ViewArea(final double xl, final double xr) {
		this((int) xl, (int) xr);
	}

	/**
	 * 
	 * @param node
	 *            The node to check
	 * @return True iff the given node is positioned in this view area.
	 */
	public boolean isContained(final DNode node) {
		return node.getX() >= xLeft && node.getX() <= xRight;
	}

}
