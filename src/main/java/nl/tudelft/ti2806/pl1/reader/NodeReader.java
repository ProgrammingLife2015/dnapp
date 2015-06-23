package nl.tudelft.ti2806.pl1.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.exceptions.InvalidFileFormatException;
import nl.tudelft.ti2806.pl1.graph.DNode;

/**
 * 
 * @author Marissa, Mark
 * @since 25-04-2015 Lets you read the Nodes from the provided data.
 */
public final class NodeReader {

	/**
	 * The location of the starting point in the text format.
	 */
	private static final int STAR_TLOCATION = 2;

	/**
	 * The location of the ending point in the text format.
	 */
	private static final int END_LOCATION = 3;

	/**
	 * The amount of information pieces in the format.
	 */
	private static final int AMOUNT_OF_INFORMATION = 4;

	/**
	 */
	private NodeReader() {
	}

	/**
	 * 
	 * @param reader
	 *            Buffered Reader from which contains the Node information.
	 * @return Returns a Graph containing all the Nodes, but no Edges.
	 * @throws IOException
	 *             When the file can't be read
	 */
	public static ArrayList<DNode> readNodes(final BufferedReader reader)
			throws IOException {
		ArrayList<DNode> nodes = new ArrayList<DNode>();
		String nextnode;
		while ((nextnode = reader.readLine()) != null) {
			if (nextnode.charAt(0) == '>') {
				nextnode = nextnode.substring(1);
				String[] data = nextnode.split("\\s\\|\\s");
				if (data.length != AMOUNT_OF_INFORMATION) {
					throw new InvalidFileFormatException(
							"Missing some information to create this node");
				}
				String content = reader.readLine();
				int id;
				int start;
				int end;
				try {
					id = Integer.parseInt(data[0]);
				} catch (Exception e) {
					throw new InvalidFileFormatException(
							"The id should be an integer");
				}
				try {
					start = Integer.parseInt(data[STAR_TLOCATION]);
					end = Integer.parseInt(data[END_LOCATION]);
				} catch (Exception e) {
					throw new InvalidFileFormatException(
							"The start and end reference should be integers");
				}
				if (content != null && end - start != content.length()) {
					throw new InvalidFileFormatException(
							"Size of Node content doesn't match with its reference size");
				}
				HashSet<String> sources = new HashSet<String>(
						Arrays.asList(data[1].split(",")));
				DNode node = new DNode(id, sources, start, end, content);
				nodes.add(node);
			} else {
				throw new InvalidFileFormatException(
						"Every new node line should start with >");
			}
		}
		return nodes;
	}
}
