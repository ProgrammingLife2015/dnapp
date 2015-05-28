/**
 * 
 */
package nl.tudelft.ti2806.pl1.DGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 
 * The abstract class for reader the different nodes.
 * 
 * @author Chak Shun
 * @since 28-5-2015
 *
 */
public abstract class AbstractDNode {

	/** The id of the node. */
	private int id;

	/** The out- and inEdges of the node. */
	private Collection<DEdge> outEdges, inEdges;

	/** The different genomes which go through this node. */
	private HashSet<String> sources;

	/** The start index on the genome. */
	private int start;

	/** The end index on the genome. */
	private int end;

	/** The x and y coordinates. */
	private int x, y;

	/** The depth of the node. */
	private int depth;

	/**
	 * Superclass constructor for the DNode.
	 * 
	 * @param idIn
	 *            ID of the node.
	 * @param sourcesIn
	 *            Genomes which contains this node.
	 * @param startIn
	 *            Starting point in reference genome.
	 * @param endIn
	 *            End point in the reference genome.
	 */
	public AbstractDNode(final int idIn, final HashSet<String> sourcesIn,
			final int startIn, final int endIn) {
		this.id = idIn;
		this.outEdges = new ArrayList<DEdge>();
		this.inEdges = new ArrayList<DEdge>();
		this.start = startIn;
		this.end = endIn;
		this.sources = sourcesIn;
		this.x = 0;
		this.y = 0;
		this.depth = 0;
	}

	/**
	 * Returns the content of the node to show in the information bar..
	 * 
	 * @return Content of the node.
	 */
	public abstract String showContent();

	/**
	 * Get the content of the node in a hashmap.
	 * 
	 * @return Content of the DNode.
	 */
	public abstract HashMap<String, String> getContentMap();

	/**
	 * Get the percentage of unknown nucleotides for each source in a hashmap.
	 * 
	 * @return Percentage of unknown nucleotides.
	 */
	public abstract HashMap<String, Double> getPercUnknownMap();

	/**
	 * Add an edge to a node.
	 * 
	 * @param edge
	 *            The edge added to the node
	 * @return Boolean whether the edge was added or not.
	 */
	public final boolean addEdge(final DEdge edge) {
		if (edge.getStartNode().getId() == id && !getOutEdges().contains(edge)) {
			outEdges.add(edge);
			return true;
		} else if (edge.getEndNode().getId() == id
				&& !getInEdges().contains(edge)) {
			getInEdges().add(edge);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes an edge from the node.
	 * 
	 * @param edge
	 *            The edge to be removed
	 * @return A boolean indicating if it succeeded or failed
	 */
	public boolean deleteEdge(final DEdge edge) {
		if (getInEdges().contains(edge)) {
			getInEdges().remove(edge);
			return true;
		} else if (getOutEdges().contains(edge)) {
			getOutEdges().remove(edge);
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
	public final int hashCode() {
		return getId();
	}

	/**
	 * This method returns all the edges in the node.
	 * 
	 * @return All the edges in the node.
	 */
	public Collection<DEdge> getAllEdges() {
		Collection<DEdge> allEdges = new ArrayList<DEdge>();
		allEdges.addAll(getInEdges());
		allEdges.addAll(getOutEdges());
		return allEdges;
	}

	/**
	 * This method returns all the nodes which have an edge to this node.
	 * 
	 * @return All the nodes which have an edge to this node
	 */
	public Collection<DNode> getPreviousNodes() {
		ArrayList<DNode> previous = new ArrayList<DNode>();
		for (DEdge edge : getInEdges()) {
			previous.add(edge.getStartNode());
		}
		return previous;
	}

	/**
	 * This method returns all the nodes to which this node has an edge.
	 * 
	 * @return All the nodes to which this node has an edge
	 */
	public Collection<DNode> getNextNodes() {
		ArrayList<DNode> next = new ArrayList<DNode>();
		for (DEdge edge : getOutEdges()) {
			next.add(edge.getEndNode());
		}
		return next;
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
	 * @param xIn
	 *            the x to set
	 */
	public final void setX(final int xIn) {
		this.x = xIn;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param yIn
	 *            the y to set
	 */
	public final void setY(final int yIn) {
		this.y = yIn;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depthIn
	 *            the depth to set
	 */
	public final void setDepth(final int depthIn) {
		this.depth = depthIn;
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
	public Collection<DEdge> getOutEdges() {
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
	public HashSet<String> getSources() {
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
	public int getStart() {
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
	public int getEnd() {
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
}
