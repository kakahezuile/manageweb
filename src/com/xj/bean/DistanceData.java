package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class DistanceData {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer id;
	
	private Integer createTime;
	
	private Integer updateTime;
	
	private String distance;
	
	private Integer level;
	
	private String defaultDistance;

	public String getDefaultDistance() {
		return defaultDistance;
	}

	public void setDefaultDistance(String defaultDistance) {
		this.defaultDistance = defaultDistance;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
}
