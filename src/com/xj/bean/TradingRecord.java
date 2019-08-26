package com.xj.bean;

public class TradingRecord {

	private Double bonusPar;
	private String nickname;
	private String userName;
	private String userFloor;
	private String userUnit;
	private String room;
	private String communityName;
	private String onliePrice;
	private String orderPrice;
	private String isComplaint;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getOnliePrice() {
		return onliePrice;
	}
	public void setOnliePrice(String onliePrice) {
		this.onliePrice = onliePrice;
	}
	public String getIsComplaint() {
		return isComplaint;
	}
	public void setIsComplaint(String isComplaint) {
		this.isComplaint = isComplaint;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Double getBonusPar() {
		return bonusPar;
	}
	public void setBonusPar(Double bonusPar) {
		this.bonusPar = bonusPar;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	
}
