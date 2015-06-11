package nl.tudelft.ti2806.pl1.zoomlevels;

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
		System.out.println("collapse the inserted node: " + ins.getInNode());
		Node insertedNode = graph.getNode(ins.getInNode());
		System.out.println("remove inserted node: " + insertedNode.getId());
		graph.removeNode(insertedNode);
	}
}
