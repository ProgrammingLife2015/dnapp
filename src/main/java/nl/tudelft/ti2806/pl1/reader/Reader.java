package nl.tudelft.ti2806.pl1.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import nl.tudelft.ti2806.pl1.graph.DEdge;
import nl.tudelft.ti2806.pl1.graph.DNode;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
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
	public static Graph read(final String nodes, final String edges)
			throws FileNotFoundException {
		ArrayList<DNode> node = readNodes(nodes);
		ArrayList<DEdge> edge = readEdges(edges);
		Graph graph = new SingleGraph("DNA Sequencing Graph");
		for (DNode n : node) {
			Node gn = graph.addNode(String.valueOf(n.getId()));
			gn.addAttribute("start", n.getStart());
			gn.addAttribute("depth", 0);
			gn.addAttribute("end", n.getEnd());
			gn.addAttribute("content", n.getContent());
			gn.addAttribute("inNodes", n.getInNodes());
			gn.addAttribute("outNodes", n.getOutNodes());
			gn.addAttribute("sources", n.getSources());

			// UI attributes, to be moved! // TODO

			if (n.getContent().length() < 10) {
				gn.addAttribute("ui.label", n.getContent());
			} else {
				gn.addAttribute("ui.label",
						Integer.toString(n.getContent().length()));
			}

			if (n.getSources().contains("TKK_REF")) {
				gn.addAttribute("ui.class", "branch2");
			} else {
				gn.addAttribute("ui.class", "common");
			}
		}
		for (DEdge e : edge) {
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
	 * @return A list with the edges
	 * @throws FileNotFoundException
	 *             Throws an exception when the file isn't found
	 */
	private static ArrayList<DEdge> readEdges(final String edgesPath)
			throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(edgesPath));
		Scanner sc = new Scanner(reader);
		return EdgeReader.readEdges(sc);
	}
}
