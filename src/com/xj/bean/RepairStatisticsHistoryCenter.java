package com.xj.bean;

/**
 * 维修个人被投诉列表
 * @author Administrator
 *
 */

public class RepairStatisticsHistoryCenter {
	
	private Integer userId; // 用户id
	
	private Integer shopId; // 商家id
	
	private String shopName; // 商家名称
	
	private String nickname; // 昵称
	
	private String username; // 昵称
	
	private String avatar; // 用户表中的 头像地址
	
	private String logo; // 商家头像地址
	
	private String phone; //　联系电话
	
	private String userUnit; // 单元
	
	private String userFloor; // 楼栋
	
	private String room; // 房间号
	
	//private String price="0"; // 金额
	
	private Integer startTime; // 开始时间
	
	private Integer endTime; // 结束时间
	
	//private Integer isComplaints; // 是否被投诉
	private String status; 
	
	private String orderType;


	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserUnit() {
		return userUnit;
	}

	public void setUserUnit(String userUnit) {
		this.userUnit = userUnit;
	}

	public String getUserFloor() {
		return userFloor;
	}

	public void setUserFloor(String userFloor) {
		this.userFloor = userFloor;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
}
