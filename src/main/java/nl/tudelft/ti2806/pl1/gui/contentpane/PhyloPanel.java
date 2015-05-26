package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import nl.tudelft.ti2806.pl1.gui.Event;
import nl.tudelft.ti2806.pl1.gui.Window;
import nl.tudelft.ti2806.pl1.phylotree.BinaryTree;

/**
 * @author Maarten
 *
 */
public class PhyloPanel extends JScrollPane {

	/** The serial version UID. */
	private static final long serialVersionUID = -1936473122898892804L;

	/** The window this panel is part of. */
	private Window window;

	/**
	 * 
	 */
	private BinaryTree tree;

	/**
	 * 
	 */
	private String newickSource;

	/**
	 * 
	 */
	private GridBagConstraints gbc = new GridBagConstraints();

	/**
	 * 
	 */
	private JPanel treePanel;

	/**
	 * Initializes the panel.
	 * 
	 * @param w
	 *            The window this panel is part of.
	 */
	public PhyloPanel(final Window w) {
		this.window = w;
		treePanel = new JPanel();
		treePanel.setLayout(new GridBagLayout());
		setViewportView(treePanel);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
	}

	/**
	 * 
	 * @param newick
	 *            The Newick file to load.
	 * @return true iff the tree was loaded successfully.
	 */
	public final boolean loadTree(final File newick) {
		boolean ret = true;
		try {
			this.newickSource = readIntoString(newick);
		} catch (IOException e) {
			ret = false;
			Event.statusBarError("File " + newick.getAbsolutePath()
					+ " could not be read.");
			e.printStackTrace();
		}
		this.tree = BinaryTree.parseNewick(newickSource);
		// add(treePanel, BorderLayout.CENTER);
		// drawTree(tree, 0);
		tree.drawTree(0, 0);
		System.out.println(tree.toString());
		addNode(tree);
		// add(treePanel);
		return ret;
	}

	/**
	 * 
	 * @param tree
	 */
	private void addNode(final BinaryTree tree) {
		gbc.gridx = (int) tree.getGridCoordinates().getX();
		gbc.gridy = (int) tree.getGridCoordinates().getY();
		treePanel.add(tree, gbc);
		for (BinaryTree t : tree.getChildren()) {
			addNode(t);
		}
	}

	// /**
	// *
	// * @param tree
	// */
	// private final int drawTree(final BinaryTree tree, final int x) {
	//
	// return 0;
	// }

	/**
	 * Copies the entire file content into a single String, and returns it.
	 * 
	 * @param file
	 *            The file to read.
	 * @return the file content as a continuous string
	 * @throws IOException
	 *             when something goes wrong xD
	 */
	public static String readIntoString(final File file) throws IOException {
		final StringBuffer buff = new StringBuffer();

		final BufferedReader in = new BufferedReader(new FileReader(file));
		String line;
		while ((line = in.readLine()) != null) {
			buff.append(line);
		}
		in.close();

		return buff.toString();
	}

}
