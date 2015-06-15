package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
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
import javax.swing.JPanel;
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
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

/**
 * @author Maarten
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

	/** Which zoom level is currently shown. **/
	private int zoomLevel = 0;

	/** The zoom level thresholds. */
	private int[] thresholds = { THRESHOLD_1, THRESHOLD_2, THRESHOLD_3 };

	/**
	 * The list of node selection observers.
	 * 
	 * @see NodeSelectionObserver
	 */
	private List<NodeSelectionObserver> nodeSelectionObservers = new ArrayList<NodeSelectionObserver>();

	/**
	 * The list of graph scroll observers.
	 * 
	 * @see GraphScrollObserver
	 */
	private List<GraphScrollObserver> graphScrollObservers = new ArrayList<GraphScrollObserver>();

	/**
	 * The list of view change observers.
	 * 
	 * @see ViewChangeObserver
	 */
	private List<ViewChangeObserver> viewChangeObservers = new ArrayList<ViewChangeObserver>();

	/** The window this panel is part of. */
	private Window window;

	/** The top pane showing the graph. */
	private JScrollPane graphPane;

	/** The graph loaded into the panel. */
	private Graph graph;

	/** The graph's view panel. */
	private ViewPanel view;

	/** The size of the view panel. */
	private Dimension viewSize;

	/** The graph's view pipe. Used to listen for node click events. */
	private ViewerPipe vp;

	/** The info pane below the graph. */
	private JPanel infoPane;

	/** The minimap shown between the node content pane and the graph. */
	private Minimap minimap;

	/** The text area where node content will be shown. */
	private NodeContentBox nodeContentPane;

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

		infoPane = new JPanel(new BorderLayout());

		minimap = new Minimap();
		registerObserver((GraphScrollObserver) minimap);
		registerObserver((ViewChangeObserver) minimap);
		infoPane.add(minimap, BorderLayout.NORTH);

		nodeContentPane = new NodeContentBox();
		registerObserver(nodeContentPane);
		infoPane.add(nodeContentPane, BorderLayout.CENTER);

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
				graph = ConvertDGraph.convert(dgraph);
				analyzeDGraph();
				window.getOptionPanel().fillGenomeList(
						dgraph.getReferencesSet(), true, true);
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
		dgraph.setInsertionMutations(MutationFinder
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
		view = new DefaultView(viewer, Viewer.DEFAULT_VIEW_ID,
				Viewer.newGraphRenderer()) {

			/** The serial version UID. */
			private static final long serialVersionUID = 4902528839853178375L;

			@Override
			public void paintComponent(final java.awt.Graphics g) {
				super.paintComponent(g);
			}
		};
		viewer.addView(view);
		view.setMinimumSize(viewSize);
		view.setPreferredSize(viewSize);
		view.setMaximumSize(viewSize);

		vp = viewer.newViewerPipe();
		vp.addViewerListener(new NodeClickListener());

		scroll = new Scrolling();
		view.addMouseWheelListener(scroll);
		view.addMouseListener(new ViewMouseListener());

		int scrollval = graphPane.getHorizontalScrollBar().getValue();
		graphPane.setViewportView(view);
		graphPane.getHorizontalScrollBar().setValue(scrollval);

		this.graph = vGraph;
		vGraph.addAttribute("ui.stylesheet", "url('stylesheet.css')");
		window.revalidate();
		centerVertical();

		notifyGraphScrollObservers();
		notifyViewChangeObservers();
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
	 * @param nso
	 *            The observer to add.
	 */
	public final void registerObserver(final NodeSelectionObserver nso) {
		nodeSelectionObservers.add(nso);
	}

	/**
	 * Register a new graph scroll observer.
	 * 
	 * @param gso
	 *            The observer to add.
	 */
	public final void registerObserver(final GraphScrollObserver gso) {
		graphScrollObservers.add(gso);
	}

	/**
	 * Register a new view change observer.
	 * 
	 * @param vco
	 *            The observer to add.
	 */
	public final void registerObserver(final ViewChangeObserver vco) {
		viewChangeObservers.add(vco);
	}

	/**
	 * Notifies the node selection observers.
	 * 
	 * @param selectedNodeIn
	 *            The node clicked on by the user.
	 */
	private void notifyNodeSelectionObservers(
			final HashSet<DNode> selectedNodeIn) {
		for (NodeSelectionObserver sgo : nodeSelectionObservers) {
			sgo.update(selectedNodeIn);
		}
	}

	/**
	 * Notifies the graph scroll observers.
	 */
	private void notifyGraphScrollObservers() {
		for (GraphScrollObserver gso : graphScrollObservers) {
			gso.update(getCurrentViewArea());
		}
	}

	/**
	 * Notifies the graph scroll observers.
	 */
	private void notifyViewChangeObservers() {
		for (ViewChangeObserver vco : viewChangeObservers) {
			vco.update(view.getWidth());

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
		Rectangle visible = graphPane.getViewport().getViewRect();
		return new ViewArea(visible.getMinX(), visible.getMaxX());
	}

	/**
	 * Assigns the css class for a node being selected and stores the old one.
	 * Also restores the css for the previous selected node.
	 * 
	 * @param newSelectedNode
	 *            Newly selected node.
	 */
	@SuppressWarnings("unchecked")
	public final void selectNode(final Node newSelectedNode) {
		// Restores the old class of the previous selected node if present.
		Node oldSelected = graph.getNode(String.valueOf(dgraph.getSelected()));
		if (oldSelected != null) {
			oldSelected.setAttribute("ui.class",
					oldSelected.getAttribute("oldclass"));
			oldSelected.setAttribute("oldclass",
					checkCollapsed((HashSet<Integer>) oldSelected
							.getAttribute("collapsed")));
		}

		// Assigns new selected node and stores old ui.class
		dgraph.setSelected(newSelectedNode.getId());
		newSelectedNode.setAttribute("oldclass",
				newSelectedNode.getAttribute("ui.class"));
		newSelectedNode.setAttribute("ui.class", "selected");
	}

	/**
	 * Highlights a genome.
	 */
	@SuppressWarnings("unchecked")
	public final void highlight() {
		for (Node n : graph.getEachNode()) {
			HashSet<Integer> ids = (HashSet<Integer>) n
					.getAttribute("collapsed");
			boolean contains = false;
			for (int id : ids) {
				for (String highlight : highlightedGenomes) {
					contains = contains
							|| dgraph.getDNode(id).getSources()
									.contains(highlight);
				}
			}
			if (contains) {
				if (n.getId().equals(String.valueOf(dgraph.getSelected()))) {
					n.setAttribute("oldclass", "highlight");
				} else {
					if (n.getAttribute("ui.class") != "highlight") {
						n.setAttribute("oldclass", n.getAttribute("ui.class"));
					}
					n.setAttribute("ui.class", "highlight");
				}
			}
		}
	}

	/**
	 * Unhighlights the graph.
	 */
	@SuppressWarnings("unchecked")
	public final void unHighlight() {
		for (Node n : graph.getEachNode()) {
			HashSet<Integer> ids = (HashSet<Integer>) n
					.getAttribute("collapsed");
			boolean contains = false;
			for (int id : ids) {
				for (String source : dgraph.getDNode(id).getSources()) {
					contains = contains || highlightedGenomes.contains(source);
				}
			}
			if (!contains && n.hasAttribute("oldclass")) {
				if (n.getId().equals(String.valueOf(dgraph.getSelected()))) {
					n.setAttribute("oldclass", checkCollapsed(ids));
				} else {
					n.setAttribute("ui.class", n.getAttribute("oldclass"));
					n.setAttribute("oldclass", n.getAttribute("ui.class"));
				}
			}
		}
	}

	/**
	 * Checks the size of the hashset of ids and returns collapsed or common
	 * based on it.
	 * 
	 * @param ids
	 *            HashSet to check.
	 * @return collapsed or common based on the size.
	 */
	private String checkCollapsed(final HashSet<Integer> ids) {
		if (ids.size() > 1) {
			return "collapsed";
		} else {
			return "common";
		}
	}

	/**
	 * 
	 * @param newZoomLevel
	 *            The zoom level to apply.
	 */
	public void applyZoomLevel(final int newZoomLevel) {
		if (newZoomLevel < 0 || newZoomLevel > thresholds.length) {
			Event.statusBarError("There is no zoom level further from the current level");
		} else if (newZoomLevel == 0) {
			visualizeGraph(ConvertDGraph.convert(dgraph));
			highlight();
		} else {
			int threshold = thresholds[newZoomLevel - 1];
			Graph gr = zlc.createGraph(threshold);
			setViewSize(NodePlacer.place(gr));
			visualizeGraph(gr);
			highlight();
		}
		notifyViewChangeObservers();
	}

	/**
	 * @param newViewSize
	 *            The new view size
	 */
	private void setViewSize(final Dimension newViewSize) {
		this.viewSize = newViewSize;
		notifyViewChangeObservers();
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
			notifyGraphScrollObservers();
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
					highlightedGenomes.add(genomeRow.getId());
					highlight();
				} else {
					highlightedGenomes.remove(genomeRow.getId());
					unHighlight();
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
		@SuppressWarnings("unchecked")
		@Override
		public void buttonReleased(final String id) {
			Event.statusBarMid(" Selected node: " + id);
			HashSet<DNode> ret = new HashSet<DNode>();
			for (Integer n : (HashSet<Integer>) graph.getNode(id).getAttribute(
					"collapsed")) {
				ret.add(dgraph.getDNode(n));
			}
			notifyNodeSelectionObservers(ret);
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
