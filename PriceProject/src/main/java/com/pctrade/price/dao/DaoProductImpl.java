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
	private static final String DELETE_PRODUCT_BY_ID = "DELETE `ID`, `ARTICLE_CODE`, `ARTICLE`, `PRICE`, `UPLOAD_DATE`, `STATUS` FROM PRODUCT WHERE ID =";
	private static final String DELETE = "DELETE FROM PRODUCT";
	private static final String UPDATE_PRODUCT_SET_NOTAVAILABLE = "UPDATE PRODUCT SET `STATUS`='NOT_AVAILABLE'";
	private static final String UPDATE_PRODUCT = "UPDATE PRODUCT SET PRICE = ?, UPLOAD_DATE = ?, STATUS = 'AVAILABLE' WHERE `ARTICLE_CODE`=";
	private static final String COUNT_PRODUCT_WITH_CERTAIN_ARTICLE_CODE = "SELECT COUNT(ARTICLE_CODE) FROM PRODUCT WHERE ARTICLE_CODE =";

	private static final String INSERT_INTO_PRODUCT = "INSERT INTO PRODUCT(`ARTICLE_CODE`, `ARTICLE`, `PRICE`, `UPLOAD_DATE`) VALUES (?,?,?,?)";

	public List<Product> loadAllProductList() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<Product> productList = new ArrayList<Product>();

		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt("ID"));
				product.setArticleCode(resultSet.getInt("ARTICLE_CODE"));
				product.setArticle(resultSet.getString("ARTICLE"));
				product.setPrice(resultSet.getInt("PRICE"));
				product.setDate(resultSet.getString("UPLOAD_DATE"));
				product.setStatus(resultSet.getString("STATUS"));
				productList.add(product);
			}
		} catch (SQLException e) {
			throw new DaoException(e);

		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement, resultSet);
		}
		return productList;
	}

	public Product loadProductById(Integer productId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Product product = new Product();
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID + productId);

			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			product.setId(resultSet.getInt("ID"));
			product.setArticleCode(resultSet.getInt("ARTICLE_CODE"));
			product.setArticle(resultSet.getString("ARTICLE"));
			product.setPrice(resultSet.getInt("PRICE"));
			product.setDate(resultSet.getString("UPLOAD_DATE"));
			product.setStatus(resultSet.getString("STATUS"));
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement, resultSet);
		}
		return product;
	}

	public void createProduct(Product product) { // insert - executeUpdate
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(INSERT_INTO_PRODUCT);

			preparedStatement.setInt(1, product.getArticleCode());
			preparedStatement.setString(2, product.getArticle());
			preparedStatement.setInt(3, product.getPrice());
			preparedStatement.setString(4, product.getDate());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement);
		}
	}

	public int checkProduct(Product product) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int count = 0;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection
					.prepareStatement(COUNT_PRODUCT_WITH_CERTAIN_ARTICLE_CODE + product.getArticleCode());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement, resultSet);
		}
		return count;
	}

	public void updateProduct(Product product) { //
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();

			preparedStatement = connection.prepareStatement(UPDATE_PRODUCT + product.getArticleCode());

			preparedStatement.setInt(1, product.getPrice());
			preparedStatement.setString(2, product.getDate());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement);
		}
	}

	public void deleteProduct(Integer productId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(DELETE_PRODUCT_BY_ID + productId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement);
		}
	}

	public void deleteTable() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(DELETE);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement);
		}

	}

	public void setNotAvailableStatusForAll() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SET_NOTAVAILABLE);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement);
		}
	}

	public void updateProductTable(List<Product> products) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SET_NOTAVAILABLE);
			preparedStatement.executeUpdate();

			for (Product product : products) {
				int count = 0;
				preparedStatement = connection
						.prepareStatement(COUNT_PRODUCT_WITH_CERTAIN_ARTICLE_CODE + product.getArticleCode());
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					count = resultSet.getInt(1);
				}
				if (count == 0) {
					ConnectionManager.closeStatement(preparedStatement);
					preparedStatement = connection.prepareStatement(INSERT_INTO_PRODUCT);
					preparedStatement.setInt(1, product.getArticleCode());
					preparedStatement.setString(2, product.getArticle());
					preparedStatement.setInt(3, product.getPrice());
					preparedStatement.setString(4, product.getDate());
					preparedStatement.executeUpdate();
				} else {
					ConnectionManager.closeStatement(preparedStatement);
					preparedStatement = connection.prepareStatement(UPDATE_PRODUCT + product.getArticleCode());
					preparedStatement.setInt(1, product.getPrice());
					preparedStatement.setString(2, product.getDate());
					preparedStatement.executeUpdate();
				}				
			}
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				throw new DaoException(ex);
			}
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement, resultSet);
		}
	}

	public void createProductTable(List<Product> products) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(DELETE);
			preparedStatement.executeUpdate();
			ConnectionManager.closeStatement(preparedStatement);
			preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SET_NOTAVAILABLE);
			preparedStatement.executeUpdate();

			for (Product product : products) {
				preparedStatement = connection.prepareStatement(INSERT_INTO_PRODUCT);
				preparedStatement.setInt(1, product.getArticleCode());
				preparedStatement.setString(2, product.getArticle());
				preparedStatement.setInt(3, product.getPrice());
				preparedStatement.setString(4, product.getDate());
				preparedStatement.executeUpdate();
			}
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				throw new DaoException(ex);
			}
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement);
		}
	}
}
