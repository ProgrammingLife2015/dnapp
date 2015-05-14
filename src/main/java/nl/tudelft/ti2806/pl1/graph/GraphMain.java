package nl.tudelft.ti2806.pl1.graph;

import java.io.IOException;

import nl.tudelft.ti2806.graphWriter.GraphWriter;
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
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		Graph g = Reader.read("src/main/resources/nodes.txt",
				"src/main/resources/edges.txt");
		Viewer viewer = g.display();
		viewer.disableAutoLayout();
		NodePlacer.place(g, viewer);
		GraphWriter.write("test.txt", g);
	}

}
