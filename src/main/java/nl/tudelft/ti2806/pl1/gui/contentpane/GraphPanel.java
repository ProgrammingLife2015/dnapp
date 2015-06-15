package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingWorker;

import nl.tudelft.ti2806.pl1.DGraph.ConvertDGraph;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.gui.Event;
import nl.tudelft.ti2806.pl1.gui.ProgressDialog;
import nl.tudelft.ti2806.pl1.gui.ToolBar;
import nl.tudelft.ti2806.pl1.gui.Window;
import nl.tudelft.ti2806.pl1.gui.optionpane.GenomeRow;
import nl.tudelft.ti2806.pl1.gui.optionpane.GenomeTableObserver;
import nl.tudelft.ti2806.pl1.mutation.MutationFinder;
import nl.tudelft.ti2806.pl1.reader.NodePlacer;
import nl.tudelft.ti2806.pl1.reader.Reader;
import nl.tudelft.ti2806.pl1.zoomlevels.PointGraphConverter;
import nl.tudelft.ti2806.pl1.zoomlevels.ZoomlevelCreator;

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
public class GraphPanel extends JSplitPane implements ContentTab {

	/** The serial version UID. */
	private static final long serialVersionUID = -3581428828970208653L;

	/**
	 * The default location for the divider between the graph view and the
	 * content text area.
	 */
	private static final int DEFAULT_DIVIDER_LOC = 300;

	/** The horizontal scroll increment value. */
	private static final int HOR_SCROLL_INCR = 400;

	/** Which zoom level is currently shown. **/
	private int zoomLevel = 0;

	/** How far zoomed in. **/
	private int count = 0;

	/**
	 * Threshold value for first zoom level.
	 */
	private static final int THRESHOLD_1 = 10;

	/**
	 * Threshold value for second zoom level.
	 */
	private static final int THRESHOLD_2 = 20;

	/**
	 * Threshold value for third zoom level.
	 */
	private static final int THRESHOLD_3 = 90;

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
	private NodeContentBox infoPane;

	/** The zoom level creator. */
	private ZoomlevelCreator zlc;

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

		graphPane = new JScrollPane();
		graphPane.setMinimumSize(new Dimension(2, 2));

		infoPane = new NodeContentBox();
		registerObserver(infoPane);

		setTopComponent(graphPane);
		setBottomComponent(infoPane);

		setDividerLocation(DEFAULT_DIVIDER_LOC);
		setResizeWeight(1);

