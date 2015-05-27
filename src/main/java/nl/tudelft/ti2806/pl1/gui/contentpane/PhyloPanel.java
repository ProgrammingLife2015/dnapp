package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JScrollPane;

import nl.tudelft.ti2806.pl1.gui.Event;
import nl.tudelft.ti2806.pl1.gui.Window;
import nl.tudelft.ti2806.pl1.phylotree.BinaryTree;

import com.wordpress.tips4java.ScrollablePanel;

/**
 * @author Maarten
 *
 */
public class PhyloPanel extends JScrollPane {

	/** The serial version UID. */
	private static final long serialVersionUID = -1936473122898892804L;

	/** The window this panel is part of. */
	private Window window;

	/**
	 * 
	 */
	private BinaryTree tree;

	/**
	 * 
	 */
	private String newickSource;

	/**
	 * 
	 */
	private ScrollablePanel treePanel;

	/**
	 * 
	 */
	private int maxX = 0, maxY = 0;

	/**
	 * The space inbetween the nodes.
	 */
	private static final Insets INSETS = new Insets(3, 10, 0, 0);

	/** The size of the nodes. */
	private static final int NODE_WIDTH = 100, NODE_HEIGHT = 25;

	/**
	 * Initializes the panel.
	 * 
	 * @param w
	 *            The window this panel is part of.
	 */
	public PhyloPanel(final Window w) {
		this.window = w;
		treePanel = new ScrollablePanel();
		treePanel.setLayout(null);
		setViewportView(treePanel);
	}

	/**
	 * 
	 * @param newick
	 *            The Newick file to load.
	 * @return true iff the tree was loaded successfully.
	 */
	public final boolean loadTree(final File newick) {
		boolean ret = true;
		try {
			this.newickSource = readIntoString(newick);
		} catch (IOException e) {
			ret = false;
			Event.statusBarError("File " + newick.getAbsolutePath()
					+ " could not be read.");
			e.printStackTrace();
		}
		this.tree = BinaryTree.parseNewick(newickSource, this);
		plotTree();
		return ret;
	}

	/**
	 * 
	 */
	public final void plotTree() {
		removeTree();
		int treeWidth = tree.computePlacement(0, 0);
		int treeHeight = tree.height();
		// System.out.println(tree.toString());
		addNode(tree);
		treePanel.setPreferredSize(new Dimension((treeHeight + 1)
				* (NODE_WIDTH + INSETS.left) + INSETS.left, (treeWidth + 1)
				* (NODE_HEIGHT + INSETS.top) + INSETS.top));
		repaint();
	}

	/**
	 * Empties the panel. All tree components will be deleted.
	 */
	public final void removeTree() {
		treePanel.removeAll();
	}

	/**
	 * 
	 * @param tree
	 */
	private void addNode(final BinaryTree tree) {
		// treePanel.remove(tree);
		int x = (int) tree.getGridCoordinates().getX();
		int y = (int) tree.getGridCoordinates().getY();
		maxX = Math.max(maxX, x);
		maxY = Math.max(maxY, y);
		tree.setBounds(x * (NODE_WIDTH + INSETS.left) + INSETS.left, y
				* (NODE_HEIGHT + INSETS.top) + INSETS.top, NODE_WIDTH,
				NODE_HEIGHT);
		tree.setText(tree.getID());
		treePanel.add(tree);
		if (!tree.isCollapsed()) {
			for (BinaryTree t : tree.getChildren()) {
				addNode(t);
			}
		}
	}

	/**
	 * Copies the entire file content into a single String, and returns it.
	 * 
	 * @param file
	 *            The file to read.
	 * @return the file content as a continuous string
	 * @throws IOException
	 *             when something goes wrong xD
	 */
	public static String readIntoString(final File file) throws IOException {
		final StringBuffer buff = new StringBuffer();

		final BufferedReader in = new BufferedReader(new FileReader(file));
		String line;
		while ((line = in.readLine()) != null) {
			buff.append(line);
		}
		in.close();

		return buff.toString();
	}

	@Override
	protected final void paintComponent(final Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.PINK);
		g2.setColor(Color.PINK);
		g2.setStroke(new BasicStroke(3));
		g2.fillRect(20, 20, 200, 200);
		g.fillRect(150, 100, 300, 300);
		if (tree != null) {

			g2.drawLine((int) (tree.getX() + 0.5 * tree.getWidth()),
					(int) (tree.getY() + 0.5 * tree.getHeight()), (int) (tree
							.getX() + 0.5 * tree.getWidth()), (int) (tree
							.getRight().getY() + 0.5 * tree.getRight()
							.getHeight()));
		}

		System.out.println("Drawn!");
	}

}
