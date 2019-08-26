package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class UniqueData {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer uniqueData;
	
	private Integer createTime;

	public Integer getUniqueData() {
		return uniqueData;
	}

	public void setUniqueData(Integer uniqueData) {
		this.uniqueData = uniqueData;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
}
