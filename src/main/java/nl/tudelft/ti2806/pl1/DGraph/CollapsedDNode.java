/**
 * 
 */
package nl.tudelft.ti2806.pl1.DGraph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Keraito
 *
 */
public class CollapsedDNode extends ContentDNode {

	/** The content of the node. */
	private HashMap<String, String> content;

	/** Percentage of unknown nucleotides in the content. */
	private HashMap<String, Double> percUnknown;

	/**
	 * Constructor for the collapsed DNode with content.
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
	public CollapsedDNode(final int idIn, final HashSet<String> sourcesIn,
			final int startIn, final int endIn,
			final HashMap<String, String> contentIn) {
		super(idIn, sourcesIn, startIn, endIn);
		this.content = contentIn;
		this.percUnknown = calcPercUnknownMap(contentIn);
	}

	/**
	 * Returns a hashmap with the percentage unknown nucleotides for each
	 * source.
	 * 
	 * @param contentIn
	 *            Hashmap of the content for each source.
	 * @return Hashmap with the percentages for each source.
	 */
	public final HashMap<String, Double> calcPercUnknownMap(
			final HashMap<String, String> contentIn) {
		HashMap<String, Double> percentagesMap = new HashMap<String, Double>();
		for (String source : contentIn.keySet()) {
			percentagesMap
					.put(source, percentageUnknown(contentIn.get(source)));
		}
		return percentagesMap;
	}

	/** {@inheritDoc} */
	@Override
	public final String showContent() {
		String res = "";
		for (String contentSource : this.getContentMap().keySet()) {
			res += contentSource + " : " + this.getContentMap().get(contentSource)
					+ "\n";
		}
		return res;
	}

	/** {@inheritDoc} */
	@Override
	public final HashMap<String, String> getContentMap() {
		return this.content;
	}

	/** {@inheritDoc} */
	@Override
	public final HashMap<String, Double> getPercUnknownMap() {
		return percUnknown;
	}

}
