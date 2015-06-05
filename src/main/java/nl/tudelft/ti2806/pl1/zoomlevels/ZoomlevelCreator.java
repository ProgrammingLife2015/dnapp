package nl.tudelft.ti2806.pl1.zoomlevels;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.gui.contentpane.ViewArea;

import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

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
		// Collection<PointMutation> mutations = graph.getAllPointMutations();
		// for (PointMutation mut : mutations) {
		// if (mut.isSynonymous()) {
		// Set<String> muts = mut.getNodes();
		// ret = PointGraphConverter.collapsePointMutations(
		// ConvertDGraph.convert(graph, va), muts);
		// }
		// }
		return ret;
	}

	/**
	 * @param vg
	 *            The original graph.
	 * @return A graph in which all point mutations are collapsed.
	 */
	public Graph removeAllPMs(final Graph vg) {
		Graph g = PointGraphConverter.collapsePointMutations(vg);
		BreadthFirstIterator<Node> iter = new BreadthFirstIterator<Node>(
				g.getNode("-2"));
		while (iter.hasNext()) {
			System.out.println(iter.next().getEdgeSet().size());
		}
		return g;
	}

}
