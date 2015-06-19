/**
 * 
 */
package nl.tudelft.ti2806.pl1.geneAnnotation;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator for the ReferenceGene based on start index.
 * 
 * @author Chak Shun
 * @since 2-6-2015
 * */
public class RefGeneCompare implements Comparator<ReferenceGene>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(final ReferenceGene o1, final ReferenceGene o2) {
		if (o1.getStart() > o2.getStart()) {
			return 1;
		}
		return -1;
	}

}
