package com.xj.bean;

public class ShopsOrderHistory {
	
	private String serial; // 订单号
	
	private String shopName; // 店家名称
	
	private String logo; // 头像
	
	private Integer serviceTime; // 服务时长
	
	private String score; // 评价
	
	private String orderPrice; // 支付价格
	
	private Integer endTime; // 订单完成时间
	
	private String emobIdShop; // 店家环信id

	public String getEmobIdShop() {
		return emobIdShop;
	}

	public void setEmobIdShop(String emobIdShop) {
		this.emobIdShop = emobIdShop;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Integer serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	
	
}
