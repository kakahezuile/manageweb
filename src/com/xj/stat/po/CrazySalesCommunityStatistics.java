package com.xj.stat.po;

public class CrazySalesCommunityStatistics {

	private String communityId;		//小区id
	private String communityName;	//小区名称
	private Integer shops;			//向小区内发布抢购活动的商家数量
	private Integer activity;		//小区内抢购活动数量
	private Double price;			//小区所有抢购发布价格纸盒
	private Integer sales;			//被抢购数量
	private Integer used;			//验码数

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public Integer getShops() {
		return shops;
	}

	public void setShops(Integer shops) {
		this.shops = shops;
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