package nl.tudelft.ti2806.pl1.geneAnnotation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

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

	// @Test
	// public void testToString() {
	// assertEquals(
	// "Gene[958526, 960154, 0.0, -, DNA helicase Ercc3,{960154=nsy, 959526=nsy}]\n"
	// + "Gene[974294, 975940, 0.0, -, transmembrane protein,{}]\n"
	// +
	// "Gene[1122226, 1123602, 0.0, -, para-aminobenzoate synthase component I PABD,{1122227=del3bp}]\n"
	// +
	// "Gene[1824437, 1825894, 0.0, -, integral membrane cytochrome D ubiquinol oxidase CydA,{1825894=snp}]\n"
	// +
	// "Gene[3255695, 3262261, 0.0, +, phenolpthiocerol synthesis type-I polyketide synthase PpsC,{3255895=snp}]\n",
	// RGS.toString());
	// }

	// @Test
	// public void testNoGeneFile() {
	// new ReferenceGeneStorage("src/test/resources/NonExistingFile.gff",
	// TSV_TEST_FILE);
	// }
	//
	// @Test
	// public void testNoMutationFile() {
	// new ReferenceGeneStorage(GFF_TEST_FILE,
	// "src/test/resources/NoExistingMutationsFile.txt");
	// }
}
