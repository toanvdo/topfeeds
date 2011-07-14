package au.edu.unsw.cse.topfeeds.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

	private DatabaseConnection() {
		// NOT ALLOWED EXTERNALLY
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/topfeeds", "topfeed", "pass");
			System.out.println("Database connection established");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
