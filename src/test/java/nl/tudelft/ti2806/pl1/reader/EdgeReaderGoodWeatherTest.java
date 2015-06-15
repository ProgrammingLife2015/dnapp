package nl.tudelft.ti2806.pl1.reader;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EdgeReaderGoodWeatherTest {

	String edge = "20 2";
	ArrayList<DEdge> edges;

	@Before
	public void startUp() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(edge));
		DNode id20 = new DNode(20, new HashSet<String>(), 0, 0, "");
		DNode id2 = new DNode(2, new HashSet<String>(), 0, 0, "");
		DGraph graph = new DGraph();
		graph.addDNode(id20);
		graph.addDNode(id2);
		edges = EdgeReader.readEdges(reader, graph);
	}

	@After
	public void tearDown() {
		edge = null;
		edges = null;
	}

	@Test
	public void correctFileReadSizeTest() {
		assertTrue(edges.size() == 1);
	}

	@Test
	public void correctFileReadBeginNodeTest() {
		assertTrue(edges.get(0).getStartNode().getId() == 20);
	}

	@Test
	public void correctFileReadEndNodeTest() {
		assertTrue(edges.get(0).getEndNode().getId() == 2);
	}
}
