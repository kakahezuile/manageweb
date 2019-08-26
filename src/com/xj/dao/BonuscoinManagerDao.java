package com.xj.dao;

import com.xj.bean.BonuscoinManager;

public interface BonuscoinManagerDao extends MyBaseDao{
	
	public Integer addBonuscoinManager(BonuscoinManager bonuscoinManager) throws Exception;
	
	public BonuscoinManager getBonuscoinManager(Integer shopTypeId) throws Exception;
	
	public boolean updateBonuscoinManager(BonuscoinManager bonuscoinManager) throws Exception;
}
