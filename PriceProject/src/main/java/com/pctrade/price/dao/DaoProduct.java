package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.Product;

public interface DaoProduct {

	public List<Product> loadAllProductList();

	Product loadProductById(Integer productId);

	void createProduct(Product product); // INSERT

	void updateProduct(Product product);

	void updateProductTable(List<Product> products);
	
	void createProductTable(List<Product> products);

	void deleteProduct(Integer productId);

	void setNotAvailableStatusForAll();

	int checkProduct(Product product);

	void deleteTable();

}
