package au.edu.unsw.cse.topfeeds.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

import au.edu.unsw.cse.topfeeds.action.AddFacebook;

public class DatabaseConnection {
	private static Logger log = Logger.getLogger(DatabaseConnection.class);

	private DatabaseConnection() {
		// NOT ALLOWED EXTERNALLY
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/topfeeds", "topfeed", "pass");
			log.debug("Database connection established");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
