package nl.tudelft.ti2806.pl1.gui.contentpane;

import org.graphstream.ui.view.View;

/**
 * @author Maarten
 * @since 15-6-2015
 */
public interface GraphChangeObserver {

	/**
	 * @param view
	 *            The view that changed.
	 */
	void update(View view);

}
