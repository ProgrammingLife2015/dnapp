package nl.tudelft.ti2806.pl1.DGraph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import nl.tudelft.ti2806.pl1.gui.contentpane.ViewArea;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * Contains methods to create a visual graph from a data graph.
 * 
 * @author Mark, Maarten, Justin
 * 
 * @see Graph
 * @see DGraph
 */
public final class ConvertDGraph {

	/**
	 * When this threshold is surpassed, the length instead of the content of a
	 * node will be displayed on or next to the node.
	 */
	private static final int LABEL_LENGTH_THRESHOLD = 10;

	/**
	 */
	private ConvertDGraph() {
	}

	/**
	 * Converts a data graph into a visual graph.
	 * 
	 * 
	 * @param dgraph
	 *            The data graph to convert.
	 * @return The visual graph containing all the nodes from the data graph.
	 */
	public static Graph convert(final DGraph dgraph) {
		return convert(dgraph, new ViewArea(Integer.MIN_VALUE,
				Integer.MAX_VALUE));
	}

	/**
	 * Converts a part of a data graph into a visual graph.
	 * 
	 * @param dgraph
	 *            The data graph to convert.
	 * @param va
	 *            The area of the graph to convert.
	 * @return The visual graph containing the nodes positioned in the given
	 *         view area of the data graph.
	 */
	public static Graph convert(final DGraph dgraph, final ViewArea va) {
		Graph graph = new SingleGraph("");
		Set<DEdge> edges = new HashSet<DEdge>();
		for (DNode n : dgraph.getDNodes(va)) {
			edges.addAll(n.getAllEdges());
			String id = String.valueOf(n.getId());
			Node gn = graph.addNode(id);
			gn.addAttribute("x", n.getX());
			gn.addAttribute("y", n.getY());
			gn.addAttribute("ui.label", checkLabelLength(n.getContent()));
			if (n.getId() == dgraph.getSelected()) {
				gn.addAttribute("ui.class", "selected");
				gn.addAttribute("oldclass", "common");
			} else {
				gn.addAttribute("ui.class", "common");
			}
			gn.addAttribute("ui.color", 1 - n.getPercUnknown());
			gn.addAttribute("contentsize", n.getContent().length());
			gn.addAttribute("collapsed",
					new HashSet<Integer>(Arrays.asList(n.getId())));
		}
		for (DEdge edge : edges) {
			String from = String.valueOf(edge.getStartNode().getId());
			String to = String.valueOf(edge.getEndNode().getId());
			if (graph.getNode(from) == null) {
				addNodeToGraph(graph, from, dgraph);
			} else if (graph.getNode(to) == null) {
				addNodeToGraph(graph, to, dgraph);
			}
			graph.addEdge(from + to, from, to, true);
		}
		return graph;
	}

	/**
	 * Creates a visual node object, extracts the needed data from a data graph
	 * and adds it to a visual graph.
	 * 
	 * @param g
	 *            The graph to add the new node to.
	 * @param id
	 *            The id for the new node.
	 * @param dg
	 *            The data graph where the node is defined.
	 * @return
	 */
	private static void addNodeToGraph(final Graph g, final String id,
			final DGraph dg) {
		Node gn = g.addNode(id);
		DNode n = dg.getDNode(Integer.parseInt(id));
		gn.addAttribute("x", n.getX());
		gn.addAttribute("y", n.getY());
		gn.addAttribute("ui.label", checkLabelLength(n.getContent()));
		gn.addAttribute("ui.class", "common");
		gn.addAttribute("ui.color", 1 - n.getPercUnknown());
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
