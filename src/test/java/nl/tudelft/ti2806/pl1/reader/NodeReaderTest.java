package nl.tudelft.ti2806.pl1.reader;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.exceptions.InvalidFileFormatException;

import org.junit.Test;

public class NodeReaderTest {

	String node = ">0 | TKK-01-0029 | 27 | 28" + "\n" + "A";

	@Test
	public void readOneNodeTest() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(node));
		ArrayList<DNode> graph = NodeReader.readNodes(reader);
		assertEquals(graph.get(0).getId(), 0);
	}

	@Test(expected = InvalidFileFormatException.class)
	public void wrongFirstSymbolTest() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader("a b c"));
		NodeReader.readNodes(reader);
	}

	@Test(expected = InvalidFileFormatException.class)
	public void wrongNumberOfInputTest() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader("> | b | c"));
		NodeReader.readNodes(reader);
	}

	@Test(expected = InvalidFileFormatException.class)
	public void wrongIdTest() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(">a | b | c | d" + "\n" + "A"));
		NodeReader.readNodes(reader);
	}

	@Test(expected = InvalidFileFormatException.class)
	public void nonMatchingRefTest() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(">0 | b | 1 | 2" + "\n" + "AA"));
		NodeReader.readNodes(reader);
	}

	@Test(expected = InvalidFileFormatException.class)
	public void startAndEndNotIntegerTest() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(">0 | b | a | 2" + "\n" + "AA"));
		NodeReader.readNodes(reader);
	}
}
