package nl.tudelft.ti2806.pl1.main;

import org.StructureGraphic.v1.DSTreeNode;

/**
 * 
 * @author Maarten
 *
 */
public class Leaf extends BinaryTree {

	/**
	 * 
	 * @param nameIn
	 *            The name of the node.
	 * @param dist
	 *            The distance to the parent.
	 */
	public Leaf(final String nameIn, final double dist) {
		super(nameIn, dist);
	}

	/**
	 * @return the number of nodes (inner and leaf) in tree.
	 */
	@Override
	public final int size() {
		return 1;
	}

	/**
	 * @return an empty list of Binary Trees.
	 */
	public final DSTreeNode[] DSgetChildren() {
		return new BinaryTree[] {};
	}

	@Override
	protected final String toStringHelper(final String indent) {
		String res = indent + (getName().equals("") ? "X" : getName())
				+ " (dist=" + getPathLength() + ")\n";
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

}