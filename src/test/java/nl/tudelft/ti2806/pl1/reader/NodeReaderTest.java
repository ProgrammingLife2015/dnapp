package nl.tudelft.ti2806.pl1.reader;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Scanner;

import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.exceptions.InvalidFileFormatException;

import org.junit.Test;

public class NodeReaderTest {

	String node = ">0 | TKK-01-0029 | 27 | 28" + "\n" + "A";

	@Test
	public void readOneNodeTest() {
		Scanner sc = new Scanner(node);
		ArrayList<DNode> graph = NodeReader.readNodes(sc);
		assertEquals(graph.get(0).getId(), 0);
		sc.close();
	}

	@Test(expected = InvalidFileFormatException.class)
	public void wrongFirstSymbolTest() {
		Scanner sc = new Scanner("a b c");
		NodeReader.readNodes(sc);
	}

	@Test(expected = InvalidFileFormatException.class)
	public void wrongNumberOfInputTest() {
		Scanner sc = new Scanner("> | b | c");
		NodeReader.readNodes(sc);
	}

	@Test(expected = InvalidFileFormatException.class)
	public void wrongIdTest() {
		Scanner sc = new Scanner(">a | b | c | d" + "\n" + "A");
		NodeReader.readNodes(sc);
	}

	@Test(expected = InvalidFileFormatException.class)
	public void nonMatchingRefTest() {
		Scanner sc = new Scanner(">0 | b | 1 | 2" + "\n" + "AA");
		NodeReader.readNodes(sc);
	}

	@Test(expected = InvalidFileFormatException.class)
	public void startAndEndNotIntegerTest() {
		Scanner sc = new Scanner(">0 | b | a | 2" + "\n" + "AA");
		NodeReader.readNodes(sc);
	}

	@Test
	public void returnsOneNodeTest() {
		Scanner sc = new Scanner(node);
		ArrayList<DNode> graph = NodeReader.readNodes(sc);
		assertEquals(graph.size(), 1);
	}
}
