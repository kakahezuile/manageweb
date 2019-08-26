package com.xj.dao;

import java.util.List;





import com.xj.bean.FloorAndUnit;
import com.xj.bean.HomeOwner;

public interface HomeOwnerDao extends MyBaseDao{
	
	Integer addHomeOwner(HomeOwner homeOwner) throws Exception;
	
	List<HomeOwner> getHomeOwners(Integer communityId) throws Exception;
	
	List<FloorAndUnit> getCommunityHome(Integer communityId) throws Exception;
	
	void importHomeOwners(List<HomeOwner> homeOwners) throws Exception;

	List<String> getFloorsByCommunityId(Integer communityId);

	List<String> selectUnitByCommunityIdAndFloor(Integer communityId,
			String floor);

	List<String> selectRoom(Integer communityId, String floor, String unit);
}