package nl.tudelft.ti2806.pl1.graphWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
	 * Private constructor.
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

			statement.executeUpdate("DROP TABLE IF EXISTS nodes");
			statement.executeUpdate("DROP TABLE IF EXISTS edges");
			statement.executeUpdate("DROP TABLE IF EXISTS sources");
			statement
					.executeUpdate("CREATE TABLE nodes (id integer, start integer, end integer,"
							+ " x integer, y integer, depth integer, content string)");
			statement
					.executeUpdate("CREATE TABLE edges (startId integer, endId integer)");
			statement
					.executeUpdate("CREATE TABLE sources (id integer, source string)");

			// TODO Assumed that start and end node are in the graph
			StringBuilder nodes = new StringBuilder();
			StringBuilder sources = new StringBuilder();
			StringBuilder edges = new StringBuilder();

			nodes.append("INSERT INTO nodes VALUES ");
			sources.append("INSERT INTO sources VALUES ");
			edges.append("INSERT INTO edges VALUES ");

			for (DNode node : graph.getNodes().values()) {
				nodes.append("(" + node.getId() + ", " + node.getStart() + ", "
						+ node.getEnd() + ", " + node.getX() + ", "
						+ node.getY() + ", " + node.getDepth() + ",\""
						+ node.getContent() + "\"),");

				for (String s : node.getSources()) {
					sources.append("(" + node.getId() + ", \"" + s + "\"),");
				}

			}

			for (DEdge edge : graph.getEdges()) {
				edges.append("(" + edge.getStartNode().getId() + ", "
						+ edge.getEndNode().getId() + "),");
			}

			statement.executeUpdate(nodes.substring(0, nodes.length() - 1));
			statement.executeUpdate(sources.substring(0, sources.length() - 1));
			statement.executeUpdate(edges.substring(0, edges.length() - 1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
	}
}
