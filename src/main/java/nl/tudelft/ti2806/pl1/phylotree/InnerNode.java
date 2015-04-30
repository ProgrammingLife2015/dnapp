package nl.tudelft.ti2806.pl1.phylotree;

import org.structureGraphic.v1.DSTreeNode;

/**
 * 
 * @author Maarten
 *
 */
public class InnerNode extends BinaryTree {

	/**
	 * The children of the node.
	 */
	private final BinaryTree left, right;

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
	public InnerNode(final String nameIn, final double dist,
			final BinaryTree leftChild, final BinaryTree rightChild) {
		super(nameIn, dist);
		this.left = leftChild;
		this.right = rightChild;
	}

	/**
	 * @return the left child
	 */
	@Override
	public final BinaryTree getLeft() {
		return left;
	}

	/**
	 * @return the right child
	 */
	@Override
	public final BinaryTree getRight() {
		return right;
	}

	/**
	 * @return the number of nodes (inner and leaf) in tree.
	 */
	@Override
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
	@Override
	public final boolean hasLeft() {
		return left != null;
	}

	/**
	 * @return true iff this node has a right child
	 */
	@Override
	public final boolean hasRight() {
		return right != null;
	}

	/**
	 * @return an array of the children of this node
	 */
	public final DSTreeNode[] dsGetChildren() {
		return new BinaryTree[] { left, right };
	}

	@Override
	protected final String toStringHelper(final String indent) {
		String res = indent + (getName().equals("") ? "X" : getName())
				+ " (dist=" + getPathLength() + ")\n";
		if (hasLeft()) {
			res += left.toStringHelper(indent + "\t");
		}
		if (hasRight()) {
			res += right.toStringHelper(indent + "\t");
		}
		return res;
	}

	@Override
	public final boolean equalsTree(final BinaryTree t2) {
		if (!(t2 instanceof InnerNode)) {
			return false;
		} else {
			InnerNode t2i = (InnerNode) t2;
			if (hasLeft() != t2i.hasLeft() || hasRight() != t2i.hasRight()) {
				return false;
			}
			if (!(getPathLength() == t2i.getPathLength())) {
				return false;
			}
			if (hasLeft() && !left.equalsTree(t2i.left)) {
				return false;
			}
			if (hasRight() && !right.equalsTree(t2i.right)) {
				return false;
			}
			return true;
		}
	}

	@Override
	public final int height() {
		int h = 0;
		if (hasLeft()) {
			h = Math.max(h, left.height());
		}
		if (hasRight()) {
			h = Math.max(h, right.height());
		}
		return h + 1; // inner: one higher than highest child
	}

}
