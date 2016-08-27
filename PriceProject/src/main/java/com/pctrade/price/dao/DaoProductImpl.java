package com.pctrade.price.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (Exception e) {
				throw new DaoException(e);
			}
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				throw new DaoException(e);
			}
			ConnectionManager.closeConnection(connection);
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
			try {
				if (resultSet != null) { // memory leak
					resultSet.close();
				}
			} catch (Exception e) {
				throw new DaoException(e);
			}
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				throw new DaoException(e);
			}
			ConnectionManager.closeConnection(connection);
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
			// preparedStatement.setDate(5, product.getDate());
			preparedStatement.setString(4, product.getDate());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (Exception e) {
				throw new DaoException(e);
			}
			ConnectionManager.closeConnection(connection);
		}
	}

	public int checkProduct(Product product) {
		int count = 0;
		Connection connection = null;		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionManager.getConnection();
			//connection.getAutoCommit();
			preparedStatement = connection.prepareStatement(COUNT_PRODUCT_WITH_CERTAIN_ARTICLE_CODE + product.getArticleCode());	
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {                           // NPE из-за несчитанной 1 строки ?
				int y = resultSet.getInt(1);
				count = count + y;
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (Exception e) {
				throw new DaoException(e);
			}
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				throw new DaoException(e);
			}
			ConnectionManager.closeConnection(connection);
		}
		return count;
	}

	public void updateProduct(Product product) { //
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			
			preparedStatement = connection.prepareStatement(UPDATE_PRODUCT + product.getArticleCode());
//			connection.setAutoCommit(false);
//			connection.commit();
//			connection.rollback();
			//preparedStatement.executeQuery("BEGIN");
			preparedStatement.setInt(1, product.getPrice());
			preparedStatement.setString(2, product.getDate());
			preparedStatement.executeUpdate();
			//preparedStatement.executeQuery("COMMIT");
		} catch (SQLException e) {
//			try {
//				preparedStatement.executeQuery("ROLLBACK");
//			} catch (SQLException e1) {				
//				throw new DaoException(e);
//			}
			throw new DaoException(e);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (Exception e) {
				throw new DaoException(e);
			}
			ConnectionManager.closeConnection(connection);
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
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (Exception e) {
				throw new DaoException(e);
			}
			ConnectionManager.closeConnection(connection);
		}
	}
	public void deleteTable(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(DELETE);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (Exception e) {
				throw new DaoException(e);
			}
			ConnectionManager.closeConnection(connection);
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
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (Exception e) {
				throw new DaoException(e);
			}
			ConnectionManager.closeConnection(connection);
		}
	}

}
