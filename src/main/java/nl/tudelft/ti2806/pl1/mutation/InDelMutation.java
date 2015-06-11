package nl.tudelft.ti2806.pl1.mutation;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public class InDelMutation extends Mutation {

	/**
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 * @param rgs
	 *            The storage containing all the interesting gene information.
	 * 
	 */
	public InDelMutation(final int pre, final int post,
			final ReferenceGeneStorage rgs) {
		super(pre, post, rgs);
	}

}
