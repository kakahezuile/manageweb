package com.xj.bean;

public class BonusUserOrder {

	private String bonusId;
	private String nickname;
	private String username;
	private String userFloor;
	private String userUnit;
	private String room;
	private Integer startTime;
	private Integer expireTime;
	private Integer orderId;
	private Integer createTime;
	private String bonusStatus;
	private String serial;
	
	public String getBonusId() {
		return bonusId;
	}
	public void setBonusId(String bonusId) {
		this.bonusId = bonusId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getStartTime() {
		return startTime;
	}
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}
	public String getBonusStatus() {
		return bonusStatus;
	}
	public void setBonusStatus(String bonusStatus) {
		this.bonusStatus = bonusStatus;
	}
	public Integer getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
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
	
	
}
