package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.wordpress.tips4java.ScrollablePanel;
import com.wordpress.tips4java.ScrollablePanel.ScrollableSizeHint;

import nl.tudelft.ti2806.pl1.gui.Event;
import nl.tudelft.ti2806.pl1.gui.Window;

/**
 * @author Maarten
 *
 */
public class OptionsPane extends JScrollPane {

	/** The serial version UID. */
	private static final long serialVersionUID = -8048134952005779117L;

	/** The width of the container. */
	private static final int WIDTH = 220;

	/** The width of the pane in a dimension object. */
	private static final Dimension SIZE = new Dimension(WIDTH,
			Integer.MAX_VALUE);

	/**
	 * 
	 */
	private static final int W_OFFSET = 20;

	/**
	 * 
	 */
	private static final Dimension PANE_SIZE = new Dimension(WIDTH - W_OFFSET,
			Integer.MAX_VALUE);

	/**
	 * 
	 */
	public static final int MAX_CHILD_WIDTH = 200;

	/**
	 * 
	 */
	private static final int HOR_INSETS = 10, VER_INSETS = 5;

	/**
	 * 
	 */
	private static final Insets INSETS = new Insets(VER_INSETS, HOR_INSETS,
			VER_INSETS, HOR_INSETS);

	// ***** Fields ***** //

	/**
	 * The window this option pane is part of.
	 */
	private Window window;

	// ***** Elements ***** //

	/** The panel containing the control elements. */
	private ScrollablePanel pane;

	/** */
	private GridBagLayout gridBagLayout;

	/** The grid bag constraints used by the layout manager. */
	private GridBagConstraints gbc;

	/** The genome table. */
	private GenomeTable tblGenomes;

	/**
	 * @return the genome table
	 */
	public final GenomeTable getGenomes() {
		return tblGenomes;
	}

	/**
	 * The group of elements showing information about a selected genome in the
	 * genome table.
	 */
	private SelectedGenomeGroup grpSelectedGenome;

	/** The group of elements showing information about a selected node. */
	private SelectedNodeGroup grpSelectedNode;

	/**
	 * @return the selected node group
	 */
	public final SelectedNodeGroup getSelectedNodeGroup() {
		return grpSelectedNode;
	}

	/**
	 * Initialize the option pane.
	 * 
	 * @param w
	 *            The window this option pane is part of.
	 */
	public OptionsPane(final Window w) {
		this.window = w;
		this.gridBagLayout = new GridBagLayout();
		this.gbc = new GridBagConstraints();

		// Scrollable

		this.pane = new ScrollablePanel(gridBagLayout);
		pane.setAlignmentY(TOP_ALIGNMENT);
		pane.setScrollableWidth(ScrollableSizeHint.FIT);
		pane.setScrollableHeight(ScrollableSizeHint.STRETCH);

		this.tblGenomes = new GenomeTable(MAX_CHILD_WIDTH);
		this.grpSelectedNode = new SelectedNodeGroup(this);
		this.grpSelectedGenome = new SelectedGenomeGroup(this);

		// setMinimumSize(SIZE);
		// setMaximumSize(SIZE);

		setPreferredSize(SIZE);
		addControls();

		setViewportView(pane);
	}

	/**
	 * Method called after all window components are initialized.
	 * 
	 * @see Window#Window()
	 */
	public final void componentsLoaded() {
		window.content().getGraphPanel().registerObserver(grpSelectedNode);
	}

	/**
	 * Adds the control elements to the option pane.
	 */
	private void addControls() {
		setupConstraints();

		place(new JLabel("Genomes:"), 0);
		place(tblGenomes);
		place(grpSelectedGenome);
		place(grpSelectedNode);

		gbc.weighty = 100;
		place(Box.createGlue());
	}

	/**
	 * Sets up the grid bag constraints.
	 */
	private void setupConstraints() {
		// gridBagLayout.rowWeights = new double[] { 1.0 };
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		// gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = INSETS;
	}

	/**
	 * Creates a list of check box items based on a list of genome names and
	 * adds them to the genomes list.
	 * 
	 * @param genomeIds
	 *            A list of genome identifiers/names
	 * @param empty
	 *            If true, the list will first be emptied before filled with the
	 *            new rows.
	 * @param selected
	 *            The value of the check box for all genome rows created.
	 */
	public final void fillGenomeList(final List<String> genomeIds,
			final boolean empty, final boolean selected) {
		tblGenomes.fill(genomeIds, empty, selected);
	}

	/**
	 * Creates a button element.
	 * 
	 * @param text
	 *            File name of the image icon.
	 * @param action
	 *            Action id.
	 * @param toolTipText
	 *            Tool tip text.
	 * 
	 * @see Event
	 * 
	 * @return The button created.
	 */
	private JButton makeButton(final String text, final Event action,
			final String toolTipText) {
		JButton button = new JButton();
		button.setText(text);
		button.setToolTipText(toolTipText);
		button.addActionListener(action);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		return button;
	}

	/**
	 * Creates a check box element.
	 * 
	 * @param text
	 *            Text to show.
	 * @param action
	 *            Action id.
	 * @param toolTipText
	 *            Tool tip text.
	 * 
	 * @see Event
	 * 
	 * @return The button created.
	 */
	private JCheckBox makeCheckBox(final String text, final Event action,
			final String toolTipText) {
		JCheckBox cbox = new JCheckBox();
		cbox.setText(text);
		cbox.setToolTipText(toolTipText);
		cbox.addActionListener(action);
		return cbox;
	}

	/**
	 * Places a component on the next empty spot in the layout.
	 * 
	 * @param elem
	 *            The element to place.
	 */
	private void place(final Component elem) {
		gbc.gridy = GridBagConstraints.RELATIVE;
		pane.add(elem, gbc);
		// pane.revalidate();
	}

	/**
	 * Places a component on a given row in the layout, adding a given amount of
	 * vertical insets below the element.
	 * 
	 * @param elem
	 *            The element to place.
	 * @param belowInset
	 *            The vertical insets below the element.
	 */
	private void place(final Component elem, final int belowInset) {
		gbc.insets = new Insets(VER_INSETS, HOR_INSETS, belowInset, HOR_INSETS);
		place(elem);
		gbc.insets = INSETS;
	}

	@Override
	public final String toString() {
		return this.getClass().toString();
	}

}
