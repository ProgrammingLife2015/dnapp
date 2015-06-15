package nl.tudelft.ti2806.pl1.mutation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;

/**
 * 
 * @author Justin, Maarten
 * @since 10-6-2015
 */
public final class MutationFinder {

	/**
	 * Reference genome name.
	 */
	private static final String REFERENCE_GENOME = "TKK_REF";

	/**
	 */
	private MutationFinder() {
	}

	/**
	 * Finds the simple Insertion mutations of a DGraph.
	 * 
	 * @param graph
	 *            The DGraph.
	 * @return A collection of the Insertion mutations.
	 */
	public static Collection<InsertionMutation> findInsertionMutations(
			final DGraph graph) {
		ArrayList<InsertionMutation> ins = new ArrayList<InsertionMutation>();
		Collection<DNode> nodes = graph.getReference(REFERENCE_GENOME);
		for (DNode node : nodes) {
			for (DNode next : node.getNextNodes()) {
				if (!(next.getSources().contains(REFERENCE_GENOME))) {
					Collection<DNode> nextnodes = next.getNextNodes();
					if (nextnodes.size() == 1) {
						DNode endnode = nextnodes.iterator().next();
						if (node.getNextNodes().contains(endnode)) {
							ins.add(new InsertionMutation(node.getId(), endnode
									.getId(), next.getId(), graph
									.getReferenceGeneStorage()));
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
	 * @return A collection of the Deletion mutations.
	 */
	public static Collection<DeletionMutation> findDeletionMutations(
			final DGraph graph) {
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
				dels.add(new DeletionMutation(node.getId(), endnode.getId(),
						graph.getReferenceGeneStorage()));
			}
		}
		return dels;
	}

	/**
	 * Finds the complex mutations of a DGraph.
	 * 
	 * @param graph
	 *            The DGraph.
	 * @return A collection of the complex mutations.
	 */
	public static Collection<ComplexMutation> findComplexMutations(
			final DGraph graph) {
		Collection<ComplexMutation> ins = new ArrayList<ComplexMutation>();
		Collection<DNode> nodes = graph.getReference(REFERENCE_GENOME);
		HashSet<DNode> visitednodes = new HashSet<DNode>();
		for (DNode node : nodes) {
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
							}
						}
					}
				}
			}
			if (inNodes.size() != 1) {
				ins.add(new ComplexMutation(node.getId(), null, inNodes, graph
						.getReferenceGeneStorage()));
			}
		}
		return ins;
	}
}
