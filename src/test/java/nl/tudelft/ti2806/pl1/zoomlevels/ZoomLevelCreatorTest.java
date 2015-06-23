/**
 * 
 */
package nl.tudelft.ti2806.pl1.zoomlevels;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.mutation.ComplexMutation;
import nl.tudelft.ti2806.pl1.mutation.DeletionMutation;
import nl.tudelft.ti2806.pl1.mutation.InsertionMutation;
import nl.tudelft.ti2806.pl1.mutation.PointMutation;

import org.graphstream.graph.Graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Keraito
 *
 */
public class ZoomLevelCreatorTest {

	private ZoomlevelCreator zlc;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		DNode node1 = mock(DNode.class);
		when(node1.getSources()).thenReturn(new HashSet<String>());
		DGraph dgraph = mock(DGraph.class);
		when(dgraph.getInsMutations()).thenReturn(
				new HashSet<InsertionMutation>());
		when(dgraph.getDelMutations()).thenReturn(
				new HashSet<DeletionMutation>());
		when(dgraph.getComplexMutations()).thenReturn(
				new HashSet<ComplexMutation>());
		when(dgraph.getPointMutations()).thenReturn(
				new HashSet<PointMutation>());
		when(dgraph.getStart()).thenReturn(node1);
		when(dgraph.getNodes()).thenReturn(new HashMap<Integer, DNode>());
		when(dgraph.getEdges()).thenReturn(new HashSet<DEdge>());
		zlc = new ZoomlevelCreator(dgraph);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() {
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.zoomlevels.ZoomlevelCreator#createGraph(int)}
	 * .
	 */
	@Test
	public void testCreateGraph() {
		assertTrue(zlc.createGraph(0) instanceof Graph);
	}
}
