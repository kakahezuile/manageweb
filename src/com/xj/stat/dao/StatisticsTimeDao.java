package com.xj.stat.dao;

import java.util.List;

import com.xj.stat.po.StatisticsTime;

public interface StatisticsTimeDao {

	List<StatisticsTime> selectStatisticsTime();

	Integer insertStatisticsTime(StatisticsTime statisticsTime);

	List<StatisticsTime> selectStatisticsTimeType(String type);

}