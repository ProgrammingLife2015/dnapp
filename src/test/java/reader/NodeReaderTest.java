package reader;

import static org.junit.Assert.assertEquals;
import exceptions.InvalidFileFormatException;
import graph.Node;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

public class NodeReaderTest {

	String node = ">0 | TKK-01-0029 | 27 | 28" + "\n" + "A";

	@Test
	public void readOneNode() {
		Scanner sc = new Scanner(node);
		ArrayList<Node> graph = NodeReader.ReadNodes(sc);
		assertEquals(graph.get(0).getId(), 0);
		sc.close();
	}

	@Test(expected = InvalidFileFormatException.class)
	public void wrongFirstSymbol() {
		Scanner sc = new Scanner("a b c");
		NodeReader.ReadNodes(sc);
	}

	@Test(expected = InvalidFileFormatException.class)
	public void wrongNumberOfInput() {
		Scanner sc = new Scanner("> | b | c");
		NodeReader.ReadNodes(sc);
	}

	@Test(expected = InvalidFileFormatException.class)
	public void wrongID() {
		Scanner sc = new Scanner(">a | b | c | d" + "\n" + "A");
		NodeReader.ReadNodes(sc);
	}

	@Test(expected = InvalidFileFormatException.class)
	public void nonMatchingRef() {
		Scanner sc = new Scanner(">0 | b | 1 | 2" + "\n" + "AA");
		NodeReader.ReadNodes(sc);
	}

}
