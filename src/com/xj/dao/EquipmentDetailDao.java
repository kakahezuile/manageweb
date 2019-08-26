package com.xj.dao;

import java.util.List;

import com.xj.bean.EquipmentDetail;

public interface EquipmentDetailDao extends MyBaseDao{
	
	public Integer addAppversionDetail(EquipmentDetail appversionDetail) throws Exception;
	
	public List<EquipmentDetail> getAppversionDetail(Integer communityId , Integer versionId) throws Exception;
	
	public boolean updateAppversionDetail(EquipmentDetail appversionDetail) throws Exception;
}
