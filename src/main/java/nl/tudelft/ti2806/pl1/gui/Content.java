package nl.tudelft.ti2806.pl1.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileNotFoundException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import nl.tudelft.ti2806.pl1.DGraph.ConvertDGraph;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.main.Start;
import nl.tudelft.ti2806.pl1.reader.NodePlacer;
import nl.tudelft.ti2806.pl1.reader.Reader;

import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

/**
 * A panel representing the content of the main window.
 * 
 * @author Maarten
 *
 */
public class Content extends JPanel {

	/**
	 * 
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
	private JScrollPane mainPanel;

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
	}

	/**
	 * Loads a graph into the content scroll pane.
	 * 
	 * @param nodePath
	 *            The path to the node file.
	 * @param edgePath
	 *            The path to the edge file.
	 */
	public final void loadGraph(final String nodePath, final String edgePath) {
		try {
			DGraph dgraph = Reader.read(nodePath, edgePath);
			NodePlacer.place(dgraph);
			graph = ConvertDGraph.convert(dgraph);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			window.statusBar().error(e.getMessage());
		}
		graph.addAttribute("ui.stylesheet",
				"url('src/main/resources/stylesheet.css')");
		// System.setProperty("org.graphstream.ui.renderer",
		// "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Viewer viewer = new Viewer(graph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.disableAutoLayout();
		view = viewer.addDefaultView(false);
		view.setPreferredSize(new Dimension(100000, 500));

		mainPanel = new JScrollPane(view);
		mainPanel.setMinimumSize(new Dimension(20, 40));
		mainPanel.setPreferredSize(new Dimension(20, 40));

		phyloPanel = new JScrollPane(new JPanel());

		tabs.addTab("Main", mainPanel);
		tabs.addTab("Phylogenetic tree", phyloPanel);

		add(tabs, BorderLayout.CENTER);

		window.revalidate();
	}

	/**
	 * @param args
	 *            jwz
	 */
	public static void main(final String[] args) {
		Start.main(args);
	}

}
