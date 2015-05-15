/**
 * 
 */
package nl.tudelft.ti2806.pl1.gui;

import java.awt.Dimension;
import java.io.FileNotFoundException;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import nl.tudelft.ti2806.pl1.reader.NodePlacer;
import nl.tudelft.ti2806.pl1.reader.Reader;

import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

/**
 * @author Maarten
 *
 */
public class GraphPanel extends JSplitPane {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -3581428828970208653L;

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

		graphPane = new JScrollPane();

		text = new JTextArea();
		infoPane = new JScrollPane(text);

		setTopComponent(graphPane);
		setBottomComponent(infoPane);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setDividerLocation(0.8f);
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
	 */
	public final void loadGraph(final String nodePath, final String edgePath) {
		try {
			graph = Reader.read(nodePath, edgePath);
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
		NodePlacer.place(graph, viewer);
		view = viewer.addDefaultView(false);
		view.setPreferredSize(new Dimension(100000, 500));

		graphPane.setViewportView(view);
		graphPane.setMinimumSize(new Dimension(50, 50));
		graphPane.setPreferredSize(new Dimension(50, 50));

		window.revalidate();
	}

}
