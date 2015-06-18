package nl.tudelft.ti2806.pl1.mutation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Maarten, Justin
 */
public class DeletionMutationTest {

	DGraph graph;
	DNode start, end, deletion;
	DEdge si, ie, se;
	HashSet<String> insertSrc;
	HashSet<String> sources;
	ReferenceGeneStorage rgs;

	@Before
	public void setup() {
		sources = new HashSet<String>(Arrays.asList("TKK_REF", "REF1", "REF2"));
		insertSrc = new HashSet<String>(Arrays.asList("TKK_2"));
		rgs = new ReferenceGeneStorage("ReferenceGeneStorageTestGenes.gff",
				"resistanceCausingMutationsTesting.txt");

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
		Collection<DeletionMutation> expected = new ArrayList<DeletionMutation>();
		expected.add(new DeletionMutation(start.getId(), end.getId(), rgs, 0, 0));
		assertEquals(expected, muts);
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
		Collection<DeletionMutation> expected = Arrays.asList(
				new DeletionMutation(start.getId(), end.getId(), rgs, 0, 0),
				new DeletionMutation(end.getId(), end2.getId(), rgs, 0, 0));
		assertEquals(expected, muts);
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
		Collection<DeletionMutation> expected = Arrays.asList(
				new DeletionMutation(start.getId(), end.getId(), rgs, 0, 0),
				new DeletionMutation(end2.getId(), end3.getId(), rgs, 0, 0));
		assertEquals(expected, muts);
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
		Collection<DeletionMutation> expected = Arrays.asList(
				new DeletionMutation(start.getId(), end.getId(), rgs, 0, 0),
				new DeletionMutation(end2.getId(), end3.getId(), rgs, 0, 0));
		assertEquals(expected, muts);
	}

}
