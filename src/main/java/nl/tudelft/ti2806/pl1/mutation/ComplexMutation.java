package nl.tudelft.ti2806.pl1.mutation;

import java.util.Collection;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;

/**
 * @author Justin, Maarten
 * @since 12-6-2015
 */
public class ComplexMutation extends Mutation {

	/**
	 * 
	 */
	private Collection<Integer> inNodes;

	/**
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 * @param startpos
	 *            The starting position of the mutation in the reference genome.
	 * @param endpos
	 *            The end position of the mutation in the reference genome.
	 * @param rgs
	 *            The storage containing all the interesting gene information.
	 * @param insertedNodes
	 *            The IDs of the inserted nodes.
	 */
	public ComplexMutation(final int pre, final Integer post,
			final int startpos, final int endpos,
			final ReferenceGeneStorage rgs,
			final Collection<Integer> insertedNodes) {
		super(pre, post, startpos, endpos, rgs);
		this.inNodes = insertedNodes;
	}

	/**
	 * @return The IDs of the inserted nodes.
	 */
	public Collection<Integer> getInNodes() {
		return this.inNodes;
	}

	@Override
	public String toString() {
		return "<CompMut[pre:" + getPreNode() + ",post:" + getPostNode()
				+ ",ins:" + getInNodes() + "]>";
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof ComplexMutation) {
			ComplexMutation that = (ComplexMutation) other;
			return super.equals(that) && inNodes == that.inNodes;
		}
		return false;
	}

	@Override
	public final int hashCode() {
		return getPreNode();
	}

	@Override
	public double getScore() {
		return super.getScore()
				* ScoreMultiplier.getMult(MutationMultipliers.INDEL.name());
	}

}
