package nl.tudelft.ti2806.pl1.graph;

import java.io.FileNotFoundException;

import nl.tudelft.ti2806.pl1.reader.NodePlacer;
import nl.tudelft.ti2806.pl1.reader.Reader;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;

/**
 * 
 * @author PL1
 *
 */
public final class GraphMain {

	/**
	 * 
	 */
	private GraphMain() {
		// Prevent instantiation
		// Optional: throw an exception e.g. AssertionError
		// if this ever *is* called
		// http://stackoverflow.com/questions/7766277/why-am-i-getting-this-warning-about-utility-classes-in-java
	}

	/**
	 * 
	 * @param args
	 *            jwz
	 * @throws FileNotFoundException
	 *             when the file to read is not found
	 */
	public static void main(final String[] args) throws FileNotFoundException {
		long time = System.currentTimeMillis();
		Graph g = Reader.read("src/main/resources/simple_graph.node.graph",
				"src/main/resources/simple_graph.edge.graph");
		long end = System.currentTimeMillis() - time;
		System.out.println("Reading took: " + end + " milliseconds");
		Viewer viewer = g.display();
		viewer.disableAutoLayout();
		NodePlacer.place(g, viewer);
	}
}
