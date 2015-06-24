/**
 * 
 */
package nl.tudelft.ti2806.pl1.zoomlevels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.graph.ConvertDGraph;
import nl.tudelft.ti2806.pl1.graph.DEdge;
import nl.tudelft.ti2806.pl1.graph.DGraph;
import nl.tudelft.ti2806.pl1.graph.DNode;

import org.graphstream.graph.Graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Chak Shun
 *
 */
public class HorizontalCollapserTest {

	DGraph graph;
	DNode start, end, SNPNode1, SNPNode2, unify;
	HashSet<String> sources;
	Graph gsg;
	DEdge e1, e2, e3, e4, e5;
	HashSet<String> insertSrc1;
	HashSet<String> insertSrc2;

	@Before
	public void setUp() {
		sources = new HashSet<String>(Arrays.asList("TKK_REF", "REF1", "REF2"));
		insertSrc1 = new HashSet<String>(Arrays.asList("TKK_REF", "REF1"));
		insertSrc2 = new HashSet<String>(Arrays.asList("REF2"));

		start = new DNode(-2, sources, 0, 0, "");
		start.setDepth(1);
		SNPNode1 = new DNode(2, insertSrc1, 0, 0, "");
		SNPNode1.setDepth(2);
		SNPNode2 = new DNode(3, insertSrc2, 0, 0, "");
		SNPNode2.setDepth(2);
		unify = new DNode(6, sources, 0, 0, "");
		unify.setDepth(3);
		end = new DNode(4, sources, 0, 0, "");
		end.setDepth(4);
		e1 = new DEdge(start, SNPNode1);
		e2 = new DEdge(start, SNPNode2);
		e3 = new DEdge(SNPNode1, unify);
		e4 = new DEdge(SNPNode2, unify);
		e5 = new DEdge(unify, end);
		SNPNode1.setContent("A");
		SNPNode2.setContent("T");
		graph = new DGraph();
		graph.addDNode(start);
		graph.addDNode(SNPNode1);
		graph.addDNode(SNPNode2);
		graph.addDNode(unify);
		graph.addDNode(end);
		graph.addDEdge(e1);
		graph.addDEdge(e2);
		graph.addDEdge(e3);
		graph.addDEdge(e4);
		graph.addDEdge(e5);
		graph.setStart(start);
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testCollapsing() {
		gsg = ConvertDGraph.convert(graph);
		assertTrue(gsg.getNode(String.valueOf(start.getId())) != null);
		assertTrue(gsg.getNode(String.valueOf(SNPNode1.getId())) != null);
		assertTrue(gsg.getNode(String.valueOf(SNPNode2.getId())) != null);
		assertTrue(gsg.getNode(String.valueOf(unify.getId())) != null);
		assertTrue(gsg.getNode(String.valueOf(end.getId())) != null);
		gsg = HorizontalCollapser.horizontalCollapse(gsg);
		assertTrue(gsg.getNode(String.valueOf(start.getId())) != null);
		assertTrue(gsg.getNode(String.valueOf(SNPNode1.getId())) != null);
		assertTrue(gsg.getNode(String.valueOf(SNPNode2.getId())) != null);
		assertTrue(gsg.getNode(String.valueOf(unify.getId())) == null);
		assertTrue(gsg.getNode(String.valueOf(end.getId())) != null);
	}

	@Test
	public void testCSSAttributes() {
		String resistant = "resistant";
		String selected = "selected";
		gsg = ConvertDGraph.convert(graph);

		gsg.getNode(String.valueOf(unify.getId())).addAttribute("ui.class",
				resistant);
		gsg.getNode(String.valueOf(end.getId())).addAttribute("ui.class",
				selected);
		gsg.getNode(String.valueOf(end.getId())).removeAttribute("collapsed");

		gsg = HorizontalCollapser.horizontalCollapse(gsg);

		assertEquals(selected, gsg.getNode(String.valueOf(end.getId()))
				.getAttribute("ui.class"));
		assertEquals(resistant, gsg.getNode(String.valueOf(end.getId()))
				.getAttribute("oldclass"));

	}
}
