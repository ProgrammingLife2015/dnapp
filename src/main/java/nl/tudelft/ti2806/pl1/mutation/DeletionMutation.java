package nl.tudelft.ti2806.pl1.mutation;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public class DeletionMutation extends Mutation {

	/**
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 */
	public DeletionMutation(final int pre, final int post) {
		super(pre, post);
	}

	@Override
	public String toString() {
		return "prenode: " + getPreNode() + " postnode: " + getPostNode();
	}

}
