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
import nl.tudelft.ti2806.pl1.phylotree.BinaryTree;

import com.wordpress.tips4java.ScrollablePanel;

/**
 * @author Maarten
 *
 */
public class PhyloPanel extends JScrollPane {

	/** The serial version UID. */
	private static final long serialVersionUID = -1936473122898892804L;

	/**
	 * The tree that this panel will show.
	 */
	private BinaryTree tree;

	public BinaryTree getTree() {
		return tree;
	}

	/**
	 * The path to the nwk file.
	 */
	private String newickSource;

	/**
	 * The scrollpanel.
	 */
	private ScrollablePanel treePanel;

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
	public PhyloPanel() {
		treePanel = new ScrollablePanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 118748767146413611L;

			@Override
			protected final void paintComponent(final Graphics g) {
				super.paintComponent(g);
				if (tree != null) {
					drawLines(g, tree);
				}
				System.out.println("Drawn!");
			}
		};
		treePanel.setLayout(null);
		setViewportView(treePanel);

	}

	/**
	 * Draws lines from the current tree to its children.
	 * 
	 * @param g
	 *            The graphics object.
	 * @param bintree
	 *            The current tree
	 */
	private void drawLines(final Graphics g, final BinaryTree bintree) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		if (!bintree.isCollapsed()) {
			BinaryTree[] children = bintree.getChildren();
			int x = (int) bintree.getCenter().getX();
			int y = (int) bintree.getCenter().getY();
			for (BinaryTree child : children) {
				int childX = (int) child.getCenter().getX();
				int childY = (int) child.getCenter().getY();
				if (childY == y) {
					g2.drawLine(x, y, childX, y);
					System.out.println("Drawn direct line");
				} else {
					g2.drawLine(x, y, x, childY);
					g2.drawLine(x, childY, childX, childY);
					System.out.println("Drawn two lines");
				}
				drawLines(g, child);
			}
		}
	}

	/**
	 * Loads a newick file.
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
	 * Plots the tree as a button.
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
	 * Adds the current tree and all its children if it is not collapsed.
	 * 
	 * @param bintree
	 *            The current tree
	 */
	private void addNode(final BinaryTree bintree) {
		// treePanel.remove(tree);
		int x = (int) bintree.getGridCoordinates().getX();
		int y = (int) bintree.getGridCoordinates().getY();
		bintree.setBounds(x * (NODE_WIDTH + INSETS.left) + INSETS.left, y
				* (NODE_HEIGHT + INSETS.top) + INSETS.top, NODE_WIDTH,
				NODE_HEIGHT);
		bintree.setText(bintree.getID());

		treePanel.add(bintree);
		if (!bintree.isCollapsed()) {
			for (BinaryTree t : bintree.getChildren()) {
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
}
