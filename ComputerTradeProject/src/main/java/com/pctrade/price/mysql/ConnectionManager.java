package com.pctrade.price.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.pctrade.price.utils.ConfigUtils;

public class ConnectionManager {

//	private DataSource dataSource;
	private final String dbDriver;
	private final String dbUrl;
	private final String dbUser;
	private final String dbPassword;
	
	private static class HolderManager {
		private final static ConnectionManager connectionManager = new ConnectionManager();
	}	
	
	private ConnectionManager() {	
		Properties properties = ConfigUtils.getInstance().getPropertiesValues();	
		dbDriver = properties.getProperty("mysql.db_driver");
		dbUrl = properties.getProperty("mysql.db_url");
		dbUser = properties.getProperty("mysql.db_user");
		dbPassword = properties.getProperty("mysql.db_password");		
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
//	private ConnectionManager() {
//		try {
//			Context initContext = new InitialContext();
//			Context rootContext = (Context) initContext.lookup("java:comp/env");
//			dataSource = (DataSource) rootContext.lookup("jdbc/pc_trade_mysql");
//		} catch (NamingException e) {
//			throw new RuntimeException("Some errors occurred during DataSource lookup!", e);
//		}
//	}

	public static ConnectionManager getManager() {
		return HolderManager.connectionManager;
	}
		
	private List<Connection> connectionList = new ArrayList<Connection>();

	public Connection getConnection() {		
		if (connectionList.isEmpty()) {
			try {
//				return dataSource.getConnection();
				return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return connectionList.remove(0);
	}

	public void closeDbResources(Connection connection, PreparedStatement preparedStatement) {
		closeDbResources(connection, preparedStatement, null);
	}

	public void closeDbResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		closeResultSet(resultSet);
		closeStatement(preparedStatement);
		closeConnection(connection);
	}

	public void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Can not close Connection");
			}
		}
	}

	public void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (Exception e) {
			System.out.println("Can not close Statement");
		}
	}

	public void closeResultSet(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception e) {
			System.out.println("Can not close ResultSet");
		}
	}
}