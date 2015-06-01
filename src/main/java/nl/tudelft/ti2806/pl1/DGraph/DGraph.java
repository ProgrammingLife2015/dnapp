package nl.tudelft.ti2806.pl1.DGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
		NEXT, SOURCE;
	}

	public DGraph(final String db_path) {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(db_path);
		addUniqueConstraint();
		createIndexNodes();
		createIndexSources();
	}

	protected void addUniqueConstraint() {
		try (Transaction tx = graphDb.beginTx()) {
			graphDb.schema().constraintFor(DynamicLabel.label("Nodes"))
					.assertPropertyIsUnique("id").create();
			tx.success();
		}
		try (Transaction tx = graphDb.beginTx()) {
			graphDb.schema().constraintFor(DynamicLabel.label("Sources"))
					.assertPropertyIsUnique("source").create();
			tx.success();
		}
	}

	protected void createIndexNodes() {
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
			tx.success();
		}
	}

	protected void createIndexSources() {
		IndexDefinition indexDefinition;
		try (Transaction tx = graphDb.beginTx()) {
			Schema schema = graphDb.schema();
			indexDefinition = schema.indexFor(DynamicLabel.label("Sources"))
					.on("source").create();
			tx.success();
		}
		try (Transaction tx = graphDb.beginTx()) {
			Schema schema = graphDb.schema();
			schema.awaitIndexOnline(indexDefinition, 10, TimeUnit.SECONDS);
			tx.success();
		}
	}

	public void addNode(final int id, final int start, final int end,
			final String content, final int x, final int y, final int depth,
			final String[] sources) {
		Label label = DynamicLabel.label("Nodes");
		try (Transaction tx = graphDb.beginTx()) {
			Node addNode = graphDb.createNode(label);
			addNode.setProperty("id", id);
			addNode.setProperty("start", start);
			addNode.setProperty("end", end);
			addNode.setProperty("content", content);
			addNode.setProperty("x", x);
			addNode.setProperty("y", y);
			addNode.setProperty("dpeth", depth);
			tx.success();
		}
		label = DynamicLabel.label("Sources");
		for (String s : sources) {
			try (Transaction tx = graphDb.beginTx()) {
				if (graphDb.findNode(label, "source", s) == null) {
					Node src = graphDb.createNode(label);
					src.setProperty("source", s);
				}
				tx.success();
			}
		}
		addSources(id, sources);
	}

	protected void addSources(final int nodeId, final String[] sources) {
		Node node = getNode(nodeId);
		for (String s : sources) {
			Node source = getSource(s);
			node.createRelationshipTo(source, type)
		}
	}

	protected Node getSource(final String s) {
		Label label = DynamicLabel.label("Sources");
		Node node = null;
		try (Transaction tx = graphDb.beginTx()) {
			node = graphDb.findNode(label, "source", s);
			tx.success();
		}
		return node;
	}

	public Node getNode(final int id) {
		Label label = DynamicLabel.label("Nodes");
		Node node = null;
		try (Transaction tx = graphDb.beginTx()) {
			node = graphDb.findNode(label, "id", id);
			tx.success();
		}
		return node;
	}

	public Collection<Node> getNodes() {
		Collection<Node> nodes = new ArrayList<Node>();
		try (Transaction tx = graphDb.beginTx()) {
			Iterator<Node> it = graphDb.getAllNodes().iterator();
			while (it.hasNext()) {
				nodes.add(it.next());
			}
		}
		return nodes;
	}

	public void addEdge(final Node node1, final Node node2) {
		node1.createRelationshipTo(node2, RelTypes.NEXT);
	}

	public void addEdge(final int nodeId1, final int nodeId2) {
		Node n1 = getNode(nodeId1);
		Node n2 = getNode(nodeId2);
		n1.createRelationshipTo(n2, RelTypes.NEXT);
	}
}