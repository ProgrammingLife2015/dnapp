package nl.tudelft.ti2806.pl1.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import nl.tudelft.ti2806.pl1.exceptions.InvalidFileFormatException;

import org.graphstream.graph.Graph;

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
	 */
	public static void readNodes(final Scanner sc, final Graph graph) {
		while (sc.hasNextLine()) {
			String nextnode = sc.nextLine();
			if (nextnode.charAt(0) == '>') {
				nextnode = nextnode.substring(1);
				String[] data = nextnode.split("\\s\\|\\s");
				if (data.length != 4) {
					throw new InvalidFileFormatException(
							"Missing some information to create this node");
				}
				String content = sc.nextLine();
				String id = data[0];
				int start;
				int end;
				try {
					Integer.parseInt(data[0]);
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
				ArrayList<String> sources = new ArrayList<String>(
						Arrays.asList(data[1].split(",")));

				graph.addNode(id);
				graph.getNode(id).addAttribute("start", start);
				graph.getNode(id).addAttribute("depth", 0);
				graph.getNode(id).addAttribute("end", end);
				graph.getNode(id).addAttribute("content", content);
				graph.getNode(id).addAttribute("sources", sources);
				graph.getNode(id).addAttribute("ui.label", id);
			} else {
				throw new InvalidFileFormatException(
						"Every new node line should start with >");
			}
		}
	}
}
