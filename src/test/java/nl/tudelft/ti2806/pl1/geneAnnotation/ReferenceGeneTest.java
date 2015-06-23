/**
 * 
 */
package nl.tudelft.ti2806.pl1.geneAnnotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class for ReferenceGene class.
 * 
 * @author Chak Shun
 * @since 2-6-2015
 *
 */
public class ReferenceGeneTest {

	private int start = 1;
	private int end = 2;
	private double score = 0.0;
	private String strand = "+";
	private String name = "hypothetical protein";
	private ReferenceGene rg;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		rg = new ReferenceGene(start, end, score, strand, name);
	}

	@Test
	public void testInitialisation() {
		assertTrue(rg instanceof ReferenceGene);
	}

	@Test
	public void testObtainInformation() {
		assertEquals(start, rg.getStart());
		assertEquals(end, rg.getEnd());
		assertEquals(score, rg.getScore(), 0.0);
		assertEquals(strand, rg.getStrand());
		assertEquals(name, rg.getName());
		assertEquals(null, rg.getMutations());
	}

	@Test
	public void testToString() {
		assertEquals(
				"<Gene[1-2,score=0.0,strand=+,name=hypothetical protein,mutations=null]>",
				rg.toString());
	}

}
