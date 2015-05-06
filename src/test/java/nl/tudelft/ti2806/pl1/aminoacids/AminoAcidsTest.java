package nl.tudelft.ti2806.pl1.aminoacids;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class AminoAcidsTest {

	@Test
	public final void getAminoStringTest() throws IOException {
		ArrayList<String> acids = AminoAcids
				.getAminoString("ATCTATACGCCGTGTAATGAGAACAACCACACCTTAGCGAATTGATCCGCCG");
		assertEquals("Met", acids.get(0));
		assertEquals("Leu", acids.get(5));
		assertEquals("Asn", acids.get(12));
		assertEquals(13, acids.size());
	}

	@Test
	public final void noStartAminoTest() throws IOException {
		ArrayList<String> acids = AminoAcids
				.getAminoString("ATCTATAGCCGTGTAATGAGAACAACCACACCTTAGCGAATTGATCCGCCG");
		assertTrue(acids.isEmpty());
	}

	@Test
	public final void noEndAminoTest() throws IOException {
		ArrayList<String> acids = AminoAcids
				.getAminoString("ATCTATACCCGTGTAATGAGAACAACCACACCTTAGCGAATTGATCCGCCG");
		assertEquals("Met", acids.get(0));
		assertEquals("Leu", acids.get(5));
		assertEquals("Arg", acids.get(14));
		assertEquals(15, acids.size());
	}
}
