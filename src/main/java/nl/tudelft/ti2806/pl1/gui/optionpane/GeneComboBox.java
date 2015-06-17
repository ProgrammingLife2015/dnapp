/**
 * 
 */
package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 * @author ChakShun
 *
 */
public class GeneComboBox extends JComboBox<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
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
					System.out.println(newSelection);
				}
			}
		});
		AutoCompleteDecorator.decorate(this);
	}

}
