package nl.tudelft.ti2806.pl1.mutation;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Maarten, Justin
 */
public class InsertionMutationTest {

	DGraph graph;
	DNode start, end, insertion, insertion2;
	DEdge si, ie, se, sy, ye;

	// DEdge edge;
	// Graph gsGraph;
	// ViewArea va;

	@Before
	public void setup() {
		HashSet<String> sources = new HashSet<String>(Arrays.asList("TKK_REF",
				"REF1", "REF2"));
		HashSet<String> insertSrc = new HashSet<String>(Arrays.asList("REF2"));

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
		System.out.println(graph.toString());
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
	public void findInsertionTest() {
		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph);
		Collection<InsertionMutation> expected = Arrays
				.asList(new InsertionMutation(start.getId(), end.getId(),
						insertion.getId()));
		assertEquals(expected, muts);
	}

	@Test
	public void findInsertionTest2() {
		HashSet<String> insertSrc2 = new HashSet<String>(Arrays.asList("REF1"));
		insertion2 = new DNode(3, insertSrc2, 0, 0, "");
		graph.addDNode(insertion2);
		sy = new DEdge(start, insertion2);
		ye = new DEdge(insertion2, end);
		graph.addDEdge(sy);
		graph.addDEdge(ye);
		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph);
		Collection<InsertionMutation> expected = Arrays.asList(
				new InsertionMutation(start.getId(), end.getId(), insertion
						.getId()),
				new InsertionMutation(start.getId(), end.getId(), insertion2
						.getId()));
		assertEquals(expected.toString(), muts.toString());
	}

}
