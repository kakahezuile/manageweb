package com.xj.stat.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.bean.parameter.CurrentTimeRange;
import com.xj.stat.bean.resource.WelfareStatics;
import com.xj.stat.dao.WelfareStaticsDao;

@Repository
public class WelfareStaticsDaoImpl extends SqlSessionDaoSupport implements WelfareStaticsDao {
	
	private String ns = "com.xj.stat.sqlmap.mapper.WelfareStaticsMapper.";
	
	@Override
	public List<WelfareStatics> statWelfareOrders(CurrentTimeRange currentTimeRange) {
		return this.getSqlSession().selectList(ns + "currentTimeRangeStatistics", currentTimeRange);
	}
	
	@Override
	public WelfareStatics statWelfareOrders(BeanUtil beanUtil) {
		return this.getSqlSession().selectOne(ns + "statistics", beanUtil);
	}

	@Override
	public List<WelfareStatics> getAllWelfareStatics(Integer communityId, Integer pageNum, Integer pageSize) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("communityId", communityId);
		map.put("start", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		return this.getSqlSession().selectList(ns + "getAllWelfareStatics", map);
	}

	@Override
	public List<WelfareStatics> getWelfareOrdersDetail(Integer welfareId, Integer pageNum, Integer pageSize) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("welfareId", welfareId);
		map.put("start", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		return this.getSqlSession().selectList(ns + "getWelfareOrdersDetail", map);
	}

	@Override
	public WelfareStatics getWelfareInfo(Integer welfareId) {
		return this.getSqlSession().selectOne(ns + "getWelfareInfo", welfareId);
	}

	@Override
	public List<WelfareStatics> getClickUserDetailDay(Object[] users) {
		return this.getSqlSession().selectList(ns + "getClickUserDetailDay", users);
	}

	@Override
	public List<WelfareStatics> getwelfareBuys(Integer communityId, Integer startTime, Integer endTime, Integer pageNum, Integer pageSize) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("communityId", communityId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("start", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		return this.getSqlSession().selectList(ns + "getwelfareBuys", map);
	}
}