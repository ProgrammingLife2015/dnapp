package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.tudelft.ti2806.pl1.exceptions.InvalidNodePlacementException;
import nl.tudelft.ti2806.pl1.gui.Window;

/**
 * A panel representing the content pane of the main window. Contains a tabbed
 * pane able to show graph panes and phylogenetic tree panes.
 * 
 * @see GraphPanel
 * @see PhyloPanel
 * 
 * @author Maarten
 */
public class Content extends JPanel {

	/** The serial version UID. */
	private static final long serialVersionUID = -518196843048978296L;

	/** The list of content loaded observers. */
	private List<ContentLoadedObserver> observers = new ArrayList<ContentLoadedObserver>();

	/** Flag to indicate whether a graph has been loaded into the graph panel. */
	private boolean graphLoaded = false;

	/**
	 * @return true iff there is a graph loaded
	 */
	public final boolean isGraphLoaded() {
		return graphLoaded;
	}

	/**
	 * Flag to indicate whether a phylogenetic tree has been loaded into the
	 * phylo panel.
	 */
	private boolean treeLoaded = false;

	/**
	 * @return true iff there is a phylogenetic tree loaded
	 */
	public final boolean isTreeLoaded() {
		return treeLoaded;
	}

	/**
	 * Register a new content loaded observer.
	 * 
	 * @param o
	 *            The observer to add.
	 */
	public final void registerObserver(final ContentLoadedObserver o) {
		observers.add(o);
	}

	/**
	 * Unregistrer a content loaded observer.
	 * 
	 * @param o
	 *            The observer to delete.
	 */
	public final void unregisterObserver(final ContentLoadedObserver o) {
		observers.remove(o);
	}

	/** The window this content pane is part of. */
	private Window window;

	/** The tab container. */
	private JTabbedPane tabs = new JTabbedPane();

	/** Content of the graph tab showing most importantly the graph. */
	private GraphPanel graphPanel;

	/**
	 * @return the graph panel.
	 */
	public final GraphPanel getGraphPanel() {
		return graphPanel;
	}

	/** The panel showing the phylogenetic tree. */
	private PhyloPanel phyloPanel;

	/**
	 * Initializes the content panel.
	 * 
	 * @param w
	 *            The window this content is part of.
	 */
	public Content(final Window w) {
		this.window = w;
		setLayout(new BorderLayout());

		graphPanel = new GraphPanel(window);
		phyloPanel = new PhyloPanel();

		tabs.addTab("Main", graphPanel);
		tabs.addTab("Phylogenetic tree", phyloPanel);
		tabs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				window.getToolBar().viewContextChanged(
						(ContentTab) tabs.getSelectedComponent());
			}
		});
		add(tabs, BorderLayout.CENTER);
	}

	/**
	 * Directly calls the write graph method of the graph panel.
	 * 
	 * @param filePath
	 *            The path to the file to write.
	 * @see GraphPanel#writeGraph(String)
	 */
	public final void writeGraph(final String filePath) {
		graphPanel.writeGraph(filePath);
	}

	/**
	 * Loads a graph into the graph tab. Directly calls the load graph method of
	 * the graph panel.
	 * 
	 * @param nodePath
	 *            The node file.
	 * @param edgePath
	 *            The edge file.
	 * @throws InvalidNodePlacementException
	 *             Placing node at invalid place.
	 */
	public final void loadGraph(final File nodePath, final File edgePath)
			throws InvalidNodePlacementException {
		graphLoaded = graphPanel.loadGraph(nodePath, edgePath);
		for (ContentLoadedObserver clo : observers) {
			clo.graphLoaded();
		}
		window.getToolBar().viewContextChanged(
				(ContentTab) tabs.getSelectedComponent());
	}

	/**
	 * Loads a graph into the graph tab. Directly calls the load graph method of
	 * the graph panel.
	 * 
	 * @param newick
	 *            The Newick tree file to load
	 */
	public final void loadTree(final File newick) {
		treeLoaded = phyloPanel.loadTree(newick);
		for (ContentLoadedObserver clo : observers) {
			clo.phyloLoaded();
		}
	}

	@Override
	public final String toString() {
		return this.getClass().toString();
	}

}
