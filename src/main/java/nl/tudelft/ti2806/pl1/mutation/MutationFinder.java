package nl.tudelft.ti2806.pl1.mutation;

import java.util.ArrayList;
import java.util.Collection;

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
	 * Finds the InDel mutations of a DGraph.
	 * 
	 * @param graph
	 *            The DGraph.
	 * @return A collection of the InDel mutations.
	 */
	public static Collection<InsertionMutation> findInDelMutations(
			final DGraph graph) {
		ArrayList<InsertionMutation> ins = new ArrayList<InsertionMutation>();
		Collection<DNode> nodes = graph.getReference(REFERENCE_GENOME);
		for (DNode node : nodes) {
			for (DNode next : node.getNextNodes()) {
				if (!(next.getSources().contains(REFERENCE_GENOME))) {
					Collection<DNode> nextnodes = next.getNextNodes();
					if (nextnodes.size() == 1) {
						DNode endnode = nextnodes.iterator().next();
						ins.add(new InsertionMutation(node.getId(), next
								.getId(), endnode.getId()));
					}
				}
			}
		}

		return ins;
	}

}
