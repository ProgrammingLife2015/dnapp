package nl.tudelft.ti2806.pl1.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.exceptions.InvalidFileFormatException;

/**
 * 
 * @author Marissa, Mark
 * @since 25-04-2015 Lets you read the Nodes from the provided data.
 */
public final class NodeReader {

	/**
	 * 
	 */
	private NodeReader() {
	}

	/**
	 * 
	 * @param sc
	 *            Scanner from which contains the Node information.
	 * @return Returns a Graph containing all the Nodes, but no Edges.
	 * @throws IOException
	 */
	public static ArrayList<DNode> readNodes(final BufferedReader reader)
			throws IOException {
		ArrayList<DNode> nodes = new ArrayList<DNode>();
		String nextnode;
		while ((nextnode = reader.readLine()) != null) {
			if (nextnode.charAt(0) == '>') {
				nextnode = nextnode.substring(1);
				String[] data = nextnode.split("\\s\\|\\s");
				if (data.length != 4) {
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
					start = Integer.parseInt(data[2]);
					end = Integer.parseInt(data[3]);
				} catch (Exception e) {
					throw new InvalidFileFormatException(
							"The start and end reference should be integers");
				}
				if (end - start != content.length()) {
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
		reader.close();
		return nodes;
	}
}
