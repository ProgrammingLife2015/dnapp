package nl.tudelft.ti2806.pl1.DGraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DGraphTest {

	DGraph graph;

	DNode node1;
	DNode node2;
	DNode node3;

	DEdge edge1;
	DEdge edge2;

	@Before
	public void setup() {
		graph = new DGraph();

		node1 = mock(DNode.class);
		node2 = mock(DNode.class);
		node3 = mock(DNode.class);

		edge1 = mock(DEdge.class);
		edge2 = mock(DEdge.class);

		when(node1.getId()).thenReturn(1);
		when(node1.getAllEdges()).thenReturn(Arrays.asList(edge1));

		when(node2.getId()).thenReturn(2);
		when(node2.getAllEdges()).thenReturn(Arrays.asList(edge1, edge2));

		when(node3.getId()).thenReturn(3);
		when(node3.getAllEdges()).thenReturn(Arrays.asList(edge2));
	}

	@After
	public void teardown() {
		node1 = null;
		graph = null;
	}

	@Test
	public void addNewNodeTest() {
		assertTrue(graph.addDNode(node1));
	}

	@Test
	public void addExistingTest() {
		graph.addDNode(node1);
		assertFalse(graph.addDNode(node1));
	}

	@Test
	public void addedNodeIncreasesSizeTest() {
		graph.addDNode(node1);
		assertEquals(graph.getNodeCount(), 1);
	}

	@Test
	public void deleteNonExistingNodeTest() {
		assertFalse(graph.removeDNode(node1));
	}

	@Test
	public void deleteExistingNodeTest() {
		graph.addDNode(node1);
		assertTrue(graph.removeDNode(node1));
	}

	@Test
	public void deleteNonExistingNodeIDTest() {
		assertFalse(graph.removeDNode(1));
	}

	@Test
	public void deleteExistingNodeIDTest() {
		graph.addDNode(node1);
		assertTrue(graph.removeDNode(1));
	}

	@Test
	public void deleteNodeDeletesEdges() {
		graph.addDNode(node1);
		graph.addDNode(node2);
		graph.addDNode(node3);
	}

}
