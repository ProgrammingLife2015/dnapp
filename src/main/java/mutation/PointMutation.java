package mutation;

import java.util.Set;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public class PointMutation extends Mutation {

	/**
	 * 
	 */
	private boolean synonymous;

	/**
	 * 
	 */
	private Set<Integer> nodes;

	/**
	 * 
	 */
	public PointMutation(final int pre, final int post, final Set<Integer> nodesIn) {
		super(pre, post);
		this.nodes = nodesIn;
	}

	/**
	 * @return the synonymous
	 */
	public final boolean isSynonymous() {
		return synonymous;
	}

	/**
	 * @param synonymous
	 *            the synonymous to set
	 */
	public final void setSynonymous(final boolean synonymous) {
		this.synonymous = synonymous;
	}

	/**
	 * @return the nodes
	 */
	public final Set<Integer> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes
	 *            the nodes to set
	 */
	public final void setNodes(final Set<Integer> nodes) {
		this.nodes = nodes;
	}

}
