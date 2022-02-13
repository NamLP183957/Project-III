package jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = SQLServerConnUtils_SQLJDBC.getSQLServerConnection();
//			conn = SQLServerConnUtils_JTDS.getSQLServerConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static void closeQuietly(Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
		}
	}

	public static void rollbackQuietly(Connection conn) {
		try {
			conn.rollback();
		} catch (Exception e) {
		}
	}

    public static void main(String[] args) {
    	try {
			Connection conn = ConnectionUtils.getConnection();
			System.out.println(conn);
			Connection conn2 = ConnectionUtils.getConnection();
			System.out.println(conn2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}