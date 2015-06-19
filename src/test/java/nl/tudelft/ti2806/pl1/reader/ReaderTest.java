package nl.tudelft.ti2806.pl1.reader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReaderTest {

	String nodes = "src/test/resources/nodes.txt";
	String edges = "src/test/resources/edges.txt";
	DGraph graph = new DGraph();

	@Before
	public void setUp() throws IOException {
		graph = Reader.read(nodes, edges);
	}

	@After
	public void tearDown() {
		graph = null;
		nodes = null;
		edges = null;
	}

	@Test
	public void added2NodesTest() {
		assertEquals(graph.getNodeCount(), 3);
	}

	@Test
	public void getStartNodeTest() {
		assertEquals(graph.getStart(), graph.getDNode(-2));
	}

	@Test
	public void starNodeHasEdgeToNode1Test() {
		ArrayList<DEdge> edges = (ArrayList<DEdge>) graph.getDNode(-2).getOutEdges();
		assertEquals(edges.get(0).getEndNode(), graph.getDNode(0));
	}

	@Test
	public void startNodeHasAllSourcesTest() {
		HashSet<String> temp = new HashSet<String>();
		temp.add("TKK-1");
		temp.add("TKK-2");
		temp.add("TKK-4");
		HashSet<String> sources = graph.getDNode(-2).getSources();
		assertEquals(temp, sources);
	}
}
