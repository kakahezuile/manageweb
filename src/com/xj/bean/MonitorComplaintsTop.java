package com.xj.bean;

/**
 * 监控 - 投诉头部
 * @author Administrator
 *
 */
public class MonitorComplaintsTop {
	
	private Integer serviceCount; // 维修数目
	
	private Integer cleanCount; // 保洁数目
	
	private Integer shopCount; // 店家数目

	public Integer getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(Integer serviceCount) {
		this.serviceCount = serviceCount;
	}

	public Integer getCleanCount() {
		return cleanCount;
	}

	public void setCleanCount(Integer cleanCount) {
		this.cleanCount = cleanCount;
	}

	public Integer getShopCount() {
		return shopCount;
	}

	public void setShopCount(Integer shopCount) {
		this.shopCount = shopCount;
	}
}
