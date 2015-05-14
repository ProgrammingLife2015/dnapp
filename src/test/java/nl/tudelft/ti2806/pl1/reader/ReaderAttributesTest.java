package nl.tudelft.ti2806.pl1.reader;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ReaderAttributesTest {

	String nodes = "src/test/resources/nodes.txt";
	String edges = "src/test/resources/edges.txt";
	Graph graph = new SingleGraph("test graph");

	/**
	 * Set the parameters for the test.
	 * 
	 * @return Returns the parameters for the test
	 */
	@Parameters
	public static Collection<Object[]> data() {
		Collection<String> sources = new HashSet<String>(Arrays.asList("TKK-1",
				"TKK-2"));
		return Arrays.asList(new Object[][] { { "start", 1 }, { "depth", 0 },
				{ "end", 6 }, { "content", "CCGCG" }, { "sources", sources },
				{ "ui.label", "CCGCG" } });
	}

	@Before
	public void setUp() throws FileNotFoundException {
		graph = Reader.read(nodes, edges);
	}

	/**
	 * Sets everything back to null after the test is done.
	 */
	@After
	public void tearDown() {
		graph = null;
		nodes = null;
		edges = null;
	}

	private String attr;
	private Object res;

	public ReaderAttributesTest(final String attr, final Object res) {
		this.attr = attr;
		this.res = res;
	}

	@Test
	public void attributeTest() {
		assertEquals(res, graph.getNode(0).getAttribute(attr));
	}
}
