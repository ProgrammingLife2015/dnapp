package nl.tudelft.ti2806.pl1.gui.optionpane;

/**
 * 
 * @author Maarten
 *
 */
public class GenomeRow {

	/**
	 * The id to set.
	 */
	private String id;

	/**
	 * Whether the genome is selected or not.
	 */
	private boolean selected;

	/**
	 * Whether the genome is highlighted.
	 */
	private boolean highlighted;

	/**
	 * Column number for the id's.
	 */
	public static final int COL_ID = 0;
	/**
	 * Column number for select checkboxes.
	 */
	public static final int COL_SELECT = 1;
	/**
	 * Column number for highlight checkboxes.
	 */
	public static final int COL_HIGHLIGHT = 2;

	/**
	 * 
	 * @param idIn
	 *            the id to set
	 * @param selectedIn
	 *            whether the genome is selected or not
	 * @param highlightedIn
	 *            whether the genome is highlighted or not
	 */
	public GenomeRow(final String idIn, final boolean selectedIn,
			final boolean highlightedIn) {
		this.id = idIn;
		this.selected = selectedIn;
		this.highlighted = highlightedIn;
	}

	/**
	 * @return the highlighted
	 */
	public final boolean isHighlighted() {
		return highlighted;
	}

	/**
	 * @param newHighlighted
	 *            the highlighted to set
	 */
	public final void setHighlighted(final boolean newHighlighted) {
		this.highlighted = newHighlighted;
	}

	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param newId
	 *            the id to set
	 */
	public final void setId(final String newId) {
		this.id = newId;
	}

	/**
	 * @return whether the genome is selected or not
	 */
	public final boolean isSelected() {
		return selected;
	}

	/**
	 * @param newSelected
	 *            whether the genome is selected or not
	 */
	public final void setSelected(final boolean newSelected) {
		this.selected = newSelected;
	}

	/**
	 * 
	 * @param col
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	final Object getCol(final int col) throws IndexOutOfBoundsException {
		switch (col) {
		case COL_ID:
			return id;
		case COL_SELECT:
			return selected;
		case COL_HIGHLIGHT:
			return highlighted;
		default:
			throw new IndexOutOfBoundsException("Genome rows have 2 columns.");
		}
	}

	/**
	 * 
	 * @param col
	 * @param value
	 * @throws IndexOutOfBoundsException
	 */
	public final void set(final int col, final Object value)
			throws IndexOutOfBoundsException {
		switch (col) {
		case 0:
			setId((String) value);
			break;
		case 1:
			setSelected((Boolean) value);
			break;
		case 2:
			setHighlighted((Boolean) value);
			break;
		default:
			throw new IndexOutOfBoundsException("Genome rows have 2 columns.");
		}
	}

	@Override
	public final String toString() {
		return "<Genome[" + id + ",selected=" + selected + ",highlighted="
				+ highlighted + "]>";
	}
}
