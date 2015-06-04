package nl.tudelft.ti2806.pl1.zoomlevels;

import java.util.Collection;
import java.util.Set;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.mutation.MutatedGraph;
import nl.tudelft.ti2806.pl1.mutation.PointMutation;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public final class ZoomlevelCreator {

	/**
	 * 
	 */
	private DGraph dGraph;

	/**
	 * 
	 * @param graph
	 *            The data graph.
	 */
	public ZoomlevelCreator(final DGraph graph) {
		this.dGraph = graph;
	}

	/**
	 * 
	 * @param graph
	 *            The graph.
	 * @return A graph with its synonymous point mutations collapsed.
	 */
	public Graph removeSYN(final MutatedGraph graph) {
		Graph ret = new SingleGraph("");
		Collection<PointMutation> mutations = graph.getAllPointMutations();
		for (PointMutation mut : mutations) {
			if (mut.isSynonymous()) {
				Set<Integer> nodes = mut.getNodes();
				// TODO collapse nodes in ret.
			}
		}
		return ret;
	}

}
