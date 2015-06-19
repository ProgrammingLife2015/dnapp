package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.util.Collection;

/**
 * 
 * @author Maarten
 *
 */
public interface GenomeTableObserver {

	/**
	 * 
	 * @param genomeRow
	 *            The currently selected genome row.
	 * @param genomeFilterChanged
	 *            Whether the choice of filtering of the genome has changed.
	 * @param genomeHighlightChanged
	 *            Whether the choice of highlighting of the genome has changed.
	 */
	void update(GenomeRow genomeRow, boolean genomeFilterChanged, boolean genomeHighlightChanged);

	/**
	 * Update the observers for the selected genes in the phylotree.
	 * 
	 * @param chosen
	 *            the genomes selcted in the phylotree.
	 */
	void update(Collection<String> chosen);
}
