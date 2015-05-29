import java.io.IOException;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.graphImporter.GraphImporter;
import nl.tudelft.ti2806.pl1.graphWriter.GraphWriter;
import nl.tudelft.ti2806.pl1.reader.NodePlacer;
import nl.tudelft.ti2806.pl1.reader.Reader;

public class test {

	public static void main(final String[] args) throws ClassNotFoundException,
			IOException {
		long time = System.currentTimeMillis();
		DGraph graph = Reader.read("src/main/resources/big-graph.graph",
				"src/main/resources/big-edge.graph");
		long end = System.currentTimeMillis() - time;
		System.out.println("Reading the 100 strains graph took: " + end
				+ " milliseconds");
		time = System.currentTimeMillis();
		NodePlacer.place(graph);
		end = System.currentTimeMillis() - time;
		System.out.println("Placing the 100 strains nodes took: " + end
				+ " milliseconds");
		time = System.currentTimeMillis();
		GraphWriter.write("test.db", graph);
		end = System.currentTimeMillis() - time;
		System.out.println("Writing the 100 strains graph to a database took: "
				+ end + " milliseconds");
		time = System.currentTimeMillis();
		GraphImporter.importDGraph("test.db");
		end = System.currentTimeMillis() - time;
		System.out
				.println("importing the 100 strains graph from a database took: "
						+ end + " milliseconds");

	}
}
