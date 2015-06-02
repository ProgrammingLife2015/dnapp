package nl.tudelft.ti2806.pl1.geneAnnotation;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReferenceGeneStorageTest {

	@Test
	public void testInitialisation() {
		assertTrue(new ReferenceGeneStorage() instanceof ReferenceGeneStorage);
	}

}
