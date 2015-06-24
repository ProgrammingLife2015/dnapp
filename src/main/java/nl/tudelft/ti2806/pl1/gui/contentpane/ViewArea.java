package nl.tudelft.ti2806.pl1.gui.contentpane;

import nl.tudelft.ti2806.pl1.graph.DNode;

import org.graphstream.graph.Node;

/**
 * Represents a horizontal section of a graph view.
 * 
 * @author Maarten
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
	 * @return The width of the view area.
	 */
	public final int getWidth() {
		return xRight - xLeft;
	}

	/**
	 * Initializes the view area.
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
	 * Initializes the view area.
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
	 * Checks whether a data node is contained by this view area. Only looks at
	 * the coordinates of the node, not at whether the node is actually in the
	 * data graph which representation is currently shown.
	 * 
	 * @param node
	 *            The data node to check whether it is in this view area.
	 * @return True iff the given node is positioned in this view area.
	 */
	public boolean isContained(final DNode node) {
		return node.getX() >= xLeft && node.getX() <= xRight;
	}

	/**
	 * Checks whether a visual node is contained by this view area. Only looks
	 * at the coordinates of the node, not at whether the node is actually in
	 * the graph shown.
	 * 
	 * @param node
	 *            The data node to check whether it is in this view area.
	 * @return true iff the given node is positioned in this view area.
	 */
	public boolean isContained(final Node node) {
		int x = (int) node.getAttribute("x");
		return x >= xLeft && x <= xRight;
	}

	@Override
	public String toString() {
		return "<ViewArea[" + xLeft + "-" + xRight + "|w=" + getWidth() + "]>";
	}

}
