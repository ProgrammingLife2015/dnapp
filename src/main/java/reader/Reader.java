package reader;

import graph.Edge;
import graph.Node;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * 
 * @author Marissa, Mark
 * @date 25-04-2015 Lets you read in all the data and returns a Graph.
 */
public class Reader {

  /**
   * 
   * @param nodes
   *          File location of the Nodes data.
   * @param edges
   *          File location of the Edges data.
   * @return Graph with this information processed.
   * @throws FileNotFoundException
   *           If file is not found.
   */
  public static Graph read(String nodes, String edges) throws FileNotFoundException {
    ArrayList<Node> node = readNodes(nodes);
    ArrayList<Edge> edge = readEdges(edges);
    Graph g = new SingleGraph("DNA Sequencing Graph");
    for (Node n : node) {
      g.addNode(String.valueOf(n.getId()));
      g.getNode(n.getId()).addAttribute("start", n.getStart());
      g.getNode(n.getId()).addAttribute("ui.label", n.getId());//TODO not all properties are implemented yet
    }
    for (Edge e : edge) {
      g.addEdge(String.valueOf(e.getStartNode()) + String.valueOf(e.getEndNode()),
          String.valueOf(e.getStartNode()), String.valueOf(e.getEndNode()));
    }
    return g;
  }

  private static ArrayList<Node> readNodes(String nodes) throws FileNotFoundException {
    BufferedReader reader = new BufferedReader(new FileReader(nodes));
    Scanner sc = new Scanner(reader);
    return NodeReader.ReadNodes(sc);
  }

  private static ArrayList<Edge> readEdges(String edges) throws FileNotFoundException {
    BufferedReader reader = new BufferedReader(new FileReader(edges));
    Scanner sc = new Scanner(reader);
    return EdgeReader.readEdges(sc);
  }
}
