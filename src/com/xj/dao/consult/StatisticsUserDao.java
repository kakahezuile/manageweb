package com.xj.dao.consult;

import java.util.List;

import com.xj.bean.click.StatisticsUser;
import com.xj.dao.MyBaseDao;

public interface StatisticsUserDao extends MyBaseDao {

	public List<StatisticsUser> selectStatisticsUser(Integer communityId,Integer startTime,Integer endTime)throws Exception ;

	
}
