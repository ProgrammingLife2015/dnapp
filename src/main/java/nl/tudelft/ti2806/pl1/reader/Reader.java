package nl.tudelft.ti2806.pl1.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;

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
	 *             When the file can't be read
	 */
	public static DGraph read(final String nodes, final String edges) throws IOException {
		DGraph graph = new DGraph();
		for (DNode node : readNodes(nodes)) {
			graph.addDNode(node);
		}
		for (DEdge edge : readEdges(edges, graph)) {
			graph.addDEdge(edge);
		}
		addStartEndNode(graph);
		return graph;
	}

	/**
	 * Reads the nodes from the given node file path.
	 * 
	 * @param nodesPath
	 *            The path for the file containing the nodes
	 * @return A list with the nodes
	 * @throws IOException
	 *             When the file can't be read
	 */
	private static ArrayList<DNode> readNodes(final String nodesPath) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(nodesPath),
				"UTF-8"));
		return NodeReader.readNodes(reader);
	}

	/**
	 * Reads the edges from the given node file path.
	 * 
	 * @param edgesPath
	 *            The path for the file containing the edges
	 * @param graph
	 *            The DGraph for which the edges will be added
	 * @return ArrayList with the edges
	 * @throws IOException
	 *             When the file can't be read
	 */
	private static ArrayList<DEdge> readEdges(final String edgesPath, final DGraph graph) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(edgesPath),
				"UTF-8"));
		return EdgeReader.readEdges(reader, graph);
	}

	/**
	 * This method adds a start and end node, with id of STARTID and ENDID
	 * respectively.
	 * 
	 * @param graph
	 *            The graph to which the nodes are added
	 */
	private static void addStartEndNode(final DGraph graph) {
		ArrayList<DNode> startNodes = new ArrayList<DNode>();
		HashSet<String> sources = new HashSet<String>();
		for (DNode node : graph.getNodes().values()) {
			sources.addAll(node.getSources());
			if (node.getInEdges().size() == 0) {
				startNodes.add(node);
			}
		}
		DNode start = new DNode(STARTID, sources, 0, 0, "");
		graph.addDNode(start);
		for (DNode n : startNodes) {
			DEdge edge = new DEdge(start, n);
			graph.addDEdge(edge);
		}
		graph.setStart(start);
	}
}