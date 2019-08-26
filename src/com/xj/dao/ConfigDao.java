package com.xj.dao;

import com.xj.bean.Config;

public interface ConfigDao extends MyBaseDao {
	
	void add(Config config);
	
	void update(Config config);
	
	Config get(String key);

}