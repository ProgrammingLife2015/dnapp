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
public class GraphNodeFileFilterTest {

	GraphNodeFileFilter gnf;

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
		gnf = new GraphNodeFileFilter();
		// directory.isd
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
	public final void testAcceptValidEdge() {
		assertFalse(gnf.accept(validEdge1));
		assertFalse(gnf.accept(validEdge2));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter#accept(java.io.File)}
	 * .
	 */
	@Test
	public final void testAcceptInvalidEdgeFile() {
		assertFalse(gnf.accept(invalidEdge1));
		assertFalse(gnf.accept(invalidEdge2));
		assertFalse(gnf.accept(invalidEdge3));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter#accept(java.io.File)}
	 * .
	 */
	@Test
	public final void testAcceptInvalidNodeFile() {
		assertFalse(gnf.accept(invalidNode1));
		assertFalse(gnf.accept(invalidNode2));
		assertFalse(gnf.accept(invalidNode3));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter#accept(java.io.File)}
	 * .
	 */
	@Test
	public final void testAcceptInvalidGeneralFile() {
		assertFalse(gnf.accept(invalid1));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter#accept(java.io.File)}
	 * .
	 */
	@Test
	public final void testAcceptNodeFile() {
		assertTrue(gnf.accept(validNode1));
		assertTrue(gnf.accept(validNode2));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter#accept(java.io.File)}
	 * .
	 */
	@Test
	public final void testAcceptFolderFile() {
		assertTrue(gnf.accept(folder));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2806.pl1.file.GraphEdgeFileFilter#getDescription()}.
	 */
	@Test
	public final void testGetDescription() {
		assertEquals("Graph nodes file", gnf.getDescription());
	}

}
