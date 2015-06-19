package nl.tudelft.ti2806.pl1.zoomlevels;

import nl.tudelft.ti2806.pl1.DGraph.ConvertDGraph;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;

import org.graphstream.graph.Graph;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public final class ZoomlevelCreator {

	/** The data graph from which the zoom levels will be created. */
	private DGraph graph;

	/**
	 * Initialize the zoom level creator.
	 * 
	 * @param dataGraph
	 *            The data graph from which the zoom levels will be created.
	 */
	public ZoomlevelCreator(final DGraph dataGraph) {
		this.graph = dataGraph;
	}

	/**
	 * Creates a graph with its mutations collapsed according to a score
	 * threshold.
	 * 
	 * @param threshold
	 *            The score threshold.
	 * @return The created graph
	 */
	public Graph createGraph(final int threshold) {
		Graph ret = ConvertDGraph.convert(graph);
		ret = InDelCollapser.collapseInsertions(graph.getInsMutations(), ret,
				threshold);
		ret = InDelCollapser.collapseDeletions(graph.getDelMutations(), ret,
				threshold);
		ret = ComplexCollapser.collapseComplexMutations(
				graph.getComplexMutations(), ret);
		ret = PointGraphConverter.collapseNodes(graph.getAllPointMutations(),
				ret, threshold, graph.getSelected());
		ret = HorizontalCollapser.horizontalCollapse(ret);
		graph.setSelected(PointGraphConverter.findSelected(ret));
		return ret;
	}
}
