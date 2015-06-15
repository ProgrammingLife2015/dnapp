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

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

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
	 * Places the nodes of a graphstream graph according to the current view
	 * size.
	 * 
	 * @param graph
	 *            The graph.
	 * @return The new view size.
	 */
	public static Dimension place(final Graph graph) {
		for (Node n : graph.getEachNode()) {
			n.setAttribute("x", 0);
		}
		Node start = graph.getNode("-2");
		Queue<Node> q = new LinkedList<Node>();
		q.add(start);
		nodesAtDepth = new ArrayList<Integer>();
		nodesAtDepth.add(graph.getNodeCount());
		depths(q);
		for (Node n : graph.getEachNode()) {
			int dep = n.getAttribute("x");
			n.setAttribute("x", dep * X_MULTIPLIER);
		}
		width = nodesAtDepth.size() * X_MULTIPLIER;
		height = Collections.max(nodesAtDepth) * Y_MULTIPLIER;
		return new Dimension(width, height);
	}

	/**
	 * For each node, this method finds the highest depth level possible and
	 * sets this in the node attribute 'x' and it updates nodesAtDepth.
	 * 
	 * @param q
	 *            The queue in which we store the unvisited nodes.
	 */
	private static void depths(final Queue<Node> q) {
		int max = 0;
		while (!q.isEmpty()) {
			Node src = q.remove();
			for (Edge edge : src.getEachLeavingEdge()) {
				Node tar = edge.getNode1();
				int odepth = tar.getAttribute("x");
				int ndepth = (int) src.getAttribute("x") + 1;
				// System.out.println("odepth" + odepth);
				// System.out.println("depth" + ndepth);
				if (ndepth > odepth) {
					nodesAtDepth.set(odepth, nodesAtDepth.get(odepth) - 1);
					if (nodesAtDepth.size() > ndepth) {
						nodesAtDepth.set(ndepth, nodesAtDepth.get(ndepth) + 1);
					} else {
						nodesAtDepth.add(1);
					}
					tar.setAttribute("x", ndepth);
					if (ndepth > max) {
						max = ndepth;
					}
					q.add(tar);
				}
			}
		}
	}

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
		if (graph.getNodeCount() == 0) {
			return new Dimension(0, 0);
		}
		nodesAtDepth = new ArrayList<Integer>();
		nodesAtDepth.add(graph.getNodeCount());
		DNode first = graph.getStart();

		Queue<DNode> que = new LinkedList<DNode>();
		que.add(first);
		depthLevel(que);

		width = nodesAtDepth.size() * X_MULTIPLIER;
		height = Collections.max(nodesAtDepth) * Y_MULTIPLIER;

		ArrayList<Integer> hdiff = heightDiff(nodesAtDepth, height);

		for (DNode node : graph.getNodes().values()) {
			node.setX(getWidth(width, node.getDepth(), nodesAtDepth.size()));
			node.setY(getHeight(node.getDepth(), hdiff, nodesAtDepth, height));
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
