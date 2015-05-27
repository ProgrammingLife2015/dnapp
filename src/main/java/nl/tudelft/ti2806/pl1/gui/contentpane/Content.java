package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import nl.tudelft.ti2806.pl1.gui.Window;
import nl.tudelft.ti2806.pl1.main.Start;

/**
 * A panel representing the content pane of the main window. Contains a tabbed
 * pane able to show graph panes and phylogenetic tree panes.
 * 
 * @see GraphPanel
 * @see PhyloPanel
 * 
 * @author Maarten
 *
 */
public class Content extends JPanel {

	/** The serial version UID. */
	private static final long serialVersionUID = -518196843048978296L;

	/** Flag to indicate whether a graph has been loaded into the graph panel. */
	private boolean graphLoaded = false;

	/**
	 * @return true iff there is a graph loaded
	 */
	public final boolean isGraphLoaded() {
		return graphLoaded;
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
	private JScrollPane phyloPanel;

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
		phyloPanel = new PhyloPanel(window);

		tabs.addTab("Main", graphPanel);
		tabs.addTab("Phylogenetic tree", phyloPanel);

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
	 *            The path to the node file.
	 * @param edgePath
	 *            The path to the edge file.
	 * @throws IOException
	 */
	public final void loadGraph(final File nodePath, final File edgePath)
			throws IOException {
		graphLoaded = graphPanel.loadGraph(nodePath, edgePath);
	}

	/**
	 * @param args
	 *            jwz
	 */
	public static void main(final String[] args) {
		Start.main(args);
	}

	@Override
	public final String toString() {
		return this.getClass().toString();
	}

}
