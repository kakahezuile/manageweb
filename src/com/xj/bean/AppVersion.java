package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class AppVersion {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer appVersionId;
	
	private String version;
	
	private Integer createTime;

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getAppVersionId() {
		return appVersionId;
	}

	public void setAppVersionId(Integer appVersionId) {
		this.appVersionId = appVersionId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
