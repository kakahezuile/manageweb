package com.xj.bean;

import com.xj.annotation.MyAnnotation;

public class EAmobGroup {
	
	private String groupName;
	
	@MyAnnotation
	private Integer maxValue;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}
	
	
}
