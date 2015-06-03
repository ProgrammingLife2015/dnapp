import java.io.IOException;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.reader.Reader;

/**
 * @author Maarten
 *
 */
public final class Start {

	/**
	 * Private constructor to prevent this utility class being instantiated.
	 */
	private Start() {
	}

	/**
	 * @param args
	 *            jwz
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		long time = System.currentTimeMillis();
		DGraph graph = Reader.read(
				"src/main/resources/simple_graph.node.graph",
				"src/main/resources/simple_graph.edge.graph");
		System.out.println("it took: " + (System.currentTimeMillis() - time)
				+ " milliseconds");
	}

}
