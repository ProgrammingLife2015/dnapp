package nl.tudelft.ti2806.pl1.DGraph;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;

import org.graphstream.graph.Graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConvertDGraphTest {

	DGraph graph;

	DNode node1;
	DNode node2;

	DEdge edge;

	Graph gsGraph;

	@Before
	public void setup() {
		graph = mock(DGraph.class);

		node1 = mock(DNode.class);
		node2 = mock(DNode.class);

		edge = mock(DEdge.class);

		when(node1.getId()).thenReturn(1);
		when(node1.getX()).thenReturn(0);
		when(node1.getY()).thenReturn(0);
		when(node1.getPercUnknown()).thenReturn(1.0);
		when(node1.getContent()).thenReturn("CATG");

		when(node2.getId()).thenReturn(2);
		when(node2.getX()).thenReturn(20);
		when(node2.getY()).thenReturn(0);
		when(node2.getPercUnknown()).thenReturn(1.0);
		when(node2.getContent()).thenReturn("CATG");

		when(edge.getStartNode()).thenReturn(node1);
		when(edge.getEndNode()).thenReturn(node2);

		HashMap<Integer, DNode> map = new HashMap<Integer, DNode>();
		map.put(1, node1);
		map.put(2, node2);

		when(graph.getNodes()).thenReturn(map);
		when(graph.getEdges()).thenReturn(Arrays.asList(edge));
		when(graph.getStart()).thenReturn(node1);

		gsGraph = ConvertDGraph.convert(graph);
	}

	@After
	public void teardown() {
		graph = null;
		node1 = null;
		node2 = null;
		edge = null;
		gsGraph = null;
	}

	@Test
	public void correctXNode1Test() {
		assertEquals(gsGraph.getNode(0).getAttribute("x"), new Integer(0));
	}

	@Test
	public void correctYNode1Test() {
		assertEquals(gsGraph.getNode(0).getAttribute("y"), new Integer(0));
	}

	@Test
	public void correctXNode2Test() {
		assertEquals(gsGraph.getNode(1).getAttribute("x"), new Integer(20));
	}

	@Test
	public void correctYNode2Test() {
		assertEquals(gsGraph.getNode(1).getAttribute("y"), new Integer(0));
	}

	@Test
	public void correctEdgeTest() {
		assertEquals(gsGraph.getEdge(0).getId(), "12");
	}

}
