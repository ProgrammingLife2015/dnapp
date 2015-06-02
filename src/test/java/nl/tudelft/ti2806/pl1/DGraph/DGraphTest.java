package nl.tudelft.ti2806.pl1.DGraph;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.MultipleFoundException;

public class DGraphTest {

	static DGraph graph;

	@BeforeClass
	public static void setUp() throws IOException {
		graph = new DGraph("src/test/resources/dgraphtestdb");
	}

	@AfterClass
	public static void tearDown() {
		graph = null;
	}

	@Test
	public void addNewNodeTest() {
		graph.addNode(0, 0, 5, "ATAGA", new Point(0, 0), 0,
				new String[] { "TKK-REF" });
		assertEquals(graph.getNodes().size(), 1);
	}

	@Test(expected = MultipleFoundException.class)
	public void addExisitingTest() {
		graph.addNode(0, 0, 2, "ATC", new Point(0, 0), 0,
				new String[] { "TKK-REF" });
	}

	@Test
	public void addNodeExistingSource() {
		graph.addNode(1, 0, 5, "ATGCA", new Point(0, 0), 0,
				new String[] { "TKK-ID2" });
		assertEquals(graph.getNodes().size(), 2);
		assertEquals(graph.getSources().size(), 2);
	}
}