package nl.tudelft.ti2806.pl1.zoomlevels;

import java.util.Collection;
import java.util.Set;

import nl.tudelft.ti2806.pl1.DGraph.ConvertDGraph;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.gui.contentpane.ViewArea;
import nl.tudelft.ti2806.pl1.mutation.PointMutation;

import org.graphstream.graph.Graph;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public final class ZoomlevelCreator {

	/**
	 * The data graph from which the zoom levels will be created.
	 */
	private DGraph graph;

	/**
	 * @param dataGraph
	 *            The data graph from which the zoom levels will be created.
	 */
	public ZoomlevelCreator(final DGraph dataGraph) {
		this.graph = dataGraph;
	}

	/**
	 * A zoom level that will collapse synonymous point mutations.
	 * 
	 * @param va
	 *            The view area.
	 * @return A graph with its synonymous point mutations collapsed.
	 */
	public Graph removeSYN(final ViewArea va) {
		Graph ret = null;
		Collection<PointMutation> mutations = graph.getAllPointMutations();
		for (PointMutation mut : mutations) {
			if (mut.isSynonymous()) {
				Set<String> muts = mut.getNodes();
				ret = PointGraphConverter.collapsePointMutations(
						ConvertDGraph.convert(graph, va), muts);
			}
		}
		return ret;
	}

	/**
	 * @param vg
	 *            The original graph.
	 * @return A graph in which all point mutations are collapsed.
	 */
	public Graph removeAllPMs(final Graph vg) {
		return PointGraphConverter.collapsePointMutations(vg);
	}

}
