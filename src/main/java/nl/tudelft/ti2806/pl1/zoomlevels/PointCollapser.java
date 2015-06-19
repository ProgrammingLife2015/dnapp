package nl.tudelft.ti2806.pl1.zoomlevels;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
public final class PointCollapser {

	/**
	 * 
	 */
	private PointCollapser() {

	}

	/**
	 * Collapses the nodegroups in the graph.
	 * 
	 * @param pointmutations
	 *            The pointmutations in the graph.
	 * @param gsg
	 *            The graph we want to collapse on.
	 * @param threshold
	 *            The threshold for collapsing pointmutations.
	 * @param string
	 *            The selected node in the graph.
	 * @return The new pointcollapsedgraph.
	 */
	public static Graph collapseNodes(
			final Collection<PointMutation> pointmutations, final Graph gsg,
			final double threshold, final String string) {
		for (PointMutation pointmutation : pointmutations) {
			if (pointmutation.getScore() < threshold) {
				collapsePointMutation(gsg, pointmutation, string);
			}
		}
		return gsg;
	}

	/**
	 * 
	 * @param gsg
	 *            The Graphstreamgraph which will contain the new
	 *            representation.
	 * @param pointmutation
	 *            The pointmutation we are trying to collapse.
	 * @param string
	 *            The selected node in the graph.
	 */
	private static void collapsePointMutation(final Graph gsg,
			final PointMutation pointmutation, final String string) {
		HashSet<Integer> nodeids = new HashSet<Integer>();
		StringBuilder sb = new StringBuilder();
		int x = 0;
		double y = 0.0;
		if (checkViable(gsg, pointmutation)) {
			for (int nodeid : pointmutation.getNodes()) {
				nodeids.add(nodeid);
				StringBuilder sb2 = new StringBuilder();
				sb2.append(nodeid);
				Node node = gsg.getNode(sb2.toString());
				if (node != null) {
					x = node.getAttribute("x");
					y += (int) node.getAttribute("y");
					if (node.getLeavingEdgeSet().size() == 1
							&& node.getEnteringEdgeSet().size() == 1) {
						gsg.removeNode(node);
					} else {
						removeEdges(node, pointmutation, gsg);
					}
				}
				sb.append(nodeid);
				sb.append("/");
			}
			y /= pointmutation.getNodes().size();
			makeNewNode(gsg, x, y, sb.toString(), pointmutation, nodeids,
					string);
		}
	}

	/**
	 * Removes the old edges when a pointmutation is being collapsed.
	 * 
	 * @param node
	 *            The nodes to which the old edges belong.
	 * @param pointmutation
	 *            The pointmutation.
	 * @param gsg
	 *            The graph
	 */
	private static void removeEdges(final Node node,
			final PointMutation pointmutation, final Graph gsg) {
		if (node.getLeavingEdgeSet().size() > 1) {
			boolean stop = false;
			Iterator<Edge> leavingedges = node.getLeavingEdgeIterator();
			while (leavingedges.hasNext() && !stop) {
				Edge edge = leavingedges.next();
				if (edge.getNode1().getId()
						.equals(pointmutation.getPostNode() + "")) {
					gsg.removeEdge(edge);
					stop = true;
				}
			}
		}
		if (node.getEnteringEdgeSet().size() > 1) {
			boolean stop = false;
			Iterator<Edge> enteringedges = node.getEnteringEdgeIterator();
			while (enteringedges.hasNext() && !stop) {
				Edge edge = enteringedges.next();
				if (edge.getNode0().getId()
						.equals(pointmutation.getPreNode() + "")) {
					gsg.removeEdge(edge);
					stop = true;
				}
			}
		}
	}

	/**
	 * Checks whether the pointmutation can still be collapsed.
	 * 
	 * @param gsg
	 *            The graph
	 * @param pointmutation
	 *            The pointmutation
	 * @return True iff the pre and post nodes of the mutation still exist in
	 *         the graph and every nodes that would be collapsed has an edge
	 *         towards the pre and post nodes.
	 */
	private static boolean checkViable(final Graph gsg,
			final PointMutation pointmutation) {
		Node preNode = gsg.getNode(pointmutation.getPreNode() + "");
		Node postNode = gsg.getNode(pointmutation.getPostNode() + "");
		if (preNode == null || postNode == null) {
			return false;
		}
		Set<Node> nodes = new HashSet<Node>();
		for (Integer n : pointmutation.getNodes()) {
			nodes.add(gsg.getNode(n + ""));
		}
		for (Node n : nodes) {
			if (n != null
					&& (!(preNode.hasEdgeBetween(n)) || !(n
							.hasEdgeBetween(postNode)))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Makes a new node in the graph.
	 * 
	 * @param gsg
	 *            The graph.
	 * @param x
	 *            The x-coordinate.
	 * @param y
	 *            The y-coordinate.
	 * @param newId
	 *            The ID of the new node.
	 * @param pointmutation
	 *            Contains the nodes that will connect to the new nods.
	 * @param nodeids
	 *            The nodes that this new node contains.
	 * @param string
	 *            The selected node in the graph.
	 */
	private static void makeNewNode(final Graph gsg, final int x,
			final double y, final String newId,
			final PointMutation pointmutation, final HashSet<Integer> nodeids,
			final String string) {
		Node newnode = gsg.addNode("COLLAPSED_" + newId
				+ pointmutation.getPostNode());
		newnode.addAttribute("x", x);
		newnode.addAttribute("y", y);
		boolean selectedbool = false;
		for (int id : nodeids) {
			selectedbool = selectedbool || String.valueOf(id).equals(string);
		}
		if (selectedbool) {
			newnode.addAttribute("ui.class", "selected");
		} else {
			newnode.addAttribute("ui.class", "collapsed");
		}
		newnode.addAttribute("ui.label", nodeids.size());
		newnode.addAttribute("collapsed", nodeids);
		newnode.addAttribute("contentsize", 1);
		gsg.addEdge("CEDGE_" + pointmutation.getPreNode() + "/" + newId,
				pointmutation.getPreNode() + "", newnode.getId(), true);
		gsg.addEdge("CEDGE_" + newId + "/" + pointmutation.getPostNode(),
				newnode.getId(), pointmutation.getPostNode() + "", true);
	}

	/**
	 * Finds the id of the selected node.
	 * 
	 * @param ret
	 *            Graph in which to search the selected node.
	 * @return the node id if a node is selected, otherwise Integer.MIN_VALUE.
	 */
	public static String findSelected(final Graph ret) {
		for (Node n : ret.getEachNode()) {
			if (n.getAttribute("ui.class").equals("selected")) {
				return n.getId();
			}
		}
		return String.valueOf(Integer.MIN_VALUE);
	}
}
