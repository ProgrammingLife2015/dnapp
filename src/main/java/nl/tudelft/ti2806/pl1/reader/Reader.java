package nl.tudelft.ti2806.pl1.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.RelTypes;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;

/**
 * 
 * @author Marissa, Mark
 * @since 25-04-2015 Lets you read in all the data and returns a Graph.
 */
public final class Reader {

	/**
	 * The start id of the start node in the graph.
	 */
	private static int startid;

	/**
	 * The end id of the end node in the graph.
	 */
	private static int endid;

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
	 * @throws IOException
	 *             Location cannot be found.
	 */
	public static DGraph read(final String nodes, final String edges)
			throws IOException {
		DGraph graph = new DGraph("src/main/resources/database/");
		readNodes(graph, nodes);
		readEdges(graph, edges);

		return graph;
	}

	/**
	 * Reads the nodes from the given node file path.
	 * 
	 * @param graph
	 *            The graph on which the nodes will be added.
	 * @param nodesPath
	 *            The path for the file containing the nodes
	 * @throws IOException
	 */
	private static void readNodes(final DGraph graph, final String nodesPath)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(nodesPath), "UTF-8"));
		graph.readNodes(reader);
	}

	/**
	 * Reads the edges from the given node file path.
	 * 
	 * @param graph
	 *            The DGraph for which the edges will be added
	 * @param edgesPath
	 *            The path for the file containing the edges
	 * @throws IOException
	 */
	private static void readEdges(final DGraph graph, final String edgesPath)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(edgesPath), "UTF-8"));
		graph.readEdges(reader);
	}

	/**
	 * 
	 * @param graph
	 *            Graph we want to get the start and end node for.
	 */
	private static void addStartEndNode(final DGraph graph) {
		Iterator<Node> it = graph.getNodes().iterator();
		int start = -1;
		int end = -1;
		while (it.hasNext()) {
			Node node = it.next();
			if ((int) node.getProperty("start") == 0) {
				start = (int) node.getProperty("id");
			}
			if (!node.hasRelationship(Direction.OUTGOING, RelTypes.NEXT)) {
				end = (int) node.getProperty("id");
			}

		}
		graph.setStart(start);
		graph.setEnd(end);
	}
}