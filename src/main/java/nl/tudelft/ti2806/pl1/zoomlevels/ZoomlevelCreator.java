package nl.tudelft.ti2806.pl1.zoomlevels;

import java.awt.Dimension;

import nl.tudelft.ti2806.pl1.DGraph.ConvertDGraph;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.reader.NodePlacer;

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
	 * Creates a graph with its mutations collapsed according to a score
	 * threshold.
	 * 
	 * @param threshold
	 *            The score threshold.
	 * @param viewSize
	 *            The view size.
	 * @return The created graph
	 */
	public Graph createGraph(final int threshold, final Dimension viewSize) {
		Graph ret = ConvertDGraph.convert(graph);
		ret = InDelCollapser.collapseInsertions(graph.getInsmutations(), ret);
		ret = InDelCollapser.collapseDeletions(graph.getDelmutations(), ret);
		ret = PointGraphConverter.collapseNodes(graph.getAllPointMutations(),
				ret, threshold);
		ret = HorizontalCollapser.horizontalCollapse(ret);
		NodePlacer.place(ret, viewSize);
		return ret;
	}
}
