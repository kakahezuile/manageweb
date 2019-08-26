package com.xj.dao;

import java.util.List;

import com.xj.bean.BonusServiceAndName;
import com.xj.bean.BonusServices;

public interface BonusServicesDao extends MyBaseDao{
	
	public Integer addBonusServices(BonusServices bonusServices) throws Exception; 
	
	public List<BonusServiceAndName> getBonusServiceNameList(Integer bonusId) throws Exception;
}
