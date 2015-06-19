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

	/**
	 * The amount of nucleoids that will determine how important the mutation
	 * is.
	 */
	private static final int LARGE_AMOUNT_OF_NUCLEOIDS = 5000;

	/** The threshold for when the mutation is very large. */
	private static final int THRESHOLD_LARGE_MUTATION = 95;

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
	public static Graph collapseInsertions(final Collection<InsertionMutation> insmutations,
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
	public static Graph collapseDeletions(final Collection<DeletionMutation> delmutations, final Graph graph,
			final int threshold) {
		for (DeletionMutation del : delmutations) {
			if (del.getScore() < threshold) {
				if (getSize(del, graph) < LARGE_AMOUNT_OF_NUCLEOIDS) {
					collapseDeletion(del, graph);
				} else if (threshold > THRESHOLD_LARGE_MUTATION) {
					collapseDeletion(del, graph);
				}
			}
		}
		return graph;
	}

	/**
	 * @param del
	 *            The mutation.
	 * @param graph
	 *            The graph.
	 * @return The amount of nucleoids the mutation contains.
	 */
	private static int getSize(final DeletionMutation del, final Graph graph) {
		Node pre = graph.getNode("" + del.getPreNode());
		Node post = graph.getNode("" + del.getPostNode());
		if (pre == null || post == null) {
			return 0;
		}
		int start;
		int end;
		if (pre.getAttribute("end") instanceof Integer && post.getAttribute("start") instanceof Integer) {
			start = pre.getAttribute("end");
			end = post.getAttribute("start");
		} else {
			return 0;
		}
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
	public static void collapseDeletion(final DeletionMutation del, final Graph graph) {
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
	public static void collapseInsertion(final InsertionMutation ins, final Graph graph) {
		Node insertedNode = graph.getNode(ins.getInNode() + "");
		graph.removeNode(insertedNode);
	}

}
