package nl.tudelft.ti2806.pl1.mutation;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public class DeletionMutation extends Mutation {

	/** Extra points for being insertion or deletion. **/
	private static final int INDELMODIFIER = 10;

	/**
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 * @param rgs
	 *            The storage containing all the interesting gene information.
	 * @param startpos
	 *            The start position on the reference genome.
	 * 
	 * @param endpos
	 *            The end position on the reference genome.
	 */
	public DeletionMutation(final int pre, final int post,
			final ReferenceGeneStorage rgs, final int startpos, final int endpos) {
		super(pre, post, rgs, startpos, endpos);
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

	@Override
	public double getScore() {
		return super.getScore()
				* ScoreMultiplier.getMult((MutationMultipliers.INDEL.name()));
	}
}
