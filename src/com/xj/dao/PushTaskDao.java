package com.xj.dao;

import com.xj.bean.PushTask;

public interface PushTaskDao extends MyBaseDao{
	
	public Integer addPushTask(PushTask pushTask) throws Exception;
	
}
