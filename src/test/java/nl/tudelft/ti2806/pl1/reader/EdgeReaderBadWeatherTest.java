package nl.tudelft.ti2806.pl1.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.exceptions.InvalidFileFormatException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class EdgeReaderBadWeatherTest {

	private static String edge1 = "a 5";
	private static String edge2 = "5 a";
	private static String edge3 = "1";
	private static String edge4 = "1 4 5";
	private static String edge5 = "3 4";

	/**
	 * Set the parameters for the test.
	 * 
	 * @return The parameters for the parameterized test
	 */
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { edge1 }, { edge2 }, { edge3 }, { edge4 }, { edge5 } });
	}

	private String edge;

	public EdgeReaderBadWeatherTest(final String edge) {
		this.edge = edge;
	}

	@Test(expected = InvalidFileFormatException.class)
	public void invalidStringsTest() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(edge));
		DGraph graph = new DGraph();
		EdgeReader.readEdges(reader, graph);
	}
}
