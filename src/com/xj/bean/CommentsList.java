package com.xj.bean;

public class CommentsList {

	
   private String logo; // 商家头像地址
	
	private String shopId; // 商家id
	
	private String shopName; // 商家名称
	
	private String phone; // 联系电话
	
	
	private Integer complaintsCount; // 被投诉次数
	
	
	
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
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getComplaintsCount() {
		return complaintsCount;
	}
	public void setComplaintsCount(Integer complaintsCount) {
		this.complaintsCount = complaintsCount;
	}
	
	
}
