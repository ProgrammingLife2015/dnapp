package nl.tudelft.ti2806.pl1.DGraph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * Converts a DGraph into a graphstream graph.
 * 
 * @author mark
 *
 */
public final class ConvertDGraph {

	/**
	 * 
	 */
	private ConvertDGraph() {

	}

	/**
	 * Converts a DGraph into a graphstream graph.
	 * 
	 * @param dgraph
	 *            The dgraph to be converted
	 * @return Graphstream graph
	 */
	public static Graph convert(final DGraph dgraph) {
		Graph graph = new SingleGraph("DNApp");
		for (DNode n : dgraph.getNodes().values()) {
			String id = String.valueOf(n.getId());
			graph.addNode(id);
			graph.getNode(id).addAttribute("x", n.getX());
			graph.getNode(id).addAttribute("y", n.getY());
			graph.getNode(id).addAttribute("ui.label", id);
		}
		for (DEdge edge : dgraph.getEdges()) {
			String src = String.valueOf(edge.getStartNode().getId());
			String target = String.valueOf(edge.getEndNode().getId());
			graph.addEdge(src + target, src, target);
		}
		return graph;
	}
}
