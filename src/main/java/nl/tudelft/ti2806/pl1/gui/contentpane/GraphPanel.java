package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
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
import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGene;
import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneObserver;
import nl.tudelft.ti2806.pl1.gui.AppEvent;
import nl.tudelft.ti2806.pl1.gui.ProgressDialog;
import nl.tudelft.ti2806.pl1.gui.Window;
import nl.tudelft.ti2806.pl1.gui.optionpane.GeneSelectionObserver;
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
public class GraphPanel extends JSplitPane implements ContentTab,
		GeneSelectionObserver {

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

	/** Map containing the position of the genes. */
	private HashMap<String, ArrayList<Integer>> genes = new HashMap<String, ArrayList<Integer>>();

	/** Map containing the outer nodes of the gene regions. */
	private HashMap<String, ArrayList<Node>> geneLocs;

	/** Diameter of nodes in pixels. */
	private final int nodeDiameter = 20;

	/** Genelocator object for detecting genes. */
	private final GeneLocator gl;

	/**
	 * Initialize a graph panel.
	 * 
	 * @param w
	 *            The window this panel is part of.
	 */
	public GraphPanel(final Window w) {
		super(JSplitPane.VERTICAL_SPLIT, true);
		this.window = w;
		this.window.getOptionPanel().getGeneNavigator().registerObserver(this);

		graphPane = new JScrollPane();
		graphPane.setMinimumSize(new Dimension(2, 2));

		infoPane = new JPanel(new BorderLayout());

		minimap = new Minimap(this);
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
		gl = new GeneLocator();
		graphPane.getHorizontalScrollBar().setUnitIncrement(HOR_SCROLL_INCR);
		highlightedGenomes = new HashSet<String>();
	}

	/**
	 * Fill the navigator box for genes with all the genes name after reading
	 * all the genes.
	 */
	private void fillGeneNavigatorBox() {
		if (dgraph.getReferenceGeneStorage().getReferenceGenes() != null) {
			for (ReferenceGene rg : dgraph.getReferenceGeneStorage()
					.getReferenceGenes()) {
				window.getOptionPanel().getGeneNavigator()
						.addItem(rg.getName());
			}
		}
	}

	@Override
	public List<JComponent> getToolBarControls() {
		List<JComponent> ret = new ArrayList<JComponent>(2);
		// ret.add(ToolBar
		// .makeButton("Reload visible part", null, Event.RELOAD_GRAPH,
		// "Loads or reloads the part of the graph currently in the view port."));
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
			AppEvent.statusBarInfo("Exported graph representation to: "
					+ filePath);
		} catch (IOException e) {
			AppEvent.statusBarError("Error during visual graph export: "
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
		final ProgressDialog pd = new ProgressDialog(window, "Importing...",
				true);
		GraphLoadWorker pw = new GraphLoadWorker(nodes, edges, pd);
		pw.execute();
		pd.start();
		try {
			this.graph = pw.get();
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
			ret = false;
		}
		NodePlacer.placeY(graph);
		visualizeGraph(graph);
		return ret;
	}

	/**
	 * @author ChakShun
	 * @since 18-6-2015
	 */
	class GeneLocator implements ReferenceGeneObserver {

		/**
		 * Locate the genes in the graph.
		 * 
		 * @param vGraph
		 *            Graph in which to look up the genes.
		 */
		public void locateGenes(final Graph vGraph) {
			if (dgraph.getReferenceGeneStorage().getReferenceGenes() != null) {
				for (ReferenceGene rg : dgraph.getReferenceGeneStorage()
						.getReferenceGenes()) {
					Integer start = searchNode(rg.getStart(), vGraph);
					Integer end = searchNode(rg.getEnd(), vGraph);
					if (start != null && end != null) {
						ArrayList<Integer> locs = new ArrayList<Integer>(2);
						locs.add(start);
						locs.add(end);
						genes.put(rg.getName(), locs);
					}
				}
			}
		}

		/**
		 * Finds the graphstream node using a index.
		 * 
		 * @param id
		 *            id of the node.
		 * @param vGraph
		 *            graph in which to search.
		 * @return Node object containing the id or node with the id.
		 */
		@SuppressWarnings("unchecked")
		private Node findNode(final int id, final Graph vGraph) {
			for (Node n : vGraph.getEachNode()) {
				for (Integer index : (HashSet<Integer>) n
						.getAttribute("collapsed")) {
					if (index == id) {
						return n;
					}
				}
			}
			return null;
		}

		/**
		 * Search the visual graph for the gene locations and stores them.
		 * 
		 * @param vGraph
		 *            Graph in which to search.
		 */
		private void searchGeneLocs(final Graph vGraph) {
			geneLocs = new HashMap<String, ArrayList<Node>>();
			if (!genes.isEmpty()) {
				for (Entry<String, ArrayList<Integer>> entry : genes.entrySet()) {
					ArrayList<Node> nodes = new ArrayList<Node>(2);
					Node begin = findNode(entry.getValue().get(0), vGraph);
					Node end = findNode(entry.getValue().get(1), vGraph);
					if (begin != null
							&& end != null
							&& (int) begin.getAttribute("x") <= (int) end
									.getAttribute("x")) {
						nodes.add(begin);
						nodes.add(end);
						geneLocs.put(entry.getKey(), nodes);
					}
				}
			}
		}

		/**
		 * 
		 * @param loc
		 *            Location on the reference genome.
		 * @param vGraph
		 *            graph in which to search.
		 * @return Index of the node.
		 */
		@SuppressWarnings("unchecked")
		private Integer searchNode(final int loc, final Graph vGraph) {
			for (Node n : vGraph.getEachNode()) {
				for (int id : (HashSet<Integer>) n.getAttribute("collapsed")) {
					if (dgraph.getDNode(id).getStart() <= loc
							&& dgraph.getDNode(id).getEnd() > loc) {
						return id;
					}
				}
			}
			return null;
		}

		@Override
		public void update() {
			locateGenes(graph);
			searchGeneLocs(graph);
			fillGeneNavigatorBox();
		}
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
				dgraph.getReferenceGeneStorage().registerObserver(minimap);
				dgraph.getReferenceGeneStorage().registerObserver(gl);
				zlc = new ZoomlevelCreator(dgraph);
				viewSize = NodePlacer.place(dgraph);
				graph = ConvertDGraph.convert(dgraph);
				viewSize = NodePlacer.place(graph, viewSize);
				analyzeDGraph();
				window.getOptionPanel().fillGenomeList(
						dgraph.getReferencesSet(), true, true);
			} catch (Exception e) {
				e.printStackTrace();
				AppEvent.statusBarError(e.getMessage());
			}
			return graph;
		}
	}

	/** Performs all the analyze methods on the DGraph. */
	private void analyzeDGraph() {
		dgraph.calculateReferenceLength();
		dgraph.setPointMutations(PointGraphConverter.getPointMutations(dgraph));
		dgraph.setDeletionMutations(MutationFinder
				.findDeletionMutations(dgraph));
		dgraph.setInsertionMutations(MutationFinder
				.findInsertionMutations(dgraph));
		dgraph.setComplexMutations(MutationFinder.findComplexMutations(dgraph));
	}

	/**
	 * Takes the virtual (GraphStream) graph and shows it in the panel.
	 * 
	 * @param vGraph
	 *            The visual graph to draw
	 */
	private void visualizeGraph(final Graph vGraph) {
		gl.searchGeneLocs(vGraph);
		Viewer viewer = new Viewer(vGraph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.disableAutoLayout();
		view = new DefaultView(viewer, Viewer.DEFAULT_VIEW_ID,
				Viewer.newGraphRenderer()) {
			/** The serial version UID. */
			private static final long serialVersionUID = 4902528839853178375L;

			@Override
			public void paintComponent(final Graphics g) {
				super.paintComponent(g);
				paintGeneBoxes(g);
			}
		};
		viewer.addView(view);
		view.setMinimumSize(viewSize);
		view.setPreferredSize(viewSize);
		view.setMaximumSize(viewSize);
		view.setEnabled(false);

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
	 * Paints the gene boxes overlay.
	 * 
	 * @param g
	 *            The Graphics object to protect.
	 */
	private void paintGeneBoxes(final Graphics g) {
		for (Entry<String, ArrayList<Node>> entry : geneLocs.entrySet()) {
			Integer xleft = (int) entry.getValue().get(0).getAttribute("x");
			Integer xright = (int) entry.getValue().get(1).getAttribute("x");
			g.setColor(Color.ORANGE);
			g.fillRect(xleft - nodeDiameter / 2, 0, xright - xleft
					+ nodeDiameter, nodeDiameter / 2);
		}
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
					checkClassType((HashSet<Integer>) oldSelected
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
					n.setAttribute("oldclass", checkClassType(ids));
				} else {
					n.setAttribute("ui.class", n.getAttribute("oldclass"));
					n.setAttribute("oldclass", n.getAttribute("ui.class"));
				}
			}
		}
	}

	/**
	 * First checks the class type on resistance and then on collapsed.
	 * 
	 * @param ids
	 *            ids of the nodes in this node.
	 * @return String <code>collapsed</code> or <code>common</code> based on the
	 *         size.
	 */
	private String checkClassType(final HashSet<Integer> ids) {
		for (int id : ids) {
			if (dgraph.getDNode(id).getResMuts() != null) {
				return "resistant";
			}
		}
		return checkCollapsed(ids);
	}

	/**
	 * Checks the size of the set of ids and returns collapsed or common based
	 * on it.
	 * 
	 * @param ids
	 *            HashSet to check.
	 * @return String <code>collapsed</code> or <code>common</code> based on the
	 *         size.
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
		count = 0;
		if (newZoomLevel < 0) {
			AppEvent.statusBarError("There is no zoom level further from the current level");
		} else if (newZoomLevel > thresholds.length) {
			AppEvent.statusBarError("There is no zoom level further from the current level");
		} else {
			zoomLevel = newZoomLevel;
			int threshold;
			if (newZoomLevel == 0) {
				threshold = Integer.MIN_VALUE;
			} else {
				threshold = thresholds[newZoomLevel - 1];
			}
			Graph gr = zlc.createGraph(threshold);
			setViewSize(NodePlacer.place(gr, viewSize));
			NodePlacer.placeY(gr);
			visualizeGraph(gr);
			highlight();
			AppEvent.statusBarInfo("Zoom level set to: " + zoomLevel);
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
				upZoomlevel();
			} else if (count < -NEWLEVEL) {
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
			AppEvent.statusBarMid(" Selected node: " + id);
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
	 * Lets you zoom in one level further.
	 */
	public void upZoomlevel() {
		applyZoomLevel(zoomLevel + 1);
	}

	/**
	 * Lets you zoom out one level further.
	 */
	public void downZoomlevel() {
		applyZoomLevel(zoomLevel - 1);
	}

	/**
	 * @return The data graph.
	 */
	public final DGraph getDgraph() {
		return dgraph;
	}

	@Override
	public void update(final String selectedGene) {
		if (geneLocs.containsKey(selectedGene)) {
			Node beginnode = this.geneLocs.get(selectedGene).get(0);
			this.selectNode(beginnode);
			graphPane.getHorizontalScrollBar().setValue(
					(int) beginnode.getAttribute("x")
							- graphPane.getViewport().getWidth() / 2);
		}

	}
}
