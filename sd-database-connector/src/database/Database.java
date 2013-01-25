package database;


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
public String result(ResultSet rs){
	StringBuilder sb = new StringBuilder();
	try {
		rs.last();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		
		for(int i=1; i < count+1; i++){
			sb.append(rs.getString(i) + " ");
			
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return sb.toString();
	
	
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

	public void doStatement(String query) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
			
			int size =0;
			while(rs.next()){
				size++;
				
			}
			if(latest == 0){
				latest = size;
			}
			if(size > latest){
				System.out.println(result(rs));
				latest=size;
				}
			
			


		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
	
}
