package com.xj.stat.service;

import java.util.List;

import com.xj.bean.Page;
import com.xj.stat.bean.resource.WelfareStatics;
import com.xj.utils.DateUtils;

/**
 * 福利统计service接口
 */
public interface WelfareStaticsService {
	
	/**
	 * 统计类型：天
	 */
	int STATISTICS_DAY = DateUtils.OFFSET_DAY;
	/**
	 * 统计类型：周
	 */
	int STATISTICS_WEEK = DateUtils.OFFSET_WEEK;
	/**
	 * 统计类型：月
	 */
	int STATISTICS_MONTH = DateUtils.OFFSET_MONTH;

	/**
	 * 获取指定时间内的数据
	 * @param communityId
	 * @param startSecond
	 * @param endSecond
	 * @return
	 */
	List<WelfareStatics> statWelfare(Integer communityId, Integer startSecond, Integer endSecond, Integer moduleId);

	/**
	 * 获取小区指定模块，所有数据
	 * @param communityId
	 * @return
	 */
	List<WelfareStatics> statWelfare(Integer communityId, Integer moduleId);

	
	/**
	 * 统计所有的福利
	 * @param communityId
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	List<WelfareStatics> getAllWelfareStatics(Integer communityId, Integer pageSize, Integer pageNum);

	/**
	 * 获取福利详情
	 * @param welfareId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	Page<WelfareStatics> getWelfareOrdersDetail(Integer welfareId, Integer pageNum, Integer pageSize);
	
	/**
	 * 获取福利信息
	 * @param welfareId
	 * @return
	 */
	WelfareStatics getWelfareInfo(Integer welfareId);

	/**
	 * 获取某一天点击的了福利模块的用户
	 * @param moduleId2 
	 * @param time
	 * @param time2 
	 * @return
	 */
	List<WelfareStatics> getClickDetailDay(Integer communityId, Integer moduleId, String time2,Integer pageNum,Integer pageSize);

	/**
	 * 获取每日福利购买
	 * @param communityId
	 * @param time
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<WelfareStatics> getwelfareBuys(Integer communityId, String time, Integer pageNum, Integer pageSize);
}