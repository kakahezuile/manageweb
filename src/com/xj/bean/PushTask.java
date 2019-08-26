package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class PushTask {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer taskId;
	
	private Integer timestamp;
	
	private String scene; // 场景
	
	private String pushStr; // 返回内容
	
	private String pushId;
	
	private String serviceName;
	
	private String serviceId;
	
	private String getuiTaskId;
	

	public String getGetuiTaskId() {
		return getuiTaskId;
	}

	public void setGetuiTaskId(String getuiTaskId) {
		this.getuiTaskId = getuiTaskId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Integer timestamp) {
		this.timestamp = timestamp;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getPushStr() {
		return pushStr;
	}

	public void setPushStr(String pushStr) {
		this.pushStr = pushStr;
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}
	

}
