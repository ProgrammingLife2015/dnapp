package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import nl.tudelft.ti2806.pl1.DGraph.ConvertDGraph;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.exceptions.InvalidNodePlacementException;
import nl.tudelft.ti2806.pl1.gui.Event;
import nl.tudelft.ti2806.pl1.gui.ProgressDialog;
import nl.tudelft.ti2806.pl1.gui.Window;
import nl.tudelft.ti2806.pl1.gui.optionpane.GenomeRow;
import nl.tudelft.ti2806.pl1.gui.optionpane.GenomeTableObserver;
import nl.tudelft.ti2806.pl1.reader.NodePlacer;
import nl.tudelft.ti2806.pl1.reader.Reader;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

/**
 * @author Maarten
 * 
 */
public class GraphPanel extends JSplitPane implements NodeSelectionObserver {

	/** The serial version UID. */
	private static final long serialVersionUID = -3581428828970208653L;

	/**
	 * The default location for the divider between the graph view and the text
	 * area.
	 */
	private static final int DEFAULT_DIVIDER_LOC = 300;

	/**
	 * The horizontal scroll increment value.
	 */
	private static final int HOR_SCROLL_INCR = 500;

	/**
	 * The list of node selection observers.
	 * 
	 * @see NodeSelectionObserver
	 */
	private List<NodeSelectionObserver> observers = new ArrayList<NodeSelectionObserver>();

	/** The window this panel is part of. */
	private Window window;

	/** The top pane showing the graph. */
	private JScrollPane graphPane;

	/** The bottom pane showing node content. */
	private JScrollPane infoPane;

	/** The graph loaded into the panel. */
	private Graph graph;

	/** The last selected node. */
	private Node selectedNode;

	/** The graph's view panel. */
	private ViewPanel view;

	/** The size of the view panel. */
	private Dimension viewSize;

	/** The graph's view pipe. Used to listen for node click events. */
	private ViewerPipe vp;

	/** The text area where node content will be shown. */
	private JTextArea text;

	/** The scroll behaviour. */
	private Scrolling scroll;

	/** The DGraph data storage. */
	private DGraph dgraph;

	/** A collection of the highlighted genomes. */
	private Set<String> highlightedGenomes;

	/**
	 * Initialize a graph panel.
	 * 
	 * @param w
	 *            The window this panel is part of.
	 */
	public GraphPanel(final Window w) {
		super(JSplitPane.VERTICAL_SPLIT, true);
		this.window = w;
		registerObserver(this);

		graphPane = new JScrollPane();
		graphPane.setMinimumSize(new Dimension(2, 2));

		text = new JTextArea();
		text.setLineWrap(true);
		infoPane = new JScrollPane(text);

		setTopComponent(graphPane);
		setBottomComponent(infoPane);

		setDividerLocation(DEFAULT_DIVIDER_LOC);
		setResizeWeight(1);

		new GenomeHighlight();
		new ScrollListener(graphPane.getHorizontalScrollBar());
		graphPane.getHorizontalScrollBar().setUnitIncrement(HOR_SCROLL_INCR);
		highlightedGenomes = new HashSet<String>();
	}

	/**
	 * Write the visual graph representation to a file.
	 * 
	 * @param filePath
	 *            Target path for exporting the file.
	 */
	public final void writeGraph(final String filePath) {
		try {
			graph.write(filePath);
			Event.statusBarInfo("Exported graph to: " + filePath);
		} catch (IOException e) {
			Event.statusBarError("Writing the graph went wrong ("
					+ e.getMessage() + ")");
			e.printStackTrace();
		}
	}

