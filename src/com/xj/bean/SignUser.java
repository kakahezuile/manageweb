package com.xj.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SignUser implements Serializable{
	
	private String ip;
	
	private String jessesionId;
	
	private Long createTime;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getJessesionId() {
		return jessesionId;
	}

	public void setJessesionId(String jessesionId) {
		this.jessesionId = jessesionId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	
}
