package com.xj.stat.service;

import java.util.List;

import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.bean.resource.ClickMobule;
import com.xj.stat.po.StatisticsModule;

/**
 * @author lence
 * @date 2015年7月9日上午1:02:08
 */
public interface StatisticsModuleService {

	Integer insertStatisticsModule(StatisticsModule statisticsModule);

	List<String> selectUserEmob(BeanUtil BeanUtil);

	List<ClickMobule> statisticsModuleNum(BeanUtil beanUtil);
}