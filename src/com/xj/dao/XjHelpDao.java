package com.xj.dao;

import com.xj.bean.XjHelp;

public interface XjHelpDao extends MyBaseDao{
	
	public Integer addXjHelp(XjHelp xjHelp) throws Exception;
	
	public boolean updateHelpByEmobIdFrom(XjHelp xjHelp) throws Exception;
	
	public boolean updateHelpByEmobIdTo(XjHelp xjHelp) throws Exception;
}
