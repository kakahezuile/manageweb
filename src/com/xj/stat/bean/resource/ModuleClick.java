package com.xj.stat.bean.resource;

public class ModuleClick {

	private String serviceId;	//模块id
	private String serviceName;	//模块名称
	private Integer userClick;	//用户点击量
	private Integer testClick;	//水军点击量
	private Integer userCount;	//点击用户数
	private Integer testCount;	//点击水军数
	
	public ModuleClick() {
	}
	
	public ModuleClick(String serviceId, String serviceName, Integer userClick, Integer testClick, Integer userCount, Integer testCount) {
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.userClick = userClick;
		this.testClick = testClick;
		this.userCount = userCount;
		this.testCount = testCount;
	}

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

	public Integer getUserClick() {
		return userClick;
	}

	public void setUserClick(Integer userClick) {
		this.userClick = userClick;
	}

	public Integer getTestClick() {
		return testClick;
	}

	public void setTestClick(Integer testClick) {
		this.testClick = testClick;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public Integer getTestCount() {
		return testCount;
	}

	public void setTestCount(Integer testCount) {
		this.testCount = testCount;
	}
}