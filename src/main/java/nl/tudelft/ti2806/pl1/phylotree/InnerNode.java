package nl.tudelft.ti2806.pl1.phylotree;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	 */
	public InnerNode(final String nameIn, final double dist,
			final BinaryTree leftChild, final BinaryTree rightChild,
			final PhyloPanel panel) {
		super(nameIn, dist, panel);
		this.left = leftChild;
		this.right = rightChild;
		addMouseListener(new NodeMouseListener());
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
		super.getBackground();
		if (isCollapsed()) {
			return Color.RED;
		}
		return Color.GREEN;
	}

	@Override
	public final Color getForeground() {
		super.getForeground();
		if (isCollapsed()) {
			return Color.RED;
		}
		return Color.GREEN;
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
	public final BinaryTree[] getChildren() {
		return new BinaryTree[] { left, right };
	}

	/**
	 * 
	 * @author Maarten, Justin
	 * @since 27-5-2015
	 * @version 1.0
	 *
	 */
	class NodeMouseListener implements MouseListener {

		/** {@inheritDoc} */
		public void mouseClicked(final MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				System.out.println("Button1");
				break;
			case MouseEvent.BUTTON3:
				System.out.println("Button3");
				setCollapsed(!isCollapsed());
				getPhyloPanel().plotTree();
				break;
			default:
				System.out.println("Other button: " + e.getButton());
				break;
			}
		}

		/** {@inheritDoc} */
		public void mousePressed(final MouseEvent e) {
		}

		/** {@inheritDoc} */
		public void mouseReleased(final MouseEvent e) {
		}

		/** {@inheritDoc} */
		public void mouseEntered(final MouseEvent e) {
		}

		/** {@inheritDoc} */
		public void mouseExited(final MouseEvent e) {
		}

	}
}
