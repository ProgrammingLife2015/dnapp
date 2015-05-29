package nl.tudelft.ti2806.pl1.graphImporter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;

/**
 * The class which imports the database file.
 * 
 * @author mark
 *
 */
public final class GraphImporter {

	/**
	 * The private constructor to avoid instantiating an object.
	 */
	private GraphImporter() {

	}

	/**
	 * The timout on the statement.
	 */
	private static final int TIMEOUT = 30;

	/**
	 * The static method which imports the database.
	 * 
	 * @param location
	 *            The location of the database
	 * @return The DGraph to which the database
	 * @throws ClassNotFoundException
	 *             When the JDBC cannot be loaded
	 */
	public static DGraph importDGraph(final String location)
			throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		DGraph graph = new DGraph();
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + location);
			statement = connection.createStatement();
			statement.setQueryTimeout(TIMEOUT);

			rs = statement.executeQuery("SELECT * FROM nodes");
			while (rs.next()) {
				DNode node = new DNode(rs.getInt("id"), new HashSet<String>(),
						rs.getInt("start"), rs.getInt("end"),
						rs.getString("content"));
				node.setX(rs.getInt("x"));
				node.setY(rs.getInt("y"));
				node.setDepth(rs.getInt("depth"));
				graph.addDNode(node);
			}
			rs.close();
			rs = statement.executeQuery("SELECT * FROM  sources");
			while (rs.next()) {
				graph.addSource(rs.getInt("id"), rs.getString("source"));
			}
			rs.close();
			rs = statement.executeQuery("SELECT * FROM edges");
			while (rs.next()) {
				int startId = rs.getInt("startId");
				int endId = rs.getInt("endId");
				DEdge edge = new DEdge(graph.getDNode(startId),
						graph.getDNode(endId));
				graph.addDEdge(edge);
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
		return graph;
	}
}
