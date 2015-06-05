package nl.tudelft.ti2806.pl1.gui.contentpane;

import nl.tudelft.ti2806.pl1.DGraph.DNode;

/**
 * An observer for the selection of nodes in a graph by the user. Will give the
 * observer the corresponding data node of the selected node.
 * 
 * @author Maarten
 * @since 18-5-2015
 * @version 1.0
 */
public interface NodeSelectionObserver {

	/**
	 * Called when a node in the graph is clicked.
	 * 
	 * @param selectedNode
	 *            The node selected in the graph view.
	 */
	void update(DNode selectedNode);

}
