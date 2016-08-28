package com.pctrade.price.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {

	private final static String URL = "jdbc:mysql://localhost/PC_TRADE";
	private final static String USER = "root";
	private final static String PASSWORD = "root";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public ConnectionManager() {
		super();
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static void closeDbResources(Connection connection, PreparedStatement preparedStatement) {
		ConnectionManager.closeStatement(preparedStatement);
		ConnectionManager.closeConnection(connection);
	}

	public static void closeDbResources(Connection connection, PreparedStatement preparedStatement,
			ResultSet resultSet) {
		ConnectionManager.closeResultSet(resultSet);
		ConnectionManager.closeStatement(preparedStatement); // или
																// closeStatement(preparedStatement);
																// почему ?
		ConnectionManager.closeConnection(connection);
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Can not close Connection");
			}
		}
	}

	public static void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (Exception e) {
			System.out.println("Can not close Statement");
		}
	}

	public static void closeResultSet(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception e) {
			System.out.println("Can not close ResultSet");
		}
	}
}