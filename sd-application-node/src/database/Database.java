package database;

import java.util.ArrayList;
import java.sql.*;

/**
 * Database is a class that specifies the interface to the movie database. Uses
 * JDBC and the MySQL Connector/J driver.
 */
public class Database {
	/**
	 * The database connection.
	 */
	private Connection conn;

	/**
	 * An SQL statement object.
	 */
	private Statement stmt;

	/**
	 * Create the database interface object. Connection to the database is
	 * performed later.
	 */
	public Database() {
		conn = null;
	}

	/**
	 * Open a connection to the database, using the specified user name and
	 * password.
	 * 
	 * @param userName
	 *            The user name.
	 * @param password
	 *            The user's password.
	 * @return true if the connection succeeded, false if the supplied user name
	 *         and password were not recognized. Returns false also if the JDBC
	 *         driver isn't found.
	 */
	public boolean openConnection(String userName, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://172.30.41.98/", userName,
					password);
			
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Close the connection to the database.
	 */
	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
		}
		conn = null;
	}

	/**
	 * Check if the connection to the database has been established
	 * 
	 * @return true if the connection has been established
	 */
	public boolean isConnected() {
		return conn != null;
	}

	public void doStatement(String query){
		ResultSet rs = null;
		try {
			rs= stmt.executeQuery(query);
			
			  
			  
			  
			  while (rs.next()) {
				  System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + " " + rs.getString(3) + " " 
						  + " " + rs.getString(4) + " " + " " + rs.getString(5) + " " + " " + rs.getString(6) + " " 
						  + " " + rs.getString(7) + " " + " " + rs.getString(8) + " " + " " + rs.getString(9));
				  
				  }
			  stmt.close();
			  rs.close();
			  conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	

		
	
	

}
}