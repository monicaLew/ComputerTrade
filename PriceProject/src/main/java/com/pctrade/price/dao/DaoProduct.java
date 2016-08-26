package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.Product;

public interface DaoProduct {

	public List<Product> loadAllProductList();

	public Product loadProductById(Integer productId);

	public void createProduct(Product product); // INSERT

	public void updateProduct(Product product);

	public void deleteProduct(Integer productId);

	public void setNotAvailableStatusForAll();

}
