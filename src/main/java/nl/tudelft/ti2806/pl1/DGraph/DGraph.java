package nl.tudelft.ti2806.pl1.DGraph;

import java.util.concurrent.TimeUnit;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;

/**
 * The DGraph class for representing our data.
 * 
 * @author Mark
 *
 */
public class DGraph {

	private GraphDatabaseService graphDb;

	private static enum RelTypes implements RelationshipType {
		NEXT
	}

	public DGraph(final String db_path) {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(db_path);
		createIndex();
	}

	public void createIndex() {
		IndexDefinition indexDefinition;
		try (Transaction tx = graphDb.beginTx()) {
			Schema schema = graphDb.schema();
			indexDefinition = schema.indexFor(DynamicLabel.label("Nodes"))
					.on("id").create();
			tx.success();
		}
		try (Transaction tx = graphDb.beginTx()) {
			Schema schema = graphDb.schema();
			schema.awaitIndexOnline(indexDefinition, 10, TimeUnit.SECONDS);
		}
	}

	public void addNode(final int id, final int start, final int end,
			final String content, final int x, final int y, final int depth,
			final String... sources) {
		try (Transaction tx = graphDb.beginTx()) {
			Label label = DynamicLabel.label("Nodes");
			Node addNode = graphDb.createNode(label);
			addNode.setProperty("id", id);
			addNode.setProperty("start", start);
			addNode.setProperty("end", end);
			addNode.setProperty("content", content);
			addNode.setProperty("x", x);
			addNode.setProperty("y", y);
			addNode.setProperty("dpeth", depth);
			addNode.setProperty("sources", sources);
		}
	}
}