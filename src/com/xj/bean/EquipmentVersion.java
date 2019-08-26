package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class EquipmentVersion {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer equipmentVersionId;
	
	private String equipment;
	
	private String equipmentVersion;

	public String getEquipmentVersion() {
		return equipmentVersion;
	}

	public void setEquipmentVersion(String equipmentVersion) {
		this.equipmentVersion = equipmentVersion;
	}

	public Integer getEquipmentVersionId() {
		return equipmentVersionId;
	}

	public void setEquipmentVersionId(Integer equipmentVersionId) {
		this.equipmentVersionId = equipmentVersionId;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	
}
