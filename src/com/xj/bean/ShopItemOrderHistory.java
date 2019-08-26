package com.xj.bean;

/**
 * 快店历史订单中的商品及其店家
 * @author Administrator
 *
 */
public class ShopItemOrderHistory {
	
	private String shopName; // 店铺名称
	
	private String serviceName; //　商品名称
	
	private String score; // 评分
	
	private String currentPrice; //　现价
	
	private String originPrice; // 原价
	
	private String serviceImg;
	
	private Integer count;
	
	private Integer serviceId;
	
	private Integer shopId; // 店家id
	
	private String shopEmobId; // 环信id
	
	private String status;
	
	private Integer purchase;
	
	public Integer getPurchase() {
		return purchase;
	}

	public void setPurchase(Integer purchase) {
		this.purchase = purchase;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getShopEmobId() {
		return shopEmobId;
	}

	public void setShopEmobId(String shopEmobId) {
		this.shopEmobId = shopEmobId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getServiceImg() {
		return serviceImg;
	}

	public void setServiceImg(String serviceImg) {
		this.serviceImg = serviceImg;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(String originPrice) {
		this.originPrice = originPrice;
	}
	
}
