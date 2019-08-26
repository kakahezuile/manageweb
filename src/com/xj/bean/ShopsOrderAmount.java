package com.xj.bean;

/**
 *  使用量的统计
 * @author longfei
 *
 */
public class ShopsOrderAmount {

	private Integer orderQuantity;  // 总下单量
	private Integer testOrderQuantity;  // 总下单量
	private String shopName;       //商铺名称
	private Integer orderPrice; //  交易额
	private Integer onlinePrice;
	private Integer testOrderPrice; //  交易额
	private Integer testOnlinePrice;
	private Integer shopId; 
	private String emobId; 
	private Integer startTime;
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
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(Integer orderPrice) {
		this.orderPrice = orderPrice;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public Integer getStartTime() {
		return startTime;
	}
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}
	public Integer getOnlinePrice() {
		return onlinePrice;
	}
	public void setOnlinePrice(Integer onlinePrice) {
		this.onlinePrice = onlinePrice;
	}
	public Integer getTestOrderQuantity() {
		return testOrderQuantity;
	}
	public void setTestOrderQuantity(Integer testOrderQuantity) {
		this.testOrderQuantity = testOrderQuantity;
	}
	public Integer getTestOrderPrice() {
		return testOrderPrice;
	}
	public void setTestOrderPrice(Integer testOrderPrice) {
		this.testOrderPrice = testOrderPrice;
	}
	public Integer getTestOnlinePrice() {
		return testOnlinePrice;
	}
	public void setTestOnlinePrice(Integer testOnlinePrice) {
		this.testOnlinePrice = testOnlinePrice;
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
