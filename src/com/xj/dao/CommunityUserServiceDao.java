package com.xj.dao;

import java.util.List;


import com.xj.bean.CommunityUserService;

public interface CommunityUserServiceDao extends MyBaseDao {
	
	public Integer addCommunityUserService(CommunityUserService communityUserService) throws Exception;
	
	public boolean updateCommunityUserService(CommunityUserService communityUserService) throws Exception;
	
	public List<CommunityUserService> getCommunityUserServiceList(String emobId) throws Exception;
	
	public CommunityUserService getCommunityUserService(String emobId , Integer serviceId) throws Exception;
}
