package nl.tudelft.ti2806.pl1.DGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.tudelft.ti2806.pl1.exceptions.InvalidGenomeIdException;
import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;
import nl.tudelft.ti2806.pl1.gui.contentpane.ViewArea;
import nl.tudelft.ti2806.pl1.mutation.ComplexMutation;
import nl.tudelft.ti2806.pl1.mutation.DeletionMutation;
import nl.tudelft.ti2806.pl1.mutation.InsertionMutation;
import nl.tudelft.ti2806.pl1.mutation.MutatedGraph;
import nl.tudelft.ti2806.pl1.mutation.PointMutation;

/**
 * The data graph class representing data.
 * 
 * @author Mark, PL1
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

	/** All the point mutations in the graph. */
	private Collection<PointMutation> pointMutations;

	/** All the deletion mutations in the graph. */
	private Collection<DeletionMutation> delMutations;

	/** All the insertion mutations in the graph. */
	private Collection<InsertionMutation> insMutations;

	/** The name of the reference genome. */
	private String refGenomeName = "TKK_REF";

	/** The length of the reference genome. */
	private int refGenomeLength;

	/** Id of the selected id. */
	private String selected = String.valueOf(Integer.MIN_VALUE);
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
		referenceGeneStorage = new ReferenceGeneStorage(this);
	}

	/**
	 * @return the references
	 */
	public Map<String, Collection<DNode>> getReferences() {
		return references;
	}

	/**
	 * 
	 * @return The set containing the names of all references.
	 */
	public Set<String> getReferencesSet() {
		return references.keySet();
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
	 * @param genomeName
	 *            The reference from which we want to gain the nodes
	 * @return A collection which contain the specified genome.
	 */
	public Collection<DNode> getReference(final String genomeName) {
		if (!references.containsKey(genomeName)) {
			return new ArrayList<DNode>();
		}
		return references.get(genomeName);
	}

	/**
	 * @return The collection of nodes of the reference genome.
	 */
	public Collection<DNode> getRefGenome() {
		return getReference(getRefGenomeName());
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

	@Override
	public Collection<PointMutation> getAllPointMutations() {
		return pointMutations;
	}

	@Override
	public Collection<PointMutation> getAllInDelMutations() {
		return pointMutations;
	}

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
		delMutations = deletionMutationsIn;
	}

	/** @return the deletion mutations. */
	public Collection<DeletionMutation> getDelMutations() {
		return delMutations;
	}

	/**
	 * @return the insertion mutations
	 */
	public Collection<InsertionMutation> getInsMutations() {
		return insMutations;
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
	 * @param newInsMutations
	 *            the insertion mutations to set
	 */
	public void setInsertionMutations(
			final Collection<InsertionMutation> newInsMutations) {
		this.insMutations = newInsMutations;
	}

	/**
	 * @return the referenceGeneStorage
	 */
	public final ReferenceGeneStorage getReferenceGeneStorage() {
		return referenceGeneStorage;
	}

	/**
	 * Calculates the length of the reference genome.
	 */
	public final void calculateReferenceLength() {
		System.out.println("IN CALC");
		int ret = 0;
		for (DNode d : references.get(refGenomeName)) {
			ret += d.getContent().length();
		}
		this.refGenomeLength = ret;
	}

	/**
	 * @return the reference genome name.
	 */
	public final String getRefGenomeName() {
		return refGenomeName;
	}

	/**
	 * @return true iff the reference genome name has been set.
	 */
	public final boolean isRefGenSet() {
		return refGenomeName != null;
	}

	/**
	 * Sets the name of the reference genome if it exists in the set of genomes
	 * existing in the graph.
	 * 
	 * @param newRefGenomeName
	 *            the new reference genome name to set.
	 */
	public final void setRefGenomeName(final String newRefGenomeName) {
		if (references.containsKey(newRefGenomeName)) {
			this.refGenomeName = newRefGenomeName;
			calculateReferenceLength();
		} else {
			throw new InvalidGenomeIdException(newRefGenomeName);
		}
	}

	/**
	 * @return the referenceLength.
	 */
	public final int getReferenceLength() {
		return refGenomeLength;
	}

	/**
	 * @return the selected node.
	 */
	public final String getSelected() {
		return selected;
	}

	/**
	 * @param id
	 *            The selected node to set
	 */
	public final void setSelected(final String id) {
		this.selected = id;
	}

	/**
	 * Takes a list of nodes, and sorts them according to their start property.
	 * 
	 * @param dnodes
	 *            List of nodes to sort.
	 * @return A list with the nodes sorted nodes.
	 */
	public List<DNode> sortNodes(final List<DNode> dnodes) {
		dnodes.sort(new Comparator<DNode>() {
			@Override
			public int compare(final DNode n1, final DNode n2) {
				int start1 = n1.getStart();
				int start2 = n2.getStart();
				return start1 - start2;
			}
		});
		return dnodes;
	}

}
