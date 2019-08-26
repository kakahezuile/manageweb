package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class PublicizePhotos {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer publicizePhotoId; // 主键
	
	private String imgUrl; //  图片地址
	
	private String role; // 用户类型
	 
	private Integer serviceId; // 模块类型
	
	private String photoModule; // 图片场景

	private Integer communityId; // 小区id
	
	private String appVersion; // 应用版本
	
	private Integer createTime; // 创建时间
	
	private Integer updateTime; // 修改时间
	
	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getPublicizePhotoId() {
		return publicizePhotoId;
	}

	public void setPublicizePhotoId(Integer publicizePhotoId) {
		this.publicizePhotoId = publicizePhotoId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getPhotoModule() {
		return photoModule;
	}

	public void setPhotoModule(String photoModule) {
		this.photoModule = photoModule;
	}
}
