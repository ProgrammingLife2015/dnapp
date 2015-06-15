package nl.tudelft.ti2806.pl1.gui.contentpane;

/**
 * @author Maarten
 * @since 15-6-2015
 */
public interface GraphScrollObserver {

	/**
	 * @param currentViewArea
	 *            The current view area.
	 */
	void update(final ViewArea currentViewArea);

}
