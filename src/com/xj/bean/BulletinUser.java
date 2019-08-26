package com.xj.bean;

import java.util.List;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class BulletinUser {

	private Integer id;
	
	private String bulletinText;
	
	private Integer createTime;
	
	private Integer communityId;
	
	private Integer adminId;
	

	private String theme;
	
	private String senderObject;
	
	private String userName;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private List<UsersApp> listUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBulletinText() {
		return bulletinText;
	}

	public void setBulletinText(String bulletinText) {
		this.bulletinText = bulletinText;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}


	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getSenderObject() {
		return senderObject;
	}

	public void setSenderObject(String senderObject) {
		this.senderObject = senderObject;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<UsersApp> getListUser() {
		return listUser;
	}

	public void setListUser(List<UsersApp> listUser) {
		this.listUser = listUser;
	}

	
	
}
