package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 小区模块关系表
 */
public class CommunityService {

	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer communityServiceId; 
	private Integer communityId; // 小区id
	private Integer serviceId; // 模块id
	private String relationStatus; // 类型
	private Integer appVersionId; // 版本id
	private Integer createTime; // 创建时间
	private Integer updateTime; // 修改时间
	private Integer serviceLevel;
	
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

	public Integer getAppVersionId() {
		return appVersionId;
	}

	public void setAppVersionId(Integer appVersionId) {
		this.appVersionId = appVersionId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
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

	public String getRelationStatus() {
		return relationStatus;
	}

	public void setRelationStatus(String relationStatus) {
		this.relationStatus = relationStatus;
	}

	public Integer getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(Integer serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	
}
