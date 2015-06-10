package nl.tudelft.ti2806.pl1.zoomlevels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import nl.tudelft.ti2806.pl1.DGraph.ConvertDGraph;
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
	 * @param g
	 *            The graph we want to collapse on.
	 */
	private static void collapseNodes(final DGraph dgraph,
			final double threshold) {
		Graph gsg = ConvertDGraph.convert(dgraph);
		Collection<PointMutation> pointmutations = dgraph
				.getAllPointMutations();
		for (PointMutation pointmutation : pointmutations) {
			if (pointmutation.getScore() < threshold) {
				collapsePointMutation(dgraph, gsg, pointmutation);
			}
		}
		// for (Node end : nodegroups.keySet()) {
		// ArrayList<String> nodegroup = nodegroups.get(end);
		// if (nodegroup.size() == 1) {
		// Node nd = g.getNode(nodegroup.get(0));
		// for (Edge edge : nd.getEnteringEdgeSet()) {
		// Node in = edge.getNode0();
		// g.addEdge("collapsed: " + edge.getId() + " " + end.getId(),
		// in, end, true);
		// }
		// removeNode(g, nd, end);
		// } else {
		// HashMap<String, String> content = new HashMap<String, String>();
		// String newId = "collapsed:";
		// for (String id : nodegroup) {
		// Node nd = g.getNode(id);
		// newId += " " + id;
		// }
		// newId += " ";
		// while (g.getNode(newId) != null) {
		// newId += 1;
		// }
		// addNewCollapsedNode(newId, g, nodegroup, content, end);
		// for (String id : nodegroup) {
		// Node nd = g.getNode(id);
		// removeNode(g, nd);
		// }
		// }
		// }
	}

	private static void collapsePointMutation(final DGraph graph,
			final Graph gsg, final PointMutation pointmutation) {
		HashSet<Integer> nodeids = new HashSet<Integer>();
		String newId = "";
		int x = 0;
		double y = 0.0;
		for (int nodeid : pointmutation.getNodes()) {
			nodeids.add(nodeid);
			Node node = gsg.getNode(nodeid);
			x = node.getAttribute("x");
			y += (Double) node.getAttribute("y");
			if (node.getLeavingEdgeSet().size() == 1
					&& node.getEnteringEdgeSet().size() == 1) {
				gsg.removeEdge(node.getLeavingEdgeIterator().next());
				gsg.removeEdge(node.getEnteringEdgeIterator().next());
				gsg.removeNode(node);
			} else {
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
					Iterator<Edge> enteringedges = node
							.getEnteringEdgeIterator();
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
			newId += nodeid + "/";
		}
		y /= pointmutation.getNodes().size();
		Node newnode = gsg.addNode("COLLAPSED_" + newId
				+ pointmutation.getPostNode());
		newnode.addAttribute("x", x);
		newnode.addAttribute("y", y);
		newnode.addAttribute("ui.class", "collapsed");
		newnode.addAttribute("collapsed", nodeids);
		gsg.addEdge("CEDGE_" + pointmutation.getPreNode() + "/" + newId,
				pointmutation.getPreNode() + "", newnode.getId());
		gsg.addEdge("CEDGE_" + newId + "/" + pointmutation.getPostNode(),
				newnode.getId(), pointmutation.getPostNode() + "");
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
