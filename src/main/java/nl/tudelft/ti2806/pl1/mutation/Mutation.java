package nl.tudelft.ti2806.pl1.mutation;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public abstract class Mutation {

	/** The IDs of the nodes before and after the mutation. */
	private final int preNode, postNode;

	/** Score of the mutation. */
	private double score = 0;

	/** Storage of all the genes in the reference genome. */
	private ReferenceGeneStorage referenceGeneStorage;

	/** The scores for the number of phylo groups. */
	private static final int[] GROUP_SCORE = { 0, 0, 8, 16, 24 };

	/** Score for a mutation if it is located on a gene. */
	private static final int SCORE_MUT = 70;

	/** Score for a mutation if it is not located on a gene. */
	private static final int SCORE_IN_GENE = 10;

	/** Start and end position on reference genome. **/
	private final int startposition, endposition;

	/** The number of node groups which are affected by the mutation. */
	private int affectedNodeGroups;

	/**
	 * 
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 * @param rgs
	 *            The storage containing all the interesting gene information.
	 * @param startpos
	 *            Start position on the reference genome.
	 * @param endpos
	 *            End position on the reference genome.
	 */
	public Mutation(final int pre, final int post, final int startpos,
			final int endpos, final ReferenceGeneStorage rgs) {
		this.preNode = pre;
		this.postNode = post;
		this.startposition = startpos;
		this.endposition = endpos;
		this.referenceGeneStorage = rgs;
		affectedNodeGroups = 0;
		calculateGeneralScore();
	}

	/**
	 * @return the affectedNodeGroups
	 */
	public int getAffectedNodeGroups() {
		return affectedNodeGroups;
	}

	/**
	 * @param newAffectedNodeGroups
	 *            the affectedNodeGroups to set
	 */
	public void setAffectedNodeGroups(final int newAffectedNodeGroups) {
		this.affectedNodeGroups = newAffectedNodeGroups;
	}

	/**
	 * @return the score of the mutation.
	 */
	public double getScore() {
		calculateGeneralScore();
		return score;
	}

	/**
	 * Calculate the general score for a mutation.
	 */
	private void calculateGeneralScore() {
		score = 0;
		ReferenceGeneStorage rgs = this.getReferenceGeneStorage();
		if (rgs.isIntragenic(startposition) || rgs.isIntragenic(endposition)) {
			addScore(SCORE_IN_GENE
					* ScoreMultiplier.getMult(MutationMultipliers.IN_GENE
							.name()));
			scoreKnownMutation();
			scoreNodeGroups();
		}
	}

	/**
	 * Add score for KnownMutation.
	 */
	private void scoreKnownMutation() {
		ReferenceGeneStorage rgs = this.getReferenceGeneStorage();
		if (rgs.getDrugResistanceMutations() != null) {
			for (long pos : rgs.getDrugResistanceMutations().keySet()) {
				if (startposition <= pos && pos < endposition) {
					addScore(SCORE_MUT
							* ScoreMultiplier
									.getMult(MutationMultipliers.KNOWN_MUTATION
											.name()));
					return;
				}
			}
		}
	}

	/**
	 * Add score for NodeGroups.
	 */
	private void scoreNodeGroups() {
		double phylomult = ScoreMultiplier.getMult(MutationMultipliers.PHYLO
				.name());
		if (affectedNodeGroups < GROUP_SCORE.length - 1) {
			addScore(GROUP_SCORE[affectedNodeGroups] * phylomult);
		} else {
			addScore(GROUP_SCORE[GROUP_SCORE.length - 1] * phylomult);
		}
	}

	/**
	 * @return the preNode
	 */
	public int getPreNode() {
		return preNode;
	}

	/**
	 * @return the postNode
	 */
	public int getPostNode() {
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

	/**
	 * @return the referenceGeneStorage
	 */
	public final ReferenceGeneStorage getReferenceGeneStorage() {
		return referenceGeneStorage;
	}

	/**
	 * Add the score to the current.
	 * 
	 * @param addScore
	 *            Score to add.
	 */
	public final void addScore(final double addScore) {
		this.score += addScore;
	}

	/**
	 * @param scoreIn
	 *            the score to set
	 */
	public final void setScore(final double scoreIn) {
		this.score = scoreIn;
	}

}
