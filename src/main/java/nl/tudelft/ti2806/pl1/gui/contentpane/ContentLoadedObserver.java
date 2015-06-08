package nl.tudelft.ti2806.pl1.gui.contentpane;

/**
 * @author Maarten
 * @since 5-6-2015
 */
public interface ContentLoadedObserver {

	/**
	 * Called when a phylotree is loaded.
	 */
	void phyloLoaded();

	/**
	 * Called when a graph is loaded.
	 */
	void graphLoaded();

}
