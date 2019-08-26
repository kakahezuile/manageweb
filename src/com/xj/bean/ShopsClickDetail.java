package com.xj.bean;

public class ShopsClickDetail {

	private Integer testOrderQuantity;
	private Integer orderQuantity;
	private Integer startTime;
	private String useTime;
	private String emobId;
	private String shopName;
	private Integer userNum;//下单量
	private Integer testUserNum;//下单量
	private Integer testEndOrderQuantity;// 交易额
	private Integer endOrderQuantity;// 交易额
	
	public Integer getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public Integer getStartTime() {
		return startTime;
	}
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public Integer getTestOrderQuantity() {
		return testOrderQuantity;
	}
	public void setTestOrderQuantity(Integer testOrderQuantity) {
		this.testOrderQuantity = testOrderQuantity;
	}
	public Integer getUserNum() {
		return userNum;
	}
	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}
	public Integer getTestUserNum() {
		return testUserNum;
	}
	public void setTestUserNum(Integer testUserNum) {
		this.testUserNum = testUserNum;
	}
	public Integer getTestEndOrderQuantity() {
		return testEndOrderQuantity;
	}
	public void setTestEndOrderQuantity(Integer testEndOrderQuantity) {
		this.testEndOrderQuantity = testEndOrderQuantity;
	}
	public Integer getEndOrderQuantity() {
		return endOrderQuantity;
	}
	public void setEndOrderQuantity(Integer endOrderQuantity) {
		this.endOrderQuantity = endOrderQuantity;
	}
	public String getEmobId() {
		return emobId;
	}
	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}
	
	
}
