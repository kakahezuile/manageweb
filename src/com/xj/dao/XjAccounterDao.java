package com.xj.dao;

import java.util.List;

import com.xj.bean.XjAccounter;

public interface XjAccounterDao extends MyBaseDao {
	
	public Integer addXjAccounter(XjAccounter xjAccounter) throws Exception;
	
	public boolean updateXjAccounter(XjAccounter xjAccounter) throws Exception;
	
	public List<XjAccounter> getXjAccountList(String emobId) throws Exception;
	
	public XjAccounter getAccount(String emobId , String month) throws Exception;
}
