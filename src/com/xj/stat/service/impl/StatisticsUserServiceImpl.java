package com.xj.stat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.dao.StatisticsUserDao;
import com.xj.stat.po.StatisticsUser;
import com.xj.stat.service.StatisticsUserService;

@Service
public class StatisticsUserServiceImpl implements StatisticsUserService {

	@Autowired
	private StatisticsUserDao statisticsUserDao;

	@Override
	public Integer insertStatisticsUser(StatisticsUser statisticsUser) {
		return statisticsUserDao.insertStatisticsUser(statisticsUser);
	}

	@Override
	public List<String> selectUserEmob(BeanUtil beanUtil) {
		return statisticsUserDao.selectUserEmob(beanUtil);
	}
}