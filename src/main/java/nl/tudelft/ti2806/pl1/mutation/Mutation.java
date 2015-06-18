package nl.tudelft.ti2806.pl1.mutation;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;

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
	 * Score of the mutation.
	 */
	private double score = 0;

	/** Storage of all the genes in the reference genome. */
	private ReferenceGeneStorage referenceGeneStorage;

	/**
	 * 
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 * @param rgs
	 *            The storage containing all the interesting gene information.
	 */
	public Mutation(final int pre, final int post,
			final ReferenceGeneStorage rgs) {
		this.preNode = pre;
		this.postNode = post;
		this.referenceGeneStorage = rgs;
		this.score = 0;
	}

	/** @return the score */
	public double getScore() {
		return score;
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
