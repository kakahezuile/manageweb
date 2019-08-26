package com.xj.dao;

import com.xj.bean.UniqueData;

public interface UniqueDataDao extends MyBaseDao {
	
	public Integer addUniqueData(UniqueData uniqueData) throws Exception;
}
