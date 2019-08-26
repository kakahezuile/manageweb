package com.xj.dao;

import com.xj.bean.LifeCircleTips;

public interface LifeCircleTipsDao extends MyBaseDao{
	
	public  LifeCircleTips getTips(String emobId) throws Exception;
}
