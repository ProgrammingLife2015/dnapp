package nl.tudelft.ti2806.pl1.zoomlevels;

import java.util.Collection;

import nl.tudelft.ti2806.pl1.mutation.ComplexMutation;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 * 
 * @author Justin
 * @since 15-6-2015
 */
public final class ComplexCollapser {

	/**
	 * 
	 */
	private ComplexCollapser() {
	}

	/**
	 * Collapses the complex mutations of the graph.
	 * 
	 * @param mutations
	 *            The complex mutations.
	 * @param graph
	 *            The graph.
	 * @return The graph with the complex mutations collapsed.
	 */
	public static Graph collapseComplexMutations(
			final Collection<ComplexMutation> mutations, final Graph graph) {
		for (ComplexMutation mut : mutations) {
			collapseComplexMutation(mut, graph);
		}
		return graph;
	}

	/**
	 * Collapses a complex mutation of the graph.
	 * 
	 * @param mut
	 *            The complex mutation.
	 * @param graph
	 *            The graph.
	 */
	public static void collapseComplexMutation(final ComplexMutation mut,
			final Graph graph) {
		Collection<Integer> nodes = mut.getInNodes();
		for (int n : nodes) {
			Node node = graph.getNode("" + n);
			graph.removeNode(node);
		}
	}
}
