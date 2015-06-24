package nl.tudelft.ti2806.pl1.graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.graphstream.graph.Edge;
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

	/** Maximum thickness of the edge. */
	private static final int MAX_SIZE_EDGE = 5;

	/**
	 */
	private ConvertDGraph() {
	}

	/**
	 * Converts a data graph into a visual graph.
	 * 
	 * @param dgraph
	 *            The data graph to convert.
	 * @return The visual graph containing all the nodes from the data graph.
	 */
	public static Graph convert(final DGraph dgraph) {
		Graph graph = new SingleGraph("");
		Set<DEdge> edges = new HashSet<DEdge>();
		for (DNode n : dgraph.getNodes().values()) {
			edges.addAll(n.getAllEdges());
			graph = addNode(graph, dgraph, n);
		}
		for (DEdge edge : edges) {
			graph = addEdge(graph, dgraph, edge);
		}
		return graph;
	}

	/**
	 * Add a edge to the visual graph based on the data.
	 * 
	 * @param graph
	 *            Visual graph which the node will be added to.
	 * @param dgraph
	 *            Data storage of the graph.
	 * @param edge
	 *            Edge data information.
	 * @return Graph with the new edge added.
	 */
	private static Graph addEdge(final Graph graph, final DGraph dgraph,
			final DEdge edge) {
		String from = String.valueOf(edge.getStartNode().getId());
		String to = String.valueOf(edge.getEndNode().getId());

		if (graph.getNode(from) == null) {
			addNodeToGraph(graph, from, dgraph);
		} else if (graph.getNode(to) == null) {
			addNodeToGraph(graph, to, dgraph);
		}
		Edge eg = graph.addEdge(from + to, from, to, true);
		addEdgeWidth(eg, edge, dgraph.getStart().getSources().size());
		return graph;
	}

	/**
	 * Calculates the amount of sources going through the edge and adjust width
	 * based on it.
	 * 
	 * @param eg
	 *            Visual edge which has to be adjusted.
	 * @param edge
	 *            Data edge containing the information.
	 * @param maxsources
	 *            The maximum amount of sources in the graph.
	 */
	@SuppressWarnings("unchecked")
	private static void addEdgeWidth(final Edge eg, final DEdge edge,
			final int maxsources) {
		Collection<String> m = (Collection<String>) edge.getStartNode()
				.getSources().clone();
		m.retainAll(edge.getEndNode().getSources());
		eg.addAttribute("ui.size",
				(int) ((double) m.size() / maxsources * MAX_SIZE_EDGE));

	}

	/**
	 * Add a node to the visual graph based on the data.
	 * 
	 * @param graph
	 *            Visual graph which the node will be added to.
	 * @param dgraph
	 *            Data storage of the graph.
	 * @param n
	 *            Corresponding data node which has to be added to the visual
	 *            graph.
	 * @return Visual graph with the new node added.
	 */
	private static Graph addNode(final Graph graph, final DGraph dgraph,
			final DNode n) {
		String id = String.valueOf(n.getId());
		Node gn = graph.addNode(id);
		addAttributes(gn, n, dgraph.getSelected());
		return graph;
	}

	/**
	 * Adds the corresponding attributes to the visual node.
	 * 
	 * @param gn
	 *            Visual node to add attributes to.
	 * @param n
	 *            Corresponding data node.
	 * @param selected
	 *            Selected node of the graph if present.
	 */
	private static void addAttributes(final Node gn, final DNode n,
			final String selected) {
		gn.addAttribute("x", n.getX());
		gn.addAttribute("y", n.getY());
		gn.addAttribute("ui.label", checkLabelLength(n.getContent()));
		String nodeclass = "common";
		if (n.hasResMuts()) {
			nodeclass = "resistant";
		}
		if (String.valueOf(n.getId()).equals(selected)) {
			gn.addAttribute("ui.class", "selected");
			gn.addAttribute("oldclass", nodeclass);
		} else {
			gn.addAttribute("ui.class", nodeclass);
		}
		gn.addAttribute("ui.color", 1 - n.getPercUnknown());
		gn.addAttribute("contentsize", n.getContent().length());
		gn.addAttribute("collapsed",
				new HashSet<Integer>(Arrays.asList(n.getId())));
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
