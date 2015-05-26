package nl.tudelft.ti2806.pl1.phylotree;

/**
 * 
 * @author Maarten
 *
 */
public class Leaf extends BinaryTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = -287972955469890107L;

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
	public final int treeSize() {
		return 1;
	}

	@Override
	protected final String toStringHelper(final String indent) {
		String res = indent + stringOrElse(getID(), "X") + " (dist="
				+ getPathLength() + ")\n";
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
	public final int drawTree(final int x, final int y) {
		setGridLoc(x, y);
		return 1;
	}

	@Override
	public final BinaryTree[] getChildren() {
		return new BinaryTree[] {};
	}

}