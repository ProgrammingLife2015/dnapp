package nl.tudelft.ti2806.pl1.DGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;
import nl.tudelft.ti2806.pl1.gui.contentpane.ViewArea;
import nl.tudelft.ti2806.pl1.mutation.ComplexMutation;
import nl.tudelft.ti2806.pl1.mutation.DeletionMutation;
import nl.tudelft.ti2806.pl1.mutation.InsertionMutation;
import nl.tudelft.ti2806.pl1.mutation.MutatedGraph;
import nl.tudelft.ti2806.pl1.mutation.PointMutation;

/**
 * The DGraph class for representing our data.
 * 
 * @author Mark
 *
 */
public class DGraph implements MutatedGraph, DynamicGraph {

	/** The nodes in the graph. */
	private final Map<Integer, DNode> nodes;

	/** A map storing a collection nodes per genome reference. */
	private HashMap<String, Collection<DNode>> references;

	/** The edges in the graph. */
	private Collection<DEdge> edges;

	/** The start node and end node of the graph. */
	private DNode start, end;

	/** Storage of all the genes in the reference genome. */
	private ReferenceGeneStorage referenceGeneStorage;

	/** File with all the gene information regarding the reference genome. */
	private static final String GFF_FILE = "decorationV5_20130412.gff";

	// /** File with all the drug resistance causing mutations. */
	// private static final String MUTATION_FILE =
	// "resistanceCausingMutations.txt";

	/** All the point mutations in the graph. */
	private Collection<PointMutation> pointMutations;

	/** All the deletion mutations in the graph. */
	private Collection<DeletionMutation> delmutations;

	/** All the insertion mutations in the graph. */
	private Collection<InsertionMutation> insmutations;

	/** All the insertion mutations in the graph. */
	private Collection<ComplexMutation> compmutations;

	/**
	 * Creates a new DGraph.
	 */
	public DGraph() {
		nodes = new HashMap<Integer, DNode>();
		edges = new ArrayList<DEdge>();
		references = new HashMap<String, Collection<DNode>>();
		start = null;
		end = null;
		referenceGeneStorage = new ReferenceGeneStorage(GFF_FILE, null);
	}

	/**
	 * @return the references
	 */
	public Map<String, Collection<DNode>> getReferences() {
		return references;
	}

	/**
	 * @param newReferences
	 *            the references to set
	 */
	protected void setReferences(
			final HashMap<String, Collection<DNode>> newReferences) {
		this.references = newReferences;
	}

	/**
	 * Returns all the nodes which contain a specific reference.
	 * 
	 * @param s
	 *            The reference from which we want to gain the nodes
	 * @return A collection which contain
	 */
	public Collection<DNode> getReference(final String s) {
		if (!references.containsKey(s)) {
			return new ArrayList<DNode>();
		}
		return references.get(s);
	}

	/**
	 * @return the edges
	 */
	public Collection<DEdge> getEdges() {
		return edges;
	}

	/**
	 * @param newEdges
	 *            the edges to set
	 */
	protected final void setEdges(final Collection<DEdge> newEdges) {
		this.edges = newEdges;
	}

	/**
	 * @return the nodes
	 */
	public Map<Integer, DNode> getNodes() {
		return nodes;
	}

	/**
	 * Adds a node to the graph.
	 * 
	 * @param node
	 *            The node to be added
	 * @return A boolean indicating of the operation succeeded
	 */
	public boolean addDNode(final DNode node) {
		if (nodes.containsKey(node.getId())) {
			return false;
		}
		for (String s : node.getSources()) {
			if (!references.containsKey(s)) {
				ArrayList<DNode> temp = new ArrayList<DNode>();
				temp.add(node);
				references.put(s, temp);
			} else {
				references.get(s).add(node);
			}
		}
		nodes.put(node.getId(), node);
		return true;
	}

	/**
	 * Removes a node from the graph.
	 * 
	 * @param node
	 *            The node to be removed
	 * @return A boolean indicating of the operation succeeded
	 */
	public boolean removeDNode(final DNode node) {
		if (!nodes.containsKey(node.getId())) {
			return false;
		}
		return removeDNode(node.getId());
	}

	/**
	 * Removes a node from the graph.
	 * 
	 * @param n
	 *            The node id to be removed
	 * @return A boolean indicating of the operation succeeded
	 */
	public boolean removeDNode(final int n) {
		if (!nodes.containsKey(n)) {
			return false;
		}
		DNode removeNode = nodes.get(n);
		for (DEdge edge : removeNode.getAllEdges()) {
			edge.getStartNode().deleteEdge(edge);
			edge.getEndNode().deleteEdge(edge);
			edges.remove(edge);
		}
		for (String s : removeNode.getSources()) {
			references.get(s).remove(removeNode);
			if (references.get(s).isEmpty()) {
				references.remove(s);
			}
		}
		nodes.remove(n);
		return true;
	}

