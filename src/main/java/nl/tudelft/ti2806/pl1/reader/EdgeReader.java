package nl.tudelft.ti2806.pl1.reader;

import java.util.Scanner;

import nl.tudelft.ti2806.pl1.exceptions.InvalidFileFormatException;

import org.graphstream.graph.Graph;

/**
 * 
 * @author Marissa, Mark
 * @since 24-04-2015 Lets you read the Edges from the provided data.
 */
public final class EdgeReader {

	/**
	 * 
	 */
	private EdgeReader() {
	}

	/**
	 * 
	 * @param sc
	 *            Scanner from which the edge information will be read.
	 * @return ArrayList of all Edges.
	 */
	public static void readEdges(final Scanner sc, final Graph graph) {
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] nodes = line.split("\\s");
			if (nodes.length != 2) {
				throw new InvalidFileFormatException(
						"There should be 2 node id's seperated by spaces in the edge file");
			}
			try {
				Integer.parseInt(nodes[0]);
				Integer.parseInt(nodes[1]);
			} catch (Exception e) {
				throw new InvalidFileFormatException(
						"The id's should be integers");
			}
			graph.addEdge(nodes[0] + nodes[1], nodes[0], nodes[1]);
		}
	}
}
