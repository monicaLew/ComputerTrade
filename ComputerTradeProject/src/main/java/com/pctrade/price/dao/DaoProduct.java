package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.Product;

public interface DaoProduct {

	public List<Product> showAllProductList() throws DaoException;

	Product showProductById(Integer productId) throws DaoException; // -

	void createProduct(Product product) throws DaoException; // - +

	void updateProduct(Product product) throws DaoException; // - +

	void deleteProduct(Integer productId) throws DaoException; // -

	void setNotAvailableStatusForAll() throws DaoException; // - +

	void createProductTable(List<Product> products) throws DaoException;

	void updateProductTable(List<Product> products) throws DaoException;

	int countProductWithArticleCode(Product product) throws DaoException; // -
																					// +

	void clearTable() throws DaoException; // - +

}
