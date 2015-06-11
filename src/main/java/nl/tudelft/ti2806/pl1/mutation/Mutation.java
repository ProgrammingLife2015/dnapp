package nl.tudelft.ti2806.pl1.mutation;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 * @version 1.0
 */
public abstract class Mutation {

	/**
	 * The IDs of the nodes before and after the mutation.
	 */
	private final int preNode, postNode;

	/**
	 * 
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 */
	public Mutation(final int pre, final int post) {
		this.preNode = pre;
		this.postNode = post;
	}

	/**
	 * @return the preNode
	 */
	public final int getPreNode() {
		return preNode;
	}

	/**
	 * @return the postNode
	 */
	public final int getPostNode() {
		return postNode;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Mutation) {
			Mutation that = (Mutation) obj;
			return this.preNode == that.preNode
					&& this.postNode == that.postNode;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
