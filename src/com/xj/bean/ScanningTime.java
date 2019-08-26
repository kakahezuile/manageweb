package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class ScanningTime {
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer id;
	private Integer lifeCirrleDetailId;
	private Integer scanningTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLifeCirrleDetailId() {
		return lifeCirrleDetailId;
	}
	public void setLifeCirrleDetailId(Integer lifeCirrleDetailId) {
		this.lifeCirrleDetailId = lifeCirrleDetailId;
	}
	public Integer getScanningTime() {
		return scanningTime;
	}
	public void setScanningTime(Integer scanningTime) {
		this.scanningTime = scanningTime;
	}
	
	
}
