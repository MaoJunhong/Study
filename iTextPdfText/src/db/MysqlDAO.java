package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlDAO {

	private DAOConfiguration configuration = new DAOConfiguration();

	private Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://" + configuration.getHostAddress() + "/"
				+ configuration.getDbName()
				+ "?zeroDateTimeBehavior=convertToNull";
		connection = DriverManager.getConnection(url,
				configuration.getUserName(), configuration.getPassword());

		if (!connection.isClosed())
			System.err.println("Succeeded connecting to the Database!");
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		Statement statement = connection.createStatement();
		return statement.executeQuery(sql);
	}

	public void close() throws SQLException {
		if (connection != null) {
			connection.close();
			System.err.println("close datebase success!");
			connection = null;
		}
	}

	public DAOConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(DAOConfiguration configuration) {
		this.configuration = configuration;
	}
}
