package backend;


import java.sql.*;

public abstract class DbConnection {

	private int serverLocation = 0;

	public DbConnection() {
		getServerInfo();
	}

	private void getServerInfo() {
		serverLocation = 1;
		// Later to work with own db
		// serverLocation = 0;
	}
	
	

	private String getServerURL() {
		String url = null;
		if (serverLocation == 1) {
			return "jdbc:mysql://172.30.41.98:3306";
		} else {
			return "jdbc:mysql://127.0.0.1:3306";
		}
	}

	protected Connection getConn() {
		Connection conn = null;

		String url = getServerURL();

		String driver = "com.mysql.jdbc.Driver";
		String user = "dm";
		String pass = "654321";


		// System.out.println("connection url: " + url);

		try {

			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, user, pass);

		} catch (Exception e) {

			// error
			System.err.println("Mysql Connection Error: ");

			// for debugging error
			e.printStackTrace();
		}

		if (conn == null) {
			System.out.println("~~~~~~~~~~ can't get a Mysql connection");
		}

		return conn;
	}

	protected static int getResultSetSize(ResultSet resultSet) {
		int size = -1;

		try {
			resultSet.last();
			size = resultSet.getRow();
			resultSet.beforeFirst();
		} catch (SQLException e) {
			return size;
		}

		return size;
	}

}
