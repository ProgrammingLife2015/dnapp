package nl.tudelft.ti2806.pl1.DGraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DNodeTest {

	DEdge edge1;
	DEdge edge2;
	DEdge edge3;

	DNode node1;
	DNode node2;
	DNode node3;
	DNode node4;
	DNode node5;

	@Before
	public void setup() {
		edge1 = mock(DEdge.class);
		edge2 = mock(DEdge.class);
		edge3 = mock(DEdge.class);

		node1 = new DNode(1, new HashSet<String>(), 0, 0, "");
		node2 = new DNode(2, new HashSet<String>(), 0, 0, "");
		node3 = new DNode(3, new HashSet<String>(), 0, 0, "");
		node4 = new DNode(1, new HashSet<String>(), 0, 0, "");
		node5 = new DNode(1, new HashSet<String>(), 0, 0, "");

		when(edge1.getStartNode()).thenReturn(1);
		when(edge1.getEndNode()).thenReturn(2);

		when(edge2.getStartNode()).thenReturn(2);
		when(edge2.getEndNode()).thenReturn(3);

		when(edge3.getStartNode()).thenReturn(1);
		when(edge3.getEndNode()).thenReturn(2);
	}

	@After
	public void teardown() {
		edge1 = null;
		edge2 = null;
		node1 = null;
		node2 = null;
		node3 = null;
	}

	@Test
	public void addEdgeStartNodeTest() {
		node2.addEdge(edge1);
		assertEquals(node2.getInEdges().size(), 1);
	}

	@Test
	public void addEndNodeTest() {
		node2.addEdge(edge2);
		assertEquals(node2.getOutEdges().size(), 1);
	}

	@Test
	public void rightEdgeHasBeenAddedTest() {
		node2.addEdge(edge1);
		ArrayList<DEdge> edges = (ArrayList<DEdge>) node2.getInEdges();
		assertTrue(edges.get(0) == edge1);
	}

	@Test
	public void addExistingEdgeSizeTest() {
		node2.addEdge(edge1);
		node2.addEdge(edge3);
		assertEquals(node2.getInEdges().size(), 1);
	}

	@Test
	public void equalsSameObjectTest() {
		assertTrue(node1.equals(node1));
	}

	@Test
	public void equalsABtest() {
		assertTrue(node1.equals(node4));
	}

	@Test
	public void equalsBCTest() {
		assertTrue(node4.equals(node5));
	}

	@Test
	public void equalsACTest() {
		assertTrue(node1.equals(node5));
	}

	@Test
	public void equalsNullTest() {
		assertFalse(node1.equals(null));
	}

	@Test
	public void hashCodeTest() {
		assertEquals(node1.hashCode(), 1);
	}

	@Test
	public void deleteEdgeTest() {
		node1.addEdge(edge1);
		assertTrue(node1.deleteEdge(edge1));
	}

	@Test
	public void deletedEdgeIsDeletedTest1() {
		node1.addEdge(edge1);
		node1.deleteEdge(edge1);
		assertEquals(node1.getOutEdges().size(), 0);
	}

	@Test
	public void deletedEdgeIsDeletedTest2() {
		node2.addEdge(edge1);
		node2.deleteEdge(edge1);
		assertEquals(node2.getInEdges().size(), 0);
	}

	@Test
	public void getNextNodesTest() {
		node2.addEdge(edge2);
		assertTrue(node2.getNextNodes().contains(node3.getId()));
		assertEquals(node2.getNextNodes().size(), 1);
	}

	@Test
	public void getPreviousNodesTest() {
		node2.addEdge(edge1);
		assertTrue(node2.getPreviousNodes().contains(node1.getId()));
		assertEquals(node2.getPreviousNodes().size(), 1);
	}

	@Test
	public void getAllEdgesTest() {
		node2.addEdge(edge1);
		node2.addEdge(edge2);
		assertEquals(node2.getAllEdges().size(), 2);
	}

	@Test
	public void addNewEdgeTest() {
		assertTrue(node2.addEdge(edge1));
	}

	@Test
	public void addExistingEdgeTest() {
		node2.addEdge(edge2);
		assertFalse(node2.addEdge(edge2));
	}
}
