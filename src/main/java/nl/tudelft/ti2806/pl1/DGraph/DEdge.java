package nl.tudelft.ti2806.pl1.DGraph;

/**
 * Lets you create Edges for a Graph.
 * 
 * @author Marissa, Mark
 * @version 25-04-2015 Lets you create Edges for a Graph
 * 
 */
public class DEdge {

	/**
	 * To ensure the hashcodes are unique, prime numbers must be used.
	 */
	private static final int PRIME_17 = 17;

	/**
	 * To ensure the hashcodes are unique, prime numbers must be used.
	 */
	private static final int PRIME_37 = 37;

	/**
	 * The starting node of the edge.
	 */
	private int startNode;

	/**
	 * The ending node of the edge.
	 */
	private int endNode;

	/**
	 * The constructor for the edge, which creates a new edge starting at start
	 * and ending at end.
	 * 
	 * @param start
	 *            The starting node id of the edge.
	 * @param end
	 *            The ending node id of the edge.
	 */
	public DEdge(final int start, final int end) {
		startNode = start;
		endNode = end;
	}

	/**
	 * The getter for the starting node id.
	 * 
	 * @return the starting node of the edge
	 */
	public int getStartNode() {
		return startNode;
	}

	/**
	 * The setter of the starting node id.
	 * 
	 * @param startNodeIn
	 *            The new starting node of the edge.
	 */
	public final void setStartNode(final int startNodeIn) {
		this.startNode = startNodeIn;
	}

	/**
	 * The getter of the ending node id.
	 * 
	 * @return the ending node of the edge.
	 */
	public int getEndNode() {
		return endNode;
	}

	/**
	 * The setter of the ending node id.
	 * 
	 * @param endNodeIn
	 *            The new ending node of the edge.
	 */
	public final void setEndNode(final int endNodeIn) {
		this.endNode = endNodeIn;
	}

	/**
	 * Checks if an edge is equal to this edge.
	 * 
	 * @param edge
	 *            The edge to check if it's equal to this edge
	 * @return True if the edge is equal, false otherwise
	 */
	@Override
	public final boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (object instanceof DEdge) {
			DEdge edge = (DEdge) object;
			return edge.getStartNode() == getStartNode()
					&& edge.getEndNode() == getEndNode();
		}
		return false;
	}

	/**
	 * Overrides the hashCode function for DEdge.
	 */
	@Override
	public int hashCode() {
		int result = PRIME_17;
		result += PRIME_37 * result + getStartNode();
		result += PRIME_37 * result + getEndNode();
		return result;
	}
}
