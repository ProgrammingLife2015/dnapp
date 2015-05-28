/**
 * 
 */
package nl.tudelft.ti2806.pl1.DGraph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Abstract class for all the DNode subclasses which contain content.
 * 
 * @author Chak Shun
 * @since 28-5-2015
 */
public abstract class ContentDNode extends AbstractDNode {

	/**
	 * Constructor for DNode with content.
	 * 
	 * @param idIn
	 *            ID of the node.
	 * @param sourcesIn
	 *            Genomes which contains this node.
	 * @param startIn
	 *            Starting point in reference genome.
	 * @param endIn
	 *            End point in the reference genome.
	 */
	public ContentDNode(final int idIn, final HashSet<String> sourcesIn,
			final int startIn, final int endIn) {
		super(idIn, sourcesIn, startIn, endIn);
	}

	/** {@inheritDoc} */
	@Override
	public abstract String showContent();

	/** {@inheritDoc} */
	@Override
	public abstract HashMap<String, String> getContentMap();

	/** {@inheritDoc} */
	@Override
	public abstract HashMap<String, Double> getPercUnknownMap();

	/**
	 * Count the amount of unknown nucleotides in the content and returns the
	 * inverse percentage of it.
	 * 
	 * @param contentIn
	 *            The string to be processed.
	 * @return Percentage of the amount of unknown nucleotides.
	 */
	protected final double percentageUnknown(final String contentIn) {
		int counter = 0;
		for (int i = 0; i < contentIn.length(); i++) {
			if (contentIn.charAt(i) == 'N') {
				counter++;
			}
		}
		return ((double) counter / contentIn.length());
	}
}
