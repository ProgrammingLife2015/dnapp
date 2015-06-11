package nl.tudelft.ti2806.pl1.mutation;

import java.util.Set;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;

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
	 * @param rgs
	 *            The storage containing all the interesting gene information.
	 * 
	 */
	public PointMutation(final int pre, final Set<Integer> nodesIn,
			final int post, final ReferenceGeneStorage rgs) {
		super(pre, post, rgs);
		this.nodes = nodesIn;
		calculateGeneralScore();
	}

	/**
	 * Calculate the general score for a mutation.
	 */
	private void calculateGeneralScore() {
		ReferenceGeneStorage rgs = this.getReferenceGeneStorage();
		int index = this.getNodes().iterator().next();
		if (rgs.isIntragenic(index)) {
			if (rgs.containsMutationIndex(index)) {
				this.setScore(80);
			}
		}
	}

	/**
	 * @return the nodes
	 */
	public final Set<Integer> getNodes() {
		return nodes;
	}

}
