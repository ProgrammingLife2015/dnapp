package nl.tudelft.ti2806.pl1.DGraph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DEdgeTest {

	DNode node1;
	DNode node2;
	DNode node3;

	DEdge edge1;
	DEdge edge2;
	DEdge edge3;
	DEdge edge4;

	@Before
	public void setup() {
		node1 = mock(DNode.class);
		node2 = mock(DNode.class);
		node3 = mock(DNode.class);

		when(node1.getId()).thenReturn(1);
		when(node2.getId()).thenReturn(2);
		when(node3.getId()).thenReturn(3);

		edge1 = new DEdge(node1, node2);
		edge2 = new DEdge(node1, node2);
		edge3 = new DEdge(node2, node3);
		edge4 = new DEdge(node1, node2);
	}

	@After
	public void tearDown() {
		node1 = null;
		node2 = null;
		node3 = null;
		edge1 = null;
		edge2 = null;
		edge3 = null;
	}

	@Test
	public void equalsTest1() {
		edge1.equals(edge2);
		verify(node1, times(2)).getId();
	}

	@Test
	public void equalsTest2() {
		assertTrue(edge1.equals(edge2));
	}

	@Test
	public void equalsTest3() {
		assertFalse(edge1.equals(edge3));
	}

	@Test
	public void equalsTest4() {
		assertFalse(edge1.equals(null));
	}

	@Test
	public void equalsTest5() {
		assertTrue(edge2.equals(edge4));
	}

	@Test
	public void equalsTest6() {
		assertTrue(edge1.equals(edge4));
	}
}
