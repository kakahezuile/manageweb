package com.xj.dao;

import com.xj.bean.XjTest;

public interface XjTestDao extends MyBaseDao{
	
	public Integer addXjTest(XjTest xjTest) throws Exception;
}
