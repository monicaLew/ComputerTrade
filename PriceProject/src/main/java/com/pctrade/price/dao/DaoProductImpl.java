package com.pctrade.price.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pctrade.price.entity.Product;
import com.pctrade.price.mysql.ConnectionManager;

public class DaoProductImpl implements DaoProduct {

	private static final String SELECT_ALL_PRODUCT = "SELECT `ID`, `ARTICLE_CODE`, `ARTICLE`, `PRICE`, `UPLOAD_DATE`, `STATUS` FROM PRODUCT";
	private static final String SELECT_PRODUCT_BY_ID = "SELECT `ID`, `ARTICLE_CODE`, `ARTICLE`, `PRICE`, `UPLOAD_DATE`, `STATUS` FROM PRODUCT WHERE ID =";
	private static final String DELETE_PRODUCT_BY_ID = "DELETE `ID`, `ARTICLE_CODE`, `ARTICLE`, `PRICE`, `UPLOAD_DATE`, `STATUS` FROM PRODUCT WHERE ID ="; // ?
																																							// наверное
																																							// ж
																																							// все
																																							// убрать
																																							// ??
	private static final String UPDATE_PRODUCT_SET_NOTAVAILABLE = "UPDATE PRODUCT SET `STATUS`='NOT_AVAILABLE'";
	// private static final String UPDATE__PRODUCT = "UPDATE PRODUCT SET
	// `STATUS`='NOT_AVAILABLE'";
	// private static final String INSERT_INTO_PRODUCT = "INSERT INTO
	// PRODUCT(`ID`, `ARTICLE_CODE`, `ARTICLE`, `PRICE`, `UPLOAD_DATE`,
	// `STATUS`) VALUES (?,?,?,?,?,?)";
	private static final String INSERT_INTO_PRODUCT = "INSERT INTO PRODUCT(`ARTICLE_CODE`, `ARTICLE`, `PRICE`, `UPLOAD_DATE`) VALUES (?,?,?,?)";

	public List<Product> loadAllProductList() {
		Connection connection = null;

		List<Product> productList = new ArrayList<Product>();

		try {
			connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt("ID"));
				product.setArticleCode(resultSet.getInt("ARTICLE_CODE"));
				product.setArticle(resultSet.getString("ARTICLE"));
				product.setPrice(resultSet.getInt("PRICE"));
				product.setDate(resultSet.getString("UPLOAD_DATE"));
				product.setStatus(resultSet.getString("STATUS"));

				productList.add(product);
				// int id = resultSet.getInt("ID");
				// int articleCode = resultSet.getInt("ARTICLE_CODE");
				// String article = resultSet.getString("ARTICLE");
				// int price = resultSet.getInt("PRICE");
				// String date = resultSet.getString("UPLOAD_DATE");
				// String status = resultSet.getString("STATUS");
			}
			resultSet.close();
			preparedStatement.close();			
		} catch (SQLException e) {

			throw new DaoException(e);

		} finally {
			ConnectionManager.closeConnection(connection);
		}
		return productList;
	}

	public Product loadProductById(Integer productId) {
		Product product = new Product();
		try {
			PreparedStatement preparedStatement = ConnectionManager.getConnection()
					.prepareStatement(SELECT_PRODUCT_BY_ID + productId);
			ResultSet resultSet = preparedStatement.executeQuery();

			product.setId(resultSet.getInt("ID"));
			product.setArticleCode(resultSet.getInt("ARTICLE_CODE"));
			product.setArticle(resultSet.getString("ARTICLE"));
			product.setPrice(resultSet.getInt("PRICE"));
			product.setDate(resultSet.getString("UPLOAD_DATE"));
			product.setStatus(resultSet.getString("STATUS"));

		} catch (SQLException e) {
			throw new DaoException(e);
		}
		// finally{ // СПРОСИТЬ !!!
		// ConnectionManager.closeConnection(connection);
		// }
		return product;
	}

	public void createProduct(Product product) { // insert - executeUpdate
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_PRODUCT);

			preparedStatement.setInt(2, product.getArticleCode());
			preparedStatement.setString(3, product.getArticle());
			preparedStatement.setInt(4, product.getPrice());
			// preparedStatement.setDate(5, product.getDate());
			preparedStatement.setString(5, product.getDate());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeConnection(connection);
		}
	}

	public void updateProduct(Product product) {
		Connection connection = null;

		try {
			connection = ConnectionManager.getConnection();
		} catch (SQLException e) {
			throw new DaoException(e);
		}

	}

	public void deleteProduct(Integer productId) {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_BY_ID + productId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeConnection(connection);
		}
	}

	public void setNotAvailableStatusForAll() {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SET_NOTAVAILABLE);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeConnection(connection);
		}
	}

}
