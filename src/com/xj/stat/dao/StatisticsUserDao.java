package com.xj.stat.dao;

import java.util.List;

import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.po.StatisticsUser;

public interface StatisticsUserDao {

	Integer insertStatisticsUser(StatisticsUser statisticsUser);

	List<String> selectUserEmob(BeanUtil beanUtil);

}