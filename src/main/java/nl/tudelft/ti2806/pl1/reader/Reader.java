package nl.tudelft.ti2806.pl1.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;

/**
 * 
 * @author Marissa, Mark
 * @since 25-04-2015 Lets you read in all the data and returns a Graph.
 */
public final class Reader {

	/**
	 * The start id of the start node in the graph.
	 */
	private static final int STARTID = -2;

	/**
	 * The end id of the end node in the graph.
	 */
	private static final int ENDID = -1;

	/**
	 * 
	 */
	private Reader() {
	}

	/**
	 * Reads the graph from the two given files.
	 * 
	 * @param nodes
	 *            File location of the Nodes data.
	 * @param edges
	 *            File location of the Edges data.
	 * @return Graph with this processed information.
	 * @throws IOException
	 */
	public static DGraph read(final String nodes, final String edges)
			throws IOException {
		DGraph graph = new DGraph("src/main/resources/database/");
		readNodes(graph, nodes);
		readEdges(graph, edges);

		return graph;
	}

	/**
	 * Reads the nodes from the given node file path.
	 * 
	 * @param graph
	 *            The graph on which the nodes will be added.
	 * @param nodesPath
	 *            The path for the file containing the nodes
	 * @throws IOException
	 */
	private static void readNodes(final DGraph graph, final String nodesPath)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(nodesPath), "UTF-8"));
		NodeReader.readNodes(graph, reader);
	}

	/**
	 * Reads the edges from the given node file path.
	 * 
	 * @param graph
	 *            The DGraph for which the edges will be added
	 * @param edgesPath
	 *            The path for the file containing the edges
	 * @throws IOException
	 */
	private static void readEdges(final DGraph graph, final String edgesPath)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(edgesPath), "UTF-8"));
		EdgeReader.readEdges(graph, reader);
	}

	// /**
	// * This method adds a start and end node, with id of STARTID and ENDID
	// * respectively.
	// *
	// * @param graph
	// * The graph to which the nodes are added
	// */
	// // TODO this needs to be fixed
	// private static void addStartEndNode(final DGraph graph) {
	// ArrayList<DNode> startNodes = new ArrayList<DNode>();
	// ArrayList<DNode> endNodes = new ArrayList<DNode>();
	// HashSet<String> sources = new HashSet<String>();
	// for (DNode node : graph.getNodes().values()) {
	// sources.addAll(node.getSources());
	// if (node.getInEdges().size() == 0) {
	// startNodes.add(node);
	// } else if (node.getOutEdges().size() == 0) {
	// endNodes.add(node);
	// }
	// }
	// DNode start = new DNode(STARTID, sources, 0, 0, "");
	// DNode end = new DNode(ENDID, sources, 0, 0, "");
	// graph.addDNode(start);
	// graph.addDNode(end);
	// for (DNode n : startNodes) {
	// DEdge edge = new DEdge(start, n);
	// graph.addDEdge(edge);
	// }
	// for (DNode n : endNodes) {
	// DEdge edge = new DEdge(n, end);
	// graph.addDEdge(edge);
	// }
	// graph.setStart(start);
	// graph.setEnd(end);
	// }
}