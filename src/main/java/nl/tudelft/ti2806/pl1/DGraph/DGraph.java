package nl.tudelft.ti2806.pl1.DGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The DGraph class for representing our data.
 * 
 * @author Mark
 *
 */
public class DGraph {

	/**
	 * The nodes in the graph.
	 */
	private final Map<Integer, DNode> nodes;

	/**
	 * A map storing a collection nodes per genome reference.
	 */
	private HashMap<String, Collection<DNode>> references;

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
		references = new HashMap<String, Collection<DNode>>();
		start = null;
		end = null;
	}

	/**
	 * @return the references
	 */
	public Map<String, Collection<DNode>> getReferences() {
		return references;
	}

	/**
	 * @param newReferences
	 *            the references to set
	 */
	protected void setReferences(
			final HashMap<String, Collection<DNode>> newReferences) {
		this.references = newReferences;
	}

	/**
	 * Returns all the nodes which contain a specific reference.
	 * 
	 * @param s
	 *            The reference from which we want to gain the nodes
	 * @return A collection which contain
	 */
	public Collection<DNode> getReference(final String s) {
		if (!references.containsKey(s)) {
			return new ArrayList<DNode>();
		}
		return references.get(s);
	}

	/**
	 * @return the edges
	 */
	public Collection<DEdge> getEdges() {
		return edges;
	}

	/**
	 * @param newEdges
	 *            the edges to set
	 */
	protected final void setEdges(final Collection<DEdge> newEdges) {
		this.edges = newEdges;
	}

	/**
	 * @return the nodes
	 */
	public Map<Integer, DNode> getNodes() {
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
		for (String s : node.getSources()) {
			if (!references.containsKey(s)) {
				ArrayList<DNode> temp = new ArrayList<DNode>();
				temp.add(node);
				references.put(s, temp);
			} else {
				references.get(s).add(node);
			}
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
		for (String s : removeNode.getSources()) {
			references.get(s).remove(removeNode);
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
	public DNode getStart() {
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
