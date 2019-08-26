package com.xj.stat.po;

import java.util.Date;

import com.xj.annotation.NotInsertAnnotation;

public class UserVo {

	
	/**
	 * 
	 */
	@NotInsertAnnotation
	private static final long serialVersionUID = 1L;

	private String username; // 用户名
	private String emobId; // 环信id
	private String nickname; // 昵称
	private String phone; // 电话
	private String avatar; // 头像
	private String room;

	private String signature; // 个性签名
	
	
	private Integer communityId; // 小区id
	
	private String userFloor;
	
	private String userUnit;
	
	private String type;
	
	private Integer successNum;
	
	private Integer failNmu;
	
	private Integer count;
	
	private Date time;
	
	/**
	 * 2015-8-31新增
	 * @return
	 */
	private String equipment; //设备信息
	
	private String equipmentVersion; // 版本
	
	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getEquipmentVersion() {
		return equipmentVersion;
	}

	public void setEquipmentVersion(String equipmentVersion) {
		this.equipmentVersion = equipmentVersion;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}

	public Integer getFailNmu() {
		return failNmu;
	}

	public void setFailNmu(Integer failNmu) {
		this.failNmu = failNmu;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	

}
