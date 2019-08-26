package com.xj.dao;

import com.xj.bean.SensitiveRunTime;

public interface SensitiveRunTimeDao extends MyBaseDao{
	
	public Integer addSensitiveRunTime(SensitiveRunTime sensitiveRunTime) throws Exception;
	
	public SensitiveRunTime getMaxSensitiveRuntime() throws Exception;
	
}
