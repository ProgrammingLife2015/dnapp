package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.util.Set;

import nl.tudelft.ti2806.pl1.DGraph.DNode;

import org.graphstream.graph.Node;

/**
 * An observer for the selection of nodes in a graph by the user. Will give the
 * observer the corresponding data node of the selected node.
 * 
 * @author Maarten
 * @since 18-5-2015
 */
public interface NodeSelectionObserver {

	/**
	 * Called when a node in the graph is clicked.
	 * 
	 * @param node
	 *            The selected visual node.
	 * @param innerNodes
	 *            The nodes selected in the graph view.
	 */
	void update(Node node, Set<DNode> innerNodes);

}
