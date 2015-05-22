package nl.tudelft.ti2806.pl1.gui.optionpane;

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
	void update(GenomeRow genomeRow, boolean genomeFilterChanged,
			boolean genomeHighlightChanged);

}
