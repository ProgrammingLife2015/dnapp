/**
 * 
 */
package nl.tudelft.ti2806.pl1.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2806.pl1.file.ImportDialog.ImportType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Maarten
 *
 */
public class ExportDialogTest {
	ImportDialog idG, idK, idN, idP, idE;

	@Before
	public void setUp() throws Exception {
		idG = new ImportDialog(ImportType.GENE_ANNOTATION);
		idK = new ImportDialog(ImportType.KNOWN_RES_MUT);
		idN = new ImportDialog(ImportType.NODES);
		idP = new ImportDialog(ImportType.PHYLO);
		idE = new ImportDialog(ImportType.EDGES);
	}

	@After
	public void tearDown() throws Exception {
		idG = null;
		idK = null;
		idN = null;
		idP = null;
		idE = null;
	}

	@Test
	public final void testImportDialogNodes() {
		assertEquals("Load node file", idN.getDialogTitle());
		assertTrue(idN.getFileFilter() instanceof GraphNodeFileFilter);
	}

	@Test
	public final void testImportDialogEdges() {
		assertEquals("Load edge file", idE.getDialogTitle());
		assertTrue(idE.getFileFilter() instanceof GraphEdgeFileFilter);
	}

	@Test
	public final void testImportDialogPhylo() {
		assertEquals("Load phylogenetic tree file", idP.getDialogTitle());
	}

	@Test
	public final void testImportDialogGeneAnnotation() {
		assertEquals("Load gene annotation", idG.getDialogTitle());
	}

	@Test
	public final void testImportDialogResMuts() {
		assertEquals("Load resistance causing mutations file.",
				idK.getDialogTitle());
	}

}
