package nl.tudelft.ti2806.pl1.mutation;

import java.util.Set;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public class PointMutation extends Mutation {

	/** The nodes involved in the point mutation. */
	private final Set<Integer> nodes;

	/**
	 * 
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 * @param nodesIn
	 *            The nodes directly involved in the mutation.
	 */
	public PointMutation(final int pre, final Set<Integer> nodesIn,
			final int post) {
		super(pre, post);
		this.nodes = nodesIn;
	}

	/**
	 * @return the nodes
	 */
	public final Set<Integer> getNodes() {
		return nodes;
	}

}
