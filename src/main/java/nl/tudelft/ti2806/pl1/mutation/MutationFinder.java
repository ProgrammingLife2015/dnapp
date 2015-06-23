package nl.tudelft.ti2806.pl1.mutation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.phylotree.BinaryTree;

/**
 * @author Justin, Maarten, Mark
 * @since 10-6-2015
 */
public final class MutationFinder {

	/** Reference genome name. */
	private static final String REFERENCE_GENOME = "TKK_REF";

	/**
	 */
	private MutationFinder() {
	}

	/**
	 * This method returns the amount of groups of nodes which share a common
	 * direct ancestor according to the phylogenetic tree.
	 * 
	 * @param graph
	 *            The graph.
	 * @param id1
	 *            The begin node id.
	 * @param id2
	 *            The end node id.
	 * @param root
	 *            The root node of the phylogenetic tree.
	 * @return The amount of groups of nodes which share a common parent
	 *         according to the phylogenetic tree.
	 */
	public static int getAffectedNodeGroupsCount(final DGraph graph,
			final int id1, final int id2, final BinaryTree root) {
		Set<String> sources1 = graph.getDNode(id1).getSources();
		Set<String> sources2 = graph.getDNode(id2).getSources();
		Set<String> sources = intersect(sources1, sources2);
		return BinaryTree.findGroups(sources, root).size();
	}

	/**
	 * This method finds the intersection between 2 hash sets.
	 * 
	 * @param set1
	 *            The first hash set
	 * @param set2
	 *            The second hash set
	 * @return The intersection between set1 and set2
	 */
	protected static Set<String> intersect(final Set<String> set1,
			final Set<String> set2) {
		Set<String> set = new HashSet<String>();
		if (set1.size() < set2.size()) {
			for (String s : set1) {
				set.add(s);
			}
			set.retainAll(set2);
		} else {
			for (String s : set2) {
				set.add(s);
			}
			set.retainAll(set1);
		}
		return set;
	}

	/**
	 * Finds the Point mutations of a DGraph.
	 * 
	 * @param graph
	 *            The data graph.
	 * @param tree
	 *            The binary tree.
	 * @return A collection of the Point mutations.
	 */
	public static Collection<PointMutation> findPointMutations(
			final DGraph graph, final BinaryTree tree) {
		return PointMutationFinder.findPointMutations(graph);
	}

	/**
	 * Finds the simple Insertion mutations of a DGraph.
	 * 
	 * @param graph
	 *            The DGraph.
	 * @param tree
	 *            The Binarytree.
	 * @return A collection of the Insertion mutations.
	 */
	public static Collection<InsertionMutation> findInsertionMutations(
			final DGraph graph, final BinaryTree tree) {
		ArrayList<InsertionMutation> ins = new ArrayList<InsertionMutation>();
		Collection<DNode> nodes = graph.getReference(REFERENCE_GENOME);
		for (DNode node : nodes) {
			for (DNode next : node.getNextNodes()) {
				if (!(next.getSources().contains(REFERENCE_GENOME))) {
					Collection<DNode> nextnodes = next.getNextNodes();
					if (nextnodes.size() == 1) {
						DNode endnode = nextnodes.iterator().next();
						if (node.getNextNodes().contains(endnode)) {
							InsertionMutation mut = new InsertionMutation(
									node.getId(), endnode.getId(),
									node.getStart(), node.getEnd(),
									graph.getReferenceGeneStorage(),
									next.getId());
							ins.add(mut);
						}
					}
				}
			}
		}
		return ins;
	}

	/**
	 * Finds the simple Deletion mutations of a DGraph.
	 * 
	 * @param graph
	 *            The DGraph.
	 * @param tree
	 *            The Binarytree.
	 * @return A collection of the Deletion mutations.
	 */
	public static Collection<DeletionMutation> findDeletionMutations(
			final DGraph graph, final BinaryTree tree) {
		ArrayList<DeletionMutation> dels = new ArrayList<DeletionMutation>();
		Collection<DNode> nodes = graph.getReference(REFERENCE_GENOME);
		for (DNode node : nodes) {
			boolean isDeletion = false;
			int countRefNodes = 0;
			int maxdepth = 0;
			DNode endnode = null;
			for (DNode next : node.getNextNodes()) {
				if (next.getSources().contains(REFERENCE_GENOME)) {
					if (next.getDepth() > maxdepth) {
						maxdepth = next.getDepth();
						endnode = next;
					}
					countRefNodes++;
				}
				if (countRefNodes > 1) {
					isDeletion = true;
				}
			}
			if (isDeletion) {
				DeletionMutation mut = new DeletionMutation(node.getId(),
						endnode.getId(), node.getStart(), node.getEnd(),
						graph.getReferenceGeneStorage());
				dels.add(mut);
			}
		}
		return dels;
	}

	/**
	 * Finds the complex mutations of a DGraph.
	 * 
	 * @param graph
	 *            The DGraph.
	 * @param tree
	 *            The Binarytree.
	 * @return A collection of the complex mutations.
	 */
	public static Collection<ComplexMutation> findComplexMutations(
			final DGraph graph, final BinaryTree tree) {
		Collection<ComplexMutation> ins = new ArrayList<ComplexMutation>();
		Collection<DNode> nodes = graph.getReference(REFERENCE_GENOME);
		HashSet<DNode> visitednodes = new HashSet<DNode>();
		for (DNode node : nodes) {
			Set<Integer> srcNodes = new HashSet<Integer>();
			Set<Integer> inNodes = new HashSet<Integer>();
			Queue<DNode> q = new LinkedList<DNode>();
			for (DNode next : node.getNextNodes()) {
				if (!(next.getSources().contains(REFERENCE_GENOME))) {
					if (!visitednodes.contains(next)) {
						q.add(next);
						visitednodes.add(next);
					}
					inNodes.add(next.getId());
					while (!q.isEmpty()) {
						DNode n = q.remove();
						for (DNode qnext : n.getNextNodes()) {
							if (!qnext.getSources().contains(REFERENCE_GENOME)) {
								if (!visitednodes.contains(qnext)) {
									q.add(qnext);
									visitednodes.add(qnext);
								}
								inNodes.add(qnext.getId());
							} else {
								srcNodes.add(qnext.getId());
							}
						}
					}
				} else {
					srcNodes.add(next.getId());
				}
			}
			int srcId = getSourceSinkNode(srcNodes, graph);
			if (node.getOutEdges().size() > 0
					&& (inNodes.size() > 1 || (inNodes.size() == 1 && graph
							.getDNode(inNodes.iterator().next()).getContent()
							.length() > 1))) {
				ComplexMutation mut = new ComplexMutation(node.getId(), srcId,
						node.getStart(), node.getEnd(),
						graph.getReferenceGeneStorage(), inNodes);
				ins.add(mut);
			}
		}
		return ins;
	}

	/**
	 * Get the Source Sink node of a set of nodes containing the reference
	 * genome.
	 * 
	 * @param srcNodes
	 *            The nodes containing the reference genome.
	 * @param g
	 *            The graph
	 * @return The ID of the Source Sink node.
	 */
	private static int getSourceSinkNode(final Set<Integer> srcNodes,
			final DGraph g) {
		DNode srcSink = null;
		int max = 0;
		for (Integer n : srcNodes) {
			DNode node = g.getDNode(n);
			if (node.getDepth() > max) {
				max = node.getDepth();
				srcSink = node;
			}
		}
		int srcId = 0;
		if (srcSink != null) {
			srcId = srcSink.getId();
		}
		return srcId;
	}
}
