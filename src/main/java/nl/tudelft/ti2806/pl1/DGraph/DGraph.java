package nl.tudelft.ti2806.pl1.DGraph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;
import org.neo4j.tooling.GlobalGraphOperations;

/**
 * The DGraph class for representing our data.
 * 
 * @author Marissa, Mark
 * @since 27-05-15
 */
public class DGraph {

	/** The neo4j database. **/
	private GraphDatabaseService graphDb;

	/** How long it calculates the index before stopping. **/
	private static final int TIMEOUT = 10;

	/**
	 * Retrieve the neo4j graph on the given location.
	 * 
	 * @param dbPath
	 *            Location of the neo4j graph.
	 */
	public DGraph(final String dbPath) {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
		createIndexNodes();
		createIndexSources();
	}

	/**
	 * Add index on id.
	 */
	protected void createIndexNodes() {
		IndexDefinition indexDefinition = null;
		Label label = DynamicLabel.label("Nodes");
		boolean containsIndex = false;
		try (Transaction tx = graphDb.beginTx()) {
			Schema schema = graphDb.schema();
			containsIndex = schema.getIndexes(label).iterator().hasNext();
			if (!containsIndex) {
				indexDefinition = schema.indexFor(label).on("id").create();
			}
			tx.success();
		}
		if (!containsIndex) {
			try (Transaction tx = graphDb.beginTx()) {
				Schema schema = graphDb.schema();
				schema.awaitIndexOnline(indexDefinition, TIMEOUT,
						TimeUnit.SECONDS);
				tx.success();
			}
		}
	}

	/**
	 * Add index on sources.
	 */
	protected void createIndexSources() {
		IndexDefinition indexDefinition = null;
		Label label = DynamicLabel.label("Sources");
		boolean containsIndex = false;
		try (Transaction tx = graphDb.beginTx()) {
			Schema schema = graphDb.schema();
			containsIndex = schema.getIndexes(label).iterator().hasNext();
			if (!containsIndex) {
				indexDefinition = schema.indexFor(label).on("source").create();
			}
			tx.success();
		}
		if (!containsIndex) {
			try (Transaction tx = graphDb.beginTx()) {
				Schema schema = graphDb.schema();
				schema.awaitIndexOnline(indexDefinition, TIMEOUT,
						TimeUnit.SECONDS);
				tx.success();
			}
		}
	}

