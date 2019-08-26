package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class CommunityExpress {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer communityExpressId;
	
	private Integer communityId; // 小区id
	
	private String expressAddress; // 快递地址
	
	private Integer expressLevel; // 优先级
	
	private Integer updateTime; // 修改时间
	
	private Integer createTime; // 创建时间

	public Integer getCommunityExpressId() {
		return communityExpressId;
	}

	public void setCommunityExpressId(Integer communityExpressId) {
		this.communityExpressId = communityExpressId;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getExpressAddress() {
		return expressAddress;
	}

	public void setExpressAddress(String expressAddress) {
		this.expressAddress = expressAddress;
	}

	public Integer getExpressLevel() {
		return expressLevel;
	}

	public void setExpressLevel(Integer expressLevel) {
		this.expressLevel = expressLevel;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
}
