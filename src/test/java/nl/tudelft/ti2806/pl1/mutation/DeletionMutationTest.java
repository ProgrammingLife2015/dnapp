package nl.tudelft.ti2806.pl1.mutation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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
public class DeletionMutationTest {

	DGraph graph;
	DNode start, end, insertion;
	DEdge si, ie, se;

	@Before
	public void setup() {
		HashSet<String> sources = new HashSet<String>(Arrays.asList("TKK_REF",
				"REF1", "REF2"));
		// HashSet<String> insertSrc = new
		// HashSet<String>(Arrays.asList("TKK_2"));

		start = new DNode(1, sources, 0, 0, "");
		start.setDepth(1);
		end = new DNode(2, sources, 0, 0, "");
		end.setDepth(3);
		insertion = new DNode(3, sources, 0, 0, "");
		insertion.setDepth(2);
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
	public void findDeletionTest() {
		Collection<DeletionMutation> muts = MutationFinder
				.findDeletionMutations(graph);
		Collection<DeletionMutation> expected = new ArrayList<DeletionMutation>();
		expected.add(new DeletionMutation(1, 2));
		assertEquals(expected, muts);
	}

}
