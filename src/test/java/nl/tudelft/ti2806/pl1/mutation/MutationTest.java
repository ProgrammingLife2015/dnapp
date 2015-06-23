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
		assertEquals(0, mutnotgen.getScore(), 0);
	}

	@Test
	public void mutInGeneTest() {
		assertEquals(10, mutingen.getScore(), 0);
	}

	@Test
	public void mutBeginInGeneTest() {
		assertEquals(10, mutbegin.getScore(), 0);
	}

	@Test
	public void mutEndInGeneTest() {
		assertEquals(10, mutend.getScore(), 0);
	}

	@Test
	public void mutResist() {
		rgs.setDrugRestistantMutations(new File(
				"src/test/resources/mutationTestResistant.txt"));
		assertEquals(80, mutresist.getScore(), 0);
	}

	@Test
	public void mutNoResistance() {
		assertEquals(10, mutresist.getScore(), 0);
	}

	@Test
	public void mutEmptyFile() {
		rgs.setDrugRestistantMutations(new File("test.txt"));
		assertEquals(10, mutresist.getScore(), 0);
	}

	@Test
	public void mutAffectedGroups() {
		mutingen.setAffectedNodeGroups(10);
		assertEquals(34, mutingen.getScore(), 0);
	}
}
