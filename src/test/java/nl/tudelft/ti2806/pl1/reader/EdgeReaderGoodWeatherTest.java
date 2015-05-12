package nl.tudelft.ti2806.pl1.reader;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Scanner;

import nl.tudelft.ti2806.pl1.graph.Edge;
import nl.tudelft.ti2806.pl1.reader.EdgeReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EdgeReaderGoodWeatherTest {

	String edge = "20 2";
	ArrayList<Edge> edges;

	@Before
	public void startUp() {
		Scanner sc = new Scanner(edge);
		edges = EdgeReader.readEdges(sc);
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
		assertTrue(edges.get(0).getStartNode() == 20);
	}

	@Test
	public void correctFileReadEndNodeTest() {
		assertTrue(edges.get(0).getEndNode() == 2);
	}
}
