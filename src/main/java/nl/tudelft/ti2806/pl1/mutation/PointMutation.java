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
	 * Position of the mutation on the reference genome.
	 */
	private final int position;

	/**
	 * Score for a mutation if it is located on a gene.
	 */
	private static final int SCORE_GENE = 80;

	/**
	 * Score for a mutation if it is not located on a gene.
	 */
	private static final int SCORE_NO_GENE = 10;

	/**
	 * 
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 * @param nodesIn
	 *            The nodes directly involved in the mutation.
	 * @param positionIn
	 *            Position of the mutation on the reference genome.
	 * @param rgs
	 *            The storage containing all the interesting gene information.
	 * 
	 */
	public PointMutation(final int pre, final Set<Integer> nodesIn,
			final int post, final int positionIn, final ReferenceGeneStorage rgs) {
		super(pre, post, rgs);
		this.nodes = nodesIn;
		this.position = positionIn;
		calculateGeneralScore();
	}

	/**
	 * Calculate the general score for a mutation.
	 */
	private void calculateGeneralScore() {
		ReferenceGeneStorage rgs = this.getReferenceGeneStorage();
		if (rgs.isIntragenic(getPosition())) {
			if (rgs.containsMutationIndex(getPosition())) {
				this.setScore(SCORE_GENE);
			} else {
				this.setScore(SCORE_NO_GENE);
			}
		} else {
			this.setScore(0);
		}
	}

	/**
	 * @return the nodes
	 */
	public final Set<Integer> getNodes() {
		return nodes;
	}

	/**
	 * @return Position of the mutation on the reference genome.
	 */
	public final int getPosition() {
		return position;
	}

}
