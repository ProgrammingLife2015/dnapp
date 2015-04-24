package graph;

import java.util.Collection;

public class Graph {
	
	private Collection<Node> nodes;
	public Graph(Collection<Node> nodes) {
		this.nodes = nodes;
	}
	
	public Collection<Node> getNodes() {
		return nodes;
	}
}
