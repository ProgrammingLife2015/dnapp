package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import javax.swing.DefaultComboBoxModel;
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
		this.setModel(new DefaultComboBoxModel<String>(new String[] {}));
		this.setEditable(true);
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (e.getActionCommand().equals("comboBoxEdited")) {
					notifyNodeSelectionObservers((String) getSelectedItem());
				}
			}
		});
		AutoCompleteDecorator.decorate(this);
	}

	@Override
	public void addItem(final String item) {
		if (((DefaultComboBoxModel<String>) getModel()).getIndexOf(item) < 0) {
			super.addItem(item);
		}
	}

	/**
	 * Replaces the combo box model by a new one containing all the items in the
	 * given list.
	 * 
	 * @param items
	 *            The list of gene names to add.
	 */
	public void setList(final SortedSet<String> items) {
		items.add("");
		setModel(new DefaultComboBoxModel<String>(
				items.toArray(new String[] {})));
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
	 * Unregister an observer for the gene selection box.
	 * 
	 * @param gso
	 *            The observer to remove..
	 */
	public final void deleteObserver(final GeneSelectionObserver gso) {
		geneSelectionObservers.remove(gso);
	}

	/**
	 * Notify all the gene selection observers of the newly selected gene.
	 * 
	 * @param selectedGene
	 *            The name of the selected gene.
	 */
	public final void notifyNodeSelectionObservers(final String selectedGene) {
		for (GeneSelectionObserver gso : geneSelectionObservers) {
			gso.update(selectedGene);
		}
	}

}
