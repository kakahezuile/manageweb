package com.xj.bean;

public class ShopOrdersTime {
	private String shopName;
	// private Integer replyTime;
	private Integer serveTime;
	private Integer serveTime15;
	private Integer serveTime30;
	private Integer serveTime45;
	private Integer avgServeTime;

	public Integer getServeTime() {
		return serveTime;
	}

	public void setServeTime(Integer serveTime) {
		this.serveTime = serveTime;
	}

	public Integer getServeTime30() {
		return serveTime30;
	}

	public void setServeTime30(Integer serveTime30) {
		this.serveTime30 = serveTime30;
	}

	public Integer getServeTime15() {
		return serveTime15;
	}

	public void setServeTime15(Integer serveTime15) {
		this.serveTime15 = serveTime15;
	}

	public Integer getServeTime45() {
		return serveTime45;
	}

	public void setServeTime45(Integer serveTime45) {
		this.serveTime45 = serveTime45;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getAvgServeTime() {
		return avgServeTime;
	}

	public void setAvgServeTime(Integer avgServeTime) {
		this.avgServeTime = avgServeTime;
	}


}
