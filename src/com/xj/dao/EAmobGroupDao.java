package com.xj.dao;

import com.xj.bean.EAmobGroup;

public interface EAmobGroupDao extends MyBaseDao{
	
	public EAmobGroup getEAmobGroup(String emobGroupId) throws Exception;
}
