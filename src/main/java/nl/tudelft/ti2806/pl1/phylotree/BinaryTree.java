package nl.tudelft.ti2806.pl1.phylotree;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.structureGraphic.v1.DSTreeNode;

/**
 * Generic binary tree, storing data of a parametric data in each node.
 * http://www.cs.dartmouth.edu/~cbk/10/notes/7/code/BinaryTree.java
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 */
public abstract class BinaryTree implements DSTreeNode {

	/**
	 * The distance to the parent node.
	 */
	private final double pathLength;

	/**
	 * The name label of the node.
	 */
	private final String name;

	/**
	 * Constructs leaf node with no children.
	 * 
	 * @param nameIn
	 *            The name of the node
	 * @param length
	 *            The path length
	 */
	public BinaryTree(final String nameIn, final double length) {
		this.name = nameIn;
		this.pathLength = length;
	}

	/**
	 * 
	 * @return the size of the (sub)tree.
	 */
	public abstract int size();

	/**
	 * @return the path length.
	 */
	public final double getPathLength() {
		return pathLength;
	}

	/**
	 * @return the node name.
	 */
	public final String getName() {
		return name;
	}

	/**
	 * 
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

	/***
	 * @return the left child, null if there is no left child
	 */
	public abstract BinaryTree getRight();

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
	 * Copies the entire file content into a single String, and returns it.
	 * 
	 * @param filename
	 *            relative path to the file to read
	 * @return the file content as a continuous string
	 * @throws IOException
	 *             when something goes wrong xD
	 */
	public static String readIntoString(final String filename) {
		final StringBuffer buff = new StringBuffer();
		try {
			final BufferedReader in = new BufferedReader(new FileReader(
					filename));
			String line;
			while ((line = in.readLine()) != null) {
				buff.append(line);
			}
			in.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		return buff.toString();
	}

	/**
	 * @return the value to show in in this node
	 */
	public final Object dsGetValue() {
		final int roundN = 5;
		return stringOrElse(name, "X") + "\n" + roundN(pathLength, roundN);
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
	private static String stringOrElse(final String str, final String sElse) {
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

	/**
	 * Some tree testing.
	 *
	 * @param args
	 *            jwz
	 * @throws IOException
	 *             when something goes wrong xD
	 */
	public static void main(final String[] args) throws IOException {
		// Tree of life
		final String s = readIntoString("src\\main\\resources\\nj_tree_10_strains.nwk");
		final BinaryTree t = parseNewick(s);
		System.out.println(t);
		System.out.println("height:" + t.height());
		System.out.println("size:" + t.size());

		// Smaller trees
		final BinaryTree t1 = parseNewick("((a,b)c,(d,e)f)g;");
		// System.out.println("fringe:" + t1.fringe());

		final BinaryTree t2 = parseNewick("((a,b)c,(d,e)f)g;");
		final BinaryTree t3 = parseNewick("((a,b)z,(d,e)f)g;");
		System.out.println("== " + t1.equalsTree(t2) + " " + t1.equalsTree(t3));
	}

}
