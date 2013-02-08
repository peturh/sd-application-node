package sd.web.app.server;


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
	
	private int latest;

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
		latest=0;
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
					"jdbc:mysql://127.0.0.1", userName,
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

	public String doStatement(String query) {
		ResultSet rs = null;
		StringBuilder output = new StringBuilder();
		StringBuilder sb = null;
		try {
			rs = stmt.executeQuery(query);
			//if(changed(rs)){
			rs.last();
			String hex = Integer.toHexString(Integer.parseInt(rs.getString(9)));
			
			output = new StringBuilder();
		    for (int i = 0; i < hex.length(); i+=2) {
		        String str = hex.substring(i, i+2);
		        output.append((char)Integer.parseInt(str, 16));
		    }
		    sb = new StringBuilder();
		    sb.append(rs.getString(9)+"\n");
		    sb.append(hex+"\n");
		    sb.append(output.toString());
		   
			//}
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return sb.toString();

	}
	
}
