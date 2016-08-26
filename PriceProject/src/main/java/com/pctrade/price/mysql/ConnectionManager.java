package com.pctrade.price.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private final static String URL = "jdbc:mysql://localhost/PC_TRADE";
	private final static String USER = "root";
	private final static String PASSWORD = "root";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e); // чтобы не надо было ловить и прога стала ибо без драйвера бесполезно
		}
	}

	public ConnectionManager() {
		super();
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println(" Can not close Connection");
			}
		}

	}
}