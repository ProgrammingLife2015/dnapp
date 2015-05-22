package nl.tudelft.ti2806.pl1.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

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
	 * @throws FileNotFoundException
	 *             If file is not found.
	 */
	public static DGraph read(final String nodes, final String edges)
			throws FileNotFoundException {
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
	 * @throws FileNotFoundException
	 *             Throws an exception when the file isn't found
	 */
	private static ArrayList<DNode> readNodes(final String nodesPath)
			throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(nodesPath));
		Scanner sc = new Scanner(reader);
		return NodeReader.readNodes(sc);
	}

	/**
	 * Reads the edges from the given node file path.
	 * 
	 * @param edgesPath
	 *            The path for the file containing the edges
	 * @param graph
	 *            The DGraph for which the edges will be added
	 * @throws FileNotFoundException
	 *             Throws an exception when the file isn't found
	 */
	private static ArrayList<DEdge> readEdges(final String edgesPath,
			final DGraph graph) throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(edgesPath));
		Scanner sc = new Scanner(reader);
		return EdgeReader.readEdges(sc, graph);
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
		ArrayList<DNode> endNodes = new ArrayList<DNode>();
		HashSet<String> sources = new HashSet<String>();
		for (DNode node : graph.getNodes().values()) {
			sources.addAll(node.getSources());
			if (node.getInEdges().size() == 0) {
				startNodes.add(node);
			} else if (node.getOutEdges().size() == 0) {
				endNodes.add(node);
			}
		}
		DNode start = new DNode(STARTID, sources, 0, 0, "");
		DNode end = new DNode(ENDID, sources, 0, 0, "");
		graph.addDNode(start);
		graph.addDNode(end);
		for (DNode n : startNodes) {
			DEdge edge = new DEdge(start, n);
			graph.addDEdge(edge);
		}
		for (DNode n : endNodes) {
			DEdge edge = new DEdge(n, end);
			graph.addDEdge(edge);
		}
		graph.setStart(start);
		graph.setEnd(end);
	}
}