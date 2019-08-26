package com.xj.bean;

import java.io.Serializable;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 用户表
 */
public class Users implements Serializable {

	@NotInsertAnnotation
	private static final long serialVersionUID = 1L;
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer userId;
	private String username; // 用户名
	private String emobId; // 环信id
	private String password; // 密码
	private String nickname; // 昵称
	private String phone; // 电话
	private Integer age; // 年龄
	private String avatar; // 头像
	private String salt; // 密码用自定义串
	private String gender;
	private String idcard;
	private String idnumber;
	private String status;//
	private String room;
	private Integer createTime;
	@NotInsertAnnotation
	@MyAnnotation
	@NotUpdataAnnotation
	private String mpassword;

	private String signature; // 个性签名

	private String role; // 用户类型 owner shops

	private Integer communityId; // 小区id

	private String userFloor;

	private String userUnit;

	private String clientId;
	
	private String deviceToken;

	private String equipment; // 设备信息

	private Integer characterValues; // 人品值

	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Double characterPercent; // 百分比

	private String equipmentVersion; // 版本
	
	@NotInsertAnnotation
	private Integer bonuscoinCount; // 帮帮币数量
	
	@NotUpdataAnnotation
	@MyAnnotation
	private Integer  registerTime;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String grade;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Integer invitCount;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String communityName;
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation   
	private Integer lifecircleCount;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String remarks;
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public Integer getLifecircleCount() {
		return lifecircleCount;
	}

	public void setLifecircleCount(Integer lifecircleCount) {
		this.lifecircleCount = lifecircleCount;
	}

	public Integer getInvitCount() {
		return invitCount;
	}

	public void setInvitCount(Integer invitCount) {
		this.invitCount = invitCount;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Integer registerTime) {
		this.registerTime = registerTime;
	}

	public Integer getBonuscoinCount() {
		return bonuscoinCount;
	}

	public void setBonuscoinCount(Integer bonuscoinCount) {
		this.bonuscoinCount = bonuscoinCount;
	}

	public String getEquipmentVersion() {
		return equipmentVersion;
	}

	public void setEquipmentVersion(String equipmentVersion) {
		this.equipmentVersion = equipmentVersion;
	}

	public Double getCharacterPercent() {
		return characterPercent;
	}

	public void setCharacterPercent(Double characterPercent) {
		this.characterPercent = characterPercent;
	}

	public Integer getCharacterValues() {
		return characterValues;
	}

	public void setCharacterValues(Integer characterValues) {
		this.characterValues = characterValues;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public String getMpassword() {
		return mpassword;
	}

	public void setMpassword(String mpassword) {
		this.mpassword = mpassword;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emobId == null) ? 0 : emobId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		if (emobId == null) {
			if (other.emobId != null)
				return false;
		} else if (!emobId.equals(other.emobId))
			return false;
		return true;
	}
	
}
