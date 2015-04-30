/**
 * 
 */
package nl.tudelft.ti2806.pl1.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Maarten, Justin
 *
 */
public class BinaryTreeNewTest {

	/**
	 * Path to the input file.
	 */
	// private String path;

	/**
	 * Content of the input file in a single string.
	 */
	// private String s;

	/**
	 * Binary treed made from the content of the input file.
	 */
	// private BinaryTree t;

	private String s1;
	private String s2;
	private String s3;

	/**
	 * Set the variables' values.
	 * 
	 * @throws IOException
	 *             path not found
	 */
	@Before
	public final void setUp() throws IOException {
		s1 = "((a,b)c,(d,e)f)g;";
		s2 = "((a,b)c,(d,e)f)g;";
		s3 = "((a,b)z,(d,e)f)g;";
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.main.BinaryTree#hasLeft()}.
	 */
	@Test
	public final void testHasLeft() {
		final BinaryTree t = BinaryTree.parseNewick(s1);
		assertTrue(t.hasLeft());
		assertTrue(t.getLeft().hasLeft());
		assertFalse(t.getRight().getLeft().hasLeft());
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.main.BinaryTree#hasRight()}.
	 */
	@Test
	public final void testHasRight() {
		final BinaryTree t = BinaryTree.parseNewick(s1);
		assertTrue(t.hasRight());
		assertTrue(t.getLeft().hasRight());
		assertFalse(t.getRight().getLeft().hasRight());
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.main.BinaryTree#size()}.
	 */
	@Test
	public final void testSize() {
		final BinaryTree t = BinaryTree.parseNewick(s1);
		assertEquals(7, t.size());
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.main.BinaryTree#height()}.
	 */
	@Test
	public final void testHeight() {
		final BinaryTree t = BinaryTree.parseNewick(s1);
		assertEquals(2, t.height());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.main.BinaryTree#equalsTree(nl.tudelft.ti2806.pl1.main.BinaryTree)}
	 * .
	 */
	@Test
	public final void testEqualsTree() {
		final BinaryTree t1 = BinaryTree.parseNewick(s1);
		final BinaryTree t2 = BinaryTree.parseNewick(s2);
		final BinaryTree t3 = BinaryTree.parseNewick(s3);
		assertTrue(t1.equalsTree(t2));
		assertTrue(t1.equalsTree(t3));
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.main.BinaryTree#toString()}.
	 */
	@Test
	public final void testToString() {
		final BinaryTree t = BinaryTree.parseNewick("((a,b)c,(d,e)f)g;");
		final String expected = "g (dist=0.0)\n" + "\tc (dist=0.0)\n"
				+ "\t\ta (dist=0.0)\n" + "\t\tb (dist=0.0)\n"
				+ "\tf (dist=0.0)\n" + "\t\td (dist=0.0)\n"
				+ "\t\te (dist=0.0)\n";
		assertEquals(expected, t.toString());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.main.BinaryTree#parseNewick(java.lang.String)}
	 * .
	 */
	@Test
	public final void testParseNewick() {
		final BinaryTree t = BinaryTree.parseNewick("((a,b)c,(d,e)f)g;");
		assertEquals("g", t.getName());
		assertEquals("c", t.getLeft().getName());
		assertEquals(0.0, t.getPathLength(), 0.0);
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
				"Bla bla bla test tekst bla bla",
				BinaryTree
						.readIntoString("src\\test\\resources\\readIntoFileTest.txt"));
	}

}
