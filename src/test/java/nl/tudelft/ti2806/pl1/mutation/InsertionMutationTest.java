package nl.tudelft.ti2806.pl1.mutation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;
import nl.tudelft.ti2806.pl1.graph.DEdge;
import nl.tudelft.ti2806.pl1.graph.DGraph;
import nl.tudelft.ti2806.pl1.graph.DNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Maarten, Justin
 */
public class InsertionMutationTest {

	DGraph graph;
	DNode start, end, insertion;
	DEdge si, ie, se;
	HashSet<String> sources;
	ReferenceGeneStorage rgs;

	@Before
	public void setup() {
		sources = new HashSet<String>(Arrays.asList("TKK_REF", "REF1", "REF2"));
		HashSet<String> insertSrc = new HashSet<String>(Arrays.asList("REF2"));
		rgs = new ReferenceGeneStorage(null);
		rgs.setDrugRestistantMutations(new File(
				"src/test/resources/resistanceCausingMutationsTesting.txt"));
		rgs.setGeneAnnotation(new File(
				"src/test/resources/ReferenceGeneStorageTestGenes.gff"));

		start = new DNode(1, sources, 0, 0, "");
		insertion = new DNode(2, insertSrc, 0, 0, "");
		end = new DNode(4, sources, 0, 0, "");
		si = new DEdge(start, insertion);
		ie = new DEdge(insertion, end);
		se = new DEdge(start, end);
		graph = new DGraph();
		graph.addDNode(start);
		graph.addDNode(insertion);
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
		insertion = null;

		si = null;
		ie = null;
		se = null;
	}

	@Test
	public void simpleInsertionCaseTest() {
		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph, null);
		Collection<InsertionMutation> expected = Arrays
				.asList(new InsertionMutation(start.getId(), end.getId(), 0, 0,
						rgs, insertion.getId()));
		assertEquals(expected, muts);
	}

	@Test
	public void doubleInsertionTest() {
		HashSet<String> insertSrc2 = new HashSet<String>(Arrays.asList("REF1"));
		DNode insertion2 = new DNode(3, insertSrc2, 0, 0, "");
		graph.addDNode(insertion2);
		DEdge sy = new DEdge(start, insertion2);
		DEdge ye = new DEdge(insertion2, end);
		graph.addDEdge(sy);
		graph.addDEdge(ye);
		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph, null);
		Collection<InsertionMutation> expected = Arrays.asList(
				new InsertionMutation(start.getId(), end.getId(), 0, 0, rgs,
						insertion.getId()),
				new InsertionMutation(start.getId(), end.getId(), 0, 0, rgs,
						insertion2.getId()));
		assertEquals(expected, muts);
	}

	@Test
	public void multipleInsertionsTest() {
		HashSet<String> insertSrc2 = new HashSet<String>(Arrays.asList("REF1"));
		DNode insertion2 = new DNode(3, insertSrc2, 0, 0, "");
		DNode end2 = new DNode(5, sources, 0, 0, "");
		graph.addDNode(insertion2);
		graph.addDNode(end2);
		DEdge sy = new DEdge(end, insertion2);
		DEdge ye = new DEdge(insertion2, end2);
		DEdge ee = new DEdge(end, end2);
		graph.addDEdge(sy);
		graph.addDEdge(ye);
		graph.addDEdge(ee);
		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph, null);
		Collection<InsertionMutation> expected = Arrays.asList(
				new InsertionMutation(start.getId(), end.getId(), 0, 0, rgs,
						insertion.getId()), new InsertionMutation(end.getId(),
						end2.getId(), 0, 0, rgs, insertion2.getId()));
		assertEquals(expected, muts);
	}

	@Test
	public void InsertionWithSNPTest() {
		HashSet<String> insertSrc2 = new HashSet<String>(Arrays.asList("REF1"));
		DNode SNP = new DNode(3, insertSrc2, 0, 0, "");
		DNode SNPREF = new DNode(5, sources, 0, 0, "");
		DNode end2 = new DNode(6, sources, 0, 0, "");
		DNode insertion2 = new DNode(7, insertSrc2, 0, 0, "");
		DNode end3 = new DNode(8, sources, 0, 0, "");

		graph.addDNode(SNPREF);
		graph.addDNode(SNP);
		graph.addDNode(end2);
		graph.addDNode(insertion2);
		graph.addDNode(end3);

		graph.addDEdge(new DEdge(end, SNP));
		graph.addDEdge(new DEdge(end, SNPREF));
		graph.addDEdge(new DEdge(SNP, end2));
		graph.addDEdge(new DEdge(SNPREF, end2));
		graph.addDEdge(new DEdge(end2, insertion2));
		graph.addDEdge(new DEdge(end2, end3));
		graph.addDEdge(new DEdge(insertion2, end3));

		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph, null);
		Collection<InsertionMutation> expected = Arrays.asList(
				new InsertionMutation(start.getId(), end.getId(), 0, 0, rgs,
						insertion.getId()), new InsertionMutation(end2.getId(),
						end3.getId(), 0, 0, rgs, insertion2.getId()));
		assertEquals(expected, muts);
	}

	@Test
	public void InsertionWithDeletionTest() {
		HashSet<String> insertSrc2 = new HashSet<String>(Arrays.asList("REF1"));
		DNode deletion = new DNode(5, sources, 0, 0, "");
		DNode end2 = new DNode(6, sources, 0, 0, "");
		DNode insertion2 = new DNode(7, insertSrc2, 0, 0, "");
		DNode end3 = new DNode(8, sources, 0, 0, "");

		graph.addDNode(deletion);
		graph.addDNode(end2);
		graph.addDNode(insertion2);
		graph.addDNode(end3);

		graph.addDEdge(new DEdge(end, end2));
		graph.addDEdge(new DEdge(end, deletion));
		graph.addDEdge(new DEdge(deletion, end2));
		graph.addDEdge(new DEdge(end2, insertion2));
		graph.addDEdge(new DEdge(end2, end3));
		graph.addDEdge(new DEdge(insertion2, end3));

		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph, null);
		Collection<InsertionMutation> expected = Arrays.asList(
				new InsertionMutation(start.getId(), end.getId(), 0, 0, rgs,
						insertion.getId()), new InsertionMutation(end2.getId(),
						end3.getId(), 0, 0, rgs, insertion2.getId()));
		assertEquals(expected, muts);
	}
}
