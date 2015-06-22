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

	/**	 */
	private InDelCollapser() {
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

	/**
	 * Collapse all the insertions.
	 * 
	 * @param insmutations
	 *            Mutations to be collapsed.
	 * @param graph
	 *            Graph in which collapsing should be applied.
	 * @return graphstream graph object with all the insertion mutations
	 *         collapsed.
	 * 
	 */
	public static Graph collapseInsertions(
			final Collection<InsertionMutation> insmutations, final Graph graph) {
		for (InsertionMutation ins : insmutations) {
			collapseInsertion(ins, graph);
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
	 * @return graphstream graph object with all the deletion mutations
	 *         collapsed.
	 */
	public static Graph collapseDeletions(
			final Collection<DeletionMutation> delmutations, final Graph graph) {
		for (DeletionMutation del : delmutations) {
			collapseDeletion(del, graph);
		}
		return graph;
	}
}
