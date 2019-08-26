package com.xj.stat.bean.parameter;

public class BeanUtil {

	private Integer communityId;
	private Integer startTime;
	private Integer endTime;
	
	public BeanUtil() {
	}

	public BeanUtil(Integer communityId, Integer startTime, Integer endTime) {
		this.communityId = communityId;
		this.startTime = startTime;
		this.endTime = endTime;
	}


	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
}