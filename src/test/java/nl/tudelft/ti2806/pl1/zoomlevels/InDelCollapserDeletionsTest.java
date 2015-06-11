package nl.tudelft.ti2806.pl1.zoomlevels;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.DGraph.ConvertDGraph;
import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.mutation.DeletionMutation;
import nl.tudelft.ti2806.pl1.mutation.MutationFinder;

import org.graphstream.graph.Graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InDelCollapserDeletionsTest {

	DGraph graph;
	DNode start, end, deletion;
	DEdge si, ie, se;
	HashSet<String> insertSrc;
	HashSet<String> sources;
	Graph gsg;

	@Before
	public void setup() {
		sources = new HashSet<String>(Arrays.asList("TKK_REF", "REF1", "REF2"));
		insertSrc = new HashSet<String>(Arrays.asList("REF2"));

		start = new DNode(1, sources, 0, 0, "");
		start.setDepth(1);
		end = new DNode(2, sources, 0, 0, "");
		end.setDepth(3);
		deletion = new DNode(3, sources, 0, 0, "");
		deletion.setDepth(2);
		si = new DEdge(start, deletion);
		ie = new DEdge(deletion, end);
		se = new DEdge(start, end);
		graph = new DGraph();
		graph.addDNode(start);
		graph.addDNode(deletion);
		graph.addDNode(end);
		graph.addDEdge(si);
		graph.addDEdge(se);
		graph.addDEdge(ie);
	}

	@After
	public void teardown() {
		graph = null;

		start = null;
		end = null;
		deletion = null;

		si = null;
		ie = null;
		se = null;
	}

	@Test
	public void simpleDeletionCaseTest() {
		Collection<DeletionMutation> muts = MutationFinder
				.findDeletionMutations(graph);
		gsg = ConvertDGraph.convert(graph);
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("3") != null);
		assertTrue(gsg.getNode("1").hasEdgeBetween(gsg.getNode("2")));
		assertTrue(gsg.getNode("1").hasEdgeBetween(gsg.getNode("3")));
		for (DeletionMutation mut : muts) {
			InDelCollapser.collapseDeletion(mut, gsg);
		}
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("3") != null);
		assertFalse(gsg.getNode("1").hasEdgeBetween(gsg.getNode("2")));
		assertTrue(gsg.getNode("1").hasEdgeBetween(gsg.getNode("3")));
	}

	@Test
	public void multipleDeletionsTest() {
		DNode deletion2 = new DNode(4, sources, 0, 0, "");
		deletion2.setDepth(4);
		DNode end2 = new DNode(5, sources, 0, 0, "");
		end2.setDepth(5);
		graph.addDNode(deletion2);
		graph.addDNode(end2);
		graph.addDEdge(new DEdge(end, deletion2));
		graph.addDEdge(new DEdge(deletion2, end2));
		graph.addDEdge(new DEdge(end, end2));
		Collection<DeletionMutation> muts = MutationFinder
				.findDeletionMutations(graph);
		gsg = ConvertDGraph.convert(graph);
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("3") != null);
		assertTrue(gsg.getNode("4") != null);
		assertTrue(gsg.getNode("5") != null);
		assertTrue(gsg.getNode("1").hasEdgeBetween(gsg.getNode("2")));
		assertTrue(gsg.getNode("2").hasEdgeBetween(gsg.getNode("5")));
		for (DeletionMutation mut : muts) {
			InDelCollapser.collapseDeletion(mut, gsg);
		}
		assertFalse(gsg.getNode("1").hasEdgeBetween(gsg.getNode("2")));
		assertFalse(gsg.getNode("2").hasEdgeBetween(gsg.getNode("5")));
	}

	@Test
	public void DeletionWithSNPTest() {
		DNode SNP = new DNode(6, insertSrc, 0, 0, "");
		DNode SNPREF = new DNode(7, sources, 0, 0, "");
		DNode deletion2 = new DNode(4, sources, 0, 0, "");
		DNode end2 = new DNode(5, sources, 0, 0, "");
		DNode end3 = new DNode(8, sources, 0, 0, "");
		SNP.setDepth(4);
		SNPREF.setDepth(4);
		end2.setDepth(5);
		deletion2.setDepth(6);
		end3.setDepth(7);
		graph.addDNode(deletion2);
		graph.addDNode(end2);
		graph.addDNode(end3);
		graph.addDNode(SNP);
		graph.addDNode(SNPREF);

		graph.addDEdge(new DEdge(end, SNP));
		graph.addDEdge(new DEdge(end, SNPREF));
		graph.addDEdge(new DEdge(SNP, end2));
		graph.addDEdge(new DEdge(SNPREF, end2));
		graph.addDEdge(new DEdge(end2, end3));
		graph.addDEdge(new DEdge(end2, deletion2));
		graph.addDEdge(new DEdge(deletion2, end3));
		Collection<DeletionMutation> muts = MutationFinder
				.findDeletionMutations(graph);
		gsg = ConvertDGraph.convert(graph);
		assertTrue(gsg.getNode("1").hasEdgeBetween(gsg.getNode("2")));
		assertTrue(gsg.getNode("5").hasEdgeBetween(gsg.getNode("8")));

		for (DeletionMutation mut : muts) {
			InDelCollapser.collapseDeletion(mut, gsg);
		}
		assertFalse(gsg.getNode("1").hasEdgeBetween(gsg.getNode("2")));
		assertFalse(gsg.getNode("5").hasEdgeBetween(gsg.getNode("8")));
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("3") != null);
		assertTrue(gsg.getNode("4") != null);
		assertTrue(gsg.getNode("5") != null);
		assertTrue(gsg.getNode("6") != null);
		assertTrue(gsg.getNode("7") != null);
		assertTrue(gsg.getNode("8") != null);
	}

	@Test
	public void DeletionWithInsertionTest() {
		DNode insertion = new DNode(6, insertSrc, 0, 0, "");
		DNode deletion2 = new DNode(4, sources, 0, 0, "");
		DNode end2 = new DNode(5, sources, 0, 0, "");
		DNode end3 = new DNode(8, sources, 0, 0, "");
		insertion.setDepth(4);
		end2.setDepth(5);
		deletion2.setDepth(6);
		end3.setDepth(7);
		graph.addDNode(deletion2);
		graph.addDNode(end2);
		graph.addDNode(end3);
		graph.addDNode(insertion);

		graph.addDEdge(new DEdge(end, insertion));
		graph.addDEdge(new DEdge(end, end2));
		graph.addDEdge(new DEdge(insertion, end2));
		graph.addDEdge(new DEdge(end2, end3));
		graph.addDEdge(new DEdge(end2, deletion2));
		graph.addDEdge(new DEdge(deletion2, end3));
		Collection<DeletionMutation> muts = MutationFinder
				.findDeletionMutations(graph);
		gsg = ConvertDGraph.convert(graph);
		assertTrue(gsg.getNode("1").hasEdgeBetween(gsg.getNode("2")));
		assertTrue(gsg.getNode("5").hasEdgeBetween(gsg.getNode("8")));

		for (DeletionMutation mut : muts) {
			InDelCollapser.collapseDeletion(mut, gsg);
		}
		assertFalse(gsg.getNode("1").hasEdgeBetween(gsg.getNode("2")));
		assertFalse(gsg.getNode("5").hasEdgeBetween(gsg.getNode("8")));
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("3") != null);
		assertTrue(gsg.getNode("4") != null);
		assertTrue(gsg.getNode("5") != null);
		assertTrue(gsg.getNode("6") != null);
		assertTrue(gsg.getNode("8") != null);
	}
}
