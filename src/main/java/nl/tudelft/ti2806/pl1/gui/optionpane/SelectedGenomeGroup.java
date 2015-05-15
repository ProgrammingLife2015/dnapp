/**
 * 
 */
package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Maarten
 *
 */
public class SelectedGenomeGroup extends JPanel implements GenomeTableObserver {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -4851724739205792429L;

	/**
	 * The default visible title.
	 */
	private static final String DEFAULT_TITLE = "Selected genome";

	/**
	 * 
	 */
	private static final Dimension SIZE = new Dimension(
			OptionsPane.MAX_CHILD_WIDTH, 100);

	/**
	 * 
	 */
	private static final Dimension E_SIZE = new Dimension(
			OptionsPane.MAX_CHILD_WIDTH - 20, 20);

	/**
	 * 
	 */
	private static final Insets INSETS = new Insets(2, 5, 2, 5);

	/**
	 * The option pane this group is part of.
	 */
	private OptionsPane parent;

	/**
	 * The grid bag constraints for the layout manager.
	 */
	private GridBagConstraints gbc = new GridBagConstraints();

	/**
	 * TODO .
	 */
	private JLabel gID = new JLabel(), gShow = new JLabel();

	/**
	 * Initialize the group layout panel.
	 * 
	 * @param op
	 *            The option panel (parent)
	 */
	public SelectedGenomeGroup(final OptionsPane op) {
		super();
		this.parent = op;
		parent.getGenomes().registerObserver(this);
		setLayout(new GridBagLayout());
		setupGBC();
		setFixedSize(this, SIZE);
		setBorder(BorderFactory.createTitledBorder(DEFAULT_TITLE));
		// setFixedSize(temp, E_SIZE);
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.weightx = 1;
		add(new JLabel("ID:"), gbc);
		gbc.gridx = 1;
		gbc.weightx = 10;
		add(gID, gbc);
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.weightx = 1;
		add(new JLabel("Show:"), gbc);
		gbc.gridx = 1;
		gbc.weightx = 10;
		add(gShow, gbc);
		gbc.weighty = 100;
		add(Box.createVerticalGlue());
	}

	/**
	 * 
	 * @param c
	 * @param size
	 */
	private void setFixedSize(final Component c, final Dimension size) {
		c.setPreferredSize(size);
		c.setMinimumSize(size);
		c.setMaximumSize(size);
	}

	/**
	 * 
	 */
	private void setupGBC() {
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc.insets = INSETS;
		gbc.weightx = 0;
		gbc.weighty = 1;
	}

	/**
	 * Sets the visible title for the grouping.
	 * 
	 * @param newTitle
	 *            The new title.
	 */
	public final void setTitle(final String newTitle) {
		setBorder(BorderFactory.createTitledBorder(newTitle));
	}

	/**
	 * {@inheritDoc}
	 */
	public final void update(final GenomeRow genomeRow) {
		gID.setText(genomeRow.getId());
		gShow.setText(genomeRow.getCol(1).toString());
	}

}
