package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.util.Collection;

/**
 * Observer for the phylopanel selection.
 * 
 * @author Chak Shun
 *
 */
public interface PhyloChosenObserver {

	/**
	 * Update method passing through all the selected genes.
	 * 
	 * @param chosen
	 *            all the genes selected.
	 */
	void update(Collection<String> chosen);
}
