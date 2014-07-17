package utils;

import java.sql.Connection;

import db.MysqlDAO;

public class ConnectionFactory {
	private static MysqlDAO dao;

	public static Connection getConnection() throws Exception {
		if (dao == null) {
			dao = new MysqlDAO();
			dao.getConfiguration().setHostAddress("localhost");
			dao.getConfiguration().setDbName("Backoffice");
			dao.getConfiguration().setUserName("root");
			dao.getConfiguration().setPassword("12345");
			dao.connect();
		}
		return dao.getConnection();
	}
}
