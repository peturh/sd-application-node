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

			
			System.out.println("Lyckades foixa procyx");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.print("Found driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://172.30.41.98", userName,
					password);
			System.out.println("Connected");
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

public boolean changed(ResultSet rs){
	int rows =0;
		try {
			if (rs.last()) {
			    rows = rs.getRow();
			    
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
if(latest==0){
	latest=rows;
	return false;
}else if(latest == rows){
	return false;
}else if(rows > latest){
	latest=rows;
	return true;
}
	
	
	return false;
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
		StringBuilder sb = new StringBuilder();
		try {
			rs = stmt.executeQuery(query);
			if(changed(rs)){
			rs.last();
			sb.append(rs.getString(9)+" \n" );
			String hex = Integer.toHexString(Integer.parseInt(rs.getString(9)));
			
			StringBuilder output = new StringBuilder();
		    for (int i = 0; i < hex.length(); i+=2) {
		        String str = hex.substring(i, i+2);
		        output.append((char)Integer.parseInt(str, 16));
		    }
		    System.out.println("Decimal number is "+rs.getString(9));
		    System.out.println("HEX number is "+hex);
		    System.out.println("Message is "+output);
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return sb.toString();

	}
	
}