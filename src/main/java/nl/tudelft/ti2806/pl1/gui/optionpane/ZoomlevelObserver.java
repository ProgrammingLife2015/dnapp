/**
 * 
 */
package nl.tudelft.ti2806.pl1.gui.optionpane;

/**
 * An observer for the information about the zoomlevel.
 * 
 * @author ChakShun
 * @since 18-6-2015
 * @version 1.0
 */
public interface ZoomlevelObserver {

	/**
	 * Called the view is changed to another zoomlevel.
	 * 
	 * @param totalNodesCount
	 *            The total number of nodes in the original graph.
	 * @param visualNodesCount
	 *            The number of nodes in the visual graph.
	 * @param zoomLevelIndex
	 *            The current zoom level threshold.
	 */
	void update(final int totalNodesCount, final int visualNodesCount, final int zoomLevelIndex);
}
