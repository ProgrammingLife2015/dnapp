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
	 * 
	 * @param idIn
	 *            the id to set
	 * @param selectedIn
	 *            whether the genome is selected or not
	 */
	public GenomeRow(final String idIn, final Boolean selectedIn) {
		this.id = idIn;
		this.selected = selectedIn;
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
	public final Object getCol(final int col) throws IndexOutOfBoundsException {
		switch (col) {
		case 0:
			return id;
		case 1:
			return selected;
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
		default:
			throw new IndexOutOfBoundsException("Genome rows have 2 columns.");
		}
	}

	@Override
	public final String toString() {
		return "<Genome[" + id + ",selected=" + selected + "]>";
	}
}
