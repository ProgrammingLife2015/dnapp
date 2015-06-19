package nl.tudelft.ti2806.pl1.phylotree;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JButton;

import nl.tudelft.ti2806.pl1.gui.contentpane.PhyloPanel;

/**
 * Generic binary tree, storing data of a parametric data in each node.
 * http://www.cs.dartmouth.edu/~cbk/10/notes/7/code/BinaryTree.java Edited by
 * Maarten.
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * 
 */
public abstract class BinaryTree extends JButton {

	/** The serial version UID. */
	private static final long serialVersionUID = -7580501984360893313L;

	/**
	 * The number of significant digits of the evolutionary distance to the
	 * parent node to show.
	 * 
	 * @see BinaryTree#pathLength
	 */
	private static final int PATH_LENGTH_ROUND_TO_N = 5;

	/** The parent container. */
	private final PhyloPanel phyloPanel;

	/** The distance to the parent node. */
	private final double pathLength;

	/** The name label of the node. */
	private final String id;

	/** Whether the ancestors of this node are collapsed 'into' this node. */
	private boolean collapsed = false;

	/**
	 * The parent of the node, initialized at null for the root.
	 */
	private BinaryTree parent;

	/** The placement of this node in the tree grid. */
	private Point gridCoordinates = new Point(0, 0);

	/**
	 * @return The grid coordinates of this node.
	 */
	public final Point getGridCoordinates() {
		return gridCoordinates;
	}

	/**
	 * Constructs leaf node with no children.
	 * 
	 * @param nameIn
	 *            The name of the node.
	 * @param length
	 *            The path length.
	 * @param panel
	 *            The phylo panel this node is part of.
	 */
	public BinaryTree(final String nameIn, final double length,
			final PhyloPanel panel) {
		super(nameIn);
		this.parent = null;
		this.id = nameIn;
		this.pathLength = length;
		this.phyloPanel = panel;
		if (nameIn.isEmpty()) {
			setToolTipText(String.valueOf(roundN(pathLength,
					PATH_LENGTH_ROUND_TO_N)));
		} else {
			setToolTipText("<html>" + nameIn + "<br>"
					+ roundN(pathLength, PATH_LENGTH_ROUND_TO_N) + "</html>");
		}
		addMouseListener(new NodeMouseListener());
		this.setBackground(Color.GRAY);

	}

	/** @return the path length. */
	public final double getPathLength() {
		return pathLength;
	}

	/**
	 * @param n
	 *            The number of digits to round the path length to.
	 * @return The path length, rounded to a specified number of decimals
	 *         places.
	 */
	public final String getPathLengthN(final int n) {
		return String.valueOf(roundN(pathLength, n));
	}

	/** @return the node name. */
	public final String getID() {
		return id;
	}

	/** @return the size of the (sub)tree. */
	public abstract int treeSize();

	/** @return the longest length to a leaf node from here. */
	public abstract int height();

	/**
	 * Checks whether two trees are equal.
	 * 
	 * @param t2
	 *            the tree to compare this tree to.
	 * @return true iff this tree is equal to <code>t2</code>.
	 */
	public abstract boolean equalsTree(final BinaryTree t2);

	/** @return true iff the node has a left child */
	public abstract boolean hasLeft();

	/** @return true iff the node has a right child */
	public abstract boolean hasRight();

	/** @return the left child, null if there is no left child */
	public abstract BinaryTree getLeft();

	/** @return the left child, null if there is no left child */
	public abstract BinaryTree getRight();

	/** @return an array of the children of this node. */
	public abstract List<BinaryTree> getChildren();

	/**
	 * @return the parent
	 */
	@Override
	public BinaryTree getParent() {
		return parent;
	}

	/**
	 * @param newParent
	 *            the parent to set
	 */
	public void setParent(final BinaryTree newParent) {
		this.parent = newParent;
	}

	/**
	 * Sets the grid coordinates of the node and computes the children's
	 * placement.
	 * 
	 * @param x
	 *            The horizontal grid coordinate of the node.
	 * @param y
	 *            The vertical grid coordinate of the node.
	 * @return the width (delta y) of the tree
	 */
	public abstract int computePlacement(int x, int y);

	/**
	 * Sets the grid coordinates.
	 * 
	 * @param x
	 *            The horizontal coordinate.
	 * @param y
	 *            The vertical coordinate.
	 */
	protected final void setGridLoc(final int x, final int y) {
		this.gridCoordinates = new Point(x, y);
	}

	/**
	 * Gets the coordinates of the center.
	 * 
	 * @return A Point with the coordinates.
	 */
	public final Point getCenter() {
		Rectangle rect = this.getBounds();
		double x = (rect.getMinX() + rect.getMaxX()) / 2;
		double y = (rect.getMinY() + rect.getMaxY()) / 2;
		return new Point((int) x, (int) y);
	}

	@Override
	public final String toString() {
		return toStringHelper("");
	}

