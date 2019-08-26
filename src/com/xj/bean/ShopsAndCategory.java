package com.xj.bean;

public class ShopsAndCategory {
	
	private Integer shopId;
	
	private String shopName;
	
	private String score;
	
	private String status;
	
	private String emobId;
	
	private Integer shopsCategoryId;
	
	private String logo;

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getShopsCategoryId() {
		return shopsCategoryId;
	}

	public void setShopsCategoryId(Integer shopsCategoryId) {
		this.shopsCategoryId = shopsCategoryId;
	}

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