	/**
	 * Loads a graph into the content scroll pane.
	 * 
	 * @param nodes
	 *            The path to the node file.
	 * @param edges
	 *            The path to the edge file.
	 * @return true iff the graph was loaded successfully.
	 * @throws InvalidNodePlacementException
	 *             Placing node at invalid place.
	 */
	public final boolean loadGraph(final File nodes, final File edges)
			throws InvalidNodePlacementException {
		ProgressDialog pd = new ProgressDialog(window, "Importing graph", true);
		pd.start();
		boolean ret = true;
		try {
			dgraph = Reader.read(nodes.getAbsolutePath(),
					edges.getAbsolutePath());
			viewSize = NodePlacer.place(dgraph);
			graph = ConvertDGraph.convert(dgraph);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ret = false;
			Event.statusBarError(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		graph.addAttribute("ui.stylesheet", "url('stylesheet.css')");
		// System.setProperty("org.graphstream.ui.renderer",
		// "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Viewer viewer = new Viewer(graph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.disableAutoLayout();
		view = viewer.addDefaultView(false);
		view.setPreferredSize(viewSize);

		vp = viewer.newViewerPipe();
		vp.addViewerListener(new NodeClickListener());

		view.addMouseListener(new ViewMouseListener());
		graphPane.setViewportView(view);
		window.revalidate();
		pd.end();
		graphPane.getVerticalScrollBar().setValue(
				graphPane.getVerticalScrollBar().getMaximum());
		graphPane.getVerticalScrollBar().setValue(
				graphPane.getVerticalScrollBar().getValue() / 2);
		scroll = new Scrolling();
		view.addMouseWheelListener(scroll);
		window.optionPanel().getGenomes()
				.fill(dgraph.getReferences().keySet(), false, true);
		return ret;
	}

	/**
	 * 
	 * @param newgraph
	 *            The graph you want to draw.
	 */
	@SuppressWarnings("unused")
	// Fixed in other branch.
	private void drawNewGraph(final Graph newgraph) {
		Viewer viewer = new Viewer(newgraph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.disableAutoLayout();
		view = viewer.addDefaultView(false);
		view.setPreferredSize(viewSize);

		vp = viewer.newViewerPipe();
		vp.addViewerListener(new NodeClickListener());

		view.getCamera().setViewPercent(scroll.getZoomPercentage());

		view.addMouseListener(new ViewMouseListener());
		graphPane.setViewportView(view);
		window.revalidate();
		graphPane.getVerticalScrollBar().setValue(
				graphPane.getVerticalScrollBar().getMaximum());
		graphPane.getVerticalScrollBar().setValue(
				graphPane.getVerticalScrollBar().getValue() / 2);
		scroll = new Scrolling();
		view.addMouseWheelListener(scroll);
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
	 * @param selectedNodeIn
	 *            The node clicked on by the user
	 */
	private void notifyObservers(final DNode selectedNodeIn) {
		for (NodeSelectionObserver sgo : observers) {
			sgo.update(selectedNodeIn);
		}
	}

	/**
	 * {@inheritDoc} Changes to the graph graphics based on the selected node.
	 */
	@Override
	public final void update(final DNode newSelectedNode) {
		text.setText(newSelectedNode.getContent());
		// TODO change strings to constants (still have to decide in which class
		// to define them)

	}

	/**
	 * Assigns the css class for a node being selected and stores the old one.
	 * Also restores the css for the previous selected node.
	 * 
	 * @param newSelectedNode
	 *            Newly selected node.
	 */
	public final void selectNode(final Node newSelectedNode) {
		// Restores the old class of the previous selected node if present.
		if (selectedNode != null) {
			selectedNode.setAttribute("ui.class",
					selectedNode.getAttribute("oldclass"));
			selectedNode.removeAttribute("oldclass");
		}

		// Assigns new selected node and stores old ui.class
		selectedNode = newSelectedNode;
		selectedNode.setAttribute("oldclass",
				selectedNode.getAttribute("ui.class"));
		selectedNode.addAttribute("ui.class", "selected");
	}

	/**
	 * Highlights a genome.
	 * 
	 * @param genomeId
	 *            Id of the genome to be highlighted.
	 */
	public final void highlight(final String genomeId) {
		for (DNode n : dgraph.getReferences().get(genomeId)) {
			graph.getNode(String.valueOf(n.getId())).setAttribute("ui.class",
					"highlight");
		}
		highlightedGenomes.add(genomeId);
	}

	/**
	 * Unhighlights a genome.
	 * 
	 * @param genomeId
	 *            Id of the genome to be highlighted.
	 */
	public final void unHighlight(final String genomeId) {
		highlightedGenomes.remove(genomeId);
		for (DNode n : dgraph.getReferences().get(genomeId)) {
			boolean contains = false;
			for (String source : n.getSources()) {
				contains = contains || highlightedGenomes.contains(source);
			}
			if (!contains) {
				graph.getNode(String.valueOf(n.getId())).setAttribute(
						"ui.class", "common");
			}
		}
	}

	@Override
	public final String toString() {
		return this.getClass().getName();
	}

	/**
	 * Defines scroll behaviour.
	 * 
	 * @author Marissa
	 * @since 27-05-2015
	 */
	class Scrolling implements MouseWheelListener {

		/** How far we're zoomed in on the current level. **/
		private int count = 0;

		/** Which zoomlevel we're on. **/
		private int zoomlevel = 0;

		/** How far there has to be zoomed in to get to a new zoomlevel. **/
		private static final int NEWLEVEL = 10;

		/** How far one scroll zooms in. **/
		private static final double ZOOMPERCENTAGE = 1.1;

		/**
		 * This method decides what to do when the mouse is scrolled.
		 * 
		 * @param e
		 *            MouseWheelEvent for which is being handled.
		 */
		@Override
		public void mouseWheelMoved(final MouseWheelEvent e) {
			int rotation = -1 * e.getWheelRotation();
			if (count > NEWLEVEL) {
				upZoomlevel();
				ZoomSelector.getGraph(zoomlevel);
			} else if (count < -NEWLEVEL) {
				downZoomlevel();
				ZoomSelector.getGraph(zoomlevel);
			} else if (rotation > 0) {
				zoomIn(ZOOMPERCENTAGE);
			} else if (rotation < 0) {
				zoomOut(ZOOMPERCENTAGE);
			}
		}

		/**
		 * 
		 * @return How much the
		 */
		public double getZoomPercentage() {
			return view.getCamera().getViewPercent();
		}

		/**
		 * Resets zoompercentage to 1.
		 */
		public void resetZoom() {
			view.getCamera().setViewPercent(1);
			count = 0;
		}

		/**
		 * Lets you zoom in a defined amount.
		 * 
		 * @param percentage
		 *            How much you want to zoom.
		 */
		public void zoomIn(final double percentage) {
			count++;
			Point3 curcenter = view.getCamera().getViewCenter();
			view.getCamera().setViewPercent(
					view.getCamera().getViewPercent() / percentage);
			view.getCamera().setViewCenter(curcenter.x, curcenter.y,
					curcenter.z);
		}

		/**
		 * Lets you zoom out a defined amount.
		 * 
		 * @param percentage
		 *            How much you want to zoom.
		 */
		public void zoomOut(final double percentage) {
			count--;
			Point3 curcenter = view.getCamera().getViewCenter();
			view.getCamera().setViewPercent(
					view.getCamera().getViewPercent() * percentage);
			view.getCamera().setViewCenter(curcenter.x, curcenter.y,
					curcenter.z);
		}

		/**
		 * Lets you zoom in one level further.
		 */
		public void upZoomlevel() {
			count = 0;
			zoomlevel++;
		}

		/**
		 * Lets you zoom out one level further.
		 */
		public void downZoomlevel() {
			count = 0;
			zoomlevel--;
		}

		/**
		 * Lets you go to a new zoomlevel.
		 * 
		 * @param newzoomlevel
		 *            The new zoomlevel
		 */
		public void setZoomlevel(final int newzoomlevel) {
			count = 0;
			zoomlevel = newzoomlevel;
		}
	}

	/**
	 * 
	 * @author Maarten
	 * @since 22-5-2015
	 * @version 1.0
	 */
	final class ScrollListener implements AdjustmentListener {

		/**
		 * Initialize the scroll listener and make it observe a given scroll
		 * bar.
		 * 
		 * @param scrollBar
		 *            The scroll bar to observe.
		 */
		private ScrollListener(final JScrollBar scrollBar) {
			scrollBar.addAdjustmentListener(this);
		}

		/** {@inheritDoc} */
		@Override
		public void adjustmentValueChanged(final AdjustmentEvent e) {
			// int type = e.getAdjustmentType();
			// if (type == AdjustmentEvent.BLOCK_DECREMENT) {
			// System.out.println("Scroll block decr");
			// } else if (type == AdjustmentEvent.BLOCK_INCREMENT) {
			// System.out.println("Scroll block incr");
			// } else if (type == AdjustmentEvent.UNIT_DECREMENT) {
			// System.out.println("Scroll unit incr");
			// } else if (type == AdjustmentEvent.UNIT_INCREMENT) {
			// System.out.println("Scroll unit incr");
			// } else if (type == AdjustmentEvent.TRACK) {
			// System.out.println("Scroll track");
			// System.out.println("e.getValue() == " + e.getValue());
			// // TODO (use this for genome location plotting?)
			// // seems to always have this adjustment event type :$
			// //
			// http://examples.javacodegeeks.com/desktop-java/awt/event/adjustmentlistener-example
			// }
		}

	}

	/**
	 * Observer class implementing the GenomeTableObserver interface processing
	 * events for filtering genomes.
	 * 
	 * @author ChakShun
	 * @since 18-05-2015
	 */
	class GenomeHighlight implements GenomeTableObserver {

		/**
		 * Constructor in which it adds itself to the observers for the option
		 * panel.
		 */
		public GenomeHighlight() {
			window.optionPanel().getGenomes().registerObserver(this);
		}

		/** {@inheritDoc} */
		@Override
		public void update(final GenomeRow genomeRow,
				final boolean genomeFilterChanged,
				final boolean genomeHighlightChanged) {
			// && genomeRow.isVisible()
			if (genomeHighlightChanged) {
				if (genomeRow.isHighlighted()) {
					highlight(genomeRow.getId());
				} else {
					unHighlight(genomeRow.getId());
				}
			}

		}
	}

	/**
	 * A viewer listener notifying all node selection observers when a node in
	 * the currently loaded graph gets clicked.
	 * 
	 * @see NodeSelectionObserver
	 * 
	 * @author Maarten
	 * @since 18-5-2015
	 * @version 1.0
	 */
	class NodeClickListener implements ViewerListener {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void viewClosed(final String viewName) {
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void buttonReleased(final String id) {
			Event.statusBarMid("Selected node: " + id);
			notifyObservers(dgraph.getDNode(Integer.valueOf(id)));
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void buttonPushed(final String id) {
			selectNode(graph.getNode(id));
		}
	}

	/**
	 * A mouse listener pumping the viewer pipe each time the mouse is pressed
	 * or released. This makes sure that the NodeClickListener receives its
	 * click events immediately when a node is clicked.
	 * 
	 * @see NodeClickListener
	 * @see ViewerPipe
	 * 
	 * @author Maarten
	 * @since 18-5-2015
	 * @version 1.0
	 */
	class ViewMouseListener implements MouseListener {

		/** {@inheritDoc} */
		@Override
		public void mouseReleased(final MouseEvent e) {
			vp.pump();
		}

		/** {@inheritDoc} */
		@Override
		public void mousePressed(final MouseEvent e) {
			vp.pump();
		}

		/** {@inheritDoc} */
		@Override
		public void mouseExited(final MouseEvent e) {
		}

		/** {@inheritDoc} */
		@Override
		public void mouseEntered(final MouseEvent e) {
		}

		/** {@inheritDoc} */
		@Override
		public void mouseClicked(final MouseEvent e) {
		}

	}
}
