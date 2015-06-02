package nl.tudelft.ti2806.pl1.DGraph;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;

public class DGraphTest {

	DGraph graph = new DGraph("src/test/resources/dgraphtestdb");

	@Test
	public void addNewNodeTest() {
		graph.addNode(0, 0, 5, "ATAGA", new Point(0, 0), 0,
				new String[] { "TKK-REF" });
		assertEquals(graph.getNodes().size(), 1);
	}

	@Test
	public void addExisitingTest() {
		graph.addNode(0, 0, 2, "ATC", new Point(0, 0), 0,
				new String[] { "TKK-REF" });
	}

}