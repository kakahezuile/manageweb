package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 活动群组 成员表
 * @author Administrator
 *
 */
public class ActivityMembers {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer activityMemberId; // 群组id
	
	private String emobGroupId; // 环信群组id
	
	private String emobUserId; // 环信id
	
	private Integer createTime; // 创建时间
	
	private String groupStatus; // 群组状态
	
	private Integer communityId; // 小区id
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String emobId;
	
	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String nickname; // 昵称
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String room; // 地址
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String avatar; // 头像
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String phone;
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String signature; // 个性签名
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String userFloor;
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String userUnit;
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String gender;
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String idcard;
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String idnumber;
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer age;
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String communityName;

	

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getGroupStatus() {
		return groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}
	
	public Integer getActivityMemberId() {
		return activityMemberId;
	}

	public void setActivityMemberId(Integer activityMemberId) {
		this.activityMemberId = activityMemberId;
	}

	public String getEmobGroupId() {
		return emobGroupId;
	}

	public void setEmobGroupId(String emobGroupId) {
		this.emobGroupId = emobGroupId;
	}

	public String getEmobUserId() {
		return emobUserId;
	}

	public void setEmobUserId(String emobUserId) {
		this.emobUserId = emobUserId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	
	
}
