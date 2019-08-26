package com.xj.bean;

public class LifeCircleGet {

	private Integer favoriteId;
	private Integer lifeCircleId;
	private Integer createTime;
	private String lifeContent;
	private Integer praiseSum;
	private Integer contentSum;
	private String avatar;
	private String nickname;
	private String userUnit;
	private String userFloor;
	private String room; // 发起人地址

	public Integer getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Integer favoriteId) {
		this.favoriteId = favoriteId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public String getLifeContent() {
		return lifeContent;
	}

	public void setLifeContent(String lifeContent) {
		this.lifeContent = lifeContent;
	}

	public Integer getPraiseSum() {
		return praiseSum;
	}

	public void setPraiseSum(Integer praiseSum) {
		this.praiseSum = praiseSum;
	}

	public Integer getContentSum() {
		return contentSum;
	}

	public void setContentSum(Integer contentSum) {
		this.contentSum = contentSum;
	}

	public Integer getLifeCircleId() {
		return lifeCircleId;
	}

	public void setLifeCircleId(Integer lifeCircleId) {
		this.lifeCircleId = lifeCircleId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
