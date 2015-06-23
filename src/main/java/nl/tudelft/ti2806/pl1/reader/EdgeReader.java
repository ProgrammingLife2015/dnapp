package nl.tudelft.ti2806.pl1.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import nl.tudelft.ti2806.pl1.exceptions.InvalidFileFormatException;
import nl.tudelft.ti2806.pl1.graph.DEdge;
import nl.tudelft.ti2806.pl1.graph.DGraph;
import nl.tudelft.ti2806.pl1.graph.DNode;

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
	 * @param reader
	 *            Buffered Reader from which the edge information will be read.
	 * @param graph
	 *            The graph which is used to extract the nodes
	 * @return ArrayList of all Edges.
	 * @throws IOException
	 *             When the file can't be read
	 */
	public static ArrayList<DEdge> readEdges(final BufferedReader reader,
			final DGraph graph) throws IOException {
		ArrayList<DEdge> edges = new ArrayList<DEdge>();
		String line;
		while ((line = reader.readLine()) != null) {
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
			DNode src = graph.getDNode(start);
			DNode tar = graph.getDNode(end);
			if (src == null || tar == null) {
				throw new InvalidFileFormatException("The id's shoould exist");
			}
			DEdge edge = new DEdge(src, tar);
			edges.add(edge);
		}
		return edges;
	}
}
