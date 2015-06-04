package nl.tudelft.ti2806.pl1.DGraph;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.ti2806.pl1.gui.contentpane.ViewArea;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * Converts a DGraph into a graphstream graph.
 * 
 * @author mark
 * 
 */
public final class ConvertDGraph {

	/**
	 * When this threshold is surpassed, the length instead of the label will be
	 * shown.
	 */
	private static final int LABEL_LENGTH_THRESHOLD = 10;

	/**
	 */
	private ConvertDGraph() {
	}

	/**
	 * Converts a data graph into a graphstream graph.
	 * 
	 * @param dgraph
	 *            The data graph to be converted
	 * @param va
	 *            The area of the graph to convert.
	 * @return a visual graph
	 */
	public static Graph convert(final DynamicGraph dgraph, final ViewArea va) {
		Graph graph = new SingleGraph("");
		Set<DEdge> edges = new HashSet<DEdge>();
		for (DNode n : dgraph.getDNodes(va)) {
			edges.addAll(n.getAllEdges());
			String id = String.valueOf(n.getId());
			Node gn = graph.addNode(id);
			gn.addAttribute("x", n.getX());
			gn.addAttribute("y", n.getY());
			gn.addAttribute("ui.label", checkLabelLength(n.getContent()));
			gn.addAttribute("ui.class", "common");
			gn.addAttribute("ui.color", 1 - n.getPercUnknown());
		}
		for (DEdge edge : edges) {
			String src = String.valueOf(edge.getStartNode().getId());
			String target = String.valueOf(edge.getEndNode().getId());
			graph.addEdge(src + target, src, target, true);
		}
		return graph;
	}

	/**
	 * Compares the label with a threshold length and returns the label if it's
	 * smaller than the threshold, otherwise it returns the length.
	 * 
	 * @param label
	 *            The label to be checked.
	 * @return Length of label if bigger than threshold, otherwise the label
	 *         itself.
	 */
	private static String checkLabelLength(final String label) {
		int l = label.length();
		if (l > LABEL_LENGTH_THRESHOLD) {
			return String.valueOf(l);
		}
		return label;
	}

}
