package nl.tudelft.ti2806.pl1.zoomlevels;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.graphstream.graph.BreadthFirstIterator;
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
	private static Graph collapseGraph(Graph graph, final int maxcontent) {
		Node first = getStart(graph);
		BreadthFirstIterator<Node> it = new BreadthFirstIterator<Node>(first);
		Queue<Node> q = new LinkedList<Node>();
		q.add(first);
		while (it.hasNext()) {
			Node node = it.next();
			q.add(node);
		}
		while (!q.isEmpty()) {
			Node node = q.remove();
			graph = collapseNodes(graph, node, maxcontent);
		}
		return graph;
	}

	private static Graph collapseNodes(final Graph graph, final Node node,
			final int maxcontent) {
		if (node.getInDegree() == 1) {
			Node prev = node.getEnteringEdgeIterator().next().getNode0();
			if (prev.getOutDegree() == 1
					&& getContentSize(prev) + getContentSize(node) <= maxcontent) {
				mergeAttributes(prev, node);
				for (Edge edge : prev.getEnteringEdgeSet()) {
					if (edge != null) {
						String id = edge.getId();
						// graph.removeEdge(id);
						graph.addEdge(id + Math.random(), edge.getNode0(),
								node, true);
					}
				}
				graph.removeNode(prev);
			}
		}
		return graph;
	}

	/**
	 * Merges the attributes of two nodes.
	 * 
	 * @param from
	 *            The node from which the attributes are taken
	 * @param to
	 *            The node into which the merged attributes are stored
	 */
	private static void mergeAttributes(final Node from, final Node to) {
		to.setAttribute("inNodes", from.getAttribute("inNodes"));
		to.setAttribute("start", from.getAttribute("start"));
		if (from.getAttribute("content") instanceof String) {
			String contentfrom = from.getAttribute("content");
			if (to.getAttribute("content") instanceof String) {
				String contentto = to.getAttribute("content");
				to.setAttribute("content", contentfrom + contentto);
			} else if (from.getAttribute("content") instanceof HashMap<?, ?>) {
				HashMap<String, String> contentto = to.getAttribute("content");
				HashMap<String, String> mergedcontent = mergeContent(
						contentfrom, contentto);
				to.setAttribute("content", mergedcontent);
			}
		} else if (from.getAttribute("content") instanceof HashMap<?, ?>) {
			HashMap<String, String> contentfrom = from.getAttribute("content");
			if (to.getAttribute("content") instanceof String) {
				String contentto = to.getAttribute("content");
				HashMap<String, String> mergedcontent = mergeContent(
						contentfrom, contentto);
				to.setAttribute("content", mergedcontent);
			} else if (to.getAttribute("content") instanceof HashMap<?, ?>) {
				HashMap<String, String> contentto = to.getAttribute("content");
				HashMap<String, String> mergedcontent = mergeContent(
						contentfrom, contentto);
				to.setAttribute("content", mergedcontent);
			}
		}
	}

	/**
	 * Merges the contents of two nodes.
	 * 
	 * @param contentfrom
	 *            The content of the first node.
	 * @param contentto
	 *            The content of the second node.
	 * @return The merged content.
	 */
	private static HashMap<String, String> mergeContent(
			final HashMap<String, String> contentfrom,
			final HashMap<String, String> contentto) {
		for (String key : contentfrom.keySet()) {
			contentto.put(key, contentfrom.get(key) + contentto.get(key));
		}
		return contentto;
	}

	/**
	 * Merges the contents of two nodes.
	 * 
	 * @param contentfrom
	 *            The content of the first node.
	 * @param contentto
	 *            The content of the second node.
	 * @return The merged content.
	 */
	private static HashMap<String, String> mergeContent(
			final HashMap<String, String> contentfrom, final String contentto) {
		for (String key : contentfrom.keySet()) {
			contentfrom.put(key, contentfrom.get(key) + contentto);
		}
		return contentfrom;
	}

	/**
	 * Merges the contents of two nodes.
	 * 
	 * @param contentfrom
	 *            The content of the first node.
	 * @param contentto
	 *            The content of the second node.
	 * @return The merged content.
	 */
	private static HashMap<String, String> mergeContent(
			final String contentfrom, final HashMap<String, String> contentto) {
		for (String key : contentto.keySet()) {
			contentto.put(key, contentfrom + contentto.get(key));
		}
		return contentto;
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
