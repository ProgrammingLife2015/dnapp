package nl.tudelft.ti2806.pl1.mutation;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public class InsertionMutation extends Mutation {

	/** The node(s) inserted in the graph. */
	private int inNode;

	/**
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 * @param insertednode
	 *            The node inserted in the graph.
	 */
	public InsertionMutation(final int pre, final int post,
			final int insertednode) {
		super(pre, post);
		this.inNode = insertednode;
	}

	/**
	 * @return The node(s) inserted in the graph.
	 */
	public int getInNode() {
		return inNode;
	}

	@Override
	public String toString() {
		return "<InsMut[pre:" + getPreNode() + ",post:" + getPostNode()
				+ ",ins:" + getInNode() + "]>";
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof InsertionMutation) {
			InsertionMutation that = (InsertionMutation) other;
			return super.equals(that) && inNode == that.inNode;
		}
		return false;
	}

	@Override
	public final int hashCode() {
		return getPreNode();
	}

}
