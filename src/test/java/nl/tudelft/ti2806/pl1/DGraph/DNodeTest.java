package nl.tudelft.ti2806.pl1.DGraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import nl.tudelft.ti2806.pl1.mutation.ResistanceMutation;

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

		node1 = new DNode(1, new HashSet<String>(), 0, 0, "NASDJASD");
		node2 = new DNode(2, new HashSet<String>(), 0, 0, "");
		node3 = new DNode(3, new HashSet<String>(), 0, 0, "");
		node4 = new DNode(1, new HashSet<String>(), 0, 0, "");
		node5 = new DNode(1, new HashSet<String>(), 0, 0, "");

		when(edge1.getStartNode()).thenReturn(node1);
		when(edge1.getEndNode()).thenReturn(node2);

		when(edge2.getStartNode()).thenReturn(node2);
		when(edge2.getEndNode()).thenReturn(node3);

		when(edge3.getStartNode()).thenReturn(node1);
		when(edge3.getEndNode()).thenReturn(node2);
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
	public void testGetters() {
		assertEquals(node1.getX(), 0);
		assertEquals(node1.getY(), 0);
		assertEquals(node1.getResMuts(), null);
		assertEquals(node1.getContent(), "NASDJASD");
		assertEquals(node1.getStart(), 0);
		assertEquals(node1.getEnd(), 0);
		assertEquals(node1.getDepth(), 0);
		assertEquals(node1.getSources(), new HashSet<String>());
		assertEquals(node1.getPercUnknown(), 0.125, 0);
		assertEquals(node1.getId(), 1);
	}

	@Test
	public void testSetters() {
		List<ResistanceMutation> LRM = new ArrayList<ResistanceMutation>();
		String content = "NASDJASD";
		int newx = 30;
		int newy = 1;
		int newstart = Integer.MAX_VALUE;
		int newend = Integer.MIN_VALUE;
		int newdepth = -200;
		HashSet<String> newsources = new HashSet<String>();
		newsources.add("a source");
		int newid = 2;
		double newperc = 2.0;
		Collection<DEdge> newedges = new HashSet<DEdge>();
		node1.setX(newx);
		assertEquals(node1.getX(), newx);
		node1.setY(newy);
		assertEquals(node1.getY(), newy);
		node1.setResMuts(LRM);
		assertEquals(node1.getResMuts(), LRM);
		node1.setContent(content);
		assertEquals(node1.getContent(), content);
		node1.setStart(newstart);
		assertEquals(node1.getStart(), newstart);
		node1.setEnd(newend);
		assertEquals(node1.getEnd(), newend);
		node1.setDepth(newdepth);
		assertEquals(node1.getDepth(), newdepth);
		node1.setSources(newsources);
		assertEquals(node1.getSources(), newsources);
		node1.setId(newid);
		assertEquals(node1.getId(), newid);
		node1.setPercUnknown(newperc);
		assertEquals(node1.getPercUnknown(), newperc, 0);
		node1.setOutEdges(newedges);
		assertEquals(node1.getOutEdges(), newedges);
	}

	@Test
	public void testToString() {
		assertEquals(node1.toString(), "<Node[1]>");
	}

	@Test
	public void negativeId() {
		DNode node = new DNode(-2, new HashSet<String>(), 0, 0, "");
		assertEquals(node.getPercUnknown(), 1.0, 0);
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
		assertEquals(edges.get(0), edge1);
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
		assertTrue(node2.getNextNodes().contains(node3));
		assertEquals(node2.getNextNodes().size(), 1);
	}

	@Test
	public void getPreviousNodesTest() {
		node2.addEdge(edge1);
		assertTrue(node2.getPreviousNodes().contains(node1));
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
