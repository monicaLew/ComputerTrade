package com.pctrade.price.entity;

public class Product {
	private Integer id;
	private Integer articleCode;
	private String article;
	private Integer price;
	private String date;
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(Integer articleCode) {
		this.articleCode = articleCode;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
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

	@Override
	public String toString() {
		return articleCode + "," + article + "," + price
				+ "," + date + "," + status;
	}
}