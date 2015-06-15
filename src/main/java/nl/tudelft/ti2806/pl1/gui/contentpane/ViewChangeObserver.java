package nl.tudelft.ti2806.pl1.gui.contentpane;

/**
 * @author Maarten
 * @since 15-6-2015
 */
public interface ViewChangeObserver {

	/**
	 * @param newViewWidth
	 *            The new width of the graph's view panel.
	 */
	void update(final int newViewWidth);

}
