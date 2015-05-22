package nl.tudelft.ti2806.pl1.zoomlevels;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.HashMap;

import nl.tudelft.ti2806.pl1.reader.NodePlacer;
import nl.tudelft.ti2806.pl1.reader.Reader;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.junit.Test;

public class HorizonalCollapseTest {

	String nodes2 = "src/test/resources/collapsetest2_nodes.txt";
	String edges2 = "src/test/resources/collapsetest2_edges.txt";
	String nodes4 = "src/test/resources/collapsetest4_nodes.txt";
	String edges4 = "src/test/resources/collapsetest4_edges.txt";
	Graph graph = new SingleGraph("test graph");

	@Test
	public void testCollapse1() throws FileNotFoundException {
		graph = Reader.read(nodes2, edges2);
		Graph g = PointGraphConverter.collapsePointMutations(graph);
		g = HorizontalCollapse.horizontalCollapse(g, 1000000);
		Viewer viewer = g.display();
		viewer.disableAutoLayout();
		NodePlacer.place(g, viewer);
		assertEquals(g.getNode("3").getAttribute("content"), "AC");
		assertEquals(g.getNode("4").getAttribute("content"), "AA");
		assertEquals(g.getNode("5").getAttribute("content"), "CT");
		assertEquals(g.getNode("6").getAttribute("content"), "CG");
	}

	@Test
	public void testContent1() throws FileNotFoundException {
		graph = Reader.read(nodes4, edges4);
		Graph g = PointGraphConverter.collapsePointMutations(graph);
		g = HorizontalCollapse.horizontalCollapse(g, 50);
		Viewer viewer = g.display();
		viewer.disableAutoLayout();
		NodePlacer.place(g, viewer);
		HashMap<String, String> hash = g.getNode("8").getAttribute("content");
		assertEquals(hash.get("TKK-1"), "GGGATGGGGATGA");
		assertEquals(hash.get("TKK-2"), "GGGATAGGGATAA");
	}

	@Test
	public void testContent2() throws FileNotFoundException {
		graph = Reader.read(nodes4, edges4);
		Graph g = PointGraphConverter.collapsePointMutations(graph);
		g = HorizontalCollapse.horizontalCollapse(g, 5);
		Viewer viewer = g.display();
		viewer.disableAutoLayout();
		NodePlacer.place(g, viewer);
		HashMap<String, String> hash1 = g.getNode("4").getAttribute("content");
		HashMap<String, String> hash2 = g.getNode("8").getAttribute("content");
		assertEquals(g.getNode("1").getAttribute("content"), "GGGAT");
		assertEquals(hash1.get("TKK-1"), "GGGG");
		assertEquals(hash1.get("TKK-2"), "AGGG");
		assertEquals(hash2.get("TKK-1"), "ATGA");
		assertEquals(hash2.get("TKK-2"), "ATAA");
	}

}
