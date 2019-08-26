package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class FacilitiesClass {
	@NotUpdataAnnotation
	@NotInsertAnnotation
	private Integer facilitiesClassId;
	
	private String facilitiesClassName;
	
	private Integer communityId;
	
	private Integer weight ;
	
	private String picName;

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public Integer getFacilitiesClassId() {
		return facilitiesClassId;
	}

	public void setFacilitiesClassId(Integer facilitiesClassId) {
		this.facilitiesClassId = facilitiesClassId;
	}

	public String getFacilitiesClassName() {
		return facilitiesClassName;
	}

	public void setFacilitiesClassName(String facilitiesClassName) {
		this.facilitiesClassName = facilitiesClassName;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	
}
