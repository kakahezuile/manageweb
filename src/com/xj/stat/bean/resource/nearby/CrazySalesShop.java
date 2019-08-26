package com.xj.stat.bean.resource.nearby;

public class CrazySalesShop {

	private Integer crazySalesId;
	private Integer createTime;
	private Integer endTime;
	 
	private String title;

	private String shopName;
	
	private String descr;
	
	private String status;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getCrazySalesId() {
		return crazySalesId;
	}

	public void setCrazySalesId(Integer crazySalesId) {
		this.crazySalesId = crazySalesId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
