package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class DateSpecification {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer specificationId;
	
	private String specificationType; // 标准类型  week  month  year
	
	private Integer specificationNum;// 标准基数
	
	private Integer createTime;// 创建时间
	
	private Integer updateTime;// 修改时间

	public Integer getSpecificationId() {
		return specificationId;
	}

	public void setSpecificationId(Integer specificationId) {
		this.specificationId = specificationId;
	}

	public String getSpecificationType() {
		return specificationType;
	}

	public void setSpecificationType(String specificationType) {
		this.specificationType = specificationType;
	}

	public Integer getSpecificationNum() {
		return specificationNum;
	}

	public void setSpecificationNum(Integer specificationNum) {
		this.specificationNum = specificationNum;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}
}
