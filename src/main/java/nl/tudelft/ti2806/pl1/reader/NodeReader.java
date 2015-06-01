package nl.tudelft.ti2806.pl1.reader;

import java.util.Scanner;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.exceptions.InvalidFileFormatException;

/**
 * 
 * @author Marissa, Mark
 * @since 25-04-2015 Lets you read the Nodes from the provided data.
 */
public final class NodeReader {

	/**
	 * The location of the starting point in the text format.
	 */
	public static final int STARTLOCATION = 2;

	/**
	 * The location of the ending point in the text format.
	 */
	public static final int ENDLOCATION = 3;

	/**
	 * The amount of information pieces in the format.
	 */
	public static final int AMOUNTOFINFORMATION = 4;

	/**
	 * cannot instantiate this class.
	 */
	private NodeReader() {
	}

	/**
	 * @param graph
	 *            The graph on which the Nodes will be added.
	 * @param sc
	 *            Scanner from which contains the Node information.
	 */
	public static void readNodes(final DGraph graph, final Scanner sc) {
		while (sc.hasNextLine()) {
			String nextnode = sc.nextLine();
			if (nextnode.charAt(0) == '>') {
				nextnode = nextnode.substring(1);
				String[] data = nextnode.split("\\s\\|\\s");
				if (data.length != AMOUNTOFINFORMATION) {
					throw new InvalidFileFormatException(
							"Missing some information to create this node");
				}
				String content = sc.nextLine();
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
					start = Integer.parseInt(data[STARTLOCATION]);
					end = Integer.parseInt(data[ENDLOCATION]);
				} catch (Exception e) {
					throw new InvalidFileFormatException(
							"The start and end reference should be integers");
				}
				if (end - start != content.length()) {
					throw new InvalidFileFormatException(
							"Size of Node content doesn't match with its reference size");
				}
				String[] sources = data[1].split(",");
				graph.addNode(id, start, end, content, 0, 0, 0, sources);
			} else {
				throw new InvalidFileFormatException(
						"Every new node line should start with >");
			}
		}
	}
}
