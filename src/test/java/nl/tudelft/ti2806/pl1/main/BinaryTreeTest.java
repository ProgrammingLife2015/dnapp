/**
 * 
 */
package nl.tudelft.ti2806.pl1.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Maarten, Justin
 *
 */
public class BinaryTreeTest {

	/**
	 * Path to the input file.
	 */
	private String path;
	/**
	 * Content of the input file in a single string.
	 */
	private String s;
	/**
	 * Binary treed made from the content of the input file.
	 */
	private BinaryTree t;

	/**
	 * Set the variables' values.
	 * 
	 * @throws IOException
	 *             path not found
	 */
	@Before
	public final void setUp() throws IOException {

		// Tree of life
		path = "src\\main\\resources\\nj_tree_10_strains.nwk";
		s = BinaryTree.readIntoString(path);
		t = BinaryTree.parseNewick(s);

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
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testReadIntoString() throws IOException {
		assertEquals(
				BinaryTree.readIntoString(path),
				"((((TKK_04_0031:0.001906661314"
						+ ",((TKK_02_0068:0.00204157229"
						+ ",TKK_02_0018:0.00066954501):0.0006628296031"
						+ ",TKK-01-0026:0.0007123091469):0.001823665486):0.001010665755"
						+ ",TKK-01-0058:0.002881882645):0.001130109667"
						+ ",(TKK_REF:5.660219e-05"
						+ ",TKK-01-0066:0.00011321144):0.004314309633):0.0004752532458"
						+ ",((TKK-01-0015:0.002115260257"
						+ ",TKK-01-0029:0.001554716943):0.001130522483"
						+ ",TKK_04_0002:0.001006393417):0.003898313306);");
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
