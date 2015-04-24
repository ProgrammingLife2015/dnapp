package reader;

import graph.Edge;

import java.util.ArrayList;
import java.util.Scanner;

public class EdgeReader {

  public static ArrayList<Edge> readEdges(Scanner sc) {
    ArrayList<Edge> edges = new ArrayList<Edge>();
    
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      String[] nodes = line.split(" ");
      int start = Integer.parseInt(nodes[0]);
      int end = Integer.parseInt(nodes[1]);
      Edge e = new Edge(start, end);
      edges.add(e);
    }
    
    return edges;
  }
}
