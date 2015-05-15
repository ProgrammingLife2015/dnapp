package nl.tudelft.ti2806.pl1.zoomlevels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import nl.tudelft.ti2806.pl1.reader.NodePlacer;
import nl.tudelft.ti2806.pl1.reader.Reader;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.junit.Before;
import org.junit.Test;

public class GraphConverterTest {

	String nodes = "src/test/resources/collapsetest_nodes.txt";
	String edges = "src/test/resources/collapsetest_edges.txt";
	Graph graph = new SingleGraph("test graph");

	@Before
	public void setUp() throws FileNotFoundException {
		graph = Reader.read(nodes, edges);
	}

	// @After
	// public void tearDown() {
	// graph = null;
	// nodes = null;
	// edges = null;
	// }

	@Test
	public void test() throws FileNotFoundException, InterruptedException {
		graph = Reader.read(nodes, edges);
		Graph g = GraphConverter.collapsePointMutations(graph);
		Viewer viewer = g.display();
		viewer.disableAutoLayout();
		NodePlacer.place(g, viewer);
		assertTrue(g.getNode("collapsed: 1 2").getAttribute("content")
				.toString().equals("{TKK-1=G, TKK-2=T}"));
		assertEquals(g.getNode("collapsed: 1 2").getAttribute("start"), 7);
		assertEquals(g.getNode("collapsed: 1 2").getAttribute("end"), 8);
		assertEquals(g.getNode("collapsed: 1 2").getAttribute("depth"), 1);
	}
}