	/**
	 * 
	 * @param id
	 *            The id of the Node.
	 * @param start
	 *            The place of the reference of the first base in the node.
	 * @param end
	 *            The place of the reference of the last base in the node.
	 * @param content
	 *            The content of the Node.
	 * @param coords
	 *            The coordinates of the Node in the graph.
	 * @param depth
	 *            The depth of the Node in the graph.
	 * @param sources
	 *            The sources of the Node.
	 */
	public void addNode(final int id, final int start, final int end,
			final String content, final Point coords, final int depth,
			final String[] sources) {
		Label label = DynamicLabel.label("Nodes");
		try (Transaction tx = graphDb.beginTx()) {
			Node addNode = graphDb.createNode(label);
			addNode.setProperty("id", id);
			addNode.setProperty("start", start);
			addNode.setProperty("end", end);
			addNode.setProperty("content", content);
			addNode.setProperty("x", coords.x);
			addNode.setProperty("y", coords.y);
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
		try (Transaction tx = graphDb.beginTx()) {
			addSources(id, sources);
		}
	}

	/**
	 * Adds sources to the Node.
	 * 
	 * @param nodeId
	 *            Id of the node we want to add a source to.
	 * @param sources
	 *            Sources we want to add to the Node.
	 */
	protected void addSources(final int nodeId, final String[] sources) {
		Node node = getNode(nodeId);
		for (String s : sources) {
			Node source = getSource(s);
			node.createRelationshipTo(source, RelTypes.SOURCE);
		}
	}

	/**
	 * A node containing the source, having an incoming edge with all of its
	 * nodes.
	 * 
	 * @param s
	 *            The source we want to get the node from.
	 * @return Node containing the source.
	 */
	protected Node getSource(final String s) {
		Label label = DynamicLabel.label("Sources");
		Node node = null;
		try (Transaction tx = graphDb.beginTx()) {
			node = graphDb.findNode(label, "source", s);
			tx.success();
		}
		return node;
	}

	/**
	 * Lets us get sources from a given node.
	 * 
	 * @param node
	 *            Node we want to get the sources from.
	 * @return Sources from this node.
	 */
	public Collection<Node> getSources(final Node node) {
		Collection<Node> nodes = new ArrayList<Node>();
		try (Transaction tx = graphDb.beginTx()) {
			Iterator<Relationship> it = node.getRelationships(
					Direction.OUTGOING, RelTypes.SOURCE).iterator();
			while (it.hasNext()) {
				nodes.add(it.next().getEndNode());
			}
			tx.success();
		}
		return nodes;
	}

	/**
	 * Get all the nodes pointing to sources.
	 * 
	 * @return Source nodes for all the nodes.
	 */
	public Collection<Node> getSources() {
		Label label = DynamicLabel.label("Sources");
		Collection<Node> nodes = new ArrayList<Node>();
		try (Transaction tx = graphDb.beginTx()) {
			ResourceIterator<Node> it = graphDb.findNodes(label);
			while (it.hasNext()) {
				nodes.add(it.next());
			}
			tx.success();
		}
		return nodes;
	}

	/**
	 * List of nodes with a given source.
	 * 
	 * @param source
	 *            The source of which we want to get the nodes
	 * @return List of nodes with the given source.
	 */
	public List<Node> getNodes(final String source) {
		Node sourcenode = getSource(source);
		List<Node> nodes = new ArrayList<Node>();
		Iterator<Relationship> it = sourcenode.getRelationships().iterator();
		while (it.hasNext()) {
			nodes.add(it.next().getEndNode());
		}
		return nodes;
	}

	/**
	 * Takes a list of nodes, and sorts them according to their start property.
	 * 
	 * @param nodes
	 *            List of nodes to sort.
	 * @return Sorted nodes.
	 */
	public List<Node> sortNodes(final List<Node> nodes) {
		nodes.sort(new Comparator<Node>() {
			@Override
			public int compare(final Node n1, final Node n2) {
				int start1 = (int) n1.getProperty("start");
				int start2 = (int) n2.getProperty("start");
				return start1 - start2;
			}
		});
		return nodes;
	}

	/**
	 * Get a Node from the graph.
	 * 
	 * @param id
	 *            Id of the Node we want to get.
	 * @return Node in the graph.
	 */
	public Node getNode(final int id) {
		Label label = DynamicLabel.label("Nodes");
		Node node = null;
		try (Transaction tx = graphDb.beginTx()) {
			node = graphDb.findNode(label, "id", id);
			tx.success();
		}
		return node;
	}

	/**
	 * Get all Nodes in the graph.
	 * 
	 * @return All the Nodes in the Graph.
	 */
	public Collection<Node> getNodes() {
		Collection<Node> nodes = new ArrayList<Node>();
		try (Transaction tx = graphDb.beginTx()) {
			ResourceIterator<Node> it = graphDb.findNodes(DynamicLabel
					.label("Nodes"));
			while (it.hasNext()) {
				nodes.add(it.next());
			}
		}
		return nodes;
	}

	/**
	 * Add an edge from node1 to node2.
	 * 
	 * @param node1
	 *            The Node we want to add an edge to.
	 * @param node2
	 *            The Node the edge is going to.
	 */
	public void addEdge(final Node node1, final Node node2) {
		node1.createRelationshipTo(node2, RelTypes.NEXT);
	}

	/**
	 * Add edge from node with nodeId1 to node with nodeId2.
	 * 
	 * @param nodeId1
	 *            Id of the node to which we want to add the node.
	 * @param nodeId2
	 *            Id of the node the edge is going to.
	 */
	public void addEdge(final int nodeId1, final int nodeId2) {
		Node n1 = getNode(nodeId1);
		Node n2 = getNode(nodeId2);
		n1.createRelationshipTo(n2, RelTypes.NEXT);
	}

	/**
	 * Registers a shutdown hook for the Neo4j instance so that it shuts down
	 * nicely when the VM exits (even if you "Ctrl-C" the running application).
	 * 
	 * @param graphDb
	 */
	public void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

	/**
	 * Empties the graph.
	 */
	protected void emptygraph() {
		for (Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {
			for (Relationship ship : node.getRelationships()) {
				ship.delete();
			}
		}
	}
}