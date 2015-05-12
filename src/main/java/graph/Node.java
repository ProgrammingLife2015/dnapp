package graph;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The node class we use for reader to read the nodes.
 * 
 * @author Marissa, Mark
 * @date 25-04-2015 Lets you create Nodes for the Graph.
 */
public class Node {

	private int id;
	private Collection<Integer> outNodes;
	private Collection<Integer> inNodes;
	private Collection<String> sources;
	private int start;
	private int end;
	private String content;

	/**
	 * The constructor of the node.
	 * 
	 * @param id
	 *            ID of the Node.
	 * @param sources
	 *            Genomes which contain this Node.
	 * @param start
	 *            Starting point in reference genome.
	 * @param end
	 *            End point in reference genome.
	 * @param content
	 *            DNA bases inside the Node.
	 */
	public Node(final int id, final Collection<String> sources,
			final int start, final int end, final String content) {
		this.id = id;
		this.outNodes = new ArrayList<Integer>();
		this.inNodes = new ArrayList<Integer>();
		this.start = start;
		this.end = end;
		this.sources = sources;
		this.content = content;
	}

	/**
	 * Add an edge to a node.
	 * 
	 * @param edge
	 *            The edge added to the node
	 */
	public void addEdge(final Edge edge) {
		if (edge.getStartNode() == id) {
			outNodes.add(edge.getEndNode());
		} else {
			inNodes.add(edge.getStartNode());
		}
	}

	/**
	 * Get the ID of the node.
	 * 
	 * @return The ID of the node
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the ID of the node.
	 * 
	 * @param id
	 *            The new ID of the node
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Get all the outgoing edges.
	 * 
	 * @return Returns all the outgoing edges
	 */
	public Collection<Integer> getOutNodes() {
		return outNodes;
	}

	/**
	 * Set all the outgoing edges.
	 * 
	 * @param outEdges
	 *            Sets all the outgoing edges
	 */
	public void setOutNodes(final Collection<Integer> outNodes) {
		this.outNodes = outNodes;
	}

	/**
	 * Get all the incoming edges.
	 * 
	 * @return Returns all the incoming edges
	 */
	public Collection<Integer> getInNodes() {
		return inNodes;
	}

	/**
	 * Set all the incoming edges.
	 * 
	 * @param inEdges
	 *            Sets all the incoming edges
	 */
	public void setInEdges(final Collection<Integer> inNodes) {
		this.inNodes = inNodes;
	}

	/**
	 * Get the sources which contains this node.
	 * 
	 * @return Returns the different sources which contains this node
	 */
	public Collection<String> getSources() {
		return sources;
	}

	/**
	 * Set the sources which contain this node.
	 * 
	 * @param sources
	 *            Sets the new sources which contains this node
	 */
	public void setSources(final Collection<String> sources) {
		this.sources = sources;
	}

	/**
	 * Get the starting point of the reference genome.
	 * 
	 * @return Returns the starting point on the reference genome
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Set the starting point of the reference genome.
	 * 
	 * @param start
	 *            Sets the starting point on the reference genome
	 */
	public void setStart(final int start) {
		this.start = start;
	}

	/**
	 * Get the ending point of the genome.
	 * 
	 * @return Returns the ending point on the reference genome
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * Set the ening point of the genome.
	 * 
	 * @param end
	 *            Sets the ending point on the reference genome
	 */
	public void setEnd(final int end) {
		this.end = end;
	}

	/**
	 * Get the content of the node.
	 * 
	 * @return Returns the content of the node
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Set the content of the node.
	 * 
	 * @param content
	 *            Sets the content on the reference genome
	 */
	public void setContent(final String content) {
		this.content = content;
	}
}