	/**
	 * Adds an edge to the graph.
	 * 
	 * @param edge
	 *            The edge to be added
	 * @return A boolean indicating of the operation succeeded
	 */
	public boolean addDEdge(final DEdge edge) {
		if (!nodes.containsKey(edge.getStartNode().getId())
				|| !nodes.containsKey(edge.getEndNode().getId())) {
			return false;
		}
		if (edges.contains(edge)) {
			return false;
		}
		nodes.get(edge.getStartNode().getId()).addEdge(edge);
		nodes.get(edge.getEndNode().getId()).addEdge(edge);
		edges.add(edge);
		return true;
	}

	/**
	 * Removes an edge from the graph.
	 * 
	 * @param edge
	 *            The edge to be removed
	 * @return A boolean indicating of the operation succeeded
	 */
	public boolean removeDEdge(final DEdge edge) {
		if (!nodes.containsKey(edge.getStartNode().getId())
				|| !nodes.containsKey(edge.getEndNode().getId())) {
			return false;
		}
		if (!edges.contains(edge)) {
			return false;
		}
		nodes.get(edge.getStartNode().getId()).deleteEdge(edge);
		nodes.get(edge.getEndNode().getId()).deleteEdge(edge);
		edges.remove(edge);
		return true;
	}

	/**
	 * Gets a node from the graph.
	 * 
	 * @param n
	 *            The id of the node
	 * @return The node in the graph
	 */
	public DNode getDNode(final int n) {
		return nodes.get(n);
	}

	/**
	 * Get the size of the amount of nodes.
	 * 
	 * @return The amount of nodes in the graph
	 */
	public int getNodeCount() {
		return nodes.size();
	}

	/**
	 * @return the start.
	 */
	public DNode getStart() {
		return start;
	}

	/**
	 * @param s
	 *            the start to set.
	 */
	public final void setStart(final DNode s) {
		this.start = s;
	}

	/**
	 * @return the end.
	 */
	public DNode getEnd() {
		return end;
	}

	/**
	 * @param e
	 *            the end to set.
	 */
	public final void setEnd(final DNode e) {
		this.end = e;
	}

	/** {@inheritDoc} */
	@Override
	public Collection<PointMutation> getAllPointMutations() {
		return pointMutations;
	}

	@Override
	public Collection<PointMutation> getAllInDelMutations() {
		return pointMutations;
	}

	/** {@inheritDoc} */
	@Override
	public Collection<DNode> getDNodes(final ViewArea va) {
		ArrayList<DNode> ret = new ArrayList<DNode>();
		for (DNode n : getNodes().values()) {
			if (va.isContained(n)) {
				ret.add(n);
			}
		}
		return ret;
	}

	@Override
	public String toString() {
		return nodes + " " + edges;
	}

	/**
	 * @param newPointMutations
	 *            pointMutations to set.
	 */
	public void setPointMutations(
			final Collection<PointMutation> newPointMutations) {
		this.pointMutations = newPointMutations;
	}

	/**
	 * @param deletionMutationsIn
	 *            deletion mutations to set.
	 */
	public void setDeletionMutations(
			final Collection<DeletionMutation> deletionMutationsIn) {
		delmutations = deletionMutationsIn;
	}

	/** @return the deletion mutations. */
	public Collection<DeletionMutation> getDelmutations() {
		return delmutations;
	}

	/**
	 * @return the insmutations
	 */
	public Collection<InsertionMutation> getInsmutations() {
		return insmutations;
	}

	/**
	 * @param mutationsIn
	 *            The complex mutations to set.
	 */
	public void setComplexMutations(
			final Collection<ComplexMutation> mutationsIn) {
		this.compmutations = mutationsIn;
	}

	/**
	 * @return The complex mutations.
	 */
	public Collection<ComplexMutation> getComplexMutations() {
		return compmutations;
	}

	/**
	 * @param insmutationsIn
	 *            the insmutations to set
	 */
	public void setInsertionmutations(
			final Collection<InsertionMutation> insmutationsIn) {
		this.insmutations = insmutationsIn;
	}

	/**
	 * @return the referenceGeneStorage
	 */
	public final ReferenceGeneStorage getReferenceGeneStorage() {
		return referenceGeneStorage;
	}
}
