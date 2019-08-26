package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class CommunityUserService {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer userServiceId;
	
	private Integer serviceId;
	
	private String emobId;
	
	private String status;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String imgName;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String serviceName;

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getUserServiceId() {
		return userServiceId;
	}

	public void setUserServiceId(Integer userServiceId) {
		this.userServiceId = userServiceId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
