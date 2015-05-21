package nl.tudelft.ti2806.pl1.zoomlevels;

import java.util.HashMap;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

/**
 * 
 * @author Justin, Marissa
 * @since 21-05-2015
 */
public class HorizontalCollapse {

	/**
	 * The default max value on how much content we want to collapse.
	 */
	private static final int DEFAULT_MAX = 100;

	/**
	 * 
	 */
	private HorizontalCollapse() {

	}

	/**
	 * 
	 * @param graph
	 *            Graph we want to collapse on.
	 * @param maxcontent
	 *            Maximum content a node can have after collapsing.
	 * @return Horizontally collapsed graph.
	 */
	public static Graph horizontalCollapse(final Graph graph,
			final int maxcontent) {
		Graph g = Graphs.merge(graph);
		if (maxcontent > 0) {
			g = collapseGraph(g, maxcontent);
		} else {
			g = collapseGraph(g, DEFAULT_MAX);
		}
		return g;
	}

	/**
	 * 
	 * @param graph
	 *            Graph we want to collapse on.
	 * @param maxcontent
	 *            Maximum content a node can have after collapsing.
	 * @return
	 */
	private static Graph collapseGraph(final Graph graph, final int maxcontent) {
		Node first = getStart(graph);
		return collapseNodes(graph, first, maxcontent);
	}

	private static Graph collapseNodes(Graph graph, final Node node,
			final int maxcontent) {
		if (node.getOutDegree() == 0) {
			return graph;
		} else if (node.getOutDegree() == 1) {
			Node out = node.getEachLeavingEdge().iterator().next().getNode1();
			if ((getContentSize(node) + getContentSize(out)) <= maxcontent) {

			} else {
				graph = collapseNodes(graph, out, maxcontent);
			}
			// if we add them together call it again on this node (because it
			// should now have the edges of node out)
			// if not we call it on the out node
		} else {
			for (Edge edge : node.getEachLeavingEdge()) {
				Node out = edge.getNode1();
				graph = collapseNodes(graph, node, maxcontent);
			}
		}
		return graph;
	}

	private static int getContentSize(final Node node) {
		if (node.getAttribute("content") instanceof HashMap<?, ?>) {
			HashMap<String, String> content = node.getAttribute("content");
			return content.entrySet().iterator().next().getValue().length();
		} else if (node.getAttribute("content") instanceof String) {
			String content = node.getAttribute("content");
			return content.length();
		}
		return 0;
	}

	/**
	 * Gets the first node of a graph.
	 * 
	 * @param g
	 *            Graph of which we want to get the startnode
	 * @return Start node
	 */
	private static Node getStart(final Graph g) {
		Node first = null;
		for (Node n : g.getNodeSet()) {
			if (first == null) {
				first = n;
			} else if ((Integer) n.getAttribute("start") < (Integer) first
					.getAttribute("start")) {
				first = n;
			}
		}
		return first;
	}

}
