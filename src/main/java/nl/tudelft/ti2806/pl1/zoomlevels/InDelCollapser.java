package nl.tudelft.ti2806.pl1.zoomlevels;

import java.util.Collection;

import nl.tudelft.ti2806.pl1.mutation.DeletionMutation;
import nl.tudelft.ti2806.pl1.mutation.InsertionMutation;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 * @author Justin
 * @since 11-6-2015
 */
public final class InDelCollapser {

	/** The amount of nucleoids that determine how important a deletion is. */
	private static final int LARGE_AMOUNT_OF_NUCLEOIDS = 20000;

	/**
	 * The threshold applied when a deletion is larger than a certain amount of
	 * nucleoids.
	 */
	private static final int THRESHOLD = 95;

	/**	 */
	private InDelCollapser() {
	}

	/**
	 * Collapse all the insertions.
	 * 
	 * @param insmutations
	 *            Mutations to be collapsed.
	 * @param graph
	 *            Graph in which collapsing should be applied.
	 * @param threshold
	 *            The threshold for collapsing insertions.
	 * @return graphstream graph object with all the insertion mutations
	 *         collapsed.
	 * 
	 */
	public static Graph collapseInsertions(
			final Collection<InsertionMutation> insmutations,
			final Graph graph, final int threshold) {
		for (InsertionMutation ins : insmutations) {
			if (ins.getScore() < threshold) {
				collapseInsertion(ins, graph);
			}
		}
		return graph;
	}

	/**
	 * Collapse all the deletions.
	 * 
	 * @param delmutations
	 *            Mutations to be collapsed.
	 * @param graph
	 *            Graph in which collapsing should be applied.
	 * @param threshold
	 *            The threshold for collapsing deletions.
	 * @return graphstream graph object with all the deletion mutations
	 *         collapsed.
	 */
	public static Graph collapseDeletions(
			final Collection<DeletionMutation> delmutations, final Graph graph,
			final int threshold) {
		for (DeletionMutation del : delmutations) {
			if (del.getScore() < threshold) {
				if (getSize(del, graph) < LARGE_AMOUNT_OF_NUCLEOIDS) {
					collapseDeletion(del, graph);
				} else if (threshold > THRESHOLD) {
					collapseDeletion(del, graph);
				}
			}
		}
		return graph;
	}

	/**
	 * @param del
	 *            The mutation
	 * @param graph
	 *            The graph
	 * @return The amount of nucleoids that are in the mutation
	 */
	private static int getSize(final DeletionMutation del, final Graph graph) {
		int start = graph.getNode(del.getPreNode()).getAttribute("end");
		int end = graph.getNode(del.getPostNode()).getAttribute("start");
		return end - start;
	}

	/**
	 * Collapses a simple deletion mutation of a graph.
	 * 
	 * @param del
	 *            The specified deletion mutation.
	 * @param graph
	 *            The graph.
	 */
	public static void collapseDeletion(final DeletionMutation del,
			final Graph graph) {
		Node preNode = graph.getNode(del.getPreNode() + "");
		Node postNode = graph.getNode(del.getPostNode() + "");
		Edge edge = preNode.getEdgeToward(postNode);
		graph.removeEdge(edge);
	}

	/**
	 * Collapses a simple insertion mutation of a graph.
	 * 
	 * @param ins
	 *            The specified insertion mutation.
	 * @param graph
	 *            The graph.
	 */
	public static void collapseInsertion(final InsertionMutation ins,
			final Graph graph) {
		Node insertedNode = graph.getNode(ins.getInNode() + "");
		graph.removeNode(insertedNode);
	}

}
