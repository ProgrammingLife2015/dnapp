package graph;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Marissa, Mark
 * @version 25-04-2015 Lets you create a Graph with Nodes and Edges.
 */
public class Graph {

	/**
	 * 
	 */
	private ArrayList<Node> nodes;

	/**
	 * 
	 */
	public Graph() {
		this.nodes = new ArrayList<Node>();
	}

	/**
	 * 
	 * @param nodesIn
	 *            the collection of nodes for the graph.
	 */
	public Graph(final Collection<Node> nodesIn) {
		this.nodes = new ArrayList<Node>(nodesIn);
	}

	/**
	 * 
	 * @return the list of nodes.
	 */
	public final ArrayList<Node> getNodes() {
		return nodes;
	}

	/**
	 * 
	 * @param id
	 *            The id of the node to get.
	 * @return the node with the given id.
	 */
	public final Node getNode(final int id) {
		return nodes.get(id);
	}

	/**
	 * 
	 * @return the number of nodes.
	 */
	public final int getSize() {
		return nodes.size();
	}
}
