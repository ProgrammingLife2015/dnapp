package nl.tudelft.ti2806.pl1.zoomlevels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.mutation.PointMutation;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 * This class converts the standard graph into a PointGraph.
 * 
 * @author Justin, Marissa
 * @since 14-05-2015
 *
 */
public final class PointGraphConverter {

	/**
	 * 
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
	public static Collection<PointMutation> getPointMutations(final DGraph graph) {
		Collection<PointMutation> pointmutations = new HashSet<PointMutation>();
		Collection<DNode> dnodes = graph.getNodes().values();
		for (DNode node : dnodes) {
			Iterator<DEdge> leaving = node.getOutEdges().iterator();
			ArrayList<Integer> muts = new ArrayList<Integer>();
			while (leaving.hasNext()) {
				DEdge out = leaving.next();
				DNode outnode = out.getEndNode();
				String content = outnode.getContent();
				if (content.length() == 1) {
					muts.add(outnode.getId());
				}
			}
			if (muts.size() > 1) {
				HashMap<Integer, Set<Integer>> nodegroups = makeNodeGroups(
						muts, graph);
				pointmutations.addAll(createPointMutations(node.getId(),
						nodegroups));
			}
		}
		return pointmutations;
	}

	/**
	 * 
	 * @param begin
	 *            First id.
	 * @param nodegroups
	 *            HashMap of end id with pointmutations.
	 * @return Collection of pointmutations.
	 */
	private static Collection<PointMutation> createPointMutations(
			final int begin, final HashMap<Integer, Set<Integer>> nodegroups) {
		Collection<PointMutation> pointmutations = new HashSet<PointMutation>();
		for (int end : nodegroups.keySet()) {
			Set<Integer> group = nodegroups.get(end);
			if (group.size() > 1) {
				pointmutations.add(new PointMutation(begin, group, end));
			}
		}
		return pointmutations;
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
	private static HashMap<Integer, Set<Integer>> makeNodeGroups(
			final ArrayList<Integer> muts, final DGraph g) {
		HashMap<Integer, Set<Integer>> nodegroups = new HashMap<Integer, Set<Integer>>();
		for (Integer n : muts) {
			ArrayList<Integer> ns = getNextNodes(g, n);
			for (Integer nd : ns) {
				if (nodegroups.containsKey(nd)) {
					nodegroups.get(nd).add(n);
				} else {
					Set<Integer> nodegroup = new HashSet<Integer>();
					nodegroup.add(n);
					nodegroups.put(nd, nodegroup);
				}
			}
		}
		return nodegroups;
	}

	/**
	 * Collapses the nodegroups in the graph.
	 * 
	 * @param nodegroups
	 *            The nodegroups
	 * @param g
	 *            The graph we want to collapse on.
	 */
	private static void collapseNodes(
			final HashMap<Node, ArrayList<String>> nodegroups, final Graph g) {
		for (Node end : nodegroups.keySet()) {
			ArrayList<String> nodegroup = nodegroups.get(end);
			if (nodegroup.size() == 1) {
				Node nd = g.getNode(nodegroup.get(0));
				for (Edge edge : nd.getEnteringEdgeSet()) {
					Node in = edge.getNode0();
					g.addEdge("collapsed: " + edge.getId() + " " + end.getId(),
							in, end, true);
				}
				removeNode(g, nd, end);
			} else {
				HashMap<String, String> content = new HashMap<String, String>();
				String newId = "collapsed:";
				for (String id : nodegroup) {
					Node nd = g.getNode(id);
					newId += " " + id;
				}
				newId += " ";
				while (g.getNode(newId) != null) {
					newId += 1;
				}
				addNewCollapsedNode(newId, g, nodegroup, content, end);
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
	 *            The nodegroup that is collapsed into the new node.
	 * @param content
	 *            The content of the new node.
	 * @param end
	 *            The node which this group is going to.
	 */
	private static void addNewCollapsedNode(final String newId, final Graph g,
			final ArrayList<String> nodegroup,
			final HashMap<String, String> content, final Node end) {
		g.addNode(newId);
		String temp = nodegroup.get(0);
		Node tempnode = g.getNode(temp);
		Node collapsednode = g.getNode(newId);
		collapsednode.addAttribute("start", tempnode.getAttribute("start"));
		collapsednode.addAttribute("depth", tempnode.getAttribute("depth"));
		collapsednode.addAttribute("end", tempnode.getAttribute("end"));
		collapsednode.addAttribute("x", tempnode.getAttribute("x"));
		collapsednode.addAttribute("content", content);
		String uilabel = tempnode.getAttribute("ui.label");
		if (!uilabel.matches("\\d+")) {
			uilabel = uilabel.length() + "";
		}
		collapsednode.addAttribute("ui.label", uilabel);
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
							+ Math.random(), sourcenode, collapsednode, true);
				}
			}
			for (Edge edge : oldnode.getLeavingEdgeSet()) {
				if (edge != null && edge.getNode1().equals(end)) {
					g.removeEdge(edge);
				}
			}
		}
		g.addEdge("collapsed: " + collapsednode.getId() + " " + end.getId(),
				collapsednode, end, true);
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
	private static ArrayList<Integer> getNextNodes(final DGraph graph,
			final int id) {
		ArrayList<Integer> nodes = new ArrayList<Integer>();
		DNode n = graph.getDNode(id);
		Iterator<DEdge> edges = n.getOutEdges().iterator();
		while (edges.hasNext()) {
			DEdge next = edges.next();
			nodes.add(next.getEndNode().getId());
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
