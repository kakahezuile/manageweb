package com.xj.stat.service;

import java.util.List;

import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.po.StatisticsUser;

/**
 * @author lence
 * @date 2015年7月9日上午1:02:08
 */
public interface StatisticsUserService {

	Integer insertStatisticsUser(StatisticsUser statisticsUser);

	List<String> selectUserEmob(BeanUtil BeanUtil);
}