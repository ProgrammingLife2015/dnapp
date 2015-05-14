package nl.tudelft.ti2806.pl1.zoomlevels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * 
 * @author Justin, Marissa
 *
 */
public final class GraphConverter {

	/**
	 * 
	 */
	private GraphConverter() {
	}

	/**
	 * Converts a graph into a graph representing a zoom level where small point
	 * mutations are collapsed.
	 * 
	 * @param graph
	 *            The initial graph of the original zoom level.
	 * @return The converted graph.
	 */
	public static Graph collapsePointMutations(final Graph graph) {
		Graph g = new SingleGraph("");
		Collection<Node> nodes = graph.getNodeSet();
		Collection<Edge> edges = graph.getEdgeSet();
		Node start = getStart(nodes);
		if (start == null) {
			return g;
		}
		addNode(g, start);
		// Collection<String> sources = start.getAttribute("sources");
		// int genomeCount = sources.size();
		// Iterator<Node> iter = start.getBreadthFirstIterator();
		// Node cur;
		// Node prev;
		// while (iter.hasNext()) {
		// cur = iter.next();
		//
		// prev = cur;
		// }
		return g;
	}

	/**
	 * Adds an edge to a graph.
	 * 
	 * @param graph
	 *            The graph.
	 * @param edge
	 *            The edge.
	 * @return
	 * @return The graph with the edge added
	 */
	private static void addEdge(final Graph graph, final Edge edge) {
		graph.addEdge(edge.getId(), edge.getSourceNode(), edge.getTargetNode());
	}

	/**
	 * Adds a node to a graph, including all its content.
	 * 
	 * @param graph
	 *            The graph.
	 * @param node
	 *            The node.
	 * @return The graph with the node added.
	 */
	private static void addNode(final Graph graph, final Node node) {
		graph.addNode(node.getId());
		Node addedNode = graph.getNode(node.getId());
		Iterator<String> iter = node.getEachAttributeKey().iterator();
		while (iter.hasNext()) {
			String next = iter.next();
			addedNode.addAttribute(next, node.getAttribute(next));
		}
	}

	/**
	 * Gets the start Node of a collection of nodes.
	 * 
	 * @param nodes
	 *            Collection of nodes
	 * @return Start node
	 */
	private static Node getStart(final Collection<Node> nodes) {
		for (Node node : nodes) {
			Iterator<Edge> it = node.getEachLeavingEdge().iterator();
			ArrayList<Edge> leaving = new ArrayList<Edge>();
			while (it.hasNext()) {
				Edge e = it.next();
				if (e.getTargetNode().getId().equals(node.getId())) {
					leaving.add(e);
				}
			}
			if (leaving.isEmpty()) {
				return node;
			}
		}
		return null;
	}
}
