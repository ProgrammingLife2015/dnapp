/**
 * 
 */
package nl.tudelft.ti2806.pl1.gui.optionpane;

/**
 * An observer for the selection of a gene by the user. Will give the observer
 * the corresponding data node of the selected gene.
 * 
 * @author ChakShun
 * @since 17-6-2015
 * @version 1.0
 */
public interface GeneSelectionObserver {

	/**
	 * Called when a gene in the combo box is chosen.
	 * 
	 * @param selectedGene
	 *            The gene selected by the user.
	 */
	void update(String selectedGene);
}
