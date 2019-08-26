package com.xj.dao;

import com.xj.bean.DistanceData;

public interface DistanceDataDao extends MyBaseDao{
	
	public Integer addDistanceData(DistanceData distanceData) throws Exception;
	
	public DistanceData getDistance(Integer level) throws Exception;
}
