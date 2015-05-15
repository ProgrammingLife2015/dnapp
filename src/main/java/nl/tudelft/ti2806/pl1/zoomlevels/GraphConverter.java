package nl.tudelft.ti2806.pl1.zoomlevels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

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
		Graph g = Graphs.merge(graph);
		Collection<Node> nodes = g.getNodeSet();
		Node start = getStart(nodes);
		// Iterator<String> keys = start.getAttributeKeyIterator();
		// while (keys.hasNext()) {
		// System.out.println(keys.next());
		// }
		BreadthFirstIterator<Node> it = new BreadthFirstIterator<Node>(start);
		while (it.hasNext()) {
			Node node = it.next();
			Iterator<Edge> leaving = node.getEachLeavingEdge().iterator();
			ArrayList<String> muts = new ArrayList<String>();
			while (leaving.hasNext()) {
				Edge out = leaving.next();
				Node outnode = out.getNode1();
				System.out.println(outnode.getId());
				String content = (String) outnode.getAttribute("content");
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
	 * Collapses the nodegroups in a graph.
	 * 
	 * @param nodegroups
	 *            The nodegroup
	 * @param g
	 *            The graph
	 */
	private static void collapseNodes(
			final HashMap<Node, ArrayList<String>> nodegroups, final Graph g) {
		for (Node end : nodegroups.keySet()) {
			ArrayList<String> nodegroup = nodegroups.get(end);
			if (nodegroup.size() == 1) {
				Node nd = g.getNode(nodegroup.get(0));
				String mutcontent = nd.getAttribute("content");
				String endcontent = end.getAttribute("content");
				end.addAttribute("content", mutcontent + endcontent);
				removeNode(g, nd, end);
			} else {
				HashMap<String, String> content = new HashMap<String, String>();
				String newId = "collapsed:";
				for (String id : nodegroup) {
					Node nd = g.getNode(id);
					Collection<String> sources = nd.getAttribute("sources");
					for (String source : sources) {
						content.put(source, (String) nd.getAttribute("content"));
					}
					newId += " " + id;
				}
				addNewCollapsedNode(newId, g, nodegroup, content);
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
	 *            The graph
	 * @param nodegroup
	 *            The nodegroup that is collapsed into the new node.
	 * @param content
	 *            The content of the new node.
	 */
	private static void addNewCollapsedNode(final String newId, final Graph g,
			final ArrayList<String> nodegroup,
			final HashMap<String, String> content) {
		g.addNode(newId);
		String temp = nodegroup.get(0);
		Node tempnode = g.getNode(temp);
		Node collapsednode = g.getNode(newId);
		collapsednode.addAttribute("start", tempnode.getAttribute("start"));
		collapsednode.addAttribute("depth", tempnode.getAttribute("depth"));
		collapsednode.addAttribute("end", tempnode.getAttribute("end"));
		collapsednode.addAttribute("content", content);
		collapseEdges(g, newId, nodegroup);

	}

	/**
	 * Makes a HashMap of groups of nodes where the keys are the end nodes and
	 * the values are the grouped nodes.
	 * 
	 * @param muts
	 *            The mutated nodes, to be grouped
	 * @param g
	 *            The graph
	 * @return The grouped nodes
	 */
	private static HashMap<Node, ArrayList<String>> makeNodeGroups(
			final ArrayList<String> muts, final Graph g) {
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
	 * Collapses all the edges of a group of nodes into a collapsed node.
	 * 
	 * @param g
	 *            The graph
	 * @param id
	 *            The id of the collapsed node
	 * @param nodegroup
	 *            The group of nodes
	 */
	private static void collapseEdges(final Graph g, final String id,
			final ArrayList<String> nodegroup) {
		Node collapsednode = g.getNode(id);
		for (String old : nodegroup) {
			Node oldnode = g.getNode(old);
			Iterator<Edge> enteringedges = oldnode.getEnteringEdgeIterator();
			Edge cur;
			while (enteringedges.hasNext()) {
				cur = enteringedges.next();
				Node sourcenode = cur.getSourceNode();
				if (!sourcenode.hasEdgeBetween(collapsednode)) {
					g.addEdge("collapsed: " + cur.getId(), sourcenode,
							collapsednode);
				}
			}
			Iterator<Edge> leavingedges = oldnode.getLeavingEdgeIterator();
			while (leavingedges.hasNext()) {
				cur = leavingedges.next();
				Node targetnode = cur.getTargetNode();
				if (!collapsednode.hasEdgeBetween(targetnode)) {
					g.addEdge("collapsed: " + cur.getId(), collapsednode,
							targetnode);
				}
			}
		}
	}

	/**
	 * Removes a node and all its edges from a graph and connects the previous
	 * nodes to an end node.
	 * 
	 * @param g
	 *            The graph
	 * @param nd
	 *            The to be removed node
	 * @param end
	 *            The end node
	 */
	private static void removeNode(final Graph g, final Node nd, final Node end) {
		Iterator<Edge> enteringedges = nd.getEnteringEdgeIterator();
		Edge cur;
		while (enteringedges.hasNext()) {
			cur = enteringedges.next();
			Node sourcenode = cur.getNode0();
			g.addEdge(cur.getId(), sourcenode, end);
			g.removeEdge(cur);
		}

		Iterator<Edge> leavingedges = nd.getLeavingEdgeIterator();
		while (leavingedges.hasNext()) {
			cur = leavingedges.next();
			g.removeEdge(cur);
		}
		g.removeNode(nd);

	}

	/**
	 * Removes a node and all its edges from a graph.
	 * 
	 * @param g
	 *            The graph
	 * @param nd
	 *            The to be removed node
	 */
	private static void removeNode(final Graph g, final Node nd) {
		Iterator<Edge> edges = nd.getEdgeIterator();
		Edge cur;
		while (edges.hasNext()) {
			cur = edges.next();
			g.removeEdge(cur);
		}
		g.removeNode(nd);

	}

	/**
	 * Gets the next nodes from a certain node.
	 * 
	 * @param graph
	 *            The graph containing the node
	 * @param id
	 *            ID of the node
	 * @return The next nodes
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
	 * Adds an edge to a graph.
	 * 
	 * @param graph
	 *            The graph.
	 * @param edge
	 *            The edge.
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