	/**
	 * Recursively constructs a String representation of the tree from this
	 * node, starting with the given indentation and indenting further going
	 * down the tree.
	 * 
	 * @param indent
	 *            indentation
	 * @return a visual text representation of the tree
	 */
	protected abstract String toStringHelper(final String indent);

	/**
	 * Very simplistic binary tree parser based on Newick representation.
	 * Assumes that each node is given a label that becomes the data. Any
	 * distance information (following the colon) is stripped [tree] = "("
	 * [tree] "," [tree] ")" [label] [":"[dist]] | [label] [":"[dist]] No effort
	 * at all to handle malformed trees or those not following these strict
	 * requirements.
	 * 
	 * @param s
	 *            the string to parse
	 * @param panel
	 *            The parent container.
	 * @return a phylogenetic tree
	 */
	public static BinaryTree parseNewick(final String s, final PhyloPanel panel) {
		return parseNewick(new StringTokenizer(s, "(,)", true), panel);
	}

	/**
	 * Does the real work of parsing, now given a tokenizer for the string.
	 * 
	 * @param st
	 *            A string tokenizer.
	 * @param panel
	 *            The parent container.
	 * @return a phylogenetic tree
	 */
	private static BinaryTree parseNewick(final StringTokenizer st,
			final PhyloPanel panel) {
		final String token = st.nextToken();
		if (token.equals("(")) { // Inner node
			final BinaryTree left = parseNewick(st, panel);
			st.nextToken();
			final BinaryTree right = parseNewick(st, panel);
			st.nextToken();
			final String label = st.nextToken();
			final String[] pieces = label.split(":");
			BinaryTree current = new InnerNode(parseName(pieces, 0),
					parseDouble(pieces, 1), left, right, panel);
			left.setParent(current);
			right.setParent(current);
			return current;
		} else { // Leaf
			final String[] pieces = token.split(":");
			return new Leaf(parseName(pieces, 0), parseDouble(pieces, 1), panel);
		}
	}

	/**
	 * 
	 * @param array
	 *            The array to extract the name from.
	 * @param index
	 *            The index in the array to parse the name from.
	 * @return the name from the given array on the given index if the name is
	 *         not equal to ";", else: empty string.
	 */
	private static String parseName(final String[] array, final int index) {
		if (index >= array.length) {
			return "";
		} else if (array[index].endsWith(";")) {
			return array[index].substring(0, array[index].length() - 1);
		}
		return array[index];
	}

	/**
	 * Extracts a double value from a String array.
	 * 
	 * @param array
	 *            The array to extract the double from.
	 * @param index
	 *            The index in the array to parse the double from
	 * @return the double value from the given array on the given index if the
	 *         index exists, else: 0.0
	 */
	private static double parseDouble(final String[] array, final int index) {
		if (index >= array.length) {
			return 0.0;
		}
		return Double.parseDouble(array[index]);
	}

	/**
	 * Returns a string unless it is empty, then returns a given default string.
	 * 
	 * @param str
	 *            The string to return if it is not empty
	 * @param sElse
	 *            The string to return if <code>str</code> is empty
	 * @return <code>str</code> if it is not empty, else <code>sElse</code>
	 */
	protected static String stringOrElse(final String str, final String sElse) {
		if (str.equals("")) {
			return sElse;
		}
		return str;
	}

	/**
	 * Rounds a number to a given number of decimal places.
	 * 
	 * @param x
	 *            the value to round
	 * @param n
	 *            the number of decimal places to round on
	 * @return the value <code>x</code> rounded to <code>n</code> decimal places
	 */
	private static double roundN(final double x, final int n) {
		final int base = 10;
		return Math.round(x * (Math.pow(base, n))) / Math.pow(base, n);
	}

	/**
	 * @return The phylo panel this node is part of.
	 */
	public final PhyloPanel getPhyloPanel() {
		return phyloPanel;
	}

	/**
	 * @return true iff this node collapses all its descendants.
	 */
	public final boolean isCollapsed() {
		return collapsed;
	}

	/**
	 * @param b
	 *            Whether this node should collapse all its descendants.
	 */
	public final void setCollapsed(final boolean b) {
		this.collapsed = b;
	}

	/*
	 * Note: don't use keyword selected! superclass has methods isSelected and
	 * setSelected.
	 */
	/**
	 * @return true iff this node is selected.
	 */
	public abstract boolean isChosen();

	/*
	 * Note: don't use keyword selected! superclass has methods isSelected and
	 * setSelected.
	 */
	/**
	 * Decides what to do when the node gets selected or deselected.
	 * 
	 * @param b
	 *            whether this node is selected.
	 */
	public abstract void setChosen(final boolean b);

	/**
	 * Checks whether a node is a leaf.
	 * 
	 * @return True if the current node is a leaf, false otherwise
	 */
	public abstract boolean isLeaf();

	/**
	 * This method returns a list of all sources accessible from this node.
	 * 
	 * @return A list of sources accessible from this node
	 */
	public List<String> getSources() {
		if (this.isLeaf()) {
			return Arrays.asList(this.getID());
		}
		ArrayList<String> sources = new ArrayList<String>();
		sources.addAll(this.getLeft().getSources());
		sources.addAll(this.getRight().getSources());
		return sources;
	}

