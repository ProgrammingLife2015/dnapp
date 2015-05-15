package nl.tudelft.ti2806.pl1.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import nl.tudelft.ti2806.pl1.main.Start;

import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.ViewPanel;

/**
 * A panel representing the content of the main window.
 * 
 * @author Maarten
 *
 */
public class Content extends JPanel {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -518196843048978296L;

	/**
	 * The window this content pane is part of.
	 */
	private Window window;

	/**
	 * 
	 */
	private JTabbedPane tabs = new JTabbedPane();

	/**
	 * The main panel showing the graph view in a scrollable environment.
	 */
	private GraphPanel graphPanel;

	/**
	 * The panel showing the phylogenetic tree.
	 */
	private JScrollPane phyloPanel;

	/**
	 * The graph view panel inside the main panel.
	 */
	private ViewPanel view;

	/**
	 * The graph inside the graph view.
	 */
	private Graph graph;

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
	 * 
	 * @param gp
	 */
	public final void setGraph(final GraphPanel gp) {
		this.graphPanel = gp;
	}

	/**
	 * 
	 * @param pp
	 */
	public final void setPhylo(final PhyloPanel pp) {
		this.phyloPanel = pp;
	}

	/**
	 * @param args
	 *            jwz
	 */
	public static void main(final String[] args) {
		Start.main(args);
	}

}
