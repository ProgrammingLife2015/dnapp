package nl.tudelft.ti2806.pl1.mutation;

import java.util.Set;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public class PointMutation extends Mutation {

	/**
	 * True iff the mutation affects the amino acid structure.
	 */
	private boolean synonymous;

	/**
	 * 
	 */
	private Set<Integer> nodes;

	/**
	 * 
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 * @param nodesIn
	 *            The nodes directly involved in the mutation.
	 */
	public PointMutation(final int pre, final int post,
			final Set<Integer> nodesIn) {
		super(pre, post);
		this.nodes = nodesIn;
	}

	/**
	 * @return true iff the mutation affects the amino acid structure.
	 */
	public final boolean isSynonymous() {
		return synonymous;
	}

	/**
	 * @param syn
	 *            the synonymous to set
	 */
	public final void setSynonymous(final boolean syn) {
		this.synonymous = syn;
	}

	/**
	 * @return the nodes
	 */
	public final Set<Integer> getNodes() {
		return nodes;
	}

	/**
	 * @param ns
	 *            the nodes to set
	 */
	public final void setNodes(final Set<Integer> ns) {
		this.nodes = ns;
	}

}
