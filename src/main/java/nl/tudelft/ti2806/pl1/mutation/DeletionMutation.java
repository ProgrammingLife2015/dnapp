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
		return "<DelMut[pre:" + getPreNode() + ",post:" + getPostNode() + "]>";
	}

	/**
	 * Returns true iff that has the same attributes.
	 */
	@Override
	public boolean equals(final Object that) {
		if (that instanceof DeletionMutation) {
			return this.toString().equals(that.toString());
		}
		return false;
	}

	@Override
	public final int hashCode() {
		return getPreNode();
	}

}
