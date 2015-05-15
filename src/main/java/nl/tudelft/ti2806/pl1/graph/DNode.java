package nl.tudelft.ti2806.pl1.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * The node class we use for reader to read the nodes.
 * 
 * @author Marissa, Mark
 * @since 25-04-2015 Lets you create Nodes for the Graph.
 */
public class DNode {

	/**
	 * 
	 */
	private int id;

	/**
	 * 
	 */
	private Collection<Integer> outNodes, inNodes;

	/**
	 * 
	 */
	private HashSet<String> sources;

	/**
	 * 
	 */
	private int start;

	/**
	 * 
	 */
	private int end;

	/**
	 * 
	 */
	private String content;

	/**
	 * The constructor of the node.
	 * 
	 * @param idIn
	 *            ID of the Node.
	 * @param sourcesIn
	 *            Genomes which contain this Node.
	 * @param startIn
	 *            Starting point in reference genome.
	 * @param endIn
	 *            End point in reference genome.
	 * @param contentIn
	 *            DNA bases inside the Node.
	 */
	public DNode(final int idIn, final HashSet<String> sourcesIn,
			final int startIn, final int endIn, final String contentIn) {
		this.id = idIn;
		this.outNodes = new ArrayList<Integer>();
		this.inNodes = new ArrayList<Integer>();
		this.start = startIn;
		this.end = endIn;
		this.sources = sourcesIn;
		this.content = contentIn;
	}

	/**
	 * Add an edge to a node.
	 * 
	 * @param edge
	 *            The edge added to the node
	 */
	public final void addEdge(final DEdge edge) {
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
	public final int getId() {
		return id;
	}

	/**
	 * Set the ID of the node.
	 * 
	 * @param newId
	 *            The new ID of the node
	 */
	public final void setId(final int newId) {
		this.id = newId;
	}

	/**
	 * Get all the outgoing edges.
	 * 
	 * @return Returns all the outgoing edges
	 */
	public final Collection<Integer> getOutNodes() {
		return outNodes;
	}

	/**
	 * Set all the outgoing edges.
	 * 
	 * @param newOutNodes
	 *            Sets all the outgoing edges
	 */
	public final void setOutNodes(final Collection<Integer> newOutNodes) {
		this.outNodes = newOutNodes;
	}

	/**
	 * Get all the incoming edges.
	 * 
	 * @return the incoming edges
	 */
	public final Collection<Integer> getInNodes() {
		return inNodes;
	}

	/**
	 * Sets all the incoming edges.
	 * 
	 * @param newInNodes
	 *            Incoming edges.
	 */
	public final void setInEdges(final Collection<Integer> newInNodes) {
		this.inNodes = newInNodes;
	}

	/**
	 * Get the sources which contains this node.
	 * 
	 * @return Returns the different sources which contains this node
	 */
	public final Collection<String> getSources() {
		return sources;
	}

	/**
	 * Set the sources which contain this node.
	 * 
	 * @param newSources
	 *            The new sources which contains this node
	 */
	public final void setSources(final HashSet<String> newSources) {
		this.sources = newSources;
	}

	/**
	 * Get the starting point of the reference genome.
	 * 
	 * @return The starting point on the reference genome
	 */
	public final int getStart() {
		return start;
	}

	/**
	 * Set the starting point of the reference genome.
	 * 
	 * @param newStart
	 *            The starting point on the reference genome.
	 */
	public final void setStart(final int newStart) {
		this.start = newStart;
	}

	/**
	 * @return The ending point on the reference genome.
	 */
	public final int getEnd() {
		return end;
	}

	/**
	 * Set the ending point of the genome.
	 * 
	 * @param newEnd
	 *            The ending point on the reference genome.
	 */
	public final void setEnd(final int newEnd) {
		this.end = newEnd;
	}

	/**
	 * Get the content of the node.
	 * 
	 * @return The content of the node
	 */
	public final String getContent() {
		return content;
	}

	/**
	 * Set the content of the node.
	 * 
	 * @param newContent
	 *            Sets the content on the reference genome
	 */
	public final void setContent(final String newContent) {
		this.content = newContent;
	}
}
