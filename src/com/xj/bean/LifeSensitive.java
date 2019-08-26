package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class LifeSensitive {
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer id;
	private String detailContent;
	private Integer lifeCircleId;
	private Integer lifeCircleDetailId;
	private Integer communityId;
	private String status;
	private Integer createTime;
	private Integer updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDetailContent() {
		return detailContent;
	}
	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}
	public Integer getLifeCircleId() {
		return lifeCircleId;
	}
	public void setLifeCircleId(Integer lifeCircleId) {
		this.lifeCircleId = lifeCircleId;
	}
	public Integer getLifeCircleDetailId() {
		return lifeCircleDetailId;
	}
	public void setLifeCircleDetailId(Integer lifeCircleDetailId) {
		this.lifeCircleDetailId = lifeCircleDetailId;
	}
	public Integer getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	
	
	
}
