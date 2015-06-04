/**
 * 
 */
package nl.tudelft.ti2806.pl1.DGraph;

import java.util.Collection;

import nl.tudelft.ti2806.pl1.gui.contentpane.ViewArea;

/**
 * @author Maarten
 *
 */
public interface DynamicGraph extends DGraphInterface {

	/**
	 * 
	 * @param va
	 *            The view area to get the nodes from.
	 * @return A collection of nodes which are located in the given view area.
	 */
	Collection<DNode> getDNodes(ViewArea va);

}
