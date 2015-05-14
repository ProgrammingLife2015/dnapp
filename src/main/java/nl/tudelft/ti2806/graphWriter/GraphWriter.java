package nl.tudelft.ti2806.graphWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 * Writes the graph to a file. File format will be
 * 
 * <amound of nodes> nodes with attributes <amount of edges> edges edges will be
 * here
 * 
 * @author mark
 */
public class GraphWriter {

	/**
	 * Writes the graph to a file, first it writes the amount of nodes followed
	 * by the writeNodes method. Then it writes the amount of edges followed by
	 * the writeEdge method.
	 * 
	 * @param location
	 *            The file location
	 * @param graph
	 *            The graph
	 * @throws IOException
	 */
	public static void write(final String location, final Graph graph)
			throws IOException {
		File file = new File(location);
		FileWriter fwriter = new FileWriter(file);
		BufferedWriter bwriter = new BufferedWriter(fwriter);
		PrintWriter writer = new PrintWriter(bwriter);
		writer.println(graph.getNodeCount());
		writeNodes(writer, graph);
		writer.println(graph.getEdgeCount());
		writeEdges(writer, graph);
		if (writer.checkError()) {
			System.err.println("an error occured during writing");
			// TODO make an exception, and throw it.
		}
		writer.close();
	}

	/**
	 * This method writes the nodes. It first wrties the id's of the node, then
	 * it writes the amount of attributes followed by each attribute on a new
	 * line.
	 * 
	 * @param writer
	 *            The writer which writes the nodes
	 * @param graph
	 *            The graph
	 */
	public static void writeNodes(final PrintWriter writer, final Graph graph) {
		for (Node n : graph.getNodeSet()) {
			writer.println(n.getId());
			writer.println(n.getAttributeCount());
			for (String s : n.getAttributeKeySet()) {
				writer.println(s + " | " + n.getAttribute(s));
			}
		}
	}

	/**
	 * This method writes the edges in the form of id | sourceNode | targetNode.
	 * 
	 * @param writer
	 *            The writer which writes the nodes
	 * @param graph
	 *            The graph
	 */
	public static void writeEdges(final PrintWriter writer, final Graph graph) {
		for (Edge e : graph.getEdgeSet()) {
			writer.println(e.getId() + " | " + e.getSourceNode() + " | "
					+ e.getTargetNode());
		}
	}
}
