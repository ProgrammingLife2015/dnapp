package nl.tudelft.ti2806.pl1.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Generic binary tree, storing data of a parametric data in each node.
 * http://www.cs.dartmouth.edu/~cbk/10/notes/7/code/BinaryTree.java
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 */
public class BinaryTree {

	/**
	 * 
	 */
	private final BinaryTree left, right; // children; can be null

	/**
	 * 
	 */
	private final double pathLength;

	/**
	 * 
	 */
	private String name;

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
		this.left = null;
		this.right = null;
	}

	/**
	 * Constructs inner node.
	 * 
	 * @param nameIn
	 *            The name of the node
	 * @param dist
	 *            The distance to the parent.
	 * @param leftChild
	 *            The left child.
	 * @param rightChild
	 *            The right child.
	 */
	public BinaryTree(final String nameIn, final double dist,
			final BinaryTree leftChild, final BinaryTree rightChild) {
		this.name = nameIn;
		this.pathLength = dist;
		this.left = leftChild;
		this.right = rightChild;
	}

	/**
	 * @return true iff this is an inner node
	 */
	public final boolean isInner() {
		return left != null || right != null;
	}

	/**
	 * @return true iff this is a leaf
	 */
	public final boolean isLeaf() {
		return left == null && right == null;
	}

	/**
	 * @return true iff this node has a left child
	 */
	public final boolean hasLeft() {
		return left != null;
	}

	/**
	 * @return true iff this node has a right child
	 */
	public final boolean hasRight() {
		return right != null;
	}

	/**
	 * @return the number of nodes (inner and leaf) in tree.
	 */
	public final int size() {
		int num = 1;
		if (hasLeft()) {
			num += left.size();
		}
		if (hasRight()) {
			num += right.size();
		}
		return num;
	}

	/**
	 * 
	 * @return the longest length to a leaf node from here
	 */
	public final int height() {
		if (isLeaf()) {
			return 0;
		}
		int h = 0;
		if (hasLeft()) {
			h = Math.max(h, left.height());
		}
		if (hasRight()) {
			h = Math.max(h, right.height());
		}
		return h + 1; // inner: one higher than highest child
	}

	/**
	 * Checks whether two trees are equal.
	 * 
	 * @param t2
	 *            the tree to compare with
	 * @return true iff this tree is equal to t2
	 */
	public final boolean equalsTree(final BinaryTree t2) {
		if (hasLeft() != t2.hasLeft() || hasRight() != t2.hasRight()) {
			return false;
		}
		if (!(pathLength == t2.pathLength)) {
			return false;
		}
		if (hasLeft() && !left.equalsTree(t2.left)) {
			return false;
		}
		if (hasRight() && !right.equalsTree(t2.right)) {
			return false;
		}
		return true;
	}

	/**
	 * @return leaves in order from left to right
	 */
	public final ArrayList<String> fringe() {
		final ArrayList<String> f = new ArrayList<String>();
		addToFringe(f);
		return f;
	}

	/**
	 * Helper for fringe, adding fringe data to the list
	 */

	/**
	 * Adds this node to a list if it is a leave, else the same recursively for
	 * its two children.
	 * 
	 * @param fringe
	 *            the list to add to
	 */
	public final void addToFringe(final ArrayList<String> fringe) {
		if (isLeaf()) {
			fringe.add(name);
		} else {
			if (hasLeft()) {
				left.addToFringe(fringe);
			}
			if (hasRight()) {
				right.addToFringe(fringe);
			}
		}
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
	public final String toStringHelper(final String indent) {
		String res = indent + (name.equals("") ? "X" : name) + " (dist="
				+ pathLength + ")\n";
		if (hasLeft()) {
			res += left.toStringHelper(indent + "\t");
		}
		if (hasRight()) {
			res += right.toStringHelper(indent + "\t");
		}
		return res;
	}

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
		final BinaryTree t = parseNewick(new StringTokenizer(s, "(,)", true));
		// Get rid of the semicolon
		t.name = t.name.substring(0, t.name.length() - 1);
		return t;
	}

	/**
	 * Does the real work of parsing, now given a tokenizer for the string.
	 * 
	 * @param st
	 *            a string tokenizer
	 * @return a phylogenetic tree
	 */
	public static BinaryTree parseNewick(final StringTokenizer st) {
		final String token = st.nextToken();
		if (token.equals("(")) {
			// Inner node
			final BinaryTree left = parseNewick(st);
			st.nextToken(); // final String comma = st.nextToken();
			final BinaryTree right = parseNewick(st);
			st.nextToken(); // final String close = st.nextToken();
			final String label = st.nextToken();
			final String[] pieces = label.split(":");
			System.out.println("label = " + label + " | split = "
					+ Arrays.toString(pieces));
			return new BinaryTree(pieces[0], parseDouble(pieces, 1), left,
					right);
		} else {
			// Leaf
			final String[] pieces = token.split(":");
			return new BinaryTree(pieces[0], parseDouble(pieces, 1));
		}
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
	public static String readIntoString(final String filename)
			throws IOException {
		final StringBuffer buff = new StringBuffer();
		final BufferedReader in = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = in.readLine()) != null) {
			buff.append(line);
		}
		in.close();
		return buff.toString();
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
		System.out.println("fringe:" + t1.fringe());

		final BinaryTree t2 = parseNewick("((a,b)c,(d,e)f)g;");
		final BinaryTree t3 = parseNewick("((a,b)z,(d,e)f)g;");
		System.out.println("== " + t1.equalsTree(t2) + " " + t1.equalsTree(t3));
	}
}