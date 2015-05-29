package nl.tudelft.ti2806.pl1.graphImport;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.graphImporter.GraphImporter;
import nl.tudelft.ti2806.pl1.graphWriter.GraphWriter;
import nl.tudelft.ti2806.pl1.reader.Reader;

import org.junit.Before;
import org.junit.Test;

public class GraphImportTest {

	DGraph read;
	DGraph imported;

	@Before
	public void setup() throws IOException, ClassNotFoundException {
		read = Reader.read("src/main/resources/simple_graph.node.graph",
				"src/main/resources/simple_graph.edge.graph");
		GraphWriter.write("test.db", read);
		imported = GraphImporter.importDGraph("test.db");
	}

	@Test
	public void sameNodesTest() {
		assertTrue(read.getNodes().equals(imported.getNodes()));
	}

	@Test
	public void sameEdgesTest() {
		assertTrue(read.getEdges().equals(imported.getEdges()));
	}
}
