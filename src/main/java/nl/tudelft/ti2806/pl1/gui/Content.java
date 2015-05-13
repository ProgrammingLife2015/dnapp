package nl.tudelft.ti2806.pl1.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
	 * 
	 */
	private Window window;

	/**
	 * The constraints for the layout of the content panel.
	 */
	private GridBagConstraints gbc = new GridBagConstraints();

	/**
	 * Initializes the content panel.
	 * 
	 * @param w
	 *            The window this content is part of.
	 */
	public Content(final Window w) {
		this.window = w;
		setLayout(new GridBagLayout());
		setupGBC();

		Graph g = null;
		try {
			g = Reader.read("src/main/resources/simple_graph.node.graph",
					"src/main/resources/simple_graph.edge.graph");
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			window.error(e.getMessage());
		}
		// Attach css file
		// System.setProperty("org.graphstream.ui.renderer",
		// "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		g.addAttribute("ui.stylesheet",
				"url('src/main/resources/stylesheet.css')");

		Viewer viewer = new Viewer(g,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.disableAutoLayout();
		ViewPanel view = viewer.addDefaultView(false);
		NodePlacer.place(g, viewer);
		view.setPreferredSize(new Dimension(100000, 500));
		// gbc.gridheight = 1;
		// gbc.gridwidth = 1;
		JScrollPane jsp = new JScrollPane(view);
		setWeight(1);
		place(new JPanel(), 0, 0);
		setWeight(3);
		place(jsp, 1, 0);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(final ComponentEvent evt) {
				window.resized(evt);
			}
		});

	}

	private void x() {

		Dimension d = new Dimension(10, 20);
		setWeight(0);
		JTextArea jta = new JTextArea("Bla bla bla bla bla.");
		// jta.setMaximumSize(d);
		// jta.setSize(d);
		jta.setPreferredSize(d);
		jta.setColumns(20);
		place(add(jta), 1, 0);
		gbc.anchor = GridBagConstraints.NORTHWEST;

		place(makeButton("button 00", Event.ANOTHER_EVENT, "another tool tip"),
				0, 0);
		// setWeight(1.0);
		place(makeButton("Center button print app name", Event.PRINT_APP_NAME,
				"print app name tool tip"), 1, 1);
		// setWeight(0.0);
		place(makeButton("Exit", Event.EXIT_APP, "exit the program tool tip"),
				2, 2);
	}

	/**
	 * Sets the grid bag constraint weight in both x and y direction.
	 * 
	 * @param weight
	 *            The weight to set.
	 */
	private void setWeight(final double weight) {
		gbc.weightx = weight;
		gbc.weighty = weight;
	}

	/**
	 * 
	 */
	private void setupGBC() {
		gbc.fill = GridBagConstraints.BOTH;
	}

	/**
	 * 
	 * @param elem
	 *            The element to place.
	 * @param x
	 *            The x grid coordinate to place the element.
	 * @param y
	 *            The y grid coordinate to place the element.
	 */
	private void place(final Component elem, final int x, final int y) {
		gbc.gridx = x;
		gbc.gridy = y;
		add(elem, gbc);
	}

	/**
	 * Creates a button element.
	 * 
	 * @param text
	 *            File name of the image icon.
	 * @param action
	 *            Action id.
	 * @param toolTipText
	 *            Tool tip text.
	 * 
	 * @see Event
	 * 
	 * @return The button created.
	 */
	private JButton makeButton(final String text, final Event action,
			final String toolTipText) {
		JButton button = new JButton();
		button.setText(text);
		button.setToolTipText(toolTipText);
		// button.setActionCommand(action.toString());
		button.addActionListener(action);
		return button;

	}

}
