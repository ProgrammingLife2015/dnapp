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
	 * @param percCollapsed
	 *            percentage collapsed nodes in the visual graph.
	 * @param threshold
	 *            Current threshold of the graph.
	 */
	void update(final double percCollapsed, final int threshold);
}
