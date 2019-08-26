package com.xj.bean;

import java.io.Serializable;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class UsersApp implements Serializable {

	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private static final long serialVersionUID = 1L;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer userId;
	@NotUpdataAnnotation
	private String username;
	private String emobId;
	private String password;
	private String nickname;
	private String phone;
	private Integer age;
	private String avatar;
	private String salt;
	private String gender;
	private String idcard;
	private String idnumber;
	private String status;
	private String room;
	private Integer createTime;
	@NotInsertAnnotation
	@MyAnnotation
	@NotUpdataAnnotation
	private String mpassword;
	
	private String signature; // 个性签名
	
	private String role;
	
	private Integer communityId;
	
	private String userFloor;
	
	private String userUnit;
	
	private String communityName;
	
	private String clientId;
	
	private Integer characterValues;
	
	private String equipment;
	
	private Integer bonuscoinCount; // 帮帮币数量
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Double characterPercent;
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Integer lifeCircleSum;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Integer bonuscoin = 100; //帮帮币基数
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Integer exchange = 1; // 帮帮币兑换值
	
	
	public Integer getBonuscoin() {
		return bonuscoin;
	}

	public void setBonuscoin(Integer bonuscoin) {
		this.bonuscoin = bonuscoin;
	}

	public Integer getExchange() {
		return exchange;
	}

	public void setExchange(Integer exchange) {
		this.exchange = exchange;
	}

	public Integer getBonuscoinCount() {
		return bonuscoinCount;
	}

	public void setBonuscoinCount(Integer bonuscoinCount) {
		this.bonuscoinCount = bonuscoinCount;
	}

	public Integer getLifeCircleSum() {
		return lifeCircleSum;
	}

	public void setLifeCircleSum(Integer lifeCircleSum) {
		this.lifeCircleSum = lifeCircleSum;
	}

	public Double getCharacterPercent() {
		return characterPercent;
	}

	public void setCharacterPercent(Double characterPercent) {
		this.characterPercent = characterPercent;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public Integer getCharacterValues() {
		return characterValues;
	}

	public void setCharacterValues(Integer characterValues) {
		this.characterValues = characterValues;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
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
}