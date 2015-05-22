package nl.tudelft.ti2806.pl1.reader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;

/**
 * This class takes care of placing the nodes in their respective place.
 * 
 * @author Marissa, Mark
 *
 */
public final class NodePlacer {

	// TODO
	static int height = 500; // view.getDefaultView().getHeight();
	static int width = 200000; // view.getDefaultView().getWidth();

	/**
	 * empty private constructor.
	 */
	private NodePlacer() {
	}

	/**
	 * The list which keeps the information about how many nodes are located at
	 * depth i.
	 */
	private static ArrayList<Integer> nodesAtDepth;

	/**
	 * The main placer method, this function sets the x and y coordinates of the
	 * nodes.
	 * 
	 * @param graph
	 *            The graph for which the nodes are set
	 * @param view
	 *            The viewer of the graph
	 */
	public static void place(final DGraph graph) {
		if (graph.getNodeCount() == 0) {
			return;
		}

		nodesAtDepth = new ArrayList<Integer>();
		nodesAtDepth.add(graph.getNodeCount());
		DNode first = graph.getStart();

		Queue<DNode> que = new LinkedList<DNode>();
		que.add(first);
		depthLevel(que);

		ArrayList<Integer> hdiff = heightDiff(nodesAtDepth, height);

		for (DNode node : graph.getNodes().values()) {
			node.setX(getWidth(width, node.getDepth(), nodesAtDepth.size()));
			node.setY(getHeight(node.getDepth(), hdiff, nodesAtDepth, height));
		}
	}

	/**
	 * For each node, this method finds the highest depth level possible and
	 * sets this in the node attribute 'depth' and it updates nodesAtDepth.
	 * 
	 * @param que
	 *            The queue in which we store the unvisited edges
	 */
	private static void depthLevel(final Queue<DNode> que) {
		while (!que.isEmpty()) {
			DNode src = que.remove();
			for (DEdge edge : src.getOutEdges()) {
				DNode tar = edge.getEndNode();
				int odepth = tar.getDepth();
				int ndepth = src.getDepth() + 1;
				if (ndepth > odepth) {
					nodesAtDepth.set(odepth, nodesAtDepth.get(odepth) - 1);
					if (nodesAtDepth.size() > ndepth) {
						nodesAtDepth.set(ndepth, nodesAtDepth.get(ndepth) + 1);
					} else {
						nodesAtDepth.add(1);
					}
					tar.setDepth(ndepth);
					que.add(tar);
				}
			}
		}
	}

	/**
	 * This method returns the with location of the node.
	 * 
	 * @param width
	 *            The width of the viewer
	 * @param depth
	 *            The depth of the node
	 * @param maxdepth
	 *            The maximum depth of the nodes in the graph
	 * @return The width location of the node
	 */
	protected static int getWidth(final double width, final double depth,
			final double maxdepth) {
		double wdiff = width / maxdepth;
		return (int) (wdiff * depth);

	}

	/**
	 * This method returns the height of the node.
	 * 
	 * @param depth
	 *            The depth of the node
	 * @param heightdiff
	 *            The difference in height between nodes
	 * @param nodesatdepth
	 *            The amount of nodes at depth i
	 * @param height
	 *            The height of the viewer
	 * @return The height of the node
	 */
	protected static int getHeight(final int depth,
			final ArrayList<Integer> heightdiff,
			final ArrayList<Integer> nodesatdepth, final int height) {
		int hdiff = heightdiff.get(depth);
		int natdepth = nodesatdepth.get(depth);
		// TODO throw error if natdepth <1 ?
		nodesatdepth.set(depth, natdepth - 1);
		return height / 2 - natdepth * hdiff;
	}

	/**
	 * Returns an array list with the height difference for each depth i.
	 * 
	 * @param nodesatdepth
	 *            The amount of nodes at depth i
	 * @param heightofscreen
	 *            The height of the viewer
	 * @return The height difference for each node at depth i
	 */
	protected static ArrayList<Integer> heightDiff(
			final ArrayList<Integer> nodesatdepth, final int heightofscreen) {
		ArrayList<Integer> hdiff = new ArrayList<Integer>(nodesatdepth.size());
		for (int i = 0; i < nodesatdepth.size(); i++) {
			hdiff.add(heightofscreen / (nodesatdepth.get(i) + 1));
		}
		return hdiff;
	}

	/**
	 * @return the height
	 */
	public static int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public static void setHeight(final int height) {
		NodePlacer.height = height;
	}

	/**
	 * @return the width
	 */
	public static int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public static void setWidth(final int width) {
		NodePlacer.width = width;
	}

}
