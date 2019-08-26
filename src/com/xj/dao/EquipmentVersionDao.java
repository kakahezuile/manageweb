package com.xj.dao;

import com.xj.bean.EquipmentVersion;

public interface EquipmentVersionDao extends MyBaseDao{
	
	public Integer addEquipmentVersion(EquipmentVersion equipmentVersion) throws Exception;
	
	public EquipmentVersion getEquimentVersionMax(String equiment) throws Exception;
}
