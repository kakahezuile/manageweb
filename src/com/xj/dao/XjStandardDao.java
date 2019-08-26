package com.xj.dao;

import com.xj.bean.XjStandard;


public interface XjStandardDao extends MyBaseDao {
	
	public Integer addXjStandard(XjStandard xjStandard) throws Exception;
	
	public XjStandard getXjStandard(Integer communityId , String sort) throws Exception;
	
	public boolean updateXjStandard(XjStandard xjStandard) throws Exception;
}
