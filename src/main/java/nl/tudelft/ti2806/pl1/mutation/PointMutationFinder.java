package nl.tudelft.ti2806.pl1.mutation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;
import nl.tudelft.ti2806.pl1.graph.DEdge;
import nl.tudelft.ti2806.pl1.graph.DGraph;
import nl.tudelft.ti2806.pl1.graph.DNode;

/**
 * Finds Pointmutations.
 * 
 * @author Justin, Marissa
 * @since 14-05-2015
 */
public final class PointMutationFinder {

	/**
	 * Finds Pointmutation.
	 */
	private PointMutationFinder() {

	}

	/**
	 * Converts the standard graph into a graph representing a zoom level where
	 * small point mutations are collapsed.
	 * 
	 * @param graph
	 *            The initial graph of the original zoom level.
	 * @return The graph with collapsed point mutations.
	 */
	public static Collection<PointMutation> findPointMutations(
			final DGraph graph) {
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
						nodegroups, graph, graph.getReferenceGeneStorage()));
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
	 * @param graph
	 *            The graph.
	 * @param rgs
	 *            The storage containing all the interesting gene information.
	 * 
	 * @return Collection of pointmutations.
	 */
	private static Collection<PointMutation> createPointMutations(
			final int begin, final HashMap<Integer, Set<Integer>> nodegroups,
			final DGraph graph, final ReferenceGeneStorage rgs) {
		Collection<PointMutation> pointmutations = new HashSet<PointMutation>();
		for (Entry<Integer, Set<Integer>> end : nodegroups.entrySet()) {
			Set<Integer> group = end.getValue();
			if (group.size() > 1) {
				int position = graph.getDNode(group.iterator().next())
						.getStart();
				pointmutations.add(new PointMutation(begin, end.getKey(),
						position, position, rgs, group));
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
}
