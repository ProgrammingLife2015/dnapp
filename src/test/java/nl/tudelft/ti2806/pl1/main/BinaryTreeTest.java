/**
 * 
 */
package nl.tudelft.ti2806.pl1.main;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Maarten, Justin
 *
 */
public class BinaryTreeTest {

	@Before
	public final void setUp() throws Exception {

		// Tree of life
		final String s = BinaryTree
				.readIntoString("src\\main\\resources\\nj_tree_10_strains.nwk");
		final BinaryTree t = BinaryTree.parseNewick(s);
		System.out.println(t);
		System.out.println("height:" + t.height());
		System.out.println("size:" + t.size());

		// Smaller trees
		final BinaryTree t1 = BinaryTree.parseNewick("((a,b)c,(d,e)f)g;");
		System.out.println("fringe:" + t1.fringe());

		final BinaryTree t2 = BinaryTree.parseNewick("((a,b)c,(d,e)f)g;");
		final BinaryTree t3 = BinaryTree.parseNewick("((a,b)z,(d,e)f)g;");
		System.out.println("== " + t1.equalsTree(t2) + " " + t1.equalsTree(t3));

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.main.BinaryTree#BinaryTree(java.lang.String, double)}
	 * .
	 */
	@Test
	public final void testBinaryTreeStringDouble() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.main.BinaryTree#BinaryTree(java.lang.String, double, nl.tudelft.ti2806.pl1.main.BinaryTree, nl.tudelft.ti2806.pl1.main.BinaryTree)}
	 * .
	 */
	@Test
	public final void testBinaryTreeStringDoubleBinaryTreeBinaryTree() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.main.BinaryTree#isInner()}.
	 */
	@Test
	public final void testIsInner() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.main.BinaryTree#isLeaf()}.
	 */
	@Test
	public final void testIsLeaf() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.main.BinaryTree#hasLeft()}.
	 */
	@Test
	public final void testHasLeft() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.main.BinaryTree#hasRight()}.
	 */
	@Test
	public final void testHasRight() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.main.BinaryTree#size()}.
	 */
	@Test
	public final void testSize() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.main.BinaryTree#height()}.
	 */
	@Test
	public final void testHeight() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.main.BinaryTree#equalsTree(nl.tudelft.ti2806.pl1.main.BinaryTree)}
	 * .
	 */
	@Test
	public final void testEqualsTree() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.main.BinaryTree#fringe()}.
	 */
	@Test
	public final void testFringe() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.main.BinaryTree#addToFringe(java.util.ArrayList)}
	 * .
	 */
	@Test
	public final void testAddToFringe() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.main.BinaryTree#toString()}.
	 */
	@Test
	public final void testToString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.main.BinaryTree#toStringHelper(java.lang.String)}
	 * .
	 */
	@Test
	public final void testToStringHelper() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.main.BinaryTree#parseNewick(java.lang.String)}
	 * .
	 */
	@Test
	public final void testParseNewickString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.main.BinaryTree#parseNewick(java.util.StringTokenizer)}
	 * .
	 */
	@Test
	public final void testParseNewickStringTokenizer() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.main.BinaryTree#readIntoString(java.lang.String)}
	 * .
	 */
	@Test
	public final void testReadIntoString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.main.BinaryTree#main(java.lang.String[])}.
	 */
	@Test
	public final void testMain() {
		fail("Not yet implemented"); // TODO
	}

}
