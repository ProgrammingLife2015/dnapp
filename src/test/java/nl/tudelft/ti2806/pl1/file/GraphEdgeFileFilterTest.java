package nl.tudelft.ti2806.pl1.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Maarten
 * @since 23-6-2015
 */
public class GraphEdgeFileFilterTest {

	GraphEdgeFileFilter gef;

	File validNode1 = new File("ns.node.graph");
	File validNode2 = new File(".node.graph");
	File invalidNode1 = new File("ns.node");
	File invalidNode2 = new File("node.graph");
	File invalidNode3 = new File("node.graph.");

	File validEdge1 = new File("ns.edge.graph");
	File validEdge2 = new File(".edge.graph");
	File invalidEdge1 = new File("ns.edge");
	File invalidEdge2 = new File("edge.graph");
	File invalidEdge3 = new File("edge.graph.");

	File invalid1 = new File("ns.graph");

	File folder = new File("src/test/resources/foldeur");

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gef = new GraphEdgeFileFilter();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter#accept(java.io.File)}
	 * .
	 */
	@Test
	public final void testAcceptValidFile() {
		assertTrue(gef.accept(validEdge1));
		assertTrue(gef.accept(validEdge2));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter#accept(java.io.File)}
	 * .
	 */
	@Test
	public final void testAcceptInvalidEdgeFile() {
		assertFalse(gef.accept(invalidEdge1));
		assertFalse(gef.accept(invalidEdge2));
		assertFalse(gef.accept(invalidEdge3));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter#accept(java.io.File)}
	 * .
	 */
	@Test
	public final void testAcceptInvalidNodeFile() {
		assertFalse(gef.accept(invalidNode1));
		assertFalse(gef.accept(invalidNode2));
		assertFalse(gef.accept(invalidNode3));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter#accept(java.io.File)}
	 * .
	 */
	@Test
	public final void testAcceptInvalidGeneralFile() {
		assertFalse(gef.accept(invalid1));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter#accept(java.io.File)}
	 * .
	 */
	@Test
	public final void testAcceptNodeFile() {
		assertFalse(gef.accept(validNode1));
		assertFalse(gef.accept(validNode2));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter#accept(java.io.File)}
	 * .
	 */
	@Test
	public final void testAcceptFolderFile() {
		assertTrue(gef.accept(folder));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter#getDescription()}.
	 */
	@Test
	public final void testGetDescription() {
		assertEquals("Graph edges file", gef.getDescription());
	}

}
