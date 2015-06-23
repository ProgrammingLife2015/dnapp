package nl.tudelft.ti2806.pl1.reader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import nl.tudelft.ti2806.pl1.exceptions.InvalidNodePlacementException;
import nl.tudelft.ti2806.pl1.graph.DGraph;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NodePlacerTest {

	private ArrayList<Integer> hdiff;
	private ArrayList<Integer> nodesatdepth, nodesatdepthInvalid;
	private int height = 60;
	private DGraph graph;

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
		NodePlacer.place(graph);
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
}
