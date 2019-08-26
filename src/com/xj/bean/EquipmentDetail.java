package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class EquipmentDetail {
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer appversionDetailId;
	
	private String detailContent; // 更新内容
	
	private Integer communityId; // 
	
	private Integer equipmentVersionId;

	public Integer getAppversionDetailId() {
		return appversionDetailId;
	}

	public void setAppversionDetailId(Integer appversionDetailId) {
		this.appversionDetailId = appversionDetailId;
	}

	public String getDetailContent() {
		return detailContent;
	}

	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getEquipmentVersionId() {
		return equipmentVersionId;
	}

	public void setEquipmentVersionId(Integer equipmentVersionId) {
		this.equipmentVersionId = equipmentVersionId;
	}
}
