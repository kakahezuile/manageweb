package com.xj.bean;

public class PayAndUser {
	
	private Integer userId;
	
	private String nickname;
	
	private String avatar;
	
	private String userFloor;
	
	private String userUnit;
	
	private String room;
	
	private String phone;
	
	private Integer payId;
	
	private Integer handleTime;
	
	private Integer createTime;
	
	private Integer completeTime;
	
	private Integer payStatus; // 0 - 待处理  1- 处理中  2- 处理完成
	
	private String paySum;
	
	private String payNo;
	
	private Integer waitTime;
	
	private String ownerName;
	
	private Integer payType; // 1 电费 2 水费 3 燃气费 4 宽带费
	
	private String emobId;
	
	private String lastPay;
	
	private String nextPay;
	
	private String payAddress;
	
	private String username;
	
	public String getPayAddress() {
		return payAddress;
	}

	public void setPayAddress(String payAddress) {
		this.payAddress = payAddress;
	}

	public Integer getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Integer completeTime) {
		this.completeTime = completeTime;
	}

	public String getLastPay() {
		return lastPay;
	}

	public void setLastPay(String lastPay) {
		this.lastPay = lastPay;
	}

	public String getNextPay() {
		return nextPay;
	}

	public void setNextPay(String nextPay) {
		this.nextPay = nextPay;
	}

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getUserFloor() {
		return userFloor;
	}

	public void setUserFloor(String userFloor) {
		this.userFloor = userFloor;
	}

	public String getUserUnit() {
		return userUnit;
	}

	public void setUserUnit(String userUnit) {
		this.userUnit = userUnit;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public Integer getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Integer handleTime) {
		this.handleTime = handleTime;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getPaySum() {
		return paySum;
	}

	public void setPaySum(String paySum) {
		this.paySum = paySum;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public Integer getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(Integer waitTime) {
		this.waitTime = waitTime;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
