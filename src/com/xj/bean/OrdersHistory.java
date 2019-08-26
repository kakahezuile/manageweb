package com.xj.bean;

import java.util.List;

public class OrdersHistory { // 缴费历史订单
	
	private Integer orderMonth;
	
	private Integer orderYear;
	
	private List<XjPayHistory> list;

	public Integer getOrderMonth() {
		return orderMonth;
	}

	public void setOrderMonth(Integer orderMonth) {
		this.orderMonth = orderMonth;
	}

	public Integer getOrderYear() {
		return orderYear;
	}

	public void setOrderYear(Integer orderYear) {
		this.orderYear = orderYear;
	}

	public List<XjPayHistory> getList() {
		return list;
	}

	public void setList(List<XjPayHistory> list) {
		this.list = list;
	}
}
