package nl.tudelft.ti2806.pl1.mutation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;

import org.junit.Before;
import org.junit.Test;

public class MutationTest {

	ReferenceGeneStorage rgs;
	PointMutation mutingen;
	PointMutation mutnotgen;
	PointMutation mutbegin;
	PointMutation mutend;
	PointMutation mutresist;

	@Before
	public void setup() {
		rgs = new ReferenceGeneStorage(null);
		rgs.setGeneAnnotation(new File(
				"src/test/resources/mutationTestGene.gff"));
		mutingen = new PointMutation(1, 4, 6, 10, rgs, new HashSet<Integer>(
				Arrays.asList(2, 3)));
		mutnotgen = new PointMutation(1, 4, 1, 5, rgs, new HashSet<Integer>(
				Arrays.asList(2, 3)));
		mutbegin = new PointMutation(1, 4, 10, 12, rgs, new HashSet<Integer>(
				Arrays.asList(2, 3)));
		mutend = new PointMutation(1, 4, 1, 6, rgs, new HashSet<Integer>(
				Arrays.asList(2, 3)));
		mutresist = new PointMutation(1, 4, 12, 15, rgs, new HashSet<Integer>(
				Arrays.asList(2, 3)));
	}

	@Test
	public void mutNotInGeneTest() {
		assertEquals(mutnotgen.getScore(), 0, 0);
	}

	@Test
	public void mutInGeneTest() {
		assertEquals(mutingen.getScore(), 10, 0);
	}

	@Test
	public void mutBeginInGeneTest() {
		assertEquals(mutbegin.getScore(), 10, 0);
	}

	@Test
	public void mutEndInGeneTest() {
		assertEquals(mutend.getScore(), 10, 0);
	}

	@Test
	public void mutResist() {
		rgs.setDrugRestistantMutations(new File(
				"src/test/resources/mutationTestResistant.txt"));
		assertEquals(mutresist.getScore(), 80, 0);
	}

	@Test
	public void mutNoResistance() {
		assertEquals(mutresist.getScore(), 10, 0);
	}

	@Test
	public void mutEmptyFile() {
		rgs.setDrugRestistantMutations(new File("test.txt"));
		assertEquals(mutresist.getScore(), 10, 0);
	}

	@Test
	public void mutAffectedGroups() {
		mutingen.setAffectedNodeGroups(10);
		assertEquals(mutingen.getScore(), 34, 0);
	}
}
