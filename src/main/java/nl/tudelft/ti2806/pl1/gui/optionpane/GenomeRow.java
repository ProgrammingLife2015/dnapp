package nl.tudelft.ti2806.pl1.gui.optionpane;

/**
 * @author Maarten
 */
public class GenomeRow {

	/** The genome id. */
	private String id;

	/** Whether the genome is selected or not. */
	private boolean visible;

	/** Whether the genome is highlighted. */
	private boolean highlighted;

	/** Column number for the id's. */
	public static final int COL_ID = 0;

	/** Column number for select checkboxes. */
	public static final int COL_SHOW = -1;

	/** Column number for highlight checkboxes. */
	public static final int COL_HIGHLIGHT = 1;

	/**
	 * Initialize the genome row.
	 * 
	 * @param idIn
	 *            the id to set
	 * @param visibleIn
	 *            whether the genome is shown or not
	 * @param highlightedIn
	 *            whether the genome is highlighted or not
	 */
	public GenomeRow(final String idIn, final boolean visibleIn, final boolean highlightedIn) {
		this.id = idIn;
		this.visible = visibleIn;
		this.highlighted = highlightedIn;
	}

	/**
	 * @return the genome id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param newId
	 *            the genome id to set
	 */
	public final void setId(final String newId) {
		this.id = newId;
	}

	/**
	 * @return whether the genome is selected or not.
	 */
	public final boolean isVisible() {
		return visible;
	}

	/**
	 * @param newSelected
	 *            whether the genome is now selected or not.
	 */
	public final void setVisible(final boolean newSelected) {
		this.visible = newSelected;
	}

	/**
	 * @return whether the genome is highlighted.
	 */
	public final boolean isHighlighted() {
		return highlighted;
	}

	/**
	 * @param newHighlighted
	 *            whether the genome is now highlighted or not.
	 */
	public final void setHighlighted(final boolean newHighlighted) {
		this.highlighted = newHighlighted;
	}

	/**
	 * 
	 * @param col
	 *            The column index.
	 * @return The value of a given column index.
	 * @throws IndexOutOfBoundsException
	 *             when the column index is out of bounds.
	 */
	final Object getCol(final int col) throws IndexOutOfBoundsException {
		switch (col) {
		case COL_ID:
			return id;
			// case COL_SHOW:
			// return visible;
		case COL_HIGHLIGHT:
			return highlighted;
		default:
			throw new IndexOutOfBoundsException("Genome rows have 2 columns.");
		}
	}

	/**
	 * Sets the value of a given column.
	 * 
	 * @param col
	 *            The column index.
	 * @param value
	 *            The value to set.
	 * @throws IndexOutOfBoundsException
	 *             when the column index is out of bounds.
	 */
	public final void set(final int col, final Object value) throws IndexOutOfBoundsException {
		switch (col) {
		case COL_ID:
			setId((String) value);
			break;
		// case 1:
		// setVisible((Boolean) value);
		// break;
		case COL_HIGHLIGHT:
			setHighlighted((Boolean) value);
			break;
		default:
			throw new IndexOutOfBoundsException("Genome rows have 2 columns.");
		}
	}

	@Override
	public final String toString() {
		return "<Genome[" + id + ",visible=" + visible + ",highlighted=" + highlighted + "]>";
	}
}
