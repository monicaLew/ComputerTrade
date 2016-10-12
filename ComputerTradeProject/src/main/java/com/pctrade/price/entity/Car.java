package com.pctrade.price.entity;

import java.io.Serializable;
import java.net.SocketTimeoutException;
import org.jsoup.HttpStatusException;

public class Car implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer pageCode;
	private String article;
	private Double priceByn;
	private Integer priceEuro;
	private Integer year;
	private String status;

	public Car() {
		super();
	}

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

	public static Car createCar(Exception ex, Integer pageId) {
		Car car = new Car();

		if (ex instanceof SocketTimeoutException) {
			car.setPageCode(pageId);
			car.setArticle(null);
			car.setPriceByn(0.0);
			car.setPriceEuro(0);
			car.setYear(0);
			car.setStatus("NOT_DOWNLOAD_SOME_INET_PROBLEM");
		} else if (ex instanceof HttpStatusException) {
			car.setPageCode(pageId);
			car.setArticle(null);
			car.setPriceByn(0.0);
			car.setPriceEuro(0);
			car.setYear(0);
			car.setStatus("PAGE_NOT_EXIST");
		} else if (ex instanceof NumberFormatException) {
			car.setPageCode(pageId);
			car.setArticle(null);
			car.setPriceByn(0.0);
			car.setPriceEuro(0);
			car.setYear(0);
			car.setStatus("PAGE_NOT_VALID");
		} else if (ex instanceof NullPointerException) {
			car.setPageCode(pageId);
			car.setArticle(null);
			car.setPriceByn(0.0);
			car.setPriceEuro(0);
			car.setYear(0);
			car.setStatus("PAGE_NOT_VALID_NPE");
		} else if (ex instanceof StringIndexOutOfBoundsException) {
			car.setPageCode(pageId);
			car.setArticle(null);
			car.setPriceByn(0.0);
			car.setPriceEuro(0);
			car.setYear(0);
			car.setStatus("PAGE_NOT_VALID_SIE");
		}
		return car;
	}
}
