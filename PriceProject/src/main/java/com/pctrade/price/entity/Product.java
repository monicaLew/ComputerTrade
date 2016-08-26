package com.pctrade.price.entity;

public class Product {
	private int id;
	private int articleCode;
	private String article;
	private int price;
	private String date;
	private String status;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getArticleCode() {
		return articleCode;
	}
	public void setArticleCode(int articleCode) {
		this.articleCode = articleCode;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
}



// ProductDAO с методами addProduct(Product product), 

// removeProduct(int productID), 

// changeStatus(int productID, boolean status)
