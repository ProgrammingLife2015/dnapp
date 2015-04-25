package graph;

import java.util.ArrayList;
import java.util.Collection;

public class Graph {
	
  private ArrayList<Node> nodes;
	public Graph(Collection<Node> nodes) {
		this.nodes = new ArrayList<Node>(nodes);
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	public Node getNode(int id) {
	  return nodes.get(id);
	}
}
