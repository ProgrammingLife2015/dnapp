package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

import nl.tudelft.ti2806.pl1.gui.Event;
import nl.tudelft.ti2806.pl1.gui.Window;
import nl.tudelft.ti2806.pl1.reader.NodePlacer;
import nl.tudelft.ti2806.pl1.reader.Reader;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

/**
 * @author Maarten
 *
 */
public class GraphPanel extends JSplitPane implements NodeSelectionObserver {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -3581428828970208653L;

	/**
	 * 
	 */
	private List<NodeSelectionObserver> observers = new ArrayList<NodeSelectionObserver>();

	/**
	 * The window this panel is part of.
	 */
	private Window window;

	/**
	 * The top pane showing the graph.
	 */
	private JScrollPane graphPane;

	/**
	 * The bottom pane showing (for example) node content.
	 */
	private JScrollPane infoPane;

	/**
	 * 
	 */
	private Graph graph;

	/**
	 * 
	 */
	private ViewPanel view;

	/**
	 * 
	 */
	private ViewerPipe vp;

	/**
	 * The text area where (for example) node content will be shown in.
	 */
	private JTextArea text;

	/**
	 * @param w
	 *            The window this panel is part of.
	 */
	public GraphPanel(final Window w) {
		super(JSplitPane.VERTICAL_SPLIT, true);
		this.window = w;
		registerObserver(this);

		graphPane = new JScrollPane();
		graphPane.setMinimumSize(new Dimension(100, 100));

		text = new JTextArea();
		text.setLineWrap(true);
		infoPane = new JScrollPane(text);
		// infoPane.setAutoscrolls(false);

		setTopComponent(graphPane);
		setBottomComponent(infoPane);

		// SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		setDividerLocation(300);
		setResizeWeight(1);
		// }
		// });

	}

	/**
	 * 
	 * @param filePath
	 */
	public final void writeGraph(final String filePath) {
		new Thread(new Runnable() {
			public void run() {
				try {
					graph.write(filePath);
				} catch (IOException e) {
					Event.statusBarError("Writing the graph went wrong ("
							+ e.getMessage() + ")");
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Loads a graph into the content scroll pane.
	 * 
	 * @param nodePath
	 *            The path to the node file.
	 * @param edgePath
	 *            The path to the edge file.
	 * @return true iff the graph was loaded successfully.
	 */
	public final boolean loadGraph(final String nodePath, final String edgePath) {
		boolean ret = true;
		try {
			graph = Reader.read(nodePath, edgePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Event.statusBarError(e.getMessage());
			ret = false;
		}
		graph.addAttribute("ui.stylesheet",
				"url('src/main/resources/stylesheet.css')");
		// System.setProperty("org.graphstream.ui.renderer",
		// "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Viewer viewer = new Viewer(graph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.disableAutoLayout();
		NodePlacer.place(graph, viewer);
		view = viewer.addDefaultView(false);
		view.setPreferredSize(new Dimension(100000, 500));

		vp = viewer.newViewerPipe();
		vp.addViewerListener(new NodeClickListener());

		view.addMouseListener(new ViewMouseListener());
		graphPane.setViewportView(view);
		graphPane.setMinimumSize(new Dimension(50, 50));
		graphPane.setPreferredSize(new Dimension(50, 50));
		window.revalidate();
		return ret;
	}

	/**
	 * 
	 * @param o
	 *            The observer to add.
	 */
	public final void registerObserver(final NodeSelectionObserver o) {
		observers.add(o);
	}

	/**
	 * 
	 * @param o
	 *            The observer to delete.
	 */
	public final void unregisterObserver(final NodeSelectionObserver o) {
		observers.remove(o);
	}

	/**
	 * 
	 * @param selectedNode
	 *            The node clicked on by the user
	 */
	private void notifyObservers(final Node selectedNode) {
		System.out.println(observers);
		for (NodeSelectionObserver sgo : observers) {
			sgo.update(selectedNode);
		}
	}

	/**
	 * TODO debug method.
	 */
	public final void filter() {
		// text.insert(String.valueOf(graph.getNode(1184).getDegree()), text
		// .getText().length());
		graph.getNode(1184).setAttribute("ui.class", "common");
		graph.getNode(1184).setAttribute("ui.color", 1.5d);
		graph.getNode(1183).setAttribute("ui.class", "common");
		graph.getNode(1183).setAttribute("ui.color", 1d);
		graph.getNode(1256).setAttribute("ui.class", "common");
		graph.getNode(1256).setAttribute("ui.color", 2d);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void update(final Node selectedNode) {
		text.setText(selectedNode.getAttribute("content").toString());
		// infoPane.scrollRectToVisible(new Rectangle(0, 0, 1, 1));
		JViewport jv = infoPane.getViewport();
		jv.setViewPosition(new Point(0, 0));
	}

	/**
	 * 
	 * @author Maarten
	 * @since 18-5-2015
	 * @version 1.0
	 *
	 */
	class NodeClickListener implements ViewerListener {

		/**
		 * {@inheritDoc}
		 */
		public void viewClosed(final String viewName) {
		}

		/**
		 * {@inheritDoc}
		 */
		public void buttonReleased(final String id) {
			Event.statusBarMid("Selected node: " + id);
			System.out.println("button released on node " + id);
			notifyObservers(graph.getNode(id));
		}

		/**
		 * {@inheritDoc}
		 */
		public void buttonPushed(final String id) {
			System.out.println("button pushed on node " + id);
		}
	}

	/**
	 * 
	 * @author Maarten
	 * @since 18-5-2015
	 * @version 1.0
	 *
	 */
	class ViewMouseListener implements MouseListener {

		/**
		 * {@inheritDoc}
		 */
		public void mouseReleased(final MouseEvent e) {
			vp.pump();
		}

		/**
		 * {@inheritDoc}
		 */
		public void mousePressed(final MouseEvent e) {
			vp.pump();
		}

		/**
		 * {@inheritDoc}
		 */
		public void mouseExited(final MouseEvent e) {
		}

		/**
		 * {@inheritDoc}
		 */
		public void mouseEntered(final MouseEvent e) {
		}

		/**
		 * {@inheritDoc}
		 */
		public void mouseClicked(final MouseEvent e) {
		}
	}

	@Override
	public final String toString() {
		return this.getClass().getName();
	}

}
