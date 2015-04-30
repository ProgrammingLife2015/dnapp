package graph;

import java.io.FileNotFoundException;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;

import reader.NodePlacer;
import reader.Reader;

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
		Graph g = Reader.read("src/main/resources/nodes.txt",
				"src/main/resources/edges.txt");
		Viewer viewer = g.display();
		viewer.disableAutoLayout();
		NodePlacer.place(g, viewer);
	}

}
