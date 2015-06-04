package nl.tudelft.ti2806.pl1.zoomlevels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

/**
 * This class converts the standard graph into a PointGraph.
 * 
 * @author Justin, Marissa
 * @since 14-05-2015
 *
 */
public final class PointGraphConverter {

	/**
	 */
	private PointGraphConverter() {
	}

	/**
	 * Converts the standard graph into a graph representing a zoom level where
	 * small point mutations are collapsed.
	 * 
	 * @param graph
	 *            The initial graph of the original zoom level.
	 * @return The graph with collapsed point mutations.
	 */
	public static Graph collapsePointMutations(final Graph graph) {
		Graph g = Graphs.merge(graph);
		Node start = getStart(g);
		BreadthFirstIterator<Node> it = new BreadthFirstIterator<Node>(start);
		Queue<Node> q = new LinkedList<Node>();
		q.add(start);
		while (it.hasNext()) {
			Node node = it.next();
			q.add(node);
		}
		while (!q.isEmpty()) {
			Node node = q.remove();
			Iterator<Edge> leaving = node.getEachLeavingEdge().iterator();
			ArrayList<String> muts = new ArrayList<String>();
			while (leaving.hasNext()) {
				Edge out = leaving.next();
				Node outnode = out.getNode1();
				String content = outnode.getAttribute("content").toString();
				if (content.length() == 1) {
					muts.add(outnode.getId());
				}
			}
			if (muts.size() > 1) {
				HashMap<Node, ArrayList<String>> nodegroups = makeNodeGroups(
						muts, g);
				collapseNodes(nodegroups, g);
			}
		}
		return g;
	}

	/**
	 * 
	 * @param graph
	 *            The initial graph of the original zoom level.
	 * @param muts
	 *            The mutated nodes, which are to be grouped
	 * @return The graph with collapsed point mutations.
	 */
	public static Graph collapsePointMutations(final Graph graph,
			final Collection<String> muts) {
		Graph g = Graphs.merge(graph);
		HashMap<Node, ArrayList<String>> nodegroups = makeNodeGroups(muts, g);
		collapseNodes(nodegroups, g);
		return g;
	}

	/**
	 * Makes a HashMap of groups of nodes where the keys are the end nodes and
	 * the values are the grouped nodes.
	 * 
	 * @param muts
	 *            The mutated nodes, which are to be grouped
	 * @param g
	 *            The graph we want to collapse
	 * @return The grouped nodes
	 */
	private static HashMap<Node, ArrayList<String>> makeNodeGroups(
			final Collection<String> muts, final Graph g) {
		HashMap<Node, ArrayList<String>> nodegroups = new HashMap<Node, ArrayList<String>>();
		for (String n : muts) {
			ArrayList<Node> ns = getNextNodes(g, n);
			for (Node nd : ns) {
				if (!nodegroups.containsKey(nd)) {
					ArrayList<String> nodegroup = new ArrayList<String>();
					nodegroup.add(n);
					nodegroups.put(nd, nodegroup);
				} else {
					nodegroups.get(nd).add(n);
				}
			}
		}
		return nodegroups;
	}

	/**
	 * Collapses the node groups in the graph.
	 * 
	 * @param nodegroups
	 *            The node groups
	 * @param g
	 *            The graph we want to collapse on.
	 */
	private static void collapseNodes(
			final HashMap<Node, ArrayList<String>> nodegroups, final Graph g) {
		for (Node end : nodegroups.keySet()) {
			ArrayList<String> nodegroup = nodegroups.get(end);
			// if (nodegroup.size() == 1) {
			// Node nd = g.getNode(nodegroup.get(0));
			// String mutcontent = nd.getAttribute("content");
			// String endcontent = end.getAttribute("content");
			// end.addAttribute("content", mutcontent + endcontent);
			// for (Edge edge : nd.getEnteringEdgeSet()) {
			// Node in = edge.getNode0();
			// g.addEdge("collapsed: " + edge.getId() + " " + end.getId(),
			// in, end);
			// }
			// removeNode(g, nd, end);
			// } else {
			if (nodegroup.size() == 1) {
				HashMap<String, String> content = new HashMap<String, String>();
				StringBuilder sb = new StringBuilder();
				String newId = "collapsed:";
				sb.append(newId);
				for (String id : nodegroup) {
					Node nd = g.getNode(id);
					Collection<String> sources = nd.getAttribute("sources");

					sb.append(" " + id);
				}
				addNewCollapsedNode(sb.toString(), g, nodegroup, end);
				for (String id : nodegroup) {
					Node nd = g.getNode(id);
					removeNode(g, nd);
				}
			}
		}
	}

