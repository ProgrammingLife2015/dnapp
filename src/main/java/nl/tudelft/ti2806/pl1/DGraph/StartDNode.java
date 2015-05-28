/**
 * 
 */
package nl.tudelft.ti2806.pl1.DGraph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Special DNode data class for the added Start Node.
 * 
 * @author Chak Shun
 *
 */
public class StartDNode extends AbstractDNode {

	/** Text for the special node to show. */
	private String specialText = "START NODE";

	/**
	 * Constructor for the special DNode StartDnode.
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
	public StartDNode(final int idIn, final HashSet<String> sourcesIn,
			final int startIn, final int endIn) {
		super(idIn, sourcesIn, startIn, endIn);
	}

	/** {@inheritDoc} */
	@Override
	public final String showContent() {
		return specialText;
	}

	/** {@inheritDoc} */
	@Override
	public final HashMap<String, String> getContentMap() {
		HashMap<String, String> contentMap = new HashMap<String, String>();
		for (String source : this.getSources()) {
			contentMap.put(source, specialText);
		}
		return contentMap;
	}

	/** {@inheritDoc} */
	@Override
	public final HashMap<String, Double> getPercUnknownMap() {
		HashMap<String, Double> percentagesMap = new HashMap<String, Double>();
		for (String source : this.getSources()) {
			percentagesMap.put(source, 1.0);
		}
		return percentagesMap;
	}

}
