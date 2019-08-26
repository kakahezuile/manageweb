package com.xj.stat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.bean.resource.ClickMobule;
import com.xj.stat.dao.StatisticsModuleDao;
import com.xj.stat.po.StatisticsModule;
import com.xj.stat.service.StatisticsModuleService;

@Service
public class StatisticsModuleServiceImpl implements StatisticsModuleService {

	@Autowired
	private StatisticsModuleDao statisticsModuleDao;

	@Override
	public Integer insertStatisticsModule(StatisticsModule statisticsModule) {
		return statisticsModuleDao.insertStatisticsModule(statisticsModule);
	}

	@Override
	public List<String> selectUserEmob(BeanUtil beanUtil) {
		return statisticsModuleDao.selectUserEmob(beanUtil);
	}

	@Override
	public List<ClickMobule> statisticsModuleNum(BeanUtil beanUtil) {
		return statisticsModuleDao.statisticsModuleNum(beanUtil);
	}
}