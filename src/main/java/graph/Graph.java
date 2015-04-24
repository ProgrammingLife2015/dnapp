package graph;

import java.util.ArrayList;
import java.util.Collection;

public class Graph {
	
	private Collection<Node> nodes;
	public Graph(Collection<Node> nodes) {
		this.nodes = nodes;
	}
	
	public ArrayList<Node> getNodes() {
		return new ArrayList<Node>(nodes);
	}
}
