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
	 * @param startpos
	 *            The starting position on the reference genome.
	 * @param endpos
	 *            the end position on the reference genome.
	 */
	public PointMutation(final int pre, final Set<Integer> nodesIn, final int post, final int startpos,
			final int endpos, final ReferenceGeneStorage rgs) {
		super(pre, post, rgs, startpos, endpos);
		this.nodes = nodesIn;
	}

	/**
	 * @return the nodes
	 */
	public final Set<Integer> getNodes() {
		return nodes;
	}

	@Override
	public double getScore() {
		return super.getScore() * ScoreMultiplier.getMult((MutationMultipliers.POINTMUTATION.name()));
	}
}
