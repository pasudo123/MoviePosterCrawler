package edu.doubler.toy.movie.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMaker {
	
	private static final String URL = "jdbc:mysql://localhost:3306/test_db?characterEncoding=UTF-8&serverTimezone=UTC";
	private static final String USER_ID = "doubler";
	private static final String USER_PW = "doublerpass";
	private static Connection connection = null;

	/**
	 * Conenction 획득 메소드
	 * @return
	 */
	public static Connection getConnection() {
		
		try {
			if(connection == null) {
				connection = DriverManager.getConnection(URL, USER_ID, USER_PW);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
}
