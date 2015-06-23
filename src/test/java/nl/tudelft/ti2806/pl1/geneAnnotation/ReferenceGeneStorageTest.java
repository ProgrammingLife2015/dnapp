package nl.tudelft.ti2806.pl1.geneAnnotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import nl.tudelft.ti2806.pl1.graph.DGraph;
import nl.tudelft.ti2806.pl1.graph.DNode;

import org.junit.Before;
import org.junit.Test;

public class ReferenceGeneStorageTest {

	private static final String GFF_TEST_FILE = "src/test/resources/ReferenceGeneStorageTestGenes.gff";

	private static final String TSV_TEST_FILE = "src/test/resources/resistanceCausingMutationsTesting.txt";

	private ReferenceGeneStorage RGS = new ReferenceGeneStorage(null);

	@Before
	public void setup() {
		RGS.setDrugRestistantMutations(new File(TSV_TEST_FILE));
		RGS.setGeneAnnotation(new File(GFF_TEST_FILE));
	}

	@Test
	public void testInitialisation() {
		assertTrue(RGS instanceof ReferenceGeneStorage);
	}

	@Test
	public void testIntragenicPosition() {
		assertTrue(RGS.isIntragenic(958526));
		assertFalse(RGS.containsMutationIndex(958526));
	}

	@Test
	public void testIntragenicPositionMinus() {
		assertFalse(RGS.isIntragenic(-1));
		assertFalse(RGS.containsMutationIndex(-1));
	}

	@Test
	public void testIntergenicPosition() {
		assertFalse(RGS.isIntragenic(958524));
		assertFalse(RGS.containsMutationIndex(958524));
	}

	@Test
	public void testIntergenicPositionMAX() {
		assertFalse(RGS.isIntragenic(Integer.MAX_VALUE));
		assertFalse(RGS.containsMutationIndex(Integer.MAX_VALUE));
	}

	@Test
	public void testIntergenicPositionMIN() {
		assertFalse(RGS.isIntragenic(Integer.MIN_VALUE));
		assertFalse(RGS.containsMutationIndex(Integer.MIN_VALUE));
	}

	@Test
	public void testNSYmutationCorrect() {
		assertTrue(RGS.containsMutationIndex(959526));
		assertTrue(RGS.isIntragenic(959526));
	}

	@Test
	public void testNSYmutationInFileButNotCorrect() {
		assertFalse(RGS.containsMutationIndex(1825895));
		assertFalse(RGS.isIntragenic(1825895));
	}

	@Test
	public void testNSYmutationCorrect2() {
		assertTrue(RGS.containsMutationIndex(3255895));
		assertTrue(RGS.isIntragenic(3255895));
	}

	@Test
	public void testNSYmutationMIN() {
		assertFalse(RGS.containsMutationIndex(Integer.MIN_VALUE));
		assertFalse(RGS.isIntragenic(Integer.MIN_VALUE));
	}

	@Test
	public void testNSYmutationMAX() {
		assertFalse(RGS.containsMutationIndex(Integer.MAX_VALUE));
		assertFalse(RGS.isIntragenic(Integer.MAX_VALUE));
	}

	@Test
	public void testEmptyFiles() {
		ReferenceGeneStorage rgs = new ReferenceGeneStorage(null);
		rgs.setGeneAnnotation(null);
		assertEquals(null, rgs.getReferenceGenes());
		rgs.setDrugRestistantMutations(null);
		assertEquals(null, rgs.getDrugResistanceMutations());
		assertFalse(rgs.isIntragenic(0));

	}

	@Test
	public void testResistanceMutationObservers() {
		ResistanceMutationObserver rmo = mock(ResistanceMutationObserver.class);
		RGS.registerObserver(rmo);
		RGS.setDrugRestistantMutations(new File(TSV_TEST_FILE));
		verify(rmo).update(RGS.getDrugResistanceMutations());
	}

	@Test
	public void testGeneObservers() {
		ReferenceGeneObserver rgo = mock(ReferenceGeneObserver.class);
		RGS.registerObserver(rgo);
		RGS.setGeneAnnotation(new File(GFF_TEST_FILE));
		verify(rgo).update();
	}

	@Test
	public void testNonExistingGeneFile() {
		File file = new File("NON_EXISTING_FILE.gff");
		RGS.setGeneAnnotation(file);
		RGS.setDrugRestistantMutations(file);
	}

	@Test
	public void testDNodes() {
		Map<Integer, DNode> map = new HashMap<Integer, DNode>();
		DNode n1 = mock(DNode.class);
		DNode n2 = mock(DNode.class);
		DNode n3 = mock(DNode.class);
		map.put(1, n1);
		map.put(2, n2);
		map.put(3, n3);
		when(n1.getStart()).thenReturn(960153);
		when(n1.getEnd()).thenReturn(960155);
		when(n2.getStart()).thenReturn(0);
		when(n2.getEnd()).thenReturn(1);
		when(n3.getStart()).thenReturn(Integer.MAX_VALUE);
		when(n3.getEnd()).thenReturn(Integer.MAX_VALUE);
		DGraph dgraph = mock(DGraph.class);
		when(dgraph.getNodes()).thenReturn(map);

		ReferenceGeneStorage rgs = new ReferenceGeneStorage(dgraph);
		rgs.setDrugRestistantMutations(new File(TSV_TEST_FILE));

		verify(n1).addResistantMutationIndex(
				rgs.getDrugResistanceMutations().get((long) 960154));
	}
}
