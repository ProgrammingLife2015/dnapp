package nl.tudelft.ti2806.pl1.DGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * The DGraph class for representing our data.
 * 
 * @author mark
 *
 */
public class DGraph {

	/**
	 * The nodes in the graph.
	 */
	private final HashMap<Integer, DNode> nodes;

	/**
	 * The edges in the graph.
	 */
	private Collection<DEdge> edges;

	/**
	 * The start node and end node of the graph.
	 */
	private DNode start, end;

	/**
	 * Creates a new DGraph.
	 */
	public DGraph() {
		nodes = new HashMap<Integer, DNode>();
		edges = new ArrayList<DEdge>();
		start = null;
		end = null;
	}

	/**
	 * @return the edges
	 */
	public final Collection<DEdge> getEdges() {
		return edges;
	}

	/**
	 * @param edges
	 *            the edges to set
	 */
	public final void setEdges(final Collection<DEdge> edges) {
		this.edges = edges;
	}

	/**
	 * @return the nodes
	 */
	public final HashMap<Integer, DNode> getNodes() {
		return nodes;
	}

	/**
	 * Adds a node to the graph.
	 * 
	 * @param node
	 *            The node to be added
	 * @return A boolean indicating of the operation succeeded
	 */
	public final boolean addDNode(final DNode node) {
		if (nodes.containsKey(node.getId())) {
			return false;
		}
		nodes.put(node.getId(), node);
		return true;
	}

	/**
	 * Removes a node from the graph.
	 * 
	 * @param node
	 *            The node to be removed
	 * @return A boolean indicating of the operation succeeded
	 */
	public final boolean removeDNode(final DNode node) {
		if (!nodes.containsKey(node.getId())) {
			return false;
		}
		return removeDNode(node.getId());
	}

	/**
	 * Removes a node from the graph.
	 * 
	 * @param n
	 *            The node id to be removed
	 * @return A boolean indicating of the operation succeeded
	 */
	public final boolean removeDNode(final int n) {
		if (!nodes.containsKey(n)) {
			return false;
		}
		DNode removeNode = nodes.get(n);
		for (DEdge edge : removeNode.getAllEdges()) {
			edge.getStartNode().deleteEdge(edge);
			edge.getEndNode().deleteEdge(edge);
			edges.remove(edge);
		}
		nodes.remove(n);
		return true;
	}

	/**
	 * Adds an edge to the graph.
	 * 
	 * @param edge
	 *            The edge to be added
	 * @return A boolean indicating of the operation succeeded
	 */
	public final boolean addDEdge(final DEdge edge) {
		if (!nodes.containsKey(edge.getStartNode().getId())
				|| !nodes.containsKey(edge.getEndNode().getId())) {
			return false;
		}
		if (edges.contains(edge)) {
			return false;
		}
		nodes.get(edge.getStartNode().getId()).addEdge(edge);
		nodes.get(edge.getEndNode().getId()).addEdge(edge);
		edges.add(edge);
		return true;
	}

	/**
	 * Removes an edge from the graph.
	 * 
	 * @param edge
	 *            The edge to be removed
	 * @return A boolean indicating of the operation succeeded
	 */
	public final boolean removeDEdge(final DEdge edge) {
		if (!nodes.containsKey(edge.getStartNode().getId())
				|| !nodes.containsKey(edge.getEndNode().getId())) {
			return false;
		}
		if (!edges.contains(edge)) {
			return false;
		}
		nodes.get(edge.getStartNode().getId()).deleteEdge(edge);
		nodes.get(edge.getEndNode().getId()).deleteEdge(edge);
		edges.remove(edge);
		return true;
	}

	/**
	 * Gets a node from the graph.
	 * 
	 * @param n
	 *            The id of the node
	 * @return The node in the graph
	 */
	public final DNode getDNode(final int n) {
		return nodes.get(n);
	}

	/**
	 * Get the size of the amount of nodes.
	 * 
	 * @return The amount of nodes in the graph
	 */
	public final int getNodeCount() {
		return nodes.size();
	}

	/**
	 * @return the start.
	 */
	public final DNode getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set.
	 */
	public final void setStart(final DNode start) {
		this.start = start;
	}

	/**
	 * @return the end.
	 */
	public final DNode getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set.
	 */
	public final void setEnd(final DNode end) {
		this.end = end;
	}

}
