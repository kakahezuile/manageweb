package com.xj.bean;

import java.util.List;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class IncomeStatisticsOrders {
	
	private String serial;
	
	private Integer startTime;
	
	private Integer orderId;
	
	private String online;
	
	private String orderPrice;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private List<OrderDetailBean> list;

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public List<OrderDetailBean> getList() {
		return list;
	}

	public void setList(List<OrderDetailBean> list) {
		this.list = list;
	}
	
	
}