	/**
	 * Checks whether a node contains a path to a given source.
	 * 
	 * @param source
	 *            The source to which we search a path
	 * @return true iff there exist a path to the source, false otherwise
	 */
	public boolean contains(final String source) {
		return this.getSources().contains(source);
	}

	/**
	 * This method finds all the groups for a given list of sources.
	 * 
	 * @param sources
	 *            The sources for which we want to find the groups
	 * @param root
	 *            The root node
	 * @return A list of groups for which each group share a common ancestor
	 */
	public Collection<Collection<String>> findGroups(
			final Collection<String> sources, final BinaryTree root) {
		Collection<Collection<String>> groupList = new ArrayList<Collection<String>>();
		while (!sources.isEmpty()) {
			Collection<String> group = findGroup(sources, root);
			groupList.add(group);
			for (String s : group) {
				sources.remove(s);
			}
		}
		return groupList;
	}

	/**
	 * This method finds a group of sources with a common ancestor.
	 * 
	 * @param sources
	 *            The sources for which we want to find a group
	 * @param root
	 *            The root node
	 * @return A list of sources which share a common ancestor
	 */
	public Collection<String> findGroup(final Collection<String> sources,
			final BinaryTree root) {
		if (root.isLeaf()) {
			if (sources.contains(root.getID())) {
				List<String> group = new ArrayList<String>();
				group.add(root.getID());
				return group;
			}
			return null;
		}
		Collection<String> left = findGroup(sources, root.getLeft());
		Collection<String> right = findGroup(sources, root.getRight());
		if (left == null || right == null) {
			if (left == null) {
				return right;
			}
			return left;
		}

		if (root.getLeft().isLeaf() && root.getRight().isLeaf()) {
			left.addAll(right);
			return left;
		} else if (!root.getLeft().isLeaf() && root.getRight().isLeaf()) {
			if (root.getLeft().getSources().equals(left)) {
				left.addAll(right);
			}
			return left;
		} else if (root.getLeft().isLeaf() && root.getRight().isLeaf()) {
			if (root.getRight().getSources().equals(right)) {
				left.addAll(right);
			}
			return left;
		} else {
			if (root.getLeft().getSources().equals(left)
					&& root.getRight().getSources().equals(right)) {
				left.addAll(right);
			}
			return left;
		}
	}

	/**
	 * Calculates the distance between the common ancestor and the root of 2
	 * source strings.
	 * 
	 * @param source1
	 *            The first source
	 * @param source2
	 *            The second source
	 * @param root
	 *            The root node
	 * @return The distance between the common ancestor of 2 sources and the
	 *         root of the tree
	 */
	public double getDistance(final String source1, final String source2,
			final BinaryTree root) {
		return getDistance(Arrays.asList(source1, source2), root);
	}

	/**
	 * Calculates the distance between the common ancestor and the root for a
	 * list of nodes.
	 * 
	 * @param sources
	 *            The list of sources for which we want to calculate the
	 *            distance
	 * @param root
	 *            The root node
	 * @return The distance between the common ancestor of a list of sources and
	 *         the root node
	 */
	public double getDistance(final List<String> sources, final BinaryTree root) {
		if (root.isLeaf()) {
			return 0;
		}
		boolean containsL = false;
		boolean containsR = false;
		Iterator<String> it = sources.iterator();
		while (it.hasNext() && !(containsL && containsR)) {
			String source = it.next();
			containsL = containsL || root.getLeft().contains(source);
			containsR = containsR || root.getRight().contains(source);
		}
		if (containsL && containsR) {
			return 0;
		} else if (containsL) {
			return root.getLeft().getPathLength()
					+ getDistance(sources, root.getLeft());
		} else if (containsR) {
			return root.getRight().getPathLength()
					+ getDistance(sources, root.getRight());
		} else {
			return 0;
		}
	}

	/**
	 * This listener handles the click events on the node button.
	 * 
	 * @author Maarten, Justin
	 * @since 27-5-2015
	 * @version 1.0
	 *
	 */
	class NodeMouseListener implements MouseListener {

		/** Not implemented. */
		@Override
		public void mouseClicked(final MouseEvent e) {
		}

		/** {@inheritDoc} */
		@Override
		public void mousePressed(final MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				setChosen(!isChosen());
				getPhyloPanel().plotTree();
				break;
			case MouseEvent.BUTTON3:
				setCollapsed(!isCollapsed());
				getPhyloPanel().plotTree();
				break;
			default:
				break;
			}
		}

		/** Not implemented. */
		@Override
		public void mouseReleased(final MouseEvent e) {
		}

		/** Not implemented. */
		@Override
		public void mouseEntered(final MouseEvent e) {
		}

		/** Not implemented. */
		@Override
		public void mouseExited(final MouseEvent e) {
		}
	}
}
