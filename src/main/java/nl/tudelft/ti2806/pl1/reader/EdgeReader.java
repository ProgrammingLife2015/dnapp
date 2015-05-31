package nl.tudelft.ti2806.pl1.reader;

import java.util.ArrayList;
import java.util.Scanner;

import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.exceptions.InvalidFileFormatException;

import org.neo4j.graphdb.Node;

/**
 * 
 * @author Marissa, Mark
 * @since 24-04-2015 Lets you read the Edges from the provided data.
 */
public final class EdgeReader {

	/**
	 * Avoid the instantiation of the edge reader.
	 */
	private EdgeReader() {
	}

	/**
	 * Reads the edges, and returns an arrayList with the edges.
	 * 
	 * @param sc
	 *            Scanner from which the edge information will be read.
	 * @param graph
	 *            The graph which is used to extract the nodes
	 * @return ArrayList of all Edges.
	 */
	public static ArrayList<DEdge> readEdges(final Scanner sc,
			final DGraph graph) {
		ArrayList<DEdge> edges = new ArrayList<DEdge>();
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] nodes = line.split("\\s");
			if (nodes.length != 2) {
				throw new InvalidFileFormatException(
						"There should be 2 node id's seperated by spaces in the edge file");
			}
			int start;
			int end;
			try {
				start = Integer.parseInt(nodes[0]);
				end = Integer.parseInt(nodes[1]);
			} catch (Exception e) {
				throw new InvalidFileFormatException(
						"The id's should be integers");
			}
			Node src = graph.getNode(start);
			Node tar = graph.getNode(end);
			if (src == null || tar == null) {
				throw new InvalidFileFormatException("The id's shoould exist");
			}
			graph.addEdge(src, tar);
		}
		return edges;
	}
}
