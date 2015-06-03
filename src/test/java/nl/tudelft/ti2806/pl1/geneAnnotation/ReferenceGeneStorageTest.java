package nl.tudelft.ti2806.pl1.geneAnnotation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReferenceGeneStorageTest {

	private static final String GFF_TEST_FILE = "src/test/resources/ReferenceGeneStorageTestGenes.gff";

	private ReferenceGeneStorage RGS = new ReferenceGeneStorage(GFF_TEST_FILE);

	@Test
	public void testInitialisation() {
		assertTrue(RGS instanceof ReferenceGeneStorage);
	}

	@Test
	public void testIntragenicPosition() {
		assertTrue(RGS.isIntragenic(958526));
	}

	@Test
	public void testIntragenicPositionMinus() {
		assertTrue(RGS.isIntragenic(-1));
	}

	@Test
	public void testIntergenicPosition() {
		assertFalse(RGS.isIntragenic(958524));
	}

	@Test
	public void testIntergenicPositionMAX() {
		assertFalse(RGS.isIntragenic(Integer.MAX_VALUE));
	}

	@Test
	public void testIntergenicPositionMIN() {
		assertFalse(RGS.isIntragenic(Integer.MIN_VALUE));
	}
}
