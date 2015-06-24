package nl.tudelft.ti2806.pl1.zoomlevels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.graph.ConvertDGraph;
import nl.tudelft.ti2806.pl1.graph.DEdge;
import nl.tudelft.ti2806.pl1.graph.DGraph;
import nl.tudelft.ti2806.pl1.graph.DNode;
import nl.tudelft.ti2806.pl1.mutation.PointMutation;
import nl.tudelft.ti2806.pl1.mutation.PointMutationFinder;

import org.graphstream.graph.Graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PointCollapserTest {

	DGraph graph;
	DNode start, end, SNPNode1, SNPNode2;
	HashSet<String> sources;
	Graph gsg;
	DEdge e1, e2, e3, e4;
	HashSet<String> insertSrc1;
	HashSet<String> insertSrc2;

	@Before
	public void setup() {
		sources = new HashSet<String>(Arrays.asList("TKK_REF", "REF1", "REF2"));
		insertSrc1 = new HashSet<String>(Arrays.asList("TKK_REF", "REF1"));
		insertSrc2 = new HashSet<String>(Arrays.asList("REF2"));

		start = new DNode(1, sources, 0, 0, "");
		start.setDepth(1);
		SNPNode1 = new DNode(2, insertSrc1, 0, 0, "");
		SNPNode1.setDepth(2);
		SNPNode2 = new DNode(3, insertSrc2, 0, 0, "");
		SNPNode2.setDepth(2);
		end = new DNode(4, sources, 0, 0, "");
		end.setDepth(3);
		e1 = new DEdge(start, SNPNode1);
		e2 = new DEdge(start, SNPNode2);
		e3 = new DEdge(SNPNode1, end);
		e4 = new DEdge(SNPNode2, end);
		SNPNode1.setContent("A");
		SNPNode2.setContent("T");
		graph = new DGraph();
		graph.addDNode(start);
		graph.addDNode(SNPNode1);
		graph.addDNode(SNPNode2);
		graph.addDNode(end);
		graph.addDEdge(e1);
		graph.addDEdge(e2);
		graph.addDEdge(e3);
		graph.addDEdge(e4);
	}

	@After
	public void teardown() {
		graph = null;
		start = null;
		end = null;
		SNPNode1 = null;
		SNPNode2 = null;
		e1 = null;
		e2 = null;
		e3 = null;
		e4 = null;
	}

	@Test
	public void simpleSNPCaseTest() {
		Collection<PointMutation> muts = PointMutationFinder
				.findPointMutations(graph);
		graph.setStart(start);
		gsg = ConvertDGraph.convert(graph);
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("3") != null);
		assertTrue(gsg.getNode("4") != null);
		for (PointMutation mut : muts) {
			mut.setScore(0);
		}
		gsg = PointCollapser.collapseNodes(muts, gsg, 10, "");
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") == null);
		assertTrue(gsg.getNode("3") == null);
		assertTrue(gsg.getNode("4") != null);
		assertTrue(gsg.getNode("COLLAPSED_2/3/4") != null);
	}

	@Test
	public void simpleSNPCaseThresholdTest() {
		Collection<PointMutation> muts = new HashSet<PointMutation>();
		PointMutation m1 = mock(PointMutation.class);
		PointMutation m2 = mock(PointMutation.class);
		PointMutation m3 = mock(PointMutation.class);
		when(m1.getScore()).thenReturn(20d);
		when(m2.getScore()).thenReturn(20d);
		when(m3.getScore()).thenReturn(20d);
		muts.add(m1);
		muts.add(m2);
		muts.add(m3);

		graph.setStart(start);
		gsg = ConvertDGraph.convert(graph);

		Graph newgsg = PointCollapser.collapseNodes(muts, gsg, 10, "");
		assertEquals(newgsg, gsg);
	}

	@Test
	public void findSelectedTest() {
		graph.setStart(start);
		gsg = ConvertDGraph.convert(graph);
		assertEquals(String.valueOf(Integer.MIN_VALUE),
				PointCollapser.findSelected(gsg));

		String id = "SELECTEDNODE";
		gsg.addNode(id);
		gsg.getNode(id).setAttribute("ui.class", "selected");
		assertEquals(id, PointCollapser.findSelected(gsg));
	}

	@Test
	public void extendedSNPTest() {
		DNode extra = new DNode(5, sources, 0, 0, "");
		DNode extra2 = new DNode(6, sources, 0, 0, "");
		DEdge e5 = new DEdge(SNPNode1, extra);
		DEdge e6 = new DEdge(extra2, SNPNode2);
		graph.addDNode(extra);
		graph.addDEdge(e5);
		graph.addDNode(extra2);
		graph.addDEdge(e6);

		Collection<PointMutation> muts = PointMutationFinder
				.findPointMutations(graph);
		graph.setStart(start);
		gsg = ConvertDGraph.convert(graph);

		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("3") != null);
		assertTrue(gsg.getNode("4") != null);
		gsg = PointCollapser.collapseNodes(muts, gsg, 10,
				String.valueOf(SNPNode1.getId()));
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("3") != null);
		assertTrue(gsg.getNode("4") != null);
		assertTrue(gsg.getNode("COLLAPSED_2/3/4") != null);
		assertTrue(gsg.getNode("COLLAPSED_2/3/4").getAttribute("ui.class")
				.equals("selected"));
	}

}
