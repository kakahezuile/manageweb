package com.xj.dao;

import com.xj.bean.BonuscoinRatio;

public interface BonuscoinRatioDao extends MyBaseDao {
	
	public Integer addBonuscoinRatio(BonuscoinRatio bonuscoinRatio) throws Exception;
	
	public boolean updateBonuscoinRatio(BonuscoinRatio bonuscoinRatio) throws Exception;
	
	public BonuscoinRatio getBonuscoinRatio(Integer communityId , Integer sort) throws Exception;

}
