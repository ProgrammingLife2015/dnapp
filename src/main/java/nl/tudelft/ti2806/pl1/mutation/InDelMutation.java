package nl.tudelft.ti2806.pl1.mutation;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public class InDelMutation extends Mutation {

	/**
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 */
	public InDelMutation(final int pre, final int post) {
		super(pre, post);
	}

}
