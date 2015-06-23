package nl.tudelft.ti2806.pl1.mutation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ScoreMultiplierTest {

	PointMutation mutingen;
	PointMutation mutnotgen;
	PointMutation mutresist;
	DeletionMutation del;
	ReferenceGeneStorage rgs;

	@Before
	public void setup() {
		rgs = new ReferenceGeneStorage(null);
		rgs.setGeneAnnotation(new File(
				"src/test/resources/mutationTestGene.gff"));
		rgs.setDrugRestistantMutations(new File(
				"src/test/resources/mutationTestResistant.txt"));
		mutingen = new PointMutation(1, 4, 6, 10, rgs, new HashSet<Integer>(
				Arrays.asList(2, 3)));
		mutnotgen = new PointMutation(1, 4, 1, 5, rgs, new HashSet<Integer>(
				Arrays.asList(2, 3)));
		mutresist = new PointMutation(1, 4, 12, 15, rgs, new HashSet<Integer>(
				Arrays.asList(2, 3)));
		del = new DeletionMutation(1, 3, 1, 10, rgs);
	}

	@After
	public void teardown() {
		ScoreMultiplier.resetMults();
	}

	/**
	 * Tests if score of only pointmutation has changed after changing the
	 * multiplier.
	 */
	@Test
	public void pointMutationMultiplierTest() {
		assertEquals(mutingen.getScore(), 10, 0.1);
		assertEquals(del.getScore(), 10, 0.1);
		ScoreMultiplier.setMult(MutationMultipliers.POINTMUTATION.name(), 2.0);
		assertEquals(mutingen.getScore(), 20, 0.1);
		assertEquals(del.getScore(), 10, 0.1);
	}

	/**
	 * Tests if score changes of mutation in gene.
	 */
	@Test
	public void inGeneMultiplierTest() {
		assertEquals(mutingen.getScore(), 10, 0.1);
		ScoreMultiplier.setMult(MutationMultipliers.IN_GENE.name(), 2.0);
		assertEquals(mutingen.getScore(), 20, 0.1);
	}

	/**
	 * Tests if score changes of known mutation when using multiplier.
	 */
	@Test
	public void knownMutationMultiplierTest() {
		assertEquals(mutresist.getScore(), 80, 0.1);
		ScoreMultiplier.setMult(MutationMultipliers.KNOWN_MUTATION.name(), 0.5);
		assertEquals(mutresist.getScore(), 45, 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void unknownMultiplierTest() {
		ScoreMultiplier.setMult("faulty", 2.0);
	}
}
