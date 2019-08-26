package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class HomeOwner {

	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer homeOwnerId;
	private String userFloor;
	private String userUnit;
	private String userRoom;
	private String communityId;

	public HomeOwner() {
	}

	public HomeOwner(String communityId, String userFloor, String userUnit, String userRoom) {
		this.communityId = communityId;
		this.userFloor = userFloor;
		this.userUnit = userUnit;
		this.userRoom = userRoom;
	}

	public Integer getHomeOwnerId() {
		return homeOwnerId;
	}

	public void setHomeOwnerId(Integer homeOwnerId) {
		this.homeOwnerId = homeOwnerId;
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

	public String getUserRoom() {
		return userRoom;
	}

	public void setUserRoom(String userRoom) {
		this.userRoom = userRoom;
	}

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
}