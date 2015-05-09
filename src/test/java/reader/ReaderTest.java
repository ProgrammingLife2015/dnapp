package reader;

import static org.junit.Assert.assertEquals;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

public class ReaderTest {

  String nodes = "src/test/resources/nodes.txt";
  String edges = "src/test/resources/edges.txt";
  Graph graph = new SingleGraph("test graph");

  @Before
  public void setUp() throws FileNotFoundException {
    graph = Reader.read(nodes, edges);
  }

  @After
  public void tearDown() {
    graph = null;
    nodes = null;
    edges = null;
  }

  @Test
  public void amountOfNodesTest() {
    assertEquals(graph.getNodeCount(), 2);
  }

  @Test
  public void amountOfEdgesTest() {
    assertEquals(graph.getEdgeCount(), 1);
  }

  @Test
  public void sourceNodeEdgeTest() {
    assertEquals(graph.getEdge(0).getSourceNode(), graph.getNode(0));
  }

  @Test
  public void targetNodeEdgeTest() {
    assertEquals(graph.getEdge(0).getTargetNode(), graph.getNode(1));
  }
}
