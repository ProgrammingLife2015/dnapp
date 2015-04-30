package graph;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

import reader.Reader;

/**
 * 
 * @author PL1
 *
 */
public final class GraphData {

	/**
	 * 
	 */
	private GraphData() {
	}

	/**
	 * 
	 */
	private static ArrayList<Integer> nodesatdepth;

	/**
	 * 
	 * @param args
	 *            jwz
	 * @throws FileNotFoundException
	 *             when the file to read is not found
	 */
	public static void main(final String[] args) throws FileNotFoundException {
		Graph g = Reader.read("src/main/resources/nodes.txt",
				"src/main/resources/edges.txt");
		nodesatdepth = new ArrayList<Integer>();
		nodesatdepth.add(g.getNodeCount());
		Viewer viewer = g.display();
		// viewer.enableAutoLayout();
		viewer.disableAutoLayout();
		Node first = null;
		for (Node n : g.getNodeSet()) {
			if (first == null) {
				first = n;
			} else if ((Integer) n.getAttribute("start") < (Integer) first
					.getAttribute("start")) {
				first = n;
			}
		}
		Queue<Node> que = new LinkedList<Node>();
		que.add(first);
		depthLevel(que);
		int height = viewer.getDefaultView().getHeight();
		int width = viewer.getDefaultView().getWidth();
		ArrayList<Integer> hdiff = heightDiff(nodesatdepth, height);

		BreadthFirstIterator<Node> it = new BreadthFirstIterator<Node>(first);
		while (it.hasNext()) {
			Node n = it.next();
			// n.getAttribute("depth")
			n.setAttribute(
					"x",
					getDepth(width, (Integer) n.getAttribute("depth"),
							nodesatdepth.size()));
			n.setAttribute(
					"y",
					getHeight((Integer) n.getAttribute("depth"), hdiff,
							nodesatdepth, height));
		}
		// returns height of screen viewer.getDefaultView().getHeight()

	}

	/**
	 * 
	 * @param width
	 * @param depth
	 * @param maxdepth
	 * @return
	 */
	public static int getDepth(final int width, final int depth,
			final int maxdepth) {
		int wdiff = width / maxdepth;
		return wdiff * (depth + 1);

	}

	/**
	 * 
	 * @param depth
	 * @param heightdiff
	 * @param nodesatdepth
	 * @param height
	 * @return
	 */
	public static int getHeight(final int depth,
			final ArrayList<Integer> heightdiff,
			final ArrayList<Integer> nodesatdepth, final int height) {
		int hdiff = heightdiff.get(depth);
		int natdepth = nodesatdepth.get(depth);
		nodesatdepth.set(depth, natdepth - 1);
		return height - natdepth * hdiff;
	}

	/**
	 * 
	 * @param nodesatdepth
	 * @param heightofscreen
	 * @return
	 */
	public static ArrayList<Integer> heightDiff(
			final ArrayList<Integer> nodesatdepth, final int heightofscreen) {
		ArrayList<Integer> hdiff = new ArrayList<Integer>(nodesatdepth.size());
		for (int i = 0; i < nodesatdepth.size(); i++) {
			hdiff.add(heightofscreen / (nodesatdepth.get(i) + 1));
		}
		return hdiff;
	}

	/**
	 * 
	 * @param que
	 */
	public static void depthLevel(final Queue<Node> que) {
		while (!que.isEmpty()) {
			Node src = que.remove();
			Iterable<Edge> itedge = src.getEachLeavingEdge();
			Iterator<Edge> it = itedge.iterator();
			while (it.hasNext()) {
				Edge edg = it.next();
				if (!edg.getNode0().getId().equals(src.getId())) {
					continue;
				}
				Node out = edg.getNode1();
				int odepth = out.getAttribute("depth");
				int ndepth = (Integer) src.getAttribute("depth") + 1;
				if (ndepth > odepth) {
					nodesatdepth.set(odepth, nodesatdepth.get(odepth) - 1);
					if (nodesatdepth.size() > ndepth) {
						nodesatdepth.set(ndepth, nodesatdepth.get(ndepth) + 1);
					} else {
						nodesatdepth.add(1);
					}
					out.setAttribute("depth", ndepth);
					que.add(out);
				}
			}
		}
	}
}