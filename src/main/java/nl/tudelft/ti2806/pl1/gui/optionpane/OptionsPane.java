/**
 * 
 */
package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import nl.tudelft.ti2806.pl1.gui.Event;
import nl.tudelft.ti2806.pl1.gui.Window;

/**
 * @author Maarten
 *
 */
public class OptionsPane extends JScrollPane {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -8048134952005779117L;

	/**
	 * The width of the pane.
	 */
	private static final int WIDTH = 220;

	/**
	 * The width of the pane in a dimension object.
	 */
	private static final Dimension SIZE = new Dimension(WIDTH, 100);

	/**
	 * The width of the scrollabe content.
	 */
	private static final int PANE_WIDTH = 200;

	/**
	 * The width of the scrollable content in a dimension object.
	 */
	private static final Dimension PANE_SIZE = new Dimension(PANE_WIDTH, 200);

	/**
	 * The free space between control elements and the side pane borders.
	 */
	private static final int WIDTH_OFFSET = 40;

	/**
	 * The size of the genome list.
	 */
	private static final Dimension GENOME_LIST_SIZE = new Dimension(150, 110);

	/**
	 * 
	 */
	public static final int MAX_CHILD_WIDTH = 180;

	/**
	 * The default insets (free space) values added around every placed control.
	 */
	private static final int HOR_INSETS = 10, VER_INSETS = 5;

	/**
	 * The default insets (free space) added around every placed control.
	 */
	private static final Insets INSETS = new Insets(VER_INSETS, HOR_INSETS,
			VER_INSETS, HOR_INSETS);

	// ***** ***** //

	/**
	 * The window this option pane is part of.
	 */
	private Window window;

	/**
	 * The grid bag constraints used by the layout manager.
	 */
	private GridBagConstraints gbc = new GridBagConstraints();

	// ***** Elements ***** //

	/**
	 * The panel containing the control elements.
	 */
	private JPanel pane = new JPanel(new GridBagLayout());

	// /**
	// * The item list containing the genomes of the graph.
	// */
	// private GenomeList list = new GenomeList();

	/**
	 * 
	 */
	private GenomeTable genomes = new GenomeTable(GENOME_LIST_SIZE);

	/**
	 * @return the genome table
	 */
	public final GenomeTable getGenomes() {
		return genomes;
	}

	/**
	 * The button enabling the user to load a graph into the interface.
	 */
	private JButton btnLoadGraph = makeButton("Load graph", Event.LOAD_FILE,
			"Click to load the default graph.");

	/**
	 * 
	 */
	private SelectedGenomeGroup selectedGenome = new SelectedGenomeGroup(this);

	/**
	 * @param w
	 *            The window this option pane is part of.
	 */
	public OptionsPane(final Window w) {
		this.window = w;
		setBackground(Color.GRAY);
		setPreferredSize(SIZE);
		addControls();
		pane.setAlignmentY(TOP_ALIGNMENT);
		setViewportView(pane);
	}

	/**
	 * Adds the control elements to the option pane.
	 */
	private void addControls() {
		setupConstraints();
		place(btnLoadGraph, 0);

		place(new JLabel("Genomes:"), 1, 0);
		place(genomes);
		place(selectedGenome);

		gbc.weighty = 100;
		place(Box.createGlue());
	}

	/**
	 * Sets up the grid bag constraints.
	 */
	private void setupConstraints() {
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
		// JCheckBox[] cboxes = new JCheckBox[genomeIds.size()];
		// int i = 0;
		// for (String id : genomeIds) {
		// cboxes[i] = makeCheckBox(id, Event.GENOME_SELECT, null);
		// i++;
		// }
		genomes.fill(genomeIds, empty, selected);
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
		pane.revalidate();
	}

	/**
	 * Places a component on a given row in the layout.
	 * 
	 * @param elem
	 *            The element to place.
	 * @param row
	 *            The vertical coordinate in the pane to place the component.
	 */
	private void place(final Component elem, final int row) {
		gbc.gridy = row;
		pane.add(elem, gbc);
		pane.revalidate();
	}

	/**
	 * Places a component on a given row in the layout, adding a given amount of
	 * vertical insets below the element.
	 * 
	 * @param elem
	 *            The element to place.
	 * @param row
	 *            The vertical coordinate in the pane to place the component.
	 * @param belowInset
	 *            The vertical insets below the element.
	 */
	private void place(final Component elem, final int row, final int belowInset) {
		gbc.insets = new Insets(VER_INSETS, HOR_INSETS, belowInset, HOR_INSETS);
		place(elem, row);
		gbc.insets = INSETS;
	}

	/**
	 * 
	 * @param enabled
	 */
	public final void enableBtnLoadGraph(final boolean enabled) {
		btnLoadGraph.setEnabled(enabled);
	}
}
