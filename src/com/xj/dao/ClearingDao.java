package com.xj.dao;

import com.xj.bean.Clearing;

public interface ClearingDao extends MyBaseDao  {
	
	public Integer addClearing(Clearing clearing) throws Exception;
	
	public boolean updateClearing(Clearing clearing) throws Exception;
	
	
}
