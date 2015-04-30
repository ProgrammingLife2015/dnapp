package graph;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Marissa, Mark
 * @date 25-04-2015 Lets you create Nodes for the Graph.
 */
public class Node {

	/**
	 * 
	 */
	private int id;

	/**
	 * 
	 */
	private Collection<Integer> outNodes;

	/**
	 * 
	 */
	private Collection<Integer> inNodes;

	/**
	 * 
	 */
	private Collection<String> sources;

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
	 * 
	 * @param e
	 *            The edge added to the node
	 */
	public final void addEdge(final Edge e) {
		if (e.getStartNode() == id) {
			outNodes.add(e.getEndNode());
		} else {
			inNodes.add(e.getStartNode());
		}
	}

	/**
	 * 
	 * @return The ID of the node
	 */
	public final int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            The new ID of the node
	 */
	public final void setId(final int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return Returns all the outgoing edges
	 */
	public final Collection<Integer> getOutNodes() {
		return outNodes;
	}

	/**
	 * 
	 * @param outEdges
	 *            Sets all the outgoing edges
	 */
	public final void setOutNodes(final Collection<Integer> outNodes) {
		this.outNodes = outNodes;
	}

	/**
	 * 
	 * @return Returns all the incoming edges
	 */
	public final Collection<Integer> getInNodes() {
		return inNodes;
	}

	/**
	 * 
	 * @param inEdges
	 *            Sets all the incoming edges
	 */
	public final void setInEdges(final Collection<Integer> inNodes) {
		this.inNodes = inNodes;
	}

	/**
	 * 
	 * @return Returns the different sources which contains this node
	 */
	public final Collection<String> getSources() {
		return sources;
	}

	/**
	 * 
	 * @param sources
	 *            Sets the new sources which contains this node
	 */
	public final void setSources(final Collection<String> sources) {
		this.sources = sources;
	}

	/**
	 * 
	 * @return Returns the starting point on the reference genome
	 */
	public final int getStart() {
		return start;
	}

	/**
	 * 
	 * @param start
	 *            Sets the starting point on the reference genome
	 */
	public final void setStart(final int start) {
		this.start = start;
	}

	/**
	 * 
	 * @return Returns the ending point on the reference genome
	 */
	public final int getEnd() {
		return end;
	}

	/**
	 * 
	 * @param end
	 *            Sets the ending point on the reference genome
	 */
	public final void setEnd(final int end) {
		this.end = end;
	}

	/**
	 * 
	 * @return Returns the content of the node
	 */
	public final String getContent() {
		return content;
	}

	/**
	 * 
	 * @param content
	 *            Sets the content on the reference genome
	 */
	public final void setContent(final String content) {
		this.content = content;
	}
}
