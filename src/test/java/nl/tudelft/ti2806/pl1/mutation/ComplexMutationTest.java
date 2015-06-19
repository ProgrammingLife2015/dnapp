package nl.tudelft.ti2806.pl1.mutation;

import static org.junit.Assert.assertEquals;

import java.io.File;
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

public class ComplexMutationTest {

	DGraph graph;
	DNode start;
	HashSet<String> insertSrc;
	HashSet<String> insertSrc2;
	HashSet<String> insertSrc3;
	HashSet<String> sources;
	ReferenceGeneStorage rgs;

	@Before
	public void setup() {
		sources = new HashSet<String>(Arrays.asList("TKK_REF", "REF1", "REF2"));
		insertSrc = new HashSet<String>(Arrays.asList("REF1", "REF2"));
		insertSrc2 = new HashSet<String>(Arrays.asList("REF1"));
		insertSrc3 = new HashSet<String>(Arrays.asList("REF2"));
		rgs = new ReferenceGeneStorage(null);
		rgs.setDrugRestistantMutations(new File("src/test/resources/resistanceCausingMutationsTesting.txt"));
		rgs.setGeneAnnotation(new File("src/test/resources/ReferenceGeneStorageTestGenes.gff"));

		start = new DNode(1, sources, 0, 0, "");
		start.setDepth(1);
		graph = new DGraph();
		graph.addDNode(start);
	}

	@After
	public void teardown() {
		graph = null;
		start = null;
	}

	@Test
	public void ComplexMutationTest1() {
		DNode ins1 = new DNode(2, insertSrc, 0, 0, "");
		DNode ins2 = new DNode(3, insertSrc2, 0, 0, "");
		DNode ins3 = new DNode(4, insertSrc3, 0, 0, "");
		DNode ins4 = new DNode(5, insertSrc3, 0, 0, "");
		DNode end = new DNode(6, sources, 0, 0, "");
		ins1.setDepth(2);
		ins2.setDepth(3);
		ins3.setDepth(3);
		ins4.setDepth(4);
		end.setDepth(5);
		DEdge e1 = new DEdge(start, ins1);
		DEdge e2 = new DEdge(start, end);
		DEdge e3 = new DEdge(ins1, ins2);
		DEdge e4 = new DEdge(ins1, ins3);
		DEdge e5 = new DEdge(ins2, end);
		DEdge e6 = new DEdge(ins3, ins4);
		DEdge e7 = new DEdge(ins3, end);
		DEdge e8 = new DEdge(ins4, end);
		graph.addDNode(ins1);
		graph.addDNode(ins2);
		graph.addDNode(ins3);
		graph.addDNode(ins4);
		graph.addDNode(end);
		graph.addDEdge(e1);
		graph.addDEdge(e2);
		graph.addDEdge(e3);
		graph.addDEdge(e4);
		graph.addDEdge(e5);
		graph.addDEdge(e6);
		graph.addDEdge(e7);
		graph.addDEdge(e8);
		Collection<ComplexMutation> muts = MutationFinder.findComplexMutations(graph, null);
		Collection<ComplexMutation> expected = new ArrayList<ComplexMutation>();
		Collection<Integer> inNodes = new ArrayList<Integer>();
		inNodes.add(ins1.getId());
		inNodes.add(ins2.getId());
		inNodes.add(ins3.getId());
		inNodes.add(ins4.getId());
		expected.add(new ComplexMutation(start.getId(), end.getId(), inNodes, rgs, 0, 0));
		assertEquals(expected.toString(), muts.toString());
	}

	@Test
	public void ComplexMutationTest2() {
		DNode ins1 = new DNode(2, insertSrc, 0, 0, "");
		DNode ins2 = new DNode(3, insertSrc2, 0, 0, "");
		DNode ins3 = new DNode(4, insertSrc3, 0, 0, "");
		DNode ins4 = new DNode(5, insertSrc3, 0, 0, "");
		DNode end1 = new DNode(6, sources, 0, 0, "");
		DNode end2 = new DNode(7, sources, 0, 0, "");
		ins1.setDepth(2);
		ins2.setDepth(3);
		ins3.setDepth(3);
		ins4.setDepth(4);
		end1.setDepth(2);
		end2.setDepth(5);
		DEdge e1 = new DEdge(start, ins1);
		DEdge e2 = new DEdge(start, end1);
		DEdge e3 = new DEdge(ins1, ins2);
		DEdge e4 = new DEdge(ins1, ins3);
		DEdge e5 = new DEdge(ins2, end2);
		DEdge e6 = new DEdge(ins3, ins4);
		DEdge e7 = new DEdge(ins3, end1);
		DEdge e8 = new DEdge(ins3, end2);
		DEdge e9 = new DEdge(ins4, end2);
		DEdge e10 = new DEdge(end1, end2);
		graph.addDNode(ins1);
		graph.addDNode(ins2);
		graph.addDNode(ins3);
		graph.addDNode(ins4);
		graph.addDNode(end1);
		graph.addDNode(end2);
		graph.addDEdge(e1);
		graph.addDEdge(e2);
		graph.addDEdge(e3);
		graph.addDEdge(e4);
		graph.addDEdge(e5);
		graph.addDEdge(e6);
		graph.addDEdge(e7);
		graph.addDEdge(e8);
		graph.addDEdge(e9);
		graph.addDEdge(e10);
		Collection<ComplexMutation> muts = MutationFinder.findComplexMutations(graph, null);
		Collection<ComplexMutation> expected = new ArrayList<ComplexMutation>();
		Collection<Integer> inNodes = new ArrayList<Integer>();
		inNodes.add(ins1.getId());
		inNodes.add(ins2.getId());
		inNodes.add(ins3.getId());
		inNodes.add(ins4.getId());
		expected.add(new ComplexMutation(start.getId(), end2.getId(), inNodes, rgs, 0, 0));
		assertEquals(expected.toString(), muts.toString());
	}
}
