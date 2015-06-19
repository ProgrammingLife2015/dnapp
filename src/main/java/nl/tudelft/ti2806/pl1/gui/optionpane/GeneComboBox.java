package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 * @author Chak Shun
 * @since 18-6-2015
 */
public class GeneComboBox extends JComboBox<String> {

	/** The serial version UID. */
	private static final long serialVersionUID = 1L;

	/** The list of gene selection observers. */
	private List<GeneSelectionObserver> geneSelectionObservers = new ArrayList<GeneSelectionObserver>();

	/**
	 * Initialize the gene combo box.
	 */
	public GeneComboBox() {
		super(new String[] { " " });
		this.setEditable(true);
		this.addActionListener(new ActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (e.getActionCommand().equals("comboBoxEdited")) {
					JComboBox<String> cb = (JComboBox<String>) e.getSource();
					String newSelection = (String) cb.getSelectedItem();
					notifyNodeSelectionObservers(newSelection);
				}
			}
		});
		AutoCompleteDecorator.decorate(this);
	}

	/**
	 * Register a new observer for the gene selection box.
	 * 
	 * @param gso
	 *            New observer.
	 */
	public final void registerObserver(final GeneSelectionObserver gso) {
		geneSelectionObservers.add(gso);
	}

	/**
	 * Unregister a new observer for the gene selection box.
	 * 
	 * @param gso
	 *            Observer to remove..
	 */
	public final void unRgisterObserver(final GeneSelectionObserver gso) {
		geneSelectionObservers.remove(gso);
	}

	/**
	 * Notify all the gene selection observers of the newly selected gene.
	 * 
	 * @param selectedGene
	 *            Newly selected gene.
	 */
	public final void notifyNodeSelectionObservers(final String selectedGene) {
		for (GeneSelectionObserver gso : geneSelectionObservers) {
			gso.update(selectedGene);
		}
	}

}
