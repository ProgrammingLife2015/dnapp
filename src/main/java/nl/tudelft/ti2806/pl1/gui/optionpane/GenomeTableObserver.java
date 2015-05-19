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
	 */
	void update(GenomeRow genomeRow, boolean genomeFilterChanged,
			boolean genomeHighlightChanged);

}
