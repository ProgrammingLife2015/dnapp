package reader;

import graph.Edge;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Marissa, Mark
 * @date 24-04-2015
 * Lets you read the Edges from the provided data.
 */
public class EdgeReader {

  /**
   * 
   * @param sc Scanner from which the edge information will be read.
   * @return ArrayList of all Edges.
   */
  public static ArrayList<Edge> readEdges(Scanner sc) {
    ArrayList<Edge> edges = new ArrayList<Edge>();
    
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      String[] nodes = line.split("\\s");
      int start = Integer.parseInt(nodes[0]);
      int end = Integer.parseInt(nodes[1]);
      Edge e = new Edge(start, end);
      edges.add(e);
    }
    
    return edges;
  }
}
