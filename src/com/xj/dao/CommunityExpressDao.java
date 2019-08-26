package com.xj.dao;

import java.util.List;

import com.xj.bean.CommunityExpress;

public interface CommunityExpressDao extends MyBaseDao{
	
	public Integer addCommunityExpress(CommunityExpress communityExpress) throws Exception;
	
	public boolean updateCommunityExpress(CommunityExpress communityExpress) throws Exception;
	
	public List<CommunityExpress> getCommunityExpressList(Integer communityId) throws Exception;
	
	public CommunityExpress getCommunityExpressByLevel(Integer communityId) throws Exception;
}
