package nl.tudelft.ti2806.pl1.phylotree;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.ti2806.pl1.gui.contentpane.PhyloPanel;

/**
 * 
 * @author Maarten
 *
 */
public class InnerNode extends BinaryTree {

	/** Serial version UID. */
	private static final long serialVersionUID = 4003391741650507864L;

	/** The children of the node. */
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
	 * @param panel
	 *            The phylo panel this node is part of.
	 */
	public InnerNode(final String nameIn, final double dist,
			final BinaryTree leftChild, final BinaryTree rightChild,
			final PhyloPanel panel) {
		super(nameIn, dist, panel);
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
	public final int treeSize() {
		int num = 1;
		if (hasLeft()) {
			num += left.treeSize();
		}
		if (hasRight()) {
			num += right.treeSize();
		}
		return num;
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

	@Override
	protected final String toStringHelper(final String indent) {
		String res = indent + stringOrElse(getID(), "X") + " (dist="
				+ getPathLength() + ",x=" + getGridCoordinates().getX() + ",y="
				+ getGridCoordinates().getY() + ")\n";
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
	public final Color getBackground() {
		if (isCollapsed()) {
			return PhyloPanel.INNER_NODE_COLLAPSED;
		}
		return PhyloPanel.INNER_NODE_NORMAL;
	}

	@Override
	public final Color getForeground() {
		if (isChosen()) {
			return PhyloPanel.NODE_SELECTED_COLOR;
		}
		return PhyloPanel.DEFAULT_COLOR;
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

	@Override
	public final int computePlacement(final int x, final int y) {
		setGridLoc(x, y);
		if (isCollapsed()) {
			return 1;
		} else {
			int lWidth = left.computePlacement(x + 1, y);
			int rWidth = right.computePlacement(x + 1, y + lWidth);
			return lWidth + rWidth;
		}
	}

	@Override
	public final List<BinaryTree> getChildren() {
		ArrayList<BinaryTree> ret = new ArrayList<BinaryTree>(2);
		if (left != null) {
			ret.add(left);
		}
		if (right != null) {
			ret.add(right);
		}
		return ret;
	}

	@Override
	public final boolean isChosen() {
		for (BinaryTree child : getChildren()) {
			if (!child.isChosen()) {
				return false;
			}
		}
		return getChildren().size() > 0;
	}

	@Override
	public final void setChosen(final boolean b) {
		for (BinaryTree child : getChildren()) {
			child.setChosen(b);
		}
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

}
