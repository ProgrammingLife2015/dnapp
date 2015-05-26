package nl.tudelft.ti2806.pl1.phylotree;

import java.awt.Color;
import java.awt.Point;
import java.util.StringTokenizer;

import javax.swing.JButton;

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

	/** The distance to the parent node. */
	private final double pathLength;

	/** The name label of the node. */
	private final String id;

	/**
	 * 
	 */
	private Point gridCoordinates;

	/**
	 * @return the gridCoordinates
	 */
	public final Point getGridCoordinates() {
		return gridCoordinates;
	}

	/**
	 * Constructs leaf node with no children.
	 * 
	 * @param nameIn
	 *            The name of the node
	 * @param length
	 *            The path length
	 */
	public BinaryTree(final String nameIn, final double length) {
		super(nameIn);
		this.id = nameIn;
		this.pathLength = length;
	}

	/**
	 * @return the path length.
	 */
	public final double getPathLength() {
		return pathLength;
	}

	/**
	 * @return the node name.
	 */
	public final String getID() {
		return id;
	}

	/**
	 * @return the size of the (sub)tree.
	 */
	public abstract int treeSize();

	/**
	 * @return the longest length to a leaf node from here.
	 */
	public abstract int height();

	/**
	 * Checks whether two trees are equal.
	 * 
	 * @param t2
	 *            the tree to compare with.
	 * @return true iff this tree is equal to t2.
	 */
	public abstract boolean equalsTree(final BinaryTree t2);

	/**
	 * @return true iff the node has a left child
	 */
	public abstract boolean hasLeft();

	/**
	 * @return true iff the node has a right child
	 */
	public abstract boolean hasRight();

	/**
	 * @return the left child, null if there is no left child
	 */
	public abstract BinaryTree getLeft();

	/**
	 * @return the left child, null if there is no left child
	 */
	public abstract BinaryTree getRight();

	/**
	 * 
	 * @return
	 */
	public abstract BinaryTree[] getChildren();

	/**
	 * 
	 * @return
	 */
	public abstract int drawTree(int x, int y);

	/**
	 * 
	 * @param x
	 * @param y
	 */
	protected void setGridLoc(final int x, final int y) {
		this.gridCoordinates = new Point(x, y);
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
	 * @return a phylogenetic tree
	 */
	public static BinaryTree parseNewick(final String s) {
		return parseNewick(new StringTokenizer(s, "(,)", true));
	}

	/**
	 * Does the real work of parsing, now given a tokenizer for the string.
	 * 
	 * @param st
	 *            a string tokenizer
	 * @return a phylogenetic tree
	 */
	private static BinaryTree parseNewick(final StringTokenizer st) {
		final String token = st.nextToken();
		if (token.equals("(")) { // Inner node
			final BinaryTree left = parseNewick(st);
			st.nextToken(); // final String comma = st.nextToken();
			final BinaryTree right = parseNewick(st);
			st.nextToken(); // final String close = st.nextToken();
			final String label = st.nextToken();
			final String[] pieces = label.split(":");
			return new InnerNode(parseName(pieces, 0), parseDouble(pieces, 1),
					left, right);
		} else { // Leaf
			final String[] pieces = token.split(":");
			return new Leaf(parseName(pieces, 0), parseDouble(pieces, 1));
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
	 * @return the value to show in in this node
	 */
	public final Object dsGetValue() {
		final int roundN = 5;
		return stringOrElse(id, "X") + "\n" + roundN(pathLength, roundN);
	}

	/**
	 * @return the color of this node
	 */
	public final Color dsGetColor() {
		return Color.black;
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
	 * @return the value x rounded to n decimal places
	 */
	private static double roundN(final double x, final int n) {
		final int base = 10;
		return Math.round(x * (Math.pow(base, n))) / Math.pow(base, n);
	}
}
