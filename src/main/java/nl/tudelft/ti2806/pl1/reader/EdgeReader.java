package nl.tudelft.ti2806.pl1.reader;

import java.util.ArrayList;
import java.util.Scanner;

import nl.tudelft.ti2806.pl1.exceptions.InvalidFileFormatException;
import nl.tudelft.ti2806.pl1.graph.DEdge;

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
	public static ArrayList<DEdge> readEdges(final Scanner sc) {
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
			DEdge edge = new DEdge(start, end);
			edges.add(edge);
		}
		return edges;
	}
}
