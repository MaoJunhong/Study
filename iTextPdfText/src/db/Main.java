package db;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
	
	public static void main(String[] args) throws Throwable {
		MysqlDAO dao = new MysqlDAO();
		dao.getConfiguration().setHostAddress("localhost");
		dao.getConfiguration().setDbName("Backoffice");
		dao.getConfiguration().setUserName("root");
		dao.getConfiguration().setPassword("12345");
		dao.connect();

		PrintStream out = new PrintStream(new FileOutputStream("countries.txt"));
		Connection conn = dao.getConnection();
		PreparedStatement pre = conn
				.prepareStatement("SELECT COUNTRY FROM `FILM_COUNTRY` ORDER BY COUNTRY");
		pre.execute();
		ResultSet rs = pre.getResultSet();
		while (rs.next()) {
			out.println(rs.getString("COUNTRY"));
		}
		pre.close();
		conn.close();
		out.flush();
		out.close();
	}

}
