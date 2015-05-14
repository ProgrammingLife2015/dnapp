package nl.tudelft.ti2806.pl1.reader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

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
	public static void place(final Graph graph, final Viewer view) {
		if (graph.getNodeCount() == 0) {
			return;
		}

		nodesAtDepth = new ArrayList<Integer>();
		nodesAtDepth.add(graph.getNodeCount());
		view.disableAutoLayout(); // Diasble the autolayout (again) just to be
									// sure
		Node first = getStartNode(graph);

		Queue<Node> que = new LinkedList<Node>();
		que.add(first);
		depthLevel(que);

		ArrayList<Integer> hdiff = heightDiff(nodesAtDepth, height);

		BreadthFirstIterator<Node> it = new BreadthFirstIterator<Node>(first);
		while (it.hasNext()) {
			Node node = it.next();
			node.setAttribute(
					"x",
					getWidth(width, ((Integer) node.getAttribute("depth")),
							nodesAtDepth.size()));

			node.setAttribute(
					"y",
					getHeight(((Integer) node.getAttribute("depth")), hdiff,
							nodesAtDepth, height));
		}
	}

	/**
	 * This function returns the starting node by looking for the lowest 'start'
	 * attribute.
	 * 
	 * @param graph
	 *            The graph which will be visualized
	 * @return The staring node of the graph
	 */
	private static Node getStartNode(final Graph graph) {
		Node first = null;
		for (Node n : graph.getNodeSet()) {
			if (first == null) {
				first = n;
			} else if (((Integer) n.getAttribute("start")) < ((Integer) first
					.getAttribute("start"))) {
				first = n;
			}
		}
		return first;
	}

	/**
	 * For each node, this method finds the highest depth level possible and
	 * sets this in the node attribute 'depth' and it updates nodesAtDepth.
	 * 
	 * @param que
	 *            The queue in which we store the unvisited edges
	 */
	private static void depthLevel(final Queue<Node> que) {
		while (!que.isEmpty()) {
			Node src = que.remove();
			Iterable<Edge> itedge = src.getEachLeavingEdge();
			Iterator<Edge> it = itedge.iterator();
			while (it.hasNext()) {
				Edge edg = it.next();
				if (!edg.getSourceNode().getId().equals(src.getId())) {
					// This is done because some strange behaviour occurred
					// where
					// the source node of the edge
					// was not equal to the source node
					continue;
				}
				Node out = edg.getTargetNode();
				int odepth = out.getAttribute("depth");
				int ndepth = (Integer) src.getAttribute("depth") + 1;
				if (ndepth > odepth) {
					nodesAtDepth.set(odepth, nodesAtDepth.get(odepth) - 1);
					if (nodesAtDepth.size() > ndepth) {
						nodesAtDepth.set(ndepth, nodesAtDepth.get(ndepth) + 1);
					} else {
						nodesAtDepth.add(1);
					}
					out.setAttribute("depth", ndepth);
					que.add(out);
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
	protected static double getWidth(final double width, final double depth,
			final double maxdepth) {
		double wdiff = width / maxdepth;
		return wdiff * depth;

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
}
