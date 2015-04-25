package graph;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Marissa, Mark
 * @date 25-04-2015
 *  Lets you create Nodes for the Graph.
 */
public class Node {

  private int id;
  private Collection<Edge> outEdges;
  private Collection<Edge> inEdges;
  private Collection<String> sources;
  private int start;
  private int end;
  private String content;

  /**
   * @param id
   *          ID of the Node.
   * @param sources
   *          Genomes which contain this Node.
   * @param start
   *          Starting point in reference genome.
   * @param end
   *          End point in reference genome.
   * @param content
   *          DNA bases inside the Node.
   */
  public Node(int id, Collection<String> sources, int start, int end, String content) {
    this.id = id;
    this.outEdges = new ArrayList<Edge>();
    this.inEdges = new ArrayList<Edge>();
    this.start = start;
    this.end = end;
    this.sources = sources;
    this.content = content;
  }

  /**
   * 
   * @param e
   *          The edge added to the node
   */
  public void addEdge(Edge e) {
    if (e.getStartNode() == id) {
      outEdges.add(e);
    } else {
      inEdges.add(e);
    }
  }

  /**
   * 
   * @return The ID of the node
   */
  public int getId() {
    return id;
  }

  /**
   * 
   * @param id
   *          The new ID of the node
   */
  public void setId(int id) {
    this.id = id;
  }

  
  /**
   * 
   * @return Returns all the outgoing edges
   */
  public Collection<Edge> getOutEdges() {
    return outEdges;
  }

  /**
   * 
   * @param outEdges Sets all the outgoing edges 
   */
  public void setOutEdges(Collection<Edge> outEdges) {
    this.outEdges = outEdges;
  }

  /**
   * 
   * @return Returns all the incoming edges
   */
  public Collection<Edge> getInEdges() {
    return inEdges;
  }

  /**
   * 
   * @param inEdges Sets all the incoming edges
   */
  public void setInEdges(Collection<Edge> inEdges) {
    this.inEdges = inEdges;
  }

  /**
   * 
   * @return Returns the different sources which contains this node
   */
  public Collection<String> getSources() {
    return sources;
  }

  /**
   * 
   * @param sources Sets the new sources which contains this node
   */
  public void setSources(Collection<String> sources) {
    this.sources = sources;
  }

  /**
   * 
   * @return Returns the starting point on the reference genome
   */
  public int getStart() {
    return start;
  }

  /**
   * 
   * @param start Sets the starting point on the reference genome
   */
  public void setStart(int start) {
    this.start = start;
  }

  /**
   * 
   * @return Returns the ending point on the reference genome
   */
  public int getEnd() {
    return end;
  }

  /**
   * 
   * @param end Sets the ending point on the reference genome
   */
  public void setEnd(int end) {
    this.end = end;
  }

  /**
   * 
   * @return Returns the content of the node
   */
  public String getContent() {
    return content;
  }

  /**
   * 
   * @param content Sets the content on the reference genome
   */
  public void setContent(String content) {
    this.content = content;
  }
}
