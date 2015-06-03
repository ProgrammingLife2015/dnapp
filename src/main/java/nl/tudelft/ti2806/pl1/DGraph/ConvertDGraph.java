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
	 * When this threshold is surpassed, the length instead of the label will be
	 * shown.
	 */
	private static final int LABEL_LENGTH_THRESHOLD = 10;

	/**
	 * Converts a DGraph into a graphstream graph.
	 * 
	 * @param dgraph
	 *            The dgraph to be converted
	 * @return Graphstream graph
	 */
	public static Graph convert(final DGraph dgraph) {
		Graph graph = new SingleGraph("DNApp");
		String s = "";
		for (DNode n : dgraph.getNodes().values()) {
			if (n.getSources().contains("TKK_REF"))
				s += n.getContent();
			String id = String.valueOf(n.getId());
			graph.addNode(id);
			graph.getNode(id).addAttribute("x", n.getX());
			graph.getNode(id).addAttribute("y", n.getY());
			graph.getNode(id).addAttribute("ui.label",
					checkLabelLength(n.getContent()));
			graph.getNode(id).addAttribute("ui.class", "common");
			graph.getNode(id).addAttribute("ui.color", 1 - n.getPercUnknown());
		}
		for (DEdge edge : dgraph.getEdges()) {
			String src = String.valueOf(edge.getStartNode().getId());
			String target = String.valueOf(edge.getEndNode().getId());
			graph.addEdge(src + target, src, target, true);
		}
		System.out.println(s);
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
