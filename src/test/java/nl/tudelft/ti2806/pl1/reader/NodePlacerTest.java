package nl.tudelft.ti2806.pl1.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NodePlacerTest {

	private ArrayList<Integer> hdiff;
	private ArrayList<Integer> nodesatdepth;
	private int height = 60;
	private DGraph graph;

	@Before
	public void startUp() throws FileNotFoundException {
		nodesatdepth = new ArrayList<Integer>();
		nodesatdepth.add(2);
		nodesatdepth.add(1);
		hdiff = NodePlacer.heightDiff(nodesatdepth, height);
		graph = Reader.read("src/test/resources/nodes.txt",
				"src/test/resources/edges.txt");
		NodePlacer.setWidth(100);
		NodePlacer.setHeight(100);
		NodePlacer.place(graph);
	}

	@After
	public void tearDown() {
		hdiff = null;
	}

	@Test
	public void heightDiffTest() {
		assertEquals(NodePlacer.getHeight(0, hdiff, nodesatdepth, height), -10);
		assertEquals(NodePlacer.getHeight(0, hdiff, nodesatdepth, height), 10);
	}

	@Test
	public void getWidthTest() {
		assertTrue(NodePlacer.getWidth(50, 0, nodesatdepth.size()) == 0);
		assertTrue(NodePlacer.getWidth(50, 1, nodesatdepth.size()) == 25);
	}

	@Test
	public void depthLevelStartNodeTest() {
		assertEquals(graph.getDNode(-2).getDepth(), 0);
	}

	@Test
	public void depthLevelEndNodeTest2() {
		assertEquals(graph.getDNode(-1).getDepth(), 3);
	}

	@Test
	public void depthLevelNode0Test() {
		assertEquals(graph.getDNode(0).getDepth(), 1);
	}

	@Test
	public void depthLevelNode1Test() {
		assertEquals(graph.getDNode(1).getDepth(), 2);
	}

	@Test
	public void xLevelNodeStartTest() {
		assertEquals(graph.getDNode(-2).getX(), 0);
	}

	@Test
	public void xLevelNodeEndTest() {
		assertEquals(graph.getDNode(-1).getX(), 75);
	}

	@Test
	public void xLevelNode0Test() {
		assertEquals(graph.getDNode(0).getX(), 25);
	}

	@Test
	public void xLevelNode1Test() {
		assertEquals(graph.getDNode(1).getX(), 50);
	}
}
