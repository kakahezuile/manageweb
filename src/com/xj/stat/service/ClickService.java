package com.xj.stat.service;

import java.util.List;
import java.util.Map;

import com.xj.bean.Users;
import com.xj.stat.bean.resource.PeriodModuleClick;

public interface ClickService {

	/**
	 * 获取时间范围内小区的日模块点击量统计
	 * @param communityId 小区ID
	 * @param startSecond 开始时间的秒数
	 * @param endSecond 结束时间的秒数
	 * @return
	 */
	List<PeriodModuleClick> getDayModuleStatistic(Integer communityId, Integer startSecond, Integer endSecond);
	
	/**
	 * 获取时间范围内小区的周模块点击量统计
	 * @param communityId 小区ID
	 * @param weekSecond 被统计所在周内的任意时刻
	 * @return
	 */
	PeriodModuleClick getWeekModuleStatistic(Integer communityId, Integer weekSecond);

	/**
	 * 获取时间范围内小区的月模块点击量统计
	 * @param communityId 小区ID
	 * @param monthSecond 被统计所在月内的任意时刻
	 * @return
	 */
	PeriodModuleClick getMonthModuleStatistic(Integer communityId, Integer monthSecond);
	
	/**
	 * 获取小区按顺序为本日，昨日，本周，上周，本月，上月的点击统计结果
	 * @param communityId
	 * @return
	 */
	List<PeriodModuleClick> getStatistic(Integer communityId);
	
	
	/**
	 * 获取小区按顺序为本日，昨日，本周，上周，本月，上月的点击统计结果
	 * @param communityId
	 * @param moduleId  统计模块id
	 * @return
	 */
	Object getStatistic(Integer communityId, Integer moduleId);
	
	/**
	 * 获取时间范围内小区的日模块点击量统计
	 * @param communityId 小区ID
	 * @param start 开始时间的秒数
	 * @param end 结束时间的秒数
	 * @param moduleId  模块id
	 * @return
	 */
	List<PeriodModuleClick> getDayModuleStatistic(Integer communityId, Integer start, Integer end, Integer moduleId);

	
	/**
	 * 获取时间范围内小区的周模块点击量统计
	 * @param communityId 小区ID
	 * @param weekSecond 被统计所在周内的任意时刻
	 * @param moduleId 模块id
	 * @return
	 */
	PeriodModuleClick getWeekModuleStatistic(Integer communityId,Integer weekSecond, Integer moduleId);

	/**
	 * 获取时间范围内小区的月模块点击量统计
	 * @param communityId 小区ID
	 * @param monthSecond 被统计所在月内的任意时刻
	 * @param moduleId 模块id
	 * @return
	 */
	PeriodModuleClick getMonthModuleStatistic(Integer communityId, Integer monthSecond, Integer moduleId);

	/**
	 * 获取分类汇总统计
	 * @param statistic
	 * @return
	 */
	List<PeriodModuleClick> getSubtotalStatics(List<PeriodModuleClick> statistic);
	
	/**
	 * 获取时间范围内的某小区的点击用户
	 * @param communityId
	 * @param startTime
	 * @param endTime
	 * @param users
	 * @return
	 */
	Map<String, Integer> getClickUser(Integer communityId, Integer startTime, Integer endTime, List<Users> users);
}