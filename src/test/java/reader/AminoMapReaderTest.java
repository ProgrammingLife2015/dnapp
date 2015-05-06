package reader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

public class AminoMapReaderTest {

	@Test
	public final void readIntoMaptest() throws IOException {
		HashMap<String, String> map = AminoMapReader
				.readIntoMap("src/main/resources/amino_DNA_encoding.txt");
		assertEquals("Met", map.get("TAC"));
		assertEquals("Gly", map.get("CCC"));
		assertEquals("Leu", map.get("GAC"));
	}

}
