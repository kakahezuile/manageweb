package com.xj.stat.po;

public class CrazySalesShopStatistics {

	private String shopEmobId;
	private String shopName;
	private Integer community;	// 覆盖小区数
	private Integer activity;	// 发布活动数
	private Double price;		// 总出价金额
	private Integer sales;		// 总抢购量
	private Integer used;		// 总验码量

	public String getShopEmobId() {
		return shopEmobId;
	}

	public void setShopEmobId(String shopEmobId) {
		this.shopEmobId = shopEmobId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getCommunity() {
		return community;
	}

	public void setCommunity(Integer community) {
		this.community = community;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public Integer getUsed() {
		return used;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}
}