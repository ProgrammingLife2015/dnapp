package reader;

import graph.Edge;
import graph.Graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Marissa, Mark
 * @date 25-04-2015
 * Lets you read in all the data and returns a Graph.
 */
public class Reader {
  
  /**
   * 
   * @param nodes File location of the Nodes data.
   * @param edges File location of the Edges data.
   * @return Graph with this information processed.
   * @throws FileNotFoundException If file is not found.
   */
  public static Graph read(String nodes, String edges) throws FileNotFoundException {
    Graph graph = readNodes(nodes);
    ArrayList<Edge> edg = readEdges(edges);
    for(int i = 0; i<edg.size();i++) {
      int startid = edg.get(i).getStartNode();
      graph.getNode(startid).addEdge(edg.get(i));
    }
    return graph;
  }
  
  private static Graph readNodes(String nodes) throws FileNotFoundException {
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
