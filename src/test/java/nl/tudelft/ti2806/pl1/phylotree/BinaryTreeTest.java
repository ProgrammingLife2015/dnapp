/**
 * 
 */
package nl.tudelft.ti2806.pl1.phylotree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.tudelft.ti2806.pl1.gui.contentpane.PhyloPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Maarten, Justin
 *
 */
public class BinaryTreeTest {

	private String s1;
	private String s2;
	private String s3;
	private String s4;

	/**
	 * Set the variables' values.
	 * 
	 * @throws IOException
	 *             path not found
	 */
	@Before
	public final void setUp() {
		s1 = "((a,b)c,(d,e)f)g;";
		s2 = "((a,b)c,(d,e)f)g;";
		s3 = "((a,b)z,(d,e)f)g;";
		s4 = "(A:1,((B:3,C:3)c:2,D:2)b:1)a;";
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
		final BinaryTree t = BinaryTree.parseNewick(s1, null);
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
		final BinaryTree t = BinaryTree.parseNewick(s1, null);
		assertTrue(t.hasRight());
		assertTrue(t.getLeft().hasRight());
		assertFalse(t.getRight().getLeft().hasRight());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.phylotree.BinaryTree#treeSize()} .
	 */
	@Test
	public final void testSize() {
		final BinaryTree t = BinaryTree.parseNewick(s1, null);
		assertEquals(7, t.treeSize());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.phylotree.BinaryTree#height()}.
	 */
	@Test
	public final void testHeight() {
		final BinaryTree t = BinaryTree.parseNewick(s1, null);
		final BinaryTree root = BinaryTree.parseNewick("root", null);
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
		final BinaryTree t1 = BinaryTree.parseNewick(s1, null);
		final BinaryTree t2 = BinaryTree.parseNewick(s2, null);
		final BinaryTree t3 = BinaryTree.parseNewick(s3, null);
		final BinaryTree t4 = BinaryTree.parseNewick("(a,b)z", null);
		final BinaryTree t5 = BinaryTree
				.parseNewick("(a:1.0,b:1.0)z:1.0", null);
		final BinaryTree t6 = BinaryTree
				.parseNewick("(a:1.0,b:0.0)z:1.0", null);
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
		final BinaryTree t = BinaryTree.parseNewick("((a,b)c,(d,e)f)g;", null);
		final String expected = "g (dist=0.0,x=0.0,y=0.0)\n"
				+ "\tc (dist=0.0,x=0.0,y=0.0)\n"
				+ "\t\ta (dist=0.0,x=0.0,y=0.0)\n"
				+ "\t\tb (dist=0.0,x=0.0,y=0.0)\n"
				+ "\tf (dist=0.0,x=0.0,y=0.0)\n"
				+ "\t\td (dist=0.0,x=0.0,y=0.0)\n"
				+ "\t\te (dist=0.0,x=0.0,y=0.0)\n";
		assertEquals(expected, t.toString());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.phylotree.BinaryTree#parseNewick(java.lang.String)}
	 * .
	 */
	@Test
	public final void testParseNewick() {
		final BinaryTree t = BinaryTree.parseNewick("((a,b)c,(d,e)f)g;", null);
		final BinaryTree t2 = BinaryTree.parseNewick(
				"((((a:0.01,((b:0.2,c:0.3):0.4,d:0.5):0.6)"
						+ ":0.7,e:0.8):0.9,(f:0.11,g:0.12)"
						+ ":0.13):0.14,((h:0.15,i:0.15)"
						+ ":0.16,j:0.17):0.18);", null);
		assertEquals("g", t.getID());
		assertEquals("c", t.getLeft().getID());
		assertEquals(0.0, t.getPathLength(), 0.0);
		assertEquals("", t2.getID());
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
		assertEquals("Bla bla bla test tekst bla bla",
				PhyloPanel.readIntoString(new File(
						"src/test/resources/readIntoFileTest.txt")));
	}

	/**
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testGetCenter() {
		final InnerNode t = (InnerNode) BinaryTree.parseNewick(
				"((a,b)c,(d,e)f)g;", null);
		assertEquals(t.getCenter(), new Point(0, 0));
	}

	@Test
	public final void testComputePlacement() {
		PhyloPanel pp = new PhyloPanel(null);
		pp.loadTree(new File("src/test/resources/phylotree.txt"));
		BinaryTree t = pp.getTree();
		assertEquals(t.computePlacement(0, 0), 4);
		t.setCollapsed(true);
		t.getPhyloPanel().plotTree();
		assertEquals(t.computePlacement(0, 0), 1);
	}

	@Test
	public void containsTrueTest() {
		BinaryTree tree = BinaryTree.parseNewick(s4, null);
		assertTrue(tree.contains("C"));
	}

	@Test
	public void containsFalseTest() {
		BinaryTree tree = BinaryTree.parseNewick(s4, null);
		assertFalse(tree.getRight().contains("A"));
	}

	@Test
	public void distanceTest() {
		BinaryTree tree = BinaryTree.parseNewick(s4, null);
		assertTrue(tree.getDistance("B", "C", tree) == 3);
	}

	@Test
	public void distanceRootLeafTest() {
		BinaryTree tree = BinaryTree.parseNewick(s4, null);
		assertTrue(tree.getDistance("B", "C", tree.getLeft()) == 0);
	}

	@Test
	public void distanceCommonAncestorIsRootTest() {
		BinaryTree tree = BinaryTree.parseNewick(s4, null);
		assertTrue(tree.getDistance("A", "C", tree) == 0);
	}

	@Test
	public void distanceNonExistingSourceIsZeroTest() {
		BinaryTree tree = BinaryTree.parseNewick(s4, null);
		assertTrue(tree.getDistance(Arrays.asList("hello world"), tree) == 0);
	}

	@Test
	public void getGroups2GroupsTest() {
		BinaryTree tree = BinaryTree.parseNewick(s4, null);
		List<List<String>> groups = new ArrayList<List<String>>();
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("A", "B", "C"));
		groups.add(Arrays.asList("A"));
		groups.add(Arrays.asList("B", "C"));
		assertEquals(groups, BinaryTree.findGroups(list, tree));
	}

	@Test
	public void getGroups1GroupTest() {
		BinaryTree tree = BinaryTree.parseNewick(s4, null);
		List<List<String>> groups = new ArrayList<List<String>>();
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("A", "B", "C", "D"));
		groups.add(Arrays.asList("A", "B", "C", "D"));
		assertEquals(groups, BinaryTree.findGroups(list, tree));
	}
}
