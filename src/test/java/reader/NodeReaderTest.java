package reader;

import static org.junit.Assert.*;
import graph.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

public class NodeReaderTest {

  @Test
  public void test() throws FileNotFoundException {
    File file = new File("src\\main\\resources\\test.txt");
    Scanner sc = new Scanner(file);
    Graph graph = NodeReader.ReadNodes(sc);
    System.out.println(graph.getNodes().size());
  }

}
