package reader;

import graph.Edge;
import graph.Graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
  
  public static Graph read(String nodes, String edges) throws FileNotFoundException {
    BufferedReader reader = new BufferedReader(new FileReader(nodes));
    Scanner sc = new Scanner(reader);
    return null;
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
