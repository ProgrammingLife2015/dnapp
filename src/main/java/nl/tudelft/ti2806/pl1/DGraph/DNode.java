package nl.tudelft.ti2806.pl1.DGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import nl.tudelft.ti2806.pl1.mutation.ResistanceMutation;

/**
 * The node class we use for reader to read the nodes.
 * 
 * @author Marissa, Mark
 * @since 25-04-2015 Lets you create Nodes for the Graph.
 */
public class DNode {

	/** The id of the node. */
	private int id;

	/** The edges of the node. */
	private Collection<DEdge> outEdges, inEdges;

	/** The different genomes which go through this node. */
	private HashSet<String> sources;

	/** The start index on the genome. */
	private int start;

	/** The end index on the genome. */
	private int end;

	/** The content of the node. */
	private String content;

	/** The original placement coordinates. */
	private int x, y;

	/** The depth of the node. */
	private int depth;

	/** The list of resistance caused mutations in this node. */
	private List<ResistanceMutation> resMuts;

	/** Percentage of unknown nucleotides in the content. */
	private double percUnknown;

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
		this.resMuts = null;
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
	 * @param contentIn
	 *            The string to be processed.
	 * @return Percentage of the amount of unknown nucleotides.
	 */
	private double percentageUnknown(final String contentIn) {
		int counter = 0;
		for (int i = 0; i < contentIn.length(); i++) {
			if (contentIn.charAt(i) == 'N') {
				counter++;
			}
		}
		return (double) counter / contentIn.length();
	}

	/**
	 * Add an edge to a node.
	 * 
	 * @param edge
	 *            The edge added to the node
	 * @return true iff the ID of the edge is not already in use.
	 */
	public final boolean addEdge(final DEdge edge) {
		if (edge.getStartNode().getId() == id && !getOutEdges().contains(edge)) {
			outEdges.add(edge);
			return true;
		} else if (edge.getEndNode().id == id && !getInEdges().contains(edge)) {
			getInEdges().add(edge);
			return true;
		}
		return false;
	}

	/**
	 * @return The ID of the node
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the x coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x coordinate.
	 * 
	 * @param newX
	 *            the new x coordinate to set
	 */
	public final void setX(final int newX) {
		this.x = newX;
	}

	/**
	 * @return the y coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y coordinate.
	 * 
	 * @param newY
	 *            The y to set.
	 */
	public final void setY(final int newY) {
		this.y = newY;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Sets the depth.
	 * 
	 * @param newDepth
	 *            The depth to set.
	 */
	public final void setDepth(final int newDepth) {
		this.depth = newDepth;
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
	 * @return the incoming edges
	 */
	public Collection<DEdge> getInEdges() {
		return inEdges;
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

	/**
	 * Get the content of the node.
	 * 
	 * @return The content of the node
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Set the content of the node.
	 * 
	 * @param newContent
	 *            The new content.
	 */
	public final void setContent(final String newContent) {
		this.content = newContent;
	}

	/**
	 * @return the percUnknown
	 */
	public double getPercUnknown() {
		return percUnknown;
	}

	/**
	 * @param percUnknownIn
	 *            the percUnknown to set
	 */
	public final void setPercUnknown(final double percUnknownIn) {
		this.percUnknown = percUnknownIn;
	}

	/**
	 * This method returns all the edges in the node.
	 * 
	 * @return All the edges of the node.
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
	 * @return All the nodes which have an edge to this node.
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
		}
		return false;
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
	 * Adds an index to the list of resistance caused mutations.
	 * 
	 * @param resMut
	 *            The index to add. Should be relative to the reference genome.
	 */
	public void addResistantMutationIndex(final ResistanceMutation resMut) {
		if (resMut.getRefIndex() < getStart()
				|| resMut.getRefIndex() > getEnd()) {
			System.out.println("!!! refIndex not in this node!");
		} else {
			if (resMuts == null) {
				resMuts = new ArrayList<ResistanceMutation>(1);
			}
			resMuts.add(resMut);
		}
	}

	@Override
	public final int hashCode() {
		return getId();
	}

	@Override
	public String toString() {
		return "<Node[" + id + "]>";
	}
}
