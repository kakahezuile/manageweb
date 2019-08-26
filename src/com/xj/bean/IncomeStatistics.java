package com.xj.bean;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 个人收入
 * @author Administrator
 *
 */
public class IncomeStatistics {
	
	private String orderPrice; // 总收入
	
	private String onlinePrice; // 在线收入
	
	private String thelinePrice;//线下收入
	 
	private Integer orderSum; // 订单量
	
	@MyAnnotation
	private String avgOrderPrice; // 均收入
	
	@MyAnnotation
	@NotUpdataAnnotation
	@NotInsertAnnotation
	private String avgOrderSum; // 均销量
	
	@MyAnnotation
	private Integer startTime;
	
	@MyAnnotation
	private Integer endTime;

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

	public String getAvgOrderPrice() {
		return avgOrderPrice;
	}

	public void setAvgOrderPrice(String avgOrderPrice) {
		this.avgOrderPrice = avgOrderPrice;
	}

	

	public String getAvgOrderSum() {
		return avgOrderSum;
	}

	public void setAvgOrderSum(String avgOrderSum) {
		this.avgOrderSum = avgOrderSum;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOnlinePrice() {
		return onlinePrice;
	}

	public void setOnlinePrice(String onlinePrice) {
		this.onlinePrice = onlinePrice;
	}

	public Integer getOrderSum() {
		return orderSum;
	}

	public void setOrderSum(Integer orderSum) {
		this.orderSum = orderSum;
	}
}
