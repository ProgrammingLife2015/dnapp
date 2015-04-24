package graph;

import java.util.ArrayList;
import java.util.Collection;

/** Lets you create Nodes for the Graph.
 * 
 * @author Marissa
 * @date 24-04-2015
 */
public class Node {

  private int id;
  private Collection<Edge> edges;
  private Collection<String> sources;
  private int start;
  private int end;
  private String content;

  /**
   * @param id ID of the Node.
   * @param sources Genomes which contain this Node.
   * @param start Starting point in reference genome.
   * @param end End point in reference genome.
   * @param content DNA bases inside the Node.
   */
  public Node(int id,Collection<String> sources,int start, int end, String content) {
    this.id = id;
    this.edges = new ArrayList<Edge>();
    this.start = start;
    this.end = end;
    this.sources = sources;
    this.content = content;
  }

  /** 
   * @return Returns the ID of the Node.
   */
  public int getID() {
    return this.id;
  }

  //give either node or int??
  public void addEdge() {

  }

  /** 
   * 
   * @return Returns starting point in reference genome.
   */
  public int getStart() {
    return this.start;
  }

  /**
   * 
   * @return Returns end point in reference genome.
   */
  public int getEnd() {
    return this.end;
  }
}