	/**
	 * Adds a new collapsed node to the graph.
	 * 
	 * @param newId
	 *            The ID of the new node.
	 * @param g
	 *            The graph we want to collapse on.
	 * @param nodegroup
	 *            The node group that is collapsed into the new node.
	 * @param end
	 *            The node which this group is going to.
	 */
	private static void addNewCollapsedNode(final String newId, final Graph g,
			final ArrayList<String> nodegroup, final Node end) {
		g.addNode(newId);
		Node tempnode = g.getNode(nodegroup.get(0));
		Node collapsednode = g.getNode(newId);
		collapsednode.addAttribute("start", tempnode.getAttribute("start"));
		collapsednode.addAttribute("depth", tempnode.getAttribute("depth"));
		collapsednode.addAttribute("end", tempnode.getAttribute("end"));
		collapsednode.addAttribute("type", "collapsed");

		collapseEdges(g, newId, nodegroup, end);
	}

	/**
	 * Adds all the edges of a group of nodes onto the collapsed node.
	 * 
	 * @param g
	 *            The graph we want to collapse on.
	 * @param id
	 *            The id of the collapsed node.
	 * @param nodegroup
	 *            The group of nodes we are collapsing.
	 * @param end
	 *            The node which this group is going to.
	 */
	private static void collapseEdges(final Graph g, final String id,
			final ArrayList<String> nodegroup, final Node end) {
		Node collapsednode = g.getNode(id);
		for (String old : nodegroup) {
			Node oldnode = g.getNode(old);
			Iterator<Edge> enteringedges = oldnode.getEnteringEdgeIterator();
			while (enteringedges.hasNext()) {
				Edge cur = enteringedges.next();
				Node sourcenode = cur.getNode0();
				if (!sourcenode.hasEdgeBetween(collapsednode)) {
					g.addEdge("collapsed: " + cur.getId() + sourcenode.getId()
							+ Math.random(), sourcenode, collapsednode);
				}
			}
			for (Edge edge : oldnode.getLeavingEdgeSet()) {
				if (edge != null && edge.getNode1().equals(end)) {
					g.removeEdge(edge);
				}
			}
		}
		g.addEdge("collapsed: " + collapsednode.getId() + " " + end.getId(),
				collapsednode, end);
	}

	/**
	 * Removes a node and all its edges from a graph and connects the previous
	 * nodes to an end node.
	 * 
	 * @param g
	 *            The graph where we want to collapse.
	 * @param nd
	 *            The node to be removed.
	 * @param end
	 *            The end node.
	 */
	private static void removeNode(final Graph g, final Node nd, final Node end) {
		for (Edge edge : nd.getLeavingEdgeSet()) {
			if (edge != null && edge.getNode1().equals(end)) {
				g.removeEdge(edge);
			}
		}
		if (nd.getLeavingEdgeSet().isEmpty()) {
			for (Edge edge : nd.getEachEdge()) {
				g.removeEdge(edge);
			}
			g.removeNode(nd);
		}
	}

	/**
	 * 
	 * @param g
	 *            The graph we want to collapse on.
	 * @param node
	 *            The node to be removed.
	 */
	private static void removeNode(final Graph g, final Node node) {
		if (node.getLeavingEdgeSet().isEmpty()) {
			for (Edge edge : node.getEachEdge()) {
				g.removeEdge(edge);
			}
			g.removeNode(node);
		}
	}

	/**
	 * Gets the next nodes for a certain node.
	 * 
	 * @param graph
	 *            The graph containing the node.
	 * @param id
	 *            ID of the node.
	 * @return The nodes following the given node.
	 */
	private static ArrayList<Node> getNextNodes(final Graph graph,
			final String id) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		Node n = graph.getNode(id);
		Iterator<Edge> edges = n.getLeavingEdgeIterator();
		Edge next;
		while (edges.hasNext()) {
			next = edges.next();
			nodes.add(next.getNode1());
		}
		return nodes;
	}

	/**
	 * Gets the first node of a graph.
	 * 
	 * @param g
	 *            Graph of which we want to get the startnode
	 * @return Start node
	 */
	private static Node getStart(final Graph g) {
		return g.getNode(String.valueOf(g.getAttribute("start")));
	}
}
