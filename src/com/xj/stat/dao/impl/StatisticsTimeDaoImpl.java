package com.xj.stat.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.xj.stat.dao.StatisticsTimeDao;
import com.xj.stat.po.StatisticsTime;

@Repository
public class StatisticsTimeDaoImpl extends SqlSessionDaoSupport implements StatisticsTimeDao {

	private String ns = "com.xj.stat.sqlmap.mapper.statistics.statistics_time.";

	@Override
	public List<StatisticsTime> selectStatisticsTime() {
		return this.getSqlSession().selectList(ns + "selectStatisticsTime");
	}

	@Override
	public Integer insertStatisticsTime(StatisticsTime statisticsTime) {
		return this.getSqlSession().insert(ns + "insertStatisticsTime", statisticsTime);
	}

	@Override
	public List<StatisticsTime> selectStatisticsTimeType(String type) {
		return this.getSqlSession().selectList(ns + "selectStatisticsTimeType", type);
	}
}