/**
 * 
 */
package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import nl.tudelft.ti2806.pl1.gui.Window;
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
		graphPane.setMinimumSize(new Dimension(100, 100));

		text = new JTextArea();
		infoPane = new JScrollPane(text);

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
					window.statusBar().error(
							"Writing the graph went wrong (" + e.getMessage()
									+ ")");
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
		// view.
		view.addMouseListener(new MouseListener() {

			public void mouseReleased(final MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(final MouseEvent e) {
				text.setText(e.getComponent().toString());
				filter();
				view.revalidate();

			}

			public void mouseExited(final MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(final MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(final MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		graphPane.setViewportView(view);
		graphPane.setMinimumSize(new Dimension(50, 50));
		graphPane.setPreferredSize(new Dimension(50, 50));

		window.revalidate();
	}

	/**
	 * 
	 */
	public void filter() {
		// text.insert(String.valueOf(graph.getNode(1184).getDegree()), text
		// .getText().length());

		graph.getNode(1184).setAttribute("ui.class", "common");
		graph.getNode(1184).setAttribute("ui.color", 1.5d);
		graph.getNode(1183).setAttribute("ui.class", "common");
		graph.getNode(1183).setAttribute("ui.color", 1d);
		graph.getNode(1256).setAttribute("ui.class", "common");
		graph.getNode(1256).setAttribute("ui.color", 2d);
	}

}
