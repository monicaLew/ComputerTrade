package com.pctrade.price.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pctrade.price.entity.Car;
import com.pctrade.price.mysql.ConnectionManager;

public class DaoCarImpl implements DaoCar {

	private static final String SELECT_ALL_CARS = "SELECT `ID`, `PAGE_CODE`, `ARTICLE`, `PRICE_BYN`, `PRICE_EUR`, `YEAR`, `STATUS` FROM CAR";
	private static final String SELECT_CARS_BY_STATUS = "SELECT `ID`, `PAGE_CODE`, `ARTICLE`, `PRICE_BYN`, `PRICE_EUR`, `YEAR`, `STATUS` FROM CAR WHERE STATUS = 'AVAILABLE'";
	private static final String INSERT_INTO_CAR = "INSERT INTO CAR(`PAGE_CODE`, `ARTICLE`, `PRICE_BYN`, `PRICE_EUR`, `YEAR`, `STATUS`) VALUES (?,?,?,?,?,?)";
	private static final String DELETE = "DELETE FROM CAR";
	private static final String DELETE_CAR_BY_ID = "DELETE FROM CAR WHERE ID =?";

	public List<Car> showAllCarsList() throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Car> carList = new ArrayList<Car>();

		try {
			connection = ConnectionManager.getManager().getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ALL_CARS);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Car car = new Car();
				car.setId(resultSet.getInt("ID"));
				car.setPageCode(resultSet.getInt("PAGE_CODE"));
				car.setArticle(resultSet.getString("ARTICLE"));
				car.setPriceByn(resultSet.getDouble("PRICE_BYN"));
				car.setPriceEuro(resultSet.getInt("PRICE_EUR"));
				car.setYear(resultSet.getInt("YEAR"));
				car.setStatus(resultSet.getString("STATUS"));
				carList.add(car);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			ConnectionManager.getManager().closeDbResources(connection, preparedStatement, resultSet);
		}
		return carList;
	}

	public List<Car> showCarsByStatus() throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Car> availableCarList = new ArrayList<Car>();

		try {
			connection = ConnectionManager.getManager().getConnection();
			preparedStatement = connection.prepareStatement(SELECT_CARS_BY_STATUS);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Car car = new Car();
				car.setId(resultSet.getInt("ID"));
				car.setPageCode(resultSet.getInt("PAGE_CODE"));
				car.setArticle(resultSet.getString("ARTICLE"));
				car.setPriceByn(resultSet.getDouble("PRICE_BYN"));
				car.setPriceEuro(resultSet.getInt("PRICE_EUR"));
				car.setYear(resultSet.getInt("YEAR"));
				car.setStatus(resultSet.getString("STATUS"));
				availableCarList.add(car);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			ConnectionManager.getManager().closeDbResources(connection, preparedStatement, resultSet);
		}
		return availableCarList;
	}

	public void createCar(Car car) throws DaoException {		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getManager().getConnection();
			preparedStatement = connection.prepareStatement(INSERT_INTO_CAR);

			preparedStatement.setInt(1, car.getPageCode());
			preparedStatement.setString(2, car.getArticle());
			preparedStatement.setDouble(3, car.getPriceByn());
			preparedStatement.setInt(4, car.getPriceEuro());
			preparedStatement.setInt(5, car.getYear());
			preparedStatement.setString(6, car.getStatus());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			ConnectionManager.getManager().closeDbResources(connection, preparedStatement);
		}
	}

	public void deleteCar(Integer carId) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getManager().getConnection();
			preparedStatement = connection.prepareStatement(DELETE_CAR_BY_ID);
			preparedStatement.setInt(1, carId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			ConnectionManager.getManager().closeDbResources(connection, preparedStatement);
		}
	}

	public void clearTable() throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getManager().getConnection();
			preparedStatement = connection.prepareStatement(DELETE);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			ConnectionManager.getManager().closeDbResources(connection, preparedStatement);
		}
	}
}
