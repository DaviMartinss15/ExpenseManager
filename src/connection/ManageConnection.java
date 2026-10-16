package connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import exception.DbException;
public class ManageConnection {
	public static Connection openConnect() throws SQLException {
		Properties props = loadProperties();
		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String password = props.getProperty("db.password");
		return DriverManager.getConnection(url, user, password);
	}

	private static Properties loadProperties() {
		try (InputStream input = ManageConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
			if (input == null) {
				throw new DbException("db.properties file not found in resources folder");
			}
			Properties props = new Properties();
			props.load(input);
			return props;
		} catch (IOException e) {
			throw new DbException("Error loading db.properties: " + e.getMessage());
		}
	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new DbException("Error! can't close the connection because: " + e.getMessage());
		}
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
