package com.xj.stat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.stat.dao.StatisticsTimeDao;
import com.xj.stat.po.StatisticsTime;
import com.xj.stat.service.StatisticsTimeService;

@Service
public class StatisticsTimeServiceImpl implements StatisticsTimeService {

	@Autowired
	private StatisticsTimeDao statisticsTimeDao;

	@Override
	public List<StatisticsTime> selectStatisticsTime() {
		return statisticsTimeDao.selectStatisticsTime();
	}

	@Override
	public Integer insertStatisticsTime(StatisticsTime statisticsTime) {
		return statisticsTimeDao.insertStatisticsTime(statisticsTime);
	}

	@Override
	public List<StatisticsTime> selectStatisticsTimeType(String type) {
		return statisticsTimeDao.selectStatisticsTimeType(type);
	}
}