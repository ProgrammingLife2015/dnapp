package nl.tudelft.ti2806.pl1.mutation;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public class DeletionMutation extends Mutation {

	/** Starting position of the deletion. **/
	private int startposition;
	/** End position of the deletion. **/
	private int endposition;
	/** Score if deletion is in gene. **/
	private static final int SCORE_GENE = 80;

	/**
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 * @param rgs
	 *            The storage containing all the interesting gene information.
	 */
	public DeletionMutation(final int pre, final int post,
			final ReferenceGeneStorage rgs, final int startpos, final int endpos) {
		super(pre, post, rgs);
		startposition = startpos;
		endposition = endpos;
		calculateGeneralScore();
	}

	/**
	 * Calculate the general score for a mutation.
	 */
	private void calculateGeneralScore() {
		ReferenceGeneStorage rgs = this.getReferenceGeneStorage();
		if (rgs.isIntragenic(startposition) && rgs.isIntragenic(endposition)) {
			this.setScore(SCORE_GENE);
		} else {
			this.setScore(0);
		}
	}

	@Override
	public String toString() {
		return "<DelMut[pre:" + getPreNode() + ",post:" + getPostNode() + "]>";
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof DeletionMutation) {
			DeletionMutation that = (DeletionMutation) other;
			return super.equals(that);
		}
		return false;
	}

	@Override
	public final int hashCode() {
		return super.hashCode();
	}
}
