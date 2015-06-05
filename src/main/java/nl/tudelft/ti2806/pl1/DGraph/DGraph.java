package nl.tudelft.ti2806.pl1.DGraph;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import nl.tudelft.ti2806.pl1.exceptions.InvalidFileFormatException;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;
import org.neo4j.io.fs.FileUtils;
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

	/**
	 * The amount of read nodes and edges we allow per transaction.
	 */
	private static final int BATCH = 2500;

	/**
	 * The location of the starting point in the text format.
	 */
	public static final int STARTLOCATION = 2;

	/**
	 * The location of the ending point in the text format.
	 */
	public static final int ENDLOCATION = 3;

	/**
	 * The amount of information pieces in the format.
	 */
	public static final int AMOUNTOFINFORMATION = 4;

	/** How long it calculates the index before stopping. **/
	private static final int TIMEOUT = 10;

	/** Id of the first node of the graph. **/
	private int startid;

	/** Id of the last node of the graph. **/
	private int endid;

	/**
	 * Retrieve the neo4j graph on the given location.
	 * 
	 * @param dbPath
	 *            Location of the neo4j graph.
	 * @throws IOException
	 */
	public DGraph(final String dbPath) throws IOException {
		FileUtils.deleteRecursively(new File(dbPath));
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
		createIndexNodes();
		createIndexSources();
	}

	/**
	 * Convert a Node into a DNode.
	 * 
	 * @param noteid
	 *            Id of the node you want to get a DNode for.
	 * @return A Dnode.
	 */
	public DNode getDNode(final int noteid) {
		Node node = getNode(noteid);
		return convertNodeDNode(node);
	}

	/**
	 * Convert a Node into a DNode.
	 * 
	 * @param node
	 *            Node you want to convert to DNode.
	 * @return A DNode.
	 */
	public DNode convertNodeDNode(final Node node) {
		DNode dnode = null;
		try (Transaction tx = graphDb.beginTx()) {
			dnode = convertNodeDNodeNoTransaction(node);
			tx.success();
		}
		return dnode;
	}

	/**
	 * Converts a node to a dnode. This method assumes that it's called in a
	 * transaction.
	 * 
	 * @param node
	 *            The node to be converted to a dnode
	 * @return A converted node
	 */
	protected DNode convertNodeDNodeNoTransaction(final Node node) {
		Collection<Node> sources = getSources(node);
		HashSet<String> dnodesources = new HashSet<String>();
		String content = null;
		List<DEdge> edges = new ArrayList<DEdge>();
		for (Node source : sources) {
			dnodesources.add((String) source.getProperty("source"));
		}
		int id = (int) node.getProperty("id");
		int start = (int) node.getProperty("start");
		int end = (int) node.getProperty("end");
		content = (String) node.getProperty("content");
		edges = getEdges(node);
		int x = (int) node.getProperty("x");
		int y = (int) node.getProperty("y");
		int z = (int) node.getProperty("depth");
		DNode dnode = new DNode(id, dnodesources, start, end, content);
		for (DEdge edge : edges) {
			dnode.addEdge(edge);
		}
		dnode.setX(x);
		dnode.setY(y);
		dnode.setDepth(z);
		return dnode;
	}

	/**
	 * Get InNodes for Node.
	 * 
	 * @param node
	 *            Node you want to get the InEdges for
	 * @return InNodes
	 */
	protected List<DEdge> getEdges(final Node node) {
		List<DEdge> edges = new ArrayList<DEdge>();
		Iterator<Relationship> it = node.getRelationships(RelTypes.NEXT)
				.iterator();
		while (it.hasNext()) {
			Relationship edge = it.next();
			edges.add(new DEdge((int) edge.getStartNode().getProperty("id"),
					(int) edge.getEndNode().getProperty("id")));
		}
		return edges;
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
	 * Adds a node to the graphDb, this method will be used instead of the
	 * private one because this method is safe to use since it starts a new
	 * transaction.
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
		try (Transaction tx = graphDb.beginTx()) {
			addNodeWithoutTransaction(id, start, end, content, coords, depth,
					sources);
		}
	}

	/**
	 * Adds a node to the graphDb, this method does not start a transaction
	 * since it's assumed that the transaction is started in another method.
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
	private void addNodeWithoutTransaction(final int id, final int start,
			final int end, final String content, final Point coords,
			final int depth, final String[] sources) {
		Label label = DynamicLabel.label("Nodes");
		try (Transaction tx = graphDb.beginTx()) {
			Node addNode = graphDb.createNode(label);
			addNode.setProperty("id", id);
			addNode.setProperty("start", start);
			addNode.setProperty("end", end);
			addNode.setProperty("content", content);
			addNode.setProperty("x", coords.x);
			addNode.setProperty("y", coords.y);
			addNode.setProperty("depth", depth);
			tx.success();
		}
		label = DynamicLabel.label("Sources");
		for (String s : sources) {
			if (graphDb.findNode(label, "source", s) == null) {
				Node src = graphDb.createNode(label);
				src.setProperty("source", s);
			}
		}
		addSources(id, sources);
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
		Iterator<Relationship> it;
		try (Transaction tx = graphDb.beginTx()) {
			it = sourcenode.getRelationships().iterator();
			while (it.hasNext()) {
				nodes.add(it.next().getStartNode());
			}
		}
		return nodes;
	}

	/**
	 * Converts all Nodes to a DNode.
	 * 
	 * @return A list of all nodes converted to a DNode
	 */
	public List<DNode> getDNodes() {
		List<DNode> nodes = new ArrayList<DNode>();
		try (Transaction tx = graphDb.beginTx()) {
			ResourceIterator<Node> it = graphDb.findNodes(DynamicLabel
					.label("Nodes"));
			while (it.hasNext()) {
				nodes.add(convertNodeDNode(it.next()));
			}
		}
		return nodes;
	}

	public List<DNode> getDNodes(final int xl, final int xr) {
		List<DNode> nodes = new ArrayList<DNode>();
		try (Transaction tx = graphDb.beginTx()) {
			Result rs = graphDb.execute("MATCH (n:Nodes) WHERE (n.x >= " + xl
					+ " AND n.x <= " + xr + ") RETURN n");
			while (rs.hasNext()) {
				Map<String, Object> row = rs.next();
				nodes.add(convertNodeDNodeNoTransaction((Node) row.get("n")));
			}
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
	 * Change id of the startnode.
	 * 
	 * @param id
	 *            New id of the startnode.
	 */
	public void setStart(final int id) {
		startid = id;
	}

	/**
	 * Change id of the endnode.
	 * 
	 * @param id
	 *            New id of the endnode.
	 */
	public void setEnd(final int id) {
		endid = id;
	}

	/**
	 * Get id of the startnode.
	 * 
	 * @return id of startnode
	 */
	public int getStart() {
		return startid;
	}

	/**
	 * Get id of the endnode.
	 * 
	 * @return id of endnode
	 */
	public int getEnd() {
		return endid;
	}

	/**
	 * Empties the graph.
	 */
	protected void emptygraph() {
		try (Transaction tx = graphDb.beginTx()) {
			for (Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {
				for (Relationship ship : node.getRelationships()) {
					ship.delete();
				}
				node.delete();
			}
		}
	}

	/**
	 * Reads the node from the bufferedreader.
	 * 
	 * @param reader
	 *            The reader which reads the nodes file
	 * @throws IOException
	 */
	public void readNodes(final BufferedReader reader) throws IOException {
		boolean done = false;
		while (!done) {
			done = readNodesInBatch(reader);
		}
		reader.close();
	}

	protected boolean readNodesInBatch(final BufferedReader reader)
			throws IOException {
		Boolean done = false;
		int readNodes = 0;
		try (Transaction tx = graphDb.beginTx()) {
			String nextnode = "";
			while (readNodes < BATCH && (nextnode = reader.readLine()) != null) {
				if (nextnode.charAt(0) == '>') {
					nextnode = nextnode.substring(1);
					String[] data = nextnode.split("\\s\\|\\s");
					if (data.length != AMOUNTOFINFORMATION) {
						throw new InvalidFileFormatException(
								"Missing some information to create this node");
					}
					String content = reader.readLine();
					int id;
					int start;
					int end;
					try {
						id = Integer.parseInt(data[0]);
					} catch (Exception e) {
						throw new InvalidFileFormatException(
								"The id should be an integer");
					}
					try {
						start = Integer.parseInt(data[STARTLOCATION]);
						end = Integer.parseInt(data[ENDLOCATION]);
					} catch (Exception e) {
						throw new InvalidFileFormatException(
								"The start and end reference should be integers");
					}
					if (end - start != content.length()) {
						throw new InvalidFileFormatException(
								"Size of Node content doesn't match with its reference size");
					}
					String[] sources = data[1].split(",");
					addNodeWithoutTransaction(id, start, end, content,
							new Point(0, 0), 0, sources);
					readNodes += 1;
				} else {
					throw new InvalidFileFormatException(
							"Every new node line should start with >");
				}
			}
			done = nextnode == null;
			tx.success();
		}
		return done;
	}

	/**
	 * Reads the edges from the file.
	 * 
	 * @param reader
	 *            The reader used for reading the edges
	 * @throws IOException
	 */
	public void readEdges(final BufferedReader reader) throws IOException {
		boolean done = false;
		while (!done) {
			done = readEdgesInBatch(reader);
		}
		reader.close();
	}

	protected boolean readEdgesInBatch(final BufferedReader reader)
			throws IOException {
		boolean done = false;
		int readEdges = 0;
		Label label = DynamicLabel.label("Nodes");
		try (Transaction tx = graphDb.beginTx()) {
			String line = "";
			while (readEdges < BATCH && (line = reader.readLine()) != null) {
				String[] nodes = line.split("\\s");
				if (nodes.length != 2) {
					throw new InvalidFileFormatException(
							"There should be 2 node id's seperated by spaces in the edge file");
				}
				int start;
				int end;
				try {
					start = Integer.parseInt(nodes[0]);
					end = Integer.parseInt(nodes[1]);
				} catch (Exception e) {
					throw new InvalidFileFormatException(
							"The id's should be integers");
				}
				Node src = graphDb.findNode(label, "id", start);
				Node tar = graphDb.findNode(label, "id", end);
				if (src == null || tar == null) {
					throw new InvalidFileFormatException(
							"The id's shoould exist");
				}
				addEdge(src, tar);
				readEdges += 1;
			}
			done = line == null;
			tx.success();
		}
		return done;
	}
}