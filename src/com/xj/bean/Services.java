package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class Services {

	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer serviceId;
	
	private String serviceName;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer communityServiceId;
	
	private Integer serviceLevel;	

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

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
