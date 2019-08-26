package com.xj.stat.service;

import java.util.List;

import com.xj.stat.po.StatisticsTime;

/**
 * @author lence
 * @date 2015年7月9日上午1:02:08
 */
public interface StatisticsTimeService {

	List<StatisticsTime> selectStatisticsTime();

	List<StatisticsTime> selectStatisticsTimeType(String type);

	Integer insertStatisticsTime(StatisticsTime statisticsTime);

}