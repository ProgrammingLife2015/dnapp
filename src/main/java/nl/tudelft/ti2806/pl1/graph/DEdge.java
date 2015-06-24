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
	private DNode startNode;

	/**
	 * The ending node of the edge.
	 */
	private DNode endNode;

	/**
	 * The constructor for the edge, which creates a new edge starting at start
	 * and ending at end.
	 * 
	 * @param start
	 *            The starting node of the edge.
	 * @param end
	 *            The ending node of the edge.
	 */
	public DEdge(final DNode start, final DNode end) {
		startNode = start;
		endNode = end;
	}

	/**
	 * The getter for the starting node.
	 * 
	 * @return the starting node of the edge
	 */
	public DNode getStartNode() {
		return startNode;
	}

	/**
	 * The setter of the starting node.
	 * 
	 * @param startNodeIn
	 *            The new starting node of the edge.
	 */
	public final void setStartNode(final DNode startNodeIn) {
		this.startNode = startNodeIn;
	}

	/**
	 * The getter of the ending node.
	 * 
	 * @return the ending node of the edge.
	 */
	public DNode getEndNode() {
		return endNode;
	}

	/**
	 * The setter of the ending node.
	 * 
	 * @param endNodeIn
	 *            The new ending node of the edge.
	 */
	public final void setEndNode(final DNode endNodeIn) {
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
			return edge.getStartNode().getId() == getStartNode().getId()
					&& edge.getEndNode().getId() == getEndNode().getId();
		}
		return false;
	}

	/**
	 * Overrides the hashCode function for DEdge.
	 */
	@Override
	public int hashCode() {
		int result = PRIME_17;
		result += PRIME_37 * result + getStartNode().getId();
		result += PRIME_37 * result + getEndNode().getId();
		return result;
	}

	@Override
	public String toString() {
		return "<Edge[" + startNode + "->" + endNode + "]>";
	}
}
