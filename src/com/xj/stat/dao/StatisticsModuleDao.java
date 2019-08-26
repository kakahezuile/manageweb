package com.xj.stat.dao;

import java.util.List;

import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.bean.resource.ClickMobule;
import com.xj.stat.po.StatisticsModule;

public interface StatisticsModuleDao {

	Integer insertStatisticsModule(StatisticsModule statisticsModule);

	List<String> selectUserEmob(BeanUtil beanUtil);

	List<ClickMobule> statisticsModuleNum(BeanUtil beanUtil);

}