package com.xj.bean;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class ShopIncomeStatistics {
	
	private String orderPrice;
	
	private String onlinePrice;
	
	private String thelinePrice;
	
	private Integer orderSum;
	
	private Integer endedSum;
	
	@MyAnnotation
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer startTime;
	
	@MyAnnotation
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer endTime;

	public String getOnlinePrice() {
		return onlinePrice;
	}

	public void setOnlinePrice(String onlinePrice) {
		this.onlinePrice = onlinePrice;
	}

	public String getThelinePrice() {
		return thelinePrice;
	}

	public void setThelinePrice(String thelinePrice) {
		this.thelinePrice = thelinePrice;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Integer getOrderSum() {
		return orderSum;
	}

	public void setOrderSum(Integer orderSum) {
		this.orderSum = orderSum;
	}

	public Integer getEndedSum() {
		return endedSum;
	}

	public void setEndedSum(Integer endedSum) {
		this.endedSum = endedSum;
	}
}
