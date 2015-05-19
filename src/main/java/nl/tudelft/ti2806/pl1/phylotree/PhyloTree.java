package nl.tudelft.ti2806.pl1.phylotree;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 * @author Maarten
 *
 */
public class PhyloTree extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1247433052336330302L;

	/**
	 * 
	 */
	public PhyloTree() {
		super();
	}

	/**
	 * @param value
	 */
	public PhyloTree(final Object[] value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param value
	 */
	public PhyloTree(final Vector<?> value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param value
	 */
	public PhyloTree(final Hashtable<?, ?> value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param root
	 */
	public PhyloTree(final TreeNode root) {
		super(root);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param newModel
	 */
	public PhyloTree(final TreeModel newModel) {
		super(newModel);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param root
	 * @param asksAllowsChildren
	 */
	public PhyloTree(final TreeNode root, final boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
		// TODO Auto-generated constructor stub
	}

}
