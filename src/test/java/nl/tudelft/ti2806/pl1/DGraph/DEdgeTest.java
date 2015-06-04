package nl.tudelft.ti2806.pl1.DGraph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DEdgeTest {

	DEdge edge1;
	DEdge edge2;
	DEdge edge3;
	DEdge edge4;

	@Before
	public void setup() {
		edge1 = new DEdge(1, 2);
		edge2 = new DEdge(1, 2);
		edge3 = new DEdge(2, 3);
		edge4 = new DEdge(1, 3);
	}

	@After
	public void tearDown() {
		edge1 = null;
		edge2 = null;
		edge3 = null;
	}

	@Test
	public void equalsTest() {
		assertTrue(edge1.equals(edge2));
	}

	@Test
	public void equalsNotTest() {
		assertFalse(edge1.equals(edge3));
	}

	@Test
	public void equalsNullTest() {
		assertFalse(edge1.equals(null));
	}
}
