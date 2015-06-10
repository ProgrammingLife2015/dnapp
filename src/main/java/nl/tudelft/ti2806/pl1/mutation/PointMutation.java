package nl.tudelft.ti2806.pl1.mutation;

import java.util.Set;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public class PointMutation extends Mutation {

	/** The nodes involved in the point mutation. */
	private final Set<String> nodes;

	/**
	 * 
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 * @param nodesIn
	 *            The nodes directly involved in the mutation.
	 * @param syn
	 *            Whether the mutation affects the amino acid structure
	 *            (synonymous mutation)
	 */
	public PointMutation(final int pre, final int post,
			final Set<String> nodesIn, final boolean syn) {
		super(pre, post);
		this.nodes = nodesIn;
	}

	/**
	 * @return the nodes
	 */
	public final Set<String> getNodes() {
		return nodes;
	}

}
