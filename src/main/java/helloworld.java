import java.io.FileNotFoundException;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;

import reader.NodePlacer;
import reader.Reader;


public class helloworld {

	public static void main(String[] args) throws FileNotFoundException {
	  Graph g = Reader.read("src/main/resources/nodes.txt", "src/main/resources/edges.txt");
	  Viewer viewer = g.display();
	  viewer.disableAutoLayout();
	  NodePlacer.place(g, viewer);
	}

}
