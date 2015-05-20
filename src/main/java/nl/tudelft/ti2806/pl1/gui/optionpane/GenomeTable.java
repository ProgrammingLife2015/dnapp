package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import nl.tudelft.ti2806.pl1.exceptions.InvalidGenomeIdException;

/**
 * 
 * @author Maarten
 *
 */
public class GenomeTable extends JScrollPane {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -2803975406952542688L;

	/**
	 * 
	 */
	private List<GenomeTableObserver> observers = new ArrayList<GenomeTableObserver>();

	/**
	 * The data model of the table.
	 */
	private GenomeTableModel gtm = new GenomeTableModel();

	/**
	 * The table interface.
	 */
	private JTable table = new JTable(gtm);

	/**
	 * 
	 * @param size
	 */
	public GenomeTable(final Dimension size) {
		super();
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		// table.setPreferredScrollableViewportSize(size);
		// table.setFillsViewportHeight(true);
		table.getColumnModel().getColumn(1).setMinWidth(50);
		table.getColumnModel().getColumn(1).setMaxWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getTableHeader().setReorderingAllowed(false);
		table.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(final TableModelEvent e) {
				notifyObservers(e.getColumn() == GenomeRow.COL_SELECT,
						e.getColumn() == GenomeRow.COL_HIGHLIGHT);
			}
		});

		ListSelectionModel lsm = table.getSelectionModel();
		lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lsm.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(final ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					notifyObservers(false, false);
				}
			}
		});

		setViewportView(table);
		fillDebug();
	}

	/**
	 * 
	 * @param id
	 *            The requested genome id.
	 * @return the table genome row with the given id.
	 * @throws InvalidGenomeIdException
	 *             if the requested genome does not exist.
	 */
	public final GenomeRow getGenomeRow(final String id)
			throws InvalidGenomeIdException {
		for (GenomeRow gr : gtm.data) {
			if (gr.getId().equals(id)) {
				return gr;
			}
		}
		throw new InvalidGenomeIdException(id);
	}

	/**
	 * Refills or appends a list of genome rows created from given genome names
	 * to the table with a given default value for the check box column.
	 * 
	 * @param genomeIds
	 *            The list of genome names to convert into genome rows and add
	 *            to the table.
	 * @param empty
	 *            If true, the list will first be emptied before filled with the
	 *            new rows.
	 * @param selected
	 *            The value of the check box for all genome rows created.
	 */
	public final void fill(final List<String> genomeIds, final boolean empty,
			final boolean selected) {
		List<GenomeRow> res = new ArrayList<GenomeRow>();
		for (String id : genomeIds) {
			res.add(new GenomeRow(id, selected, false));
		}
		fill(res, empty);
	}

	/**
	 * 
	 * @param genomeRows
	 *            The list of genome rows to add to the table.
	 * @param empty
	 *            If true, the list will first be emptied before filled with the
	 *            new rows.
	 */
	private void fill(final List<GenomeRow> genomeRows, final boolean empty) {
		if (empty) {
			gtm.data.clear();
		}
		gtm.data.addAll(genomeRows);
		revalidate();
	}

	// /**
	// *
	// * @param index
	// * @return
	// */
	// public GenomeRow getGenomeRow(final int index) {
	// return gtm.data.get(index);
	// }

	/**
	 * TODO delete.
	 */
	public final void fillDebug() {
		fill(Arrays.asList(new GenomeRow("Genome 1", true, false),
				new GenomeRow("Genome 2", false, false), new GenomeRow(
						"Genome 3", false, false), new GenomeRow("Genome 4",
						true, false), new GenomeRow("Genome 5", false, false)),
				true);

		fill(Arrays.asList("GenomeString1t", "GenomeString2t",
				"GenomeString3t", "GenomeString4t"), false, true);
		fill(Arrays.asList("GenomeString5f", "GenomeString6f",
				"GenomeString7f", "GenomeString48f"), false, false);
	}

	/**
	 * 
	 * @param o
	 *            The observer to add.
	 */
	public final void registerObserver(final GenomeTableObserver o) {
		observers.add(o);
	}

	/**
	 * 
	 * @param o
	 *            The observer to delete.
	 */
	public final void unregisterObserver(final GenomeTableObserver o) {
		observers.remove(o);
	}

	/**
	 * Notify genome table observers.
	 * 
	 * @param genomeFilterChanged
	 *            Flag set true if the content of the genome table has changed.
	 *            Specifically: when a genome was checked or unchecked to show.
	 * @param genomeHighlightChanged
	 *            Flag set true if the content of the genome table has changed.
	 *            Specifically: when a genome was checked or unchecked to
	 *            highlight.
	 */
	private void notifyObservers(final boolean genomeFilterChanged,
			final boolean genomeHighlightChanged) {
		for (GenomeTableObserver sgo : observers) {
			sgo.update(gtm.data.get(table.getSelectedRow()),
					genomeFilterChanged, genomeHighlightChanged);
		}
	}

	/**
	 * 
	 * @author Maarten
	 *
	 */
	class GenomeTableModel extends AbstractTableModel {

		/**
		 * The serial version UID.
		 */
		private static final long serialVersionUID = -2345996098819339949L;

		/**
		 * The column headers.
		 */
		private String[] columnNames = { "Genome", "Show", "Highlight" };

		/**
		 * The table content.
		 */
		private List<GenomeRow> data = new ArrayList<GenomeRow>();

		/**
		 * {@inheritDoc}
		 */
		public int getColumnCount() {
			return columnNames.length;
		}

		/**
		 * {@inheritDoc}
		 */
		public int getRowCount() {
			return data.size();
		}

		@Override
		public String getColumnName(final int col) {
			return columnNames[col];
		}

		/**
		 * {@inheritDoc}
		 */
		public Object getValueAt(final int row, final int col) {
			return data.get(row).getCol(col);
		}

		@Override
		public Class<? extends Object> getColumnClass(final int c) {
			return getValueAt(0, c).getClass();
		}

		@Override
		public boolean isCellEditable(final int row, final int col) {
			return (col == 1 || col == 2);
		}

		@Override
		public void setValueAt(final Object value, final int row, final int col) {
			GenomeRow gr = data.get(row);
			gr.set(col, value);
			fireTableCellUpdated(row, col);
		}

	}

}
