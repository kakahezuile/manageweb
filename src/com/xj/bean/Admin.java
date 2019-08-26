package com.xj.bean;


import java.io.Serializable;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;





/**
 * 管理员表
 * @author Administrator
 *
 */
public class Admin implements Serializable{

	/**
	 * 
	 */
	@NotInsertAnnotation
	private static final long serialVersionUID = 1L;
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer adminId;
	@NotUpdataAnnotation
	private String username; // 用户 名
	private String emobId; // 环信id
	private String password; // 密码
	private String nickname; // 昵称
	private String phone; // 手机号
 	private Integer age;  // 年龄
	private String avatar;  // 头像地址
	private String salt; // 密码专用 自定义串
	private String gender; 
	private String idcard; 
	private String idnumber;
	private String status;
	private String room;
	private Integer createTime; // 创建时间
	@NotInsertAnnotation
	@MyAnnotation
	private String mpassword;
	
	private String role; //
	
	private Integer communityId; // 小区id
	
	@NotUpdataAnnotation
	@NotInsertAnnotation
	@MyAnnotation
	private String xPassWord;
	
	private String adminStatus;
	
	private String startTime; // 服务开始时间
	
	private String endTime; // 服务截止时间

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}

	public String getxPassWord() {
		return xPassWord;
	}

	public void setxPassWord(String xPassWord) {
		this.xPassWord = xPassWord;
	}

	

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
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
	
	
}
