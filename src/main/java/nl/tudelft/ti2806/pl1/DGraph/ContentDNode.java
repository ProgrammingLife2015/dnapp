/**
 * 
 */
package nl.tudelft.ti2806.pl1.DGraph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 
 * @author Chak Shun
 * @since 28-5-2015
 */
public abstract class ContentDNode extends AbstractDNode {

	/** The content of the node. */
	private Object content;

	/** Percentage of unknown nucleotides in the content. */
	private double percUnknown;

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
	 * @param contentIn
	 *            Content of the node.
	 */
	public ContentDNode(final int idIn, final HashSet<String> sourcesIn,
			final int startIn, final int endIn, final String contentIn) {
		super(idIn, sourcesIn, startIn, endIn);
		this.content = contentIn;
		this.percUnknown = percentageUnknown(contentIn);
	}

	/**
	 * Returns in a string
	 * 
	 * @return
	 */
	public abstract String showContent();

	/**
	 * Count the amount of unknown nucleotides in the content and returns the
	 * inverse percentage of it.
	 * 
	 * @param contentIn
	 *            The string to be processed.
	 * @return Percentage of the amount of unknown nucleotides.
	 */
	private double percentageUnknown(final String contentIn) {
		int counter = 0;
		for (int i = 0; i < contentIn.length(); i++) {
			if (contentIn.charAt(i) == 'N') {
				counter++;
			}
		}
		return ((double) counter / contentIn.length());
	}

	/** {@inheritDoc} */
	@Override
	public HashMap<String, String> getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public double getPercUnknown() {
		return percUnknown;
	}

	/**
	 * Set the content of the node.
	 * 
	 * @param newContent
	 *            Sets the content on the reference genome
	 */
	public final void setContent(final String newContent) {

	}

}
