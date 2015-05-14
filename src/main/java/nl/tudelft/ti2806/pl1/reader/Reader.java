package nl.tudelft.ti2806.pl1.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * 
 * @author Marissa, Mark
 * @since 25-04-2015 Lets you read in all the data and returns a Graph.
 */
public final class Reader {

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
	public static Graph read(final String nodePath, final String edgePath)
			throws FileNotFoundException {
		Graph graph = new SingleGraph("DNA Sequencing Graph");
		readNodes(nodePath, graph);
		readEdges(edgePath, graph);
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
	private static void readNodes(final String nodesPath, final Graph graph)
			throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(nodesPath));
		Scanner sc = new Scanner(reader);
		NodeReader.readNodes(sc, graph);
	}

	/**
	 * Reads the edges from the given node file path.
	 * 
	 * @param edgesPath
	 *            The path for the file containing the edges
	 * @return A list with the edges
	 * @throws FileNotFoundException
	 *             Throws an exception when the file isn't found
	 */
	private static void readEdges(final String edgesPath, final Graph graph)
			throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(edgesPath));
		Scanner sc = new Scanner(reader);
		EdgeReader.readEdges(sc, graph);
	}
}
