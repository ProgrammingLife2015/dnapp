package mutation;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 * @version 1.0
 */
public abstract class Mutation {

	private int preNode, postNode;

	/**
	 * 
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

}
