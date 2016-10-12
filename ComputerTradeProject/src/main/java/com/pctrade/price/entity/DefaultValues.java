package com.pctrade.price.entity;

import java.io.Serializable;

public class DefaultValues implements Serializable {	

	private static final long serialVersionUID = 1L;

	private Integer idFrom;
	private Integer idTill;
	private Integer poolCapacity;

	public DefaultValues() {
		super();
	}

	public Integer getIdFrom() {
		return idFrom;
	}

	public void setIdFrom(Integer idFrom) {
		this.idFrom = idFrom;
	}

	public Integer getIdTill() {
		return idTill;
	}

	public void setIdTill(Integer idTill) {
		this.idTill = idTill;
	}

	public Integer getPoolCapacity() {
		return poolCapacity;
	}

	public void setPoolCapacity(Integer poolCapacity) {
		this.poolCapacity = poolCapacity;
	}
	
}
