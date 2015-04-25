package graph;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Marissa, Mark
 * @date 25-04-2015
 * Lets you create a Graph with Nodes and Edges
 */
public class Graph {
	
  private ArrayList<Node> nodes;
  
  public Graph() {
    this.nodes = new ArrayList<Node>();
  }
  
	public Graph(Collection<Node> nodes) {
		this.nodes = new ArrayList<Node>(nodes);
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	public Node getNode(int id) {
	  return nodes.get(id);
	}
	
	public int getSize() {
	  return nodes.size();
	}
}
