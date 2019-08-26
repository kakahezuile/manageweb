package com.xj.bean;



public class XjPayHistory {
	
	private Integer orderYear;
	
	private Integer orderMonth;
	
	private String serial;
	
	private String orderPrice;
	
	private String endTime;
	
	private String action;


	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getOrderYear() {
		return orderYear;
	}

	public void setOrderYear(Integer orderYear) {
		this.orderYear = orderYear;
	}

	public Integer getOrderMonth() {
		return orderMonth;
	}

	public void setOrderMonth(Integer orderMonth) {
		this.orderMonth = orderMonth;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	
}
