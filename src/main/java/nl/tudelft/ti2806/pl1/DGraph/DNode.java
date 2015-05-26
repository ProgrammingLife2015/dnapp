package nl.tudelft.ti2806.pl1.DGraph;

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
	 * the id of the node.
	 */
	private int id;

	/**
	 * the out- and inEdges of the node.
	 */
	private Collection<DEdge> outEdges, inEdges;

	/**
	 * The different genomes which go through this node.
	 */
	private HashSet<String> sources;

	/**
	 * The start index on the genome.
	 */
	private int start;

	/**
	 * The end index on the genome.
	 */
	private int end;

	/**
	 * The content which this node contains.
	 */
	private String content;

	/**
	 * The x and y coordinates.
	 */
	private int x, y;

	/**
	 * The depth of the node
	 */
	private int depth;

	/** Percentage of unknown nucleotides in the content. */
	private double percUnknown;

	/** @return the percUnknown */
	public double getPercUnknown() {
		return percUnknown;
	}

	/**
	 * @param percUnknown
	 *            the percUnknown to set
	 */
	public void setPercUnknown(final double percUnknown) {
		this.percUnknown = percUnknown;
	}

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
		this.outEdges = new ArrayList<DEdge>();
		this.inEdges = new ArrayList<DEdge>();
		this.start = startIn;
		this.end = endIn;
		this.sources = sourcesIn;
		this.content = contentIn;
		this.x = 0;
		this.y = 0;
		this.depth = 0;
		if (id >= 0) {
			this.percUnknown = percentageUnknown(contentIn);
		} else {
			this.percUnknown = 1.0;
		}
	}

	/**
	 * Count the amount of unknown nucleotides in the content and returns the
	 * inverse percentage of it.
	 * 
	 * @param content
	 *            The string to be processed.
	 * @return Inverse percentage of the amount of unknown nucleotides.
	 */
	private double percentageUnknown(final String content) {
		int counter = 0;
		for (int i = 0; i < content.length(); i++) {
			if (content.charAt(i) == 'N') {
				counter++;
			}
		}
		return ((double) counter / content.length());
	}

	/**
	 * Add an edge to a node.
	 * 
	 * @param edge
	 *            The edge added to the node
	 */
	public final boolean addEdge(final DEdge edge) {
		if (edge.getStartNode().getId() == id && !getOutEdges().contains(edge)) {
			outEdges.add(edge);
			return true;
		} else if (edge.getEndNode().id == id && !getInEdges().contains(edge)) {
			getInEdges().add(edge);
			return true;
		} else {
			return false;
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
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public final void setX(final int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public final void setY(final int y) {
		this.y = y;
	}

	/**
	 * @return the depth
	 */
	public final int getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	public final void setDepth(final int depth) {
		this.depth = depth;
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
	public final Collection<DEdge> getOutEdges() {
		return outEdges;
	}

	/**
	 * Set all the outgoing edges.
	 * 
	 * @param newOutEdges
	 *            Sets all the outgoing edges
	 */
	public final void setOutEdges(final Collection<DEdge> newOutEdges) {
		this.outEdges = newOutEdges;
	}

	/**
	 * Get all the incoming edges.
	 * 
	 * @return the incoming edges
	 */
	public Collection<DEdge> getInEdges() {
		return inEdges;
	}

	/**
	 * Sets all the incoming edges.
	 * 
	 * @param newInEdges
	 *            Incoming edges.
	 */
	public final void setInEdges(final Collection<DEdge> newInEdges) {
		this.inEdges = newInEdges;
	}

	/**
	 * Get the sources which contains this node.
	 * 
	 * @return Returns the different sources which contains this node
	 */
	public final HashSet<String> getSources() {
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

	/**
	 * This method returns all the edges in the node.
	 * 
	 * @return All the edges in the node.
	 */
	public Collection<DEdge> getAllEdges() {
		Collection<DEdge> allEdges = new ArrayList<DEdge>();
		allEdges.addAll(inEdges);
		allEdges.addAll(outEdges);
		return allEdges;
	}

	/**
	 * This method returns all the nodes which have an edge to this node.
	 * 
	 * @return All the nodes which have an edge to this node
	 */
	public final Collection<DNode> getPreviousNodes() {
		ArrayList<DNode> previous = new ArrayList<DNode>();
		for (DEdge edge : inEdges) {
			previous.add(edge.getStartNode());
		}
		return previous;
	}

	/**
	 * This method returns all the nodes to which this node has an edge.
	 * 
	 * @return All the nodes to which this node has an edge
	 */
	public final Collection<DNode> getNextNodes() {
		ArrayList<DNode> next = new ArrayList<DNode>();
		for (DEdge edge : outEdges) {
			next.add(edge.getEndNode());
		}
		return next;
	}

	/**
	 * Removes an edge from the node.
	 * 
	 * @param edge
	 *            The edge to be removed
	 * @return A boolean indicating if it succeeded or failed
	 */
	public boolean deleteEdge(final DEdge edge) {
		if (inEdges.contains(edge)) {
			inEdges.remove(edge);
			return true;
		} else if (outEdges.contains(edge)) {
			outEdges.remove(edge);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks is this node is equal to the given node.
	 * 
	 * @param node
	 *            The node to check
	 * @return True if it's equal, false otherwise
	 */
	@Override
	public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof DNode)) {
			return false;
		}
		DNode node = (DNode) o;
		return node.getId() == getId();
	}

	/**
	 * returns the hashcode of this class.
	 */
	@Override
	public int hashCode() {
		return getId();
	}
}
