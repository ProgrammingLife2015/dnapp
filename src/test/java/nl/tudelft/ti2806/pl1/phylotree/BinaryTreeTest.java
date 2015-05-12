/**
 * 
 */
package nl.tudelft.ti2806.pl1.phylotree;

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
public class BinaryTreeTest {

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
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.phylotree.BinaryTree#hasLeft()}.
	 */
	@Test
	public final void testHasLeft() {
		final BinaryTree t = BinaryTree.parseNewick(s1);
		assertTrue(t.hasLeft());
		assertTrue(t.getLeft().hasLeft());
		assertFalse(t.getRight().getLeft().hasLeft());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.phylotree.BinaryTree#hasRight()}.
	 */
	@Test
	public final void testHasRight() {
		final BinaryTree t = BinaryTree.parseNewick(s1);
		assertTrue(t.hasRight());
		assertTrue(t.getLeft().hasRight());
		assertFalse(t.getRight().getLeft().hasRight());
	}

	/**
	 * Test method for {@link nl.tudelft.ti2806.pl1.phylotree.BinaryTree#size()}
	 * .
	 */
	@Test
	public final void testSize() {
		final BinaryTree t = BinaryTree.parseNewick(s1);
		assertEquals(7, t.size());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.phylotree.BinaryTree#height()}.
	 */
	@Test
	public final void testHeight() {
		final BinaryTree t = BinaryTree.parseNewick(s1);
		final BinaryTree root = BinaryTree.parseNewick("root");
		assertEquals(2, t.height());
		assertEquals(0, root.height());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.phylotree.BinaryTree#equalsTree(nl.tudelft.ti2806.pl1.phylotree.BinaryTree)}
	 * .
	 */
	@Test
	public final void testEqualsTree() {
		final BinaryTree t1 = BinaryTree.parseNewick(s1);
		final BinaryTree t2 = BinaryTree.parseNewick(s2);
		final BinaryTree t3 = BinaryTree.parseNewick(s3);
		final BinaryTree t4 = BinaryTree.parseNewick("(a,b)z");
		final BinaryTree t5 = BinaryTree.parseNewick("(a:1.0,b:1.0)z:1.0");
		final BinaryTree t6 = BinaryTree.parseNewick("(a:1.0,b:0.0)z:1.0");
		assertTrue(t1.equalsTree(t2));
		assertTrue(t1.equalsTree(t3));
		assertFalse(t1.equalsTree(t4));
		assertFalse(t5.equalsTree(t6));
		assertFalse(t4.getLeft().equalsTree(t4));
		assertEquals(t4.getLeft().getLeft(), null);
		assertEquals(t4.getRight().getRight(), null);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.phylotree.BinaryTree#toString()}.
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
	 * {@link nl.tudelft.ti2806.pl1.phylotree.BinaryTree#parseNewick(java.lang.String)}
	 * .
	 */
	@Test
	public final void testParseNewick() {
		final BinaryTree t = BinaryTree.parseNewick("((a,b)c,(d,e)f)g;");
		final BinaryTree t2 = BinaryTree
				.parseNewick("((((a:0.01,((b:0.2,c:0.3):0.4,d:0.5):0.6)"
						+ ":0.7,e:0.8):0.9,(f:0.11,g:0.12)"
						+ ":0.13):0.14,((h:0.15,i:0.15)"
						+ ":0.16,j:0.17):0.18);");
		assertEquals("g", t.getName());
		assertEquals("c", t.getLeft().getName());
		assertEquals(0.0, t.getPathLength(), 0.0);
		assertEquals("", t2.getName());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.phylotree.BinaryTree#readIntoString(java.lang.String)}
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
