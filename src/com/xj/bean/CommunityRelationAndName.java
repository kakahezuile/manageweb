package com.xj.bean;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class CommunityRelationAndName {
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer communityServiceId; 
	
	private Integer communityId;
	
	private Integer serviceId;
	
	private String relationStatus;
	
	private String serviceName;
	
	@MyAnnotation
	private Integer serviceLevel;
	
	private String imgName;
	
	private Integer updateTime;

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public Integer getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(Integer serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public Integer getCommunityServiceId() {
		return communityServiceId;
	}

	public void setCommunityServiceId(Integer communityServiceId) {
		this.communityServiceId = communityServiceId;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getRelationStatus() {
		return relationStatus;
	}

	public void setRelationStatus(String relationStatus) {
		this.relationStatus = relationStatus;
	}
}
