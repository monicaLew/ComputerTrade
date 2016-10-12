package com.pctrade.price.utils;

public class UrlReconnections {
	private Integer idPage;
	private Integer numberOfReconnect;
	
	
	public Integer getIdPage() {
		return idPage;
	}
	public void setIdPage(Integer idPage) {
		this.idPage = idPage;
	}
	public Integer getNumberOfReconnect() {
		return numberOfReconnect;
	}
	public void setNumberOfReconnect(Integer numberOfReconnect) {
		this.numberOfReconnect = numberOfReconnect;
	}
	
	public void fill(Integer id){
		setIdPage(id);
		setNumberOfReconnect(0);
	}
}
