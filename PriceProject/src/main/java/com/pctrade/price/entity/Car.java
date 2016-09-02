package com.pctrade.price.entity;

public class Car {

	private Integer id;
	private Integer pageCode;
	private String article;
	private Double priceByn;
	private Integer priceEuro;
	private Integer year;
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPageCode() {
		return pageCode;
	}

	public void setPageCode(Integer pageCode) {
		this.pageCode = pageCode;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public Double getPriceByn() {
		return priceByn;
	}

	public void setPriceByn(Double priceByn) {
		this.priceByn = priceByn;
	}

	public Integer getPriceEuro() {
		return priceEuro;
	}

	public void setPriceEuro(Integer priceEuro) {
		this.priceEuro = priceEuro;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", pageCode=" + pageCode + ", article=" + article + ", priceByn=" + priceByn
				+ ", priceEuro=" + priceEuro + ", year=" + year + ", status=" + status + "]";
	}	
	
}
