package in.co.sunrays.util;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;


public class JDBCDataSource {
	private static JDBCDataSource jds = null;
	private ComboPooledDataSource ds = null;

	private JDBCDataSource() {
		try {
			ds = new ComboPooledDataSource();
			ds.setDriverClass("com.mysql.jdbc.Driver");
			ds.setJdbcUrl("jdbc:mysql://localhost:3306/project4");
			ds.setUser("root");
			ds.setPassword("root");
			ds.setAcquireIncrement(5);
			ds.setMaxPoolSize(50);
			ds.setMinPoolSize(2);

		} catch (PropertyVetoException e) {
		}
	}

	public static JDBCDataSource getInstance() {
		if (jds == null) {
			jds = new JDBCDataSource();

		}

		return jds;
	}

	public static Connection getConnection() {
		try {
			return (Connection) getInstance().ds.getConnection();
		} catch (SQLException e) {
			return null;

		}
	}

	// Close connection
	public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if(conn!=null) conn.close();
			
		}catch (SQLException  e) {
			e.printStackTrace();
		}

	}
		//close connection
		 
		public static void closeConnection (Connection conn,Statement stmt) {
			closeConnection(conn,stmt,null);
			}
		//close connection
		public static void closeConnection(Connection conn) {
			closeConnection(conn,null,null);
		}
			
}

