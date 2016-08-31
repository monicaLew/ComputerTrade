package com.pctrade.price.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {
	private final static ConnectionManager connectionManager = new ConnectionManager();

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

	public static ConnectionManager getManager() {
		return connectionManager;
	}

	private List<Connection> connectionList = new ArrayList<Connection>();

	public Connection getConnection() {
		if (connectionList.isEmpty()) {
			try {
				return DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return connectionList.remove(0);
	}

	public void close(Connection connection) {
		connectionList.add(connection);
	}

	public static void closeDbResources(Connection connection, PreparedStatement preparedStatement) {
		closeDbResources(connection, preparedStatement, null);
	}

	public static void closeDbResources(Connection connection, PreparedStatement preparedStatement,
			ResultSet resultSet) {
		closeResultSet(resultSet);
		closeStatement(preparedStatement);
		closeConnection(connection);
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