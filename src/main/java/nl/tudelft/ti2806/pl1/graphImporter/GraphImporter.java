package nl.tudelft.ti2806.pl1.graphImporter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;

public class GraphImporter {

	private static int TIMEOUT = 30;

	public static DGraph importDGraph (final String location) {
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + location);
			Statement statement = connection.createStatement();
		    statement.setQueryTimeout(TIMEOUT);
		}
	}
}
