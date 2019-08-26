package com.xj.bean;

public class LifeScanning {
	private Integer id;
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
