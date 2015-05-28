package nl.tudelft.ti2806.pl1.phylotree;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.ti2806.pl1.gui.contentpane.PhyloPanel;

/**
 * 
 * @author Maarten
 *
 */
public class Leaf extends BinaryTree {

	/** Serial version UID. */
	private static final long serialVersionUID = -287972955469890107L;

	/** Whether this node is selected. */
	private boolean chosen = false;

	@Override
	public final boolean isChosen() {
		return chosen;
	}

	@Override
	public final void setChosen(final boolean b) {
		this.chosen = b;
	}

	/**
	 * 
	 * @param nameIn
	 *            The name of the node.
	 * @param dist
	 *            The distance to the parent.
	 * @param panel
	 *            The phylo panel this node is part of.
	 */
	public Leaf(final String nameIn, final double dist, final PhyloPanel panel) {
		super(nameIn, dist, panel);
		addMouseListener(new LeafNodeMouseListener());
	}

	/**
	 * @return the number of nodes (inner and leaf) in tree.
	 */
	@Override
	public final int treeSize() {
		return 1;
	}

	@Override
	protected final String toStringHelper(final String indent) {
		String res = indent + stringOrElse(getID(), "X") + " (dist="
				+ getPathLength() + ",x=" + getGridCoordinates().getX() + ",y="
				+ getGridCoordinates().getY() + ")\n";
		return res;
	}

	@Override
	public final boolean equalsTree(final BinaryTree t2) {
		if (!(t2 instanceof Leaf)) {
			return false;
		} else {
			Leaf t2l = (Leaf) t2;
			if (!(getPathLength() == t2l.getPathLength())) {
				return false;
			}
			return true;
		}
	}

	@Override
	public final int height() {
		return 0;
	}

	@Override
	public final boolean hasLeft() {
		return false;
	}

	@Override
	public final boolean hasRight() {
		return false;
	}

	@Override
	public final BinaryTree getLeft() {
		return null;
	}

	@Override
	public final BinaryTree getRight() {
		return null;
	}

	@Override
	public final int computePlacement(final int x, final int y) {
		setGridLoc(x, y);
		return 1;
	}

	@Override
	public final List<BinaryTree> getChildren() {
		return new ArrayList<BinaryTree>(0);
	}

	@Override
	public final Color getBackground() {
		return getColor();
	}

	@Override
	public final Color getForeground() {
		return getColor();
	}

	/**
	 * @return The appropriate color for this leaf based on whether the node is
	 *         chosen.
	 */
	private Color getColor() {
		if (isChosen()) {
			return PhyloPanel.NODE_SELECTED_COLOR;
		}
		return PhyloPanel.DEFAULT_COLOR;
	}

	/**
	 * @author Maarten, Justin
	 * @since 28-5-2015
	 * @version 1.0
	 */
	class LeafNodeMouseListener implements MouseListener {

		/** {@inheritDoc} */
		public void mouseClicked(final MouseEvent e) {
		}

		/** {@inheritDoc} */
		public void mousePressed(final MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				setChosen(!isChosen());
				getPhyloPanel().plotTree();
				break;
			default:
			}
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