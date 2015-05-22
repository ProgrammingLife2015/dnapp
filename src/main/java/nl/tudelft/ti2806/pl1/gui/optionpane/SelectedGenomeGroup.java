package nl.tudelft.ti2806.pl1.gui.optionpane;

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
 * @since 18-5-2015
 * @version 1.0
 *
 */
public class SelectedGenomeGroup extends JPanel implements GenomeTableObserver {

	/** The serial version UID. */
	private static final long serialVersionUID = -4851724739205792429L;

	/** The default visible title. */
	private static final String DEFAULT_TITLE = "Selected genome";

	/** The size of the group. */
	private static final Dimension SIZE = new Dimension(
			OptionsPane.MAX_CHILD_WIDTH, 120);

	/** The insets. */
	private static final Insets INSETS = new Insets(2, 5, 2, 5);

	/** The grid bag constraints for the layout manager. */
	private GridBagConstraints gbc = new GridBagConstraints();

	/** The info value labels. */
	private JLabel gID = new JLabel(), gShow = new JLabel(),
			gHigh = new JLabel();

	/**
	 * Initialize the group layout panel.
	 * 
	 * @param op
	 *            The option panel (parent)
	 */
	public SelectedGenomeGroup(final OptionsPane op) {
		super();
		op.getGenomes().registerObserver(this);
		setLayout(new GridBagLayout());
		setMaximumSize(SIZE);
		setAlignmentY(TOP_ALIGNMENT);
		setBorder(BorderFactory.createTitledBorder(DEFAULT_TITLE));
		addComponents();
	}

	/**
	 * 
	 */
	private void addComponents() {
		setupGBC();
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
		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.weightx = 1;
		add(new JLabel("Highlight:"), gbc);
		gbc.gridx = 1;
		gbc.weightx = 10;
		add(gHigh, gbc);
		gbc.weighty = 100;
		add(Box.createVerticalGlue());
	}

	/** Sets up the grid bag constraints. */
	private void setupGBC() {
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc.insets = INSETS;
		gbc.weightx = 1;
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
	public final void update(final GenomeRow genomeRow,
			final boolean genomeFilterChanged,
			final boolean genomeHightlightChanged) {
		gID.setText(genomeRow.getId());
		gShow.setText(String.valueOf(genomeRow.isVisible()));
		gHigh.setText(String.valueOf(genomeRow.isHighlighted()));
	}
}
