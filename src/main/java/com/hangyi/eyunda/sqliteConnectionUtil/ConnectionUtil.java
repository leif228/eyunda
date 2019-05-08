package com.hangyi.eyunda.sqliteConnectionUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import com.hangyi.eyunda.util.Constants;

public class ConnectionUtil {
	private static String driver = "org.sqlite.JDBC";

	public static Connection getConnection(String url) throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + Constants.SHIP_IMAGE + url);
		return con;
	}
	
	public static void close(Connection con, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test(){
		try {
			BasicDataSource dataSource = null;
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName(driver);
			dataSource.setUrl(Constants.SHIP_IMAGE + "/db/lastAt.dhx");
			
			Connection con = dataSource.getConnection();
			System.out.println(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
