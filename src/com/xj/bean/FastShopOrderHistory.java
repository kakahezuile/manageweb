package com.xj.bean;

import java.util.List;

import com.xj.annotation.MyAnnotation;

/**
 * 快店历史订单
 * @author Administrator
 *
 */
public class FastShopOrderHistory {
	
	private String serial; // 订单号
	
	private Integer orderId; // 订单表主键
	
	private String orderPrice; // 付款金额
	
	private Integer endTime; // 完成时间

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	@MyAnnotation
	private List<ShopItemOrderHistory> list;

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	

	public List<ShopItemOrderHistory> getList() {
		return list;
	}

	public void setList(List<ShopItemOrderHistory> list) {
		this.list = list;
	}
	
}
