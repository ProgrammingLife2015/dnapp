package nl.tudelft.ti2806.pl1.reader;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.exceptions.InvalidNodePlacementException;

/**
 * This class calculates where to place the nodes.
 * 
 * @author Marissa, Mark
 *
 */
public final class NodePlacer {

	/** The distance between each node. */
	private static final int X_MULTIPLIER = 30, Y_MULTIPLIER = 30;

	/** Vertical space over which we will spread the nodes. */
	private static int height;

	/** Horizontal space over which we will spread the nodes. */
	private static int width;

	/**
	 * Empty private constructor.
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
	 *            The DGraph for which the Nodes are set
	 * @throws InvalidNodePlacementException
	 *             Placing node at invalid place.
	 * @return The dimensions of the graph
	 */
	public static Dimension place(final DGraph graph)
			throws InvalidNodePlacementException {
		if (graph.getNodes().size() == 0) {
			return new Dimension(0, 0);
		}
		nodesAtDepth = new ArrayList<Integer>();
		nodesAtDepth.add(graph.getNodes().size());
		DNode first = graph.getDNode(graph.getStart());

		Queue<DNode> que = new LinkedList<DNode>();
		que.add(first);
		depthLevel(graph, que);

		width = nodesAtDepth.size() * X_MULTIPLIER;
		height = Collections.max(nodesAtDepth) * Y_MULTIPLIER;

		ArrayList<Integer> hdiff = heightDiff(nodesAtDepth, height);

		for (DNode node : graph.getDNodes()) {
			// TODO maybe think of another way to do this then set property
			// method
			graph.setProperty(graph.getNode(node.getId()), "y",
					getWidth(width, node.getDepth(), nodesAtDepth.size()));
			graph.setProperty(graph.getNode(node.getId()), "x",
					getHeight(node.getDepth(), hdiff, nodesAtDepth, height));
		}
		return new Dimension(width, height);
	}

	/**
	 * For each node, this method finds the highest depth level possible and
	 * sets this in the node attribute 'depth' and it updates nodesAtDepth.
	 * 
	 * @param que
	 *            The queue in which we store the unvisited edges
	 */
	private static void depthLevel(final DGraph graph, final Queue<DNode> que) {
		while (!que.isEmpty()) {
			DNode src = que.remove();
			for (DEdge edge : src.getOutEdges()) {
				DNode tar = graph.getDNode(edge.getEndNode());
				int odepth = tar.getDepth();
				int ndepth = src.getDepth() + 1;
				if (ndepth > odepth) {
					nodesAtDepth.set(odepth, nodesAtDepth.get(odepth) - 1);
					if (nodesAtDepth.size() > ndepth) {
						nodesAtDepth.set(ndepth, nodesAtDepth.get(ndepth) + 1);
					} else {
						nodesAtDepth.add(1);
					}
					// TODO maybe think of another way to do this then set
					// property method
					graph.setProperty(graph.getNode(tar.getId()), "depth",
							ndepth);
					if (!que.contains(tar)) {
						que.add(tar);
					}
				}
			}
		}
	}

	/**
	 * This method returns the width location of the node.
	 * 
	 * @param screenwidth
	 *            The width of the viewer
	 * @param nodedepth
	 *            The depth of the node
	 * @param maxdepth
	 *            The maximum depth of the nodes in the graph
	 * @return The width location of the node
	 */
	protected static int getWidth(final double screenwidth,
			final double nodedepth, final double maxdepth) {
		double wdiff = screenwidth / maxdepth;
		return (int) (wdiff * nodedepth);

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
	 * @param screenheight
	 *            The height of the viewer
	 * @return The height of the node
	 * @throws InvalidNodePlacementException
	 *             Placing node at invalid place.
	 */
	protected static int getHeight(final int depth,
			final ArrayList<Integer> heightdiff,
			final ArrayList<Integer> nodesatdepth, final int screenheight)
			throws InvalidNodePlacementException {
		int hdiff = heightdiff.get(depth);
		int natdepth = nodesatdepth.get(depth);
		if (natdepth >= 1) {
			nodesatdepth.set(depth, natdepth - 1);
			return screenheight / 2 - natdepth * hdiff;
		} else {
			throw new InvalidNodePlacementException();
		}
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
	 * @param newheight
	 *            the height to set
	 */
	public static void setHeight(final int newheight) {
		NodePlacer.height = newheight;
	}

	/**
	 * @return the width
	 */
	public static int getWidth() {
		return width;
	}

	/**
	 * @param newwidth
	 *            the width to set
	 */
	public static void setWidth(final int newwidth) {
		NodePlacer.width = newwidth;
	}

}
