/**
 * 
 */
package nl.tudelft.ti2806.pl1.phylotree;

import org.structureGraphic.v1.DSTreeParser;
import org.structureGraphic.v1.DSutils;
import org.structureGraphic.v1.TreePrinter;

/**
 * @author Maarten
 *
 */
public final class PhyloTreeMain {

	/**
	 * 
	 */
	private PhyloTreeMain() {
	}

	/**
	 * 
	 * @param args
	 *            jwz
	 */
	public static void main(final String[] args) {

		final int nodeWidth = 80;
		final int nodeHeight = 40;

		// mxGraph mxg = new mxGraph();
		String path = "src\\main\\resources\\nj_tree_10_strains.nwk";
		BinaryTree dstn;

		dstn = BinaryTree.parseNewick(BinaryTree.readIntoString(path));
		// dstn = BinaryTree.parseNewick("((a,b)c,(d,e)f)g;");

		System.out.println(dstn);
		TreePrinter.print(dstn);
		DSutils.show(DSTreeParser.from(dstn), nodeWidth, nodeHeight);
	}
}
