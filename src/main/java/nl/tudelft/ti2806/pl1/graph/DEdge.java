package nl.tudelft.ti2806.pl1.graph;

/**
 * Lets you create Edges for a Graph.
 * 
 * @author Marissa, Mark
 * @version 25-04-2015 Lets you create Edges for a Graph
 * 
 */
public class DEdge {

	/**
	 * The starting node of the edge.
	 */
	private int startNode;

	/**
	 * The ending node of the edge.
	 */
	private int endNode;

	/**
	 * The empty constructor of the edge, it creates a new edge starting at 0
	 * and ending at 0.
	 */
	public DEdge() {
		new DEdge(0, 0);
	}

	/**
	 * The constructor for the edge, which creates a new edge starting at start
	 * and ending at end.
	 * 
	 * @param start
	 *            The starting node of the edge.
	 * @param end
	 *            The ending node of the edge.
	 */
	public DEdge(final int start, final int end) {
		startNode = start;
		endNode = end;
	}

	/**
	 * The getter for the starting node.
	 * 
	 * @return the starting node of the edge
	 */
	public final int getStartNode() {
		return startNode;
	}

	/**
	 * The setter of the starting node.
	 * 
	 * @param startNodeIn
	 *            The new starting node of the edge.
	 */
	public final void setStartNode(final int startNodeIn) {
		this.startNode = startNodeIn;
	}

	/**
	 * The getter of the ending node.
	 * 
	 * @return the ending node of the edge.
	 */
	public final int getEndNode() {
		return endNode;
	}

	/**
	 * The setter of the ending node.
	 * 
	 * @param endNodeIn
	 *            The new ending node of the edge.
	 */
	public final void setEndNode(final int endNodeIn) {
		this.endNode = endNodeIn;
	}
}
