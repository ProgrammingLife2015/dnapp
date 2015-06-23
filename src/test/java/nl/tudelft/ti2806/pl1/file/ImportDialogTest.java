package nl.tudelft.ti2806.pl1.file;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImportDialogTest {

	ExportDialog ed;

	@Before
	public void setUp() throws Exception {
		ed = new ExportDialog();
	}

	@After
	public void tearDown() throws Exception {
		ed = null;
	}

	@Test
	public final void testExportDialogDGS() {
		assertEquals("Choose a folder to write the graph file to",
				ed.getDialogTitle());
	}

}
