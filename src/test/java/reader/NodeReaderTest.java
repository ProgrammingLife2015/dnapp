package reader;

import static org.junit.Assert.*;
import graph.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

public class NodeReaderTest {

  @Test
  public void readOneNode() throws FileNotFoundException {
    File file = new File("src/test/resources/test.txt");
    Scanner sc = new Scanner(file);
    Graph graph = NodeReader.ReadNodes(sc);
    assertEquals(graph.getNodes().get(0).getID(),0);
  }

}
