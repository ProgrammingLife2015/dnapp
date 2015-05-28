/**
 * 
 */
package nl.tudelft.ti2806.pl1.DGraph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Basic DNode data class. Subclass of ContentDNode.
 * 
 * @author Chak Shun
 * @since 28-5-2015.
 */
public class BasicDNode extends ContentDNode {

	/** The content of the node. */
	private String content;

	/** Percentage of unknown nucleotides in the content. */
	private double percUnknown;

	/**
	 * Constructor for the basic DNode with content.
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
	public BasicDNode(final int idIn, final HashSet<String> sourcesIn,
			final int startIn, final int endIn, final String contentIn) {
		super(idIn, sourcesIn, startIn, endIn);
		this.content = contentIn;
		this.percUnknown = percentageUnknown(contentIn);
	}

	/** {@inheritDoc} */
	@Override
	public final String showContent() {
		return this.content;
	}

	/** {@inheritDoc} */
	@Override
	public final HashMap<String, String> getContentMap() {
		HashMap<String, String> contentMap = new HashMap<String, String>();
		for (String source : this.getSources()) {
			contentMap.put(source, this.content);
		}
		return contentMap;
	}

	/** {@inheritDoc} */
	@Override
	public final HashMap<String, Double> getPercUnknownMap() {
		HashMap<String, Double> percMap = new HashMap<String, Double>();
		for (String source : this.getSources()) {
			percMap.put(source, this.percUnknown);
		}
		return percMap;
	}

	/**
	 * Returns the percentage nucleotides unknown.
	 * 
	 * @return percentage of nucleotides unknown.
	 */
	public final double getPercUnknown() {
		return percUnknown;
	}

	/**
	 * Getting for only the content string.
	 * 
	 * @return Content string.
	 */
	public final String getContentString() {
		return this.content;
	}

	/**
	 * Set the content of the node.
	 * 
	 * @param newContent
	 *            Sets the content on the reference genome
	 */
	public final void setContent(final String newContent) {
		this.content = newContent;
	}
}
