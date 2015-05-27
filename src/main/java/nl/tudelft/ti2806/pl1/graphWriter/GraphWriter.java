package nl.tudelft.ti2806.pl1.graphWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;

/**
 * Writes the graph to a file. File format will be
 * 
 * <amound of nodes> nodes with attributes <amount of edges> edges edges will be
 * here
 * 
 * @author mark
 */
public final class GraphWriter {

	/**
	 * 
	 */
	private GraphWriter() {
	}

	/**
	 * The timeout.
	 */
	public static final int TIMEOUT = 30; // set timeout to 30 sec.

	/**
	 * Writes the dgraph to an sqlite database.
	 * 
	 * @param location
	 *            The location of the database
	 * @param graph
	 *            The graph which will be written to a database.
	 * @throws ClassNotFoundException
	 *             When de JDBC class cannot be loaded
	 */
	public static void write(final String location, final DGraph graph)
			throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + location);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(TIMEOUT);

			statement
					.executeUpdate("DROP TABLE IF EXISTS nodes OR edges OR sources");
			statement
					.executeUpdate("CREATE TABLE node (id integer, start integer, end integer,"
							+ " x integer, y integer, depth integer)");
			statement
					.executeUpdate("CREATE TABLE edges (startId integer, endId integer)");
			statement
					.executeUpdate("CREATE TABLE sources (nodeId integer, reference string)");

			// TODO Assumed that start and end node are in the graph
			for (DNode node : graph.getNodes().values()) {
				statement.executeUpdate("INSERT INTO nodes VALUES ("
						+ node.getId() + ", " + node.getStart() + ", "
						+ node.getEnd() + ", " + node.getX() + ", "
						+ node.getY() + ", " + node.getDepth() + ")");

				for (String s : node.getSources()) {
					statement.executeUpdate("INSERT INTO sources VALUES ("
							+ node.getId() + ", " + s + ")");
				}
			}

			for (DEdge edge : graph.getEdges()) {
				statement.executeUpdate("INSERT INTO edges VALUES ("
						+ edge.getStartNode().getId() + ", "
						+ edge.getEndNode().getId() + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
