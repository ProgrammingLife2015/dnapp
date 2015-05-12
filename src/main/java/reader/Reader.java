package reader;

import graph.Edge;
import graph.Node;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * 
 * @author Marissa, Mark
 * @date 25-04-2015 Lets you read in all the data and returns a Graph.
 */
public class Reader {

	/**
	 * Reads the graph from the two given files.
	 * 
	 * @param nodes
	 *            File location of the Nodes data.
	 * @param edges
	 *            File location of the Edges data.
	 * @return Graph with this information processed.
	 * @throws FileNotFoundException
	 *             If file is not found.
	 */
	public static Graph read(final String nodes, final String edges)
			throws FileNotFoundException {
		ArrayList<Node> node = readNodes(nodes);
		ArrayList<Edge> edge = readEdges(edges);
		Graph graph = new SingleGraph("DNA Sequencing Graph");
		for (Node n : node) {
			graph.addNode(String.valueOf(n.getId()));
			graph.getNode(n.getId()).addAttribute("start", n.getStart());
			graph.getNode(n.getId()).addAttribute("depth", 0);
			graph.getNode(n.getId()).addAttribute("end", n.getEnd());
			graph.getNode(n.getId()).addAttribute("content", n.getContent());
			graph.getNode(n.getId()).addAttribute("inNodes", n.getInNodes());
			graph.getNode(n.getId()).addAttribute("outNodes", n.getOutNodes());
			graph.getNode(n.getId()).addAttribute("sources", n.getSources());
			graph.getNode(n.getId()).addAttribute("ui.label", n.getId());
		}
		for (Edge e : edge) {
			graph.addEdge(
					String.valueOf(e.getStartNode())
							+ String.valueOf(e.getEndNode()),
					String.valueOf(e.getStartNode()),
					String.valueOf(e.getEndNode()));
		}
		return graph;
	}

	/**
	 * Reads the nodes from the given node file path.
	 * 
	 * @param nodes
	 *            The path for the file containing the nodes
	 * @return Returns an ArrayList with the nodes
	 * @throws FileNotFoundException
	 *             Throws an exception when the file isn't found
	 */
	private static ArrayList<Node> readNodes(final String nodes)
			throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(nodes));
		Scanner sc = new Scanner(reader);
		return NodeReader.readNodes(sc);
	}

	/**
	 * Reads the edges from the given node file path.
	 * 
	 * @param nodes
	 *            The path for the file containing the edges
	 * @return Returns an ArrayList with the edges
	 * @throws FileNotFoundException
	 *             Throws an exception when the file isn't found
	 */
	private static ArrayList<Edge> readEdges(final String edges)
			throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(edges));
		Scanner sc = new Scanner(reader);
		return EdgeReader.readEdges(sc);
	}
}
