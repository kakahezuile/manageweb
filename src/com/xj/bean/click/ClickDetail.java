package com.xj.bean.click;

import java.util.List;

public class ClickDetail {

	private String serviceId;
	private String serviceName;
	private Integer total;
	private List<UserClick> userClick;
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<UserClick> getUserClick() {
		return userClick;
	}
	public void setUserClick(List<UserClick> userClick) {
		this.userClick = userClick;
	}
	
	
	
	
}
