package nl.tudelft.ti2806.pl1.zoomlevels;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import nl.tudelft.ti2806.pl1.reader.NodePlacer;
import nl.tudelft.ti2806.pl1.reader.Reader;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.junit.Test;

public class GraphConverterTest {

	String nodes1 = "src/test/resources/collapsetest_nodes.txt";
	String edges1 = "src/test/resources/collapsetest_edges.txt";
	String nodes2 = "src/test/resources/collapsetest2_nodes.txt";
	String edges2 = "src/test/resources/collapsetest2_edges.txt";
	String nodes3 = "src/test/resources/collapsetest3_nodes.txt";
	String edges3 = "src/test/resources/collapsetest3_edges.txt";
	Graph graph = new SingleGraph("test graph");

	@Test
	public void testCollapse1() throws FileNotFoundException {
		graph = Reader.read(nodes1, edges1);
		Graph g = GraphConverter.collapsePointMutations(graph);
		Viewer viewer = g.display();
		viewer.disableAutoLayout();
		NodePlacer.place(g, viewer);
		assertEquals(g.getNode("collapsed: 1 2").getAttribute("content")
				.toString(), "{TKK-2=T, TKK-1=G}");
		assertEquals(g.getNode("collapsed: 1 2").getAttribute("start"), 7);
		assertEquals(g.getNode("collapsed: 1 2").getAttribute("end"), 8);
		assertEquals(g.getNode("collapsed: 1 2").getAttribute("depth"), 1);
	}

	@Test
	public void testCollapse2() throws FileNotFoundException {
		graph = Reader.read(nodes2, edges2);
		Graph g = GraphConverter.collapsePointMutations(graph);
		Viewer viewer = g.display();
		viewer.disableAutoLayout();
		NodePlacer.place(g, viewer);
		assertEquals(g.getNode("3").getAttribute("content"), "AC");
		assertEquals(g.getNode("4").getAttribute("content"), "AA");
		assertEquals(g.getNode("5").getAttribute("content"), "CT");
		assertEquals(g.getNode("6").getAttribute("content"), "CG");
	}

	@Test
	public void testCollapse3() throws FileNotFoundException {
		graph = Reader.read(nodes3, edges3);
		Graph g = GraphConverter.collapsePointMutations(graph);
		Viewer viewer = g.display();
		viewer.disableAutoLayout();
		NodePlacer.place(g, viewer);
		assertEquals(g.getNode("collapsed: 3 4").getAttribute("content")
				.toString(), "{TKK-2=A, TKK-1=C}");
		assertEquals(g.getNode("collapsed: 5 6").getAttribute("content")
				.toString(), "{TKK-4=G, TKK-3=T}");
		assertEquals(g.getNode("1").getAttribute("content"), "AT");
		assertEquals(g.getNode("2").getAttribute("content"), "CG");
	}
}
