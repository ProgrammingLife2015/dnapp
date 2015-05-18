package nl.tudelft.ti2806.pl1.gui.contentpane;

import org.graphstream.graph.Node;

/**
 * @author Maarten
 * @since 18-5-2015
 * @version 1.0
 *
 */
public interface NodeSelectionObserver {

	/**
	 * 
	 * @param selectedNode
	 */
	void update(Node selectedNode);

}
