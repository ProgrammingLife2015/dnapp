package graph;

/**
 * 
 * @author Mark, Marissa
 * @date 25-04-2014
 */
public class Edge {

  /**
   * The starting node of the edge
   */
  private int startNode;
  
  /**
   * The ending node of the edge
   */
  private int endNode;

  /**
   * The empty constructor of the edge, it creates a new edge starting at 0 and ending at 0
   */
  public Edge() {
    new Edge(0, 0);
  }

  /**
   * The constructor for the edge, which creates a new edge starting at start and ending at end
   * @param start: the starting node of the edge
   * @param end: the ending node of the edge
   */
  public Edge(int start, int end) {
    startNode = start;
    endNode = end;
  }

  /**
   * The getter for the starting node
   * @return: the starting node of the edge
   */
  public int getStartNode() {
    return startNode;
  }

  /**
   * The setter of the starting node
   * @param startNode: the new starting node of the edge
   */
  public void setStartNode(int startNode) {
    this.startNode = startNode;
  }

  /**
   * The getter of the ending node
   * @return: the ending node of the edge
   */
  public int getEndNode() {
    return endNode;
  }

  /**
   * The setter of the ending node
   * @param endNode: the new ending node of the edge
   */
  public void setEndNode(int endNode) {
    this.endNode = endNode;
  }
}