		new GenomeHighlight();
		new ScrollListener(graphPane.getHorizontalScrollBar());
		graphPane.getHorizontalScrollBar().setUnitIncrement(HOR_SCROLL_INCR);
		highlightedGenomes = new HashSet<String>();
	}

	@Override
	public List<JComponent> getToolBarControls() {
		List<JComponent> ret = new ArrayList<JComponent>(2);
		ret.add(ToolBar
				.makeButton("Reload visible part", null, Event.RELOAD_GRAPH,
						"Loads or reloads the part of the graph currently in the view port."));
		ret.add(ToolBar.makeButton("Analyse INDEL", null, new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.out.println(MutationFinder.findDeletionMutations(dgraph));
				System.out.println(MutationFinder
						.findInsertionMutations(dgraph));
			}
		}, "BOE"));
		return ret;
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
			Event.statusBarInfo("Exported graph representation to: " + filePath);
		} catch (IOException e) {
			Event.statusBarError("Error during visual graph export: "
					+ e.getMessage());
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
	 */
	public final boolean loadGraph(final File nodes, final File edges) {
		boolean ret = true;
		final ProgressDialog pd = new ProgressDialog(window, "Importing", true);
		GraphLoadWorker pw = new GraphLoadWorker(nodes, edges, pd);
		pw.execute();
		pd.start();
		try {
			this.graph = pw.get();
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
			ret = false;
		}
		// System.setProperty("org.graphstream.ui.renderer",
		// "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		visualizeGraph(graph);
		return ret;
	}

	/**
	 * @author Maarten
	 * @since 4-6-2015
	 */
	class GraphLoadWorker extends SwingWorker<Graph, Void> {

		/** The files used in the background task. */
		private File nodes, edges;

		/**
		 * Initialize the graph loader.
		 * 
		 * @param nodesIn
		 *            The node file.
		 * @param edgesIn
		 *            The edge file.
		 * @param pd
		 *            The progress bar dialog to end once the task has been
		 *            finished.
		 */
		public GraphLoadWorker(final File nodesIn, final File edgesIn,
				final ProgressDialog pd) {
			this.nodes = nodesIn;
			this.edges = edgesIn;

			this.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(final PropertyChangeEvent evt) {
					if (evt.getPropertyName().equals("state")) {
						if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
							pd.end();
						}
					} else if (evt.getPropertyName().equals("progress")) {
						pd.repaint();
					}
				}
			});
		}

		@Override
		protected Graph doInBackground() throws Exception {
			try {
				dgraph = Reader.read(nodes.getAbsolutePath(),
						edges.getAbsolutePath());
				zlc = new ZoomlevelCreator(dgraph);
				viewSize = NodePlacer.place(dgraph);
				graph = ConvertDGraph.convert(dgraph); // TODO
				analyzeDGraph();
				window.getOptionPanel().fillGenomeList(
						dgraph.getReferences().keySet(), true, true);
			} catch (Exception e) {
				e.printStackTrace();
				Event.statusBarError(e.getMessage());
			}
			return graph;
		}
	}

	/** Performs all the analyze methods on the DGraph. */
	private void analyzeDGraph() {
		dgraph.setPointMutations(PointGraphConverter.getPointMutations(dgraph));
		dgraph.setDeletionMutations(MutationFinder
				.findDeletionMutations(dgraph));
		dgraph.setInsertionmutations(MutationFinder
				.findInsertionMutations(dgraph));
	}

	/**
	 * Takes the virtual (GraphStream) graph and shows it in the panel.
	 * 
	 * @param vGraph
	 *            The visual graph to draw
	 */
	private void visualizeGraph(final Graph vGraph) {
		Viewer viewer = new Viewer(vGraph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.disableAutoLayout();
		view = viewer.addDefaultView(false);
		view.setMinimumSize(viewSize);
		view.setPreferredSize(viewSize);
		view.setMaximumSize(viewSize);

		vp = viewer.newViewerPipe();
		vp.addViewerListener(new NodeClickListener());

		// view.getCamera().setViewPercent(scroll.getZoomPercentage());
		scroll = new Scrolling();
		view.addMouseWheelListener(scroll);
		view.addMouseListener(new ViewMouseListener());

		int scrollval = graphPane.getHorizontalScrollBar().getValue();
		graphPane.setViewportView(view);
		graphPane.getHorizontalScrollBar().setValue(scrollval);

		window.revalidate();
		centerVertical();
		this.graph = vGraph;
		vGraph.addAttribute("ui.stylesheet", "url('stylesheet.css')");
	}

	/**
	 * Vertically centers the scroll pane scroll bar.
	 */
	private void centerVertical() {
		graphPane.getVerticalScrollBar().setValue(
				graphPane.getVerticalScrollBar().getMaximum());
		graphPane.getVerticalScrollBar().setValue(
				graphPane.getVerticalScrollBar().getValue() / 2);
	}

	/**
	 * Register a new node selection observer.
	 * 
	 * @param o
	 *            The observer to add.
	 */
	public final void registerObserver(final NodeSelectionObserver o) {
		observers.add(o);
	}

	/**
	 * Unregistrer a node selection observer.
	 * 
	 * @param o
	 *            The observer to delete.
	 */
	public final void unregisterObserver(final NodeSelectionObserver o) {
		observers.remove(o);
	}

	/**
	 * Notifies the node selection observers.
	 * 
	 * @param selectedNodeIn
	 *            The node clicked on by the user
	 */
	private void notifyObservers(final DNode selectedNodeIn) {
		HashSet<DNode> selected = new HashSet<DNode>();
		selected.add(selectedNodeIn);
		for (NodeSelectionObserver sgo : observers) {
			sgo.update(selected);
		}
	}

	/**
	 * Loads or reloads a specific part of the view panel.
	 * 
	 * @param va
	 *            The view area to (re)load
	 */
	public void loadViewArea(final ViewArea va) {
		ConvertDGraph.convert(dgraph, va);
	}

	/**
	 * Loads or reloads the part of the view panel currently on the screen.
	 * 
	 * @see JScrollPane#getViewport()
	 */
	public void loadCurrentViewArea() {
		loadViewArea(getCurrentViewArea());
	}

	/**
	 * @return The current view area of the scrollable graph pane.
	 */
	public ViewArea getCurrentViewArea() {
		Rectangle visible = graphPane.getViewport().getBounds();
		return new ViewArea(visible.getMinX(), visible.getMaxX());
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
	@SuppressWarnings("unchecked")
	public final void highlight(final String genomeId) {
		for (Node n : graph.getEachNode()) {
			HashSet<Integer> ids = (HashSet<Integer>) n
					.getAttribute("collapsed");
			for (int id : ids) {
				if (dgraph.getDNode(id).getSources().contains(genomeId)) {
					n.setAttribute("ui.class", "highlight");
				}
			}
		}
		// for (DNode n : dgraph.getReferences().get(genomeId)) {
		// graph.getNode(String.valueOf(n.getId()));
		// graph.getNode(String.valueOf(n.getId())).setAttribute("ui.class",
		// "highlight");
		// }
		highlightedGenomes.add(genomeId);
	}

	/**
	 * Unhighlights a genome.
	 * 
	 * @param genomeId
	 *            Id of the genome to be highlighted.
	 */
	@SuppressWarnings("unchecked")
	public final void unHighlight(final String genomeId) {
		highlightedGenomes.remove(genomeId);
		for (Node n : graph.getEachNode()) {
			HashSet<Integer> ids = (HashSet<Integer>) n
					.getAttribute("collapsed");
			boolean contains = false;
			for (int id : ids) {
				for (String source : dgraph.getDNode(id).getSources()) {
					contains = contains || highlightedGenomes.contains(source);
				}
			}
			if (!contains) {
				graph.getNode(n.getId()).setAttribute("ui.class", "common");
			}
		}
		// for (DNode n : dgraph.getReferences().get(genomeId)) {
		// boolean contains = false;
		// for (String source : n.getSources()) {
		// contains = contains || highlightedGenomes.contains(source);
		// }
		// if (!contains) {
		// graph.getNode(String.valueOf(n.getId())).setAttribute(
		// "ui.class", "common");
		// }
		// }
	}

	/**
	 * 
	 * @param newZoomLevel
	 *            The zoom level to apply.
	 */
	public void applyZoomLevel(final int newZoomLevel) {
		int threshold = 0;
		count = 0;
		zoomLevel = newZoomLevel;
		switch (newZoomLevel) {
		case 0:
			visualizeGraph(ConvertDGraph.convert(dgraph));
			return;
		case 1:
			threshold = THRESHOLD_1;
			break;
		case 2:
			threshold = THRESHOLD_2;
			break;
		case 3:
			threshold = THRESHOLD_3;
			break;
		default:
			Event.statusBarError("There is no zoom level further from the current level");
		}
		visualizeGraph(zlc.createGraph(threshold));
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

		/** How far there has to be zoomed in to get to a new zoomlevel. **/
		private static final int NEWLEVEL = 3;

		// /** How far one scroll zooms in. **/
		// private static final double ZOOMPERCENTAGE = 1.1;

		/**
		 * This method decides what to do when the mouse is scrolled.
		 * 
		 * @param e
		 *            MouseWheelEvent for which is being handled.
		 */

		@Override
		public void mouseWheelMoved(final MouseWheelEvent e) {
			int rotation = e.getWheelRotation();
			if (count > NEWLEVEL) {
				zoomLevel++;
				upZoomlevel();
			} else if (count < -NEWLEVEL) {
				zoomLevel--;
				downZoomlevel();
			} else if (rotation > 0) {
				count++;
				// zoomIn(ZOOMPERCENTAGE);
			} else if (rotation < 0) {
				count--;
				// zoomOut(ZOOMPERCENTAGE);
			}
		}

		/**
		 * @return The geographical zoom value.
		 */
		public double getZoomPercentage() {
			return view.getCamera().getViewPercent();
		}

		/**
		 * Resets zoom percentage to 1.
		 */
		public void resetZoom() {
			view.getCamera().setViewPercent(1.0);
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
			Rectangle viewRect = graphPane.getViewport().getViewRect();
			view.getCamera().setViewPercent(
					view.getCamera().getViewPercent() / percentage);
			view.getCamera().setViewCenter(viewRect.getCenterY(),
					view.getCamera().getViewCenter().y,
					view.getCamera().getViewCenter().z);
			// System.out.println("\nIN VP = "
			// + graphPane.getViewport().getViewRect());
			// System.out.println("IN CAMERA = "
			// + view.getCamera().getViewCenter());
		}

		/**
		 * Lets you zoom out a defined amount.
		 * 
		 * @param percentage
		 *            How much you want to zoom.
		 */
		public void zoomOut(final double percentage) {
			count--;
			Rectangle viewRect = graphPane.getViewport().getViewRect();
			view.getCamera().setViewPercent(
					view.getCamera().getViewPercent() * percentage);
			view.getCamera().setViewCenter(viewRect.getCenterY(),
					view.getCamera().getViewCenter().y,
					view.getCamera().getViewCenter().z);
			// System.out.println("\nOUT VP = "
			// + graphPane.getViewport().getViewRect());
			// System.out.println("OUT CAMERA = "
			// + view.getCamera().getViewCenter());
			// System.out.println(graph.getNodeSet());
		}

		/**
		 * Lets you zoom in one level further.
		 */
		public void upZoomlevel() {
			count = 0;
			System.out.println("Zoom level up to = " + zoomLevel);
			Event.statusBarInfo("Zoom level up to = " + zoomLevel);
			applyZoomLevel(zoomLevel);
		}

		/**
		 * Lets you zoom out one level further.
		 */
		public void downZoomlevel() {
			count = 0;
			System.out.println("Zoom level down to = " + zoomLevel);
			Event.statusBarInfo("Zoom level down to = " + zoomLevel);
			applyZoomLevel(zoomLevel);
		}
	}

	/**
	 * A Listener for the scroll bars of a scroll panel.
	 * 
	 * @author Maarten
	 * @since 22-5-2015
	 * @version 1.0
	 */
	static final class ScrollListener implements AdjustmentListener {

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
			window.getOptionPanel().getGenomes().registerObserver(this);
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

		/** {@inheritDoc} */
		@Override
		public void viewClosed(final String viewName) {
		}

		/** {@inheritDoc} */
		@Override
		public void buttonReleased(final String id) {
			Event.statusBarMid(" Selected node: " + id);
			notifyObservers(dgraph.getDNode(Integer.parseInt(id)));
		}

		/** {@inheritDoc} */
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

	/**
	 * Get current zoomlevel.
	 * 
	 * @return current zoomlevel.
	 */
	public int getZoomLevel() {
		return zoomLevel;
	}
}
