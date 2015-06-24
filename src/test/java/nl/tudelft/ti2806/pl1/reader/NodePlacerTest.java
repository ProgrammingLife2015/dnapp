package nl.tudelft.ti2806.pl1.reader;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.exceptions.InvalidNodePlacementException;
import nl.tudelft.ti2806.pl1.graph.ConvertDGraph;
import nl.tudelft.ti2806.pl1.graph.DEdge;
import nl.tudelft.ti2806.pl1.graph.DGraph;
import nl.tudelft.ti2806.pl1.graph.DNode;

import org.graphstream.graph.Graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NodePlacerTest {

	private ArrayList<Integer> hdiff;
	private ArrayList<Integer> nodesatdepth, nodesatdepthInvalid;
	private int height = 60;
	private DGraph graph;
	private Dimension dim;

	@Before
	public void startUp() throws InvalidNodePlacementException, IOException {
		nodesatdepth = new ArrayList<Integer>(2);
		nodesatdepth.add(3);
		nodesatdepth.add(1);
		nodesatdepthInvalid = new ArrayList<Integer>(1);
		nodesatdepthInvalid.add(0);
		hdiff = NodePlacer.heightDiff(nodesatdepth, height);
		graph = Reader.read("src/test/resources/nodes.txt",
				"src/test/resources/edges.txt");
		dim = NodePlacer.place(graph);
	}

	@After
	public void tearDown() {
		hdiff = null;
	}

	@Test
	public void heightDiffTest() throws InvalidNodePlacementException {
		assertEquals(-15, NodePlacer.getHeight(0, hdiff, nodesatdepth, height));
		assertEquals(0, NodePlacer.getHeight(0, hdiff, nodesatdepth, height));
	}

	@Test(expected = InvalidNodePlacementException.class)
	public void heightDiffInvalidTest() throws InvalidNodePlacementException {
		NodePlacer.getHeight(0, hdiff, nodesatdepthInvalid, height);
	}

	@Test
	public void getWidthTest() {
		assertEquals(0, NodePlacer.getWidth(50, 0, nodesatdepth.size()));
		assertEquals(25, NodePlacer.getWidth(50, 1, nodesatdepth.size()));
	}

	@Test
	public void depthLevelStartNodeTest() {
		assertEquals(0, graph.getDNode(-2).getDepth());
	}

	@Test
	public void depthLevelNode0Test() {
		assertEquals(1, graph.getDNode(0).getDepth());
	}

	@Test
	public void depthLevelNode1Test() {
		assertEquals(2, graph.getDNode(1).getDepth());
	}

	@Test
	public void xLevelNodeStartTest() {
		assertEquals(0, graph.getDNode(-2).getX());
	}

	@Test
	public void xLevelNode0Test() {
		assertEquals(30, graph.getDNode(0).getX());
	}

	@Test
	public void xLevelNode1Test() {
		assertEquals(60, graph.getDNode(1).getX());
	}

	@Test
	public void placeTest() {
		int multiplier = 30;
		Graph gsg = ConvertDGraph.convert(graph);
		NodePlacer.place(gsg, dim);
		assertEquals(1 * multiplier, gsg.getNode("0").getAttribute("x"));
		assertEquals(2 * multiplier, gsg.getNode("1").getAttribute("x"));
		assertEquals(0 * multiplier, gsg.getNode("0").getAttribute("y"));
		assertEquals(0 * multiplier, gsg.getNode("1").getAttribute("y"));
	}

	@Test
	public void emptyGraph() throws InvalidNodePlacementException {
		assertEquals(new Dimension(0, 0), NodePlacer.place(new DGraph()));
	}

	@Test
	public void placeYTest() {
		DNode n = new DNode(2, new HashSet<String>(), 0, 0, "");
		graph.addDNode(n);
		graph.addDEdge(new DEdge(graph.getDNode(0), n));
		Graph gsg = ConvertDGraph.convert(graph);
		assertEquals(0, gsg.getNode("0").getAttribute("y"));
		assertEquals(0, gsg.getNode("1").getAttribute("y"));
		assertEquals(0, gsg.getNode("2").getAttribute("y"));
		NodePlacer.placeY(gsg);
		assertEquals(0, gsg.getNode("0").getAttribute("y"));
		assertEquals(30, gsg.getNode("1").getAttribute("y"));
		assertEquals(0, gsg.getNode("2").getAttribute("y"));
	}

	@Test
	public void gettersAndSettersTest() {
		assertEquals(dim.getHeight(), NodePlacer.getHeight(), 0);
		assertEquals(dim.getWidth(), NodePlacer.getWidth(), 0);
		int newheight = 20;
		int newwidth = 15;
		NodePlacer.setHeight(newheight);
		NodePlacer.setWidth(newwidth);
		assertEquals(newheight, NodePlacer.getHeight(), 0);
		assertEquals(newwidth, NodePlacer.getWidth(), 0);
	}
}
