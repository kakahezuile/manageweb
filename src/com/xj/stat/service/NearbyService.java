package com.xj.stat.service;

import java.util.List;

import com.xj.stat.bean.parameter.nearby.CrazySales;
import com.xj.stat.bean.resource.nearby.CrazySalesImg;
import com.xj.stat.bean.resource.nearby.CrazySalesShop;
import com.xj.stat.bean.resource.nearby.CrazySalesStatistics;
import com.xj.utils.DateUtils;

public interface NearbyService {
	
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

	List<CrazySalesShop> selectNearbyCrazySalesShop(CrazySales crazySales);

	void delectCrazySales(CrazySales crazySales);

	CrazySalesImg getCrazySales(CrazySales crazySales);

	void upCrazySales(CrazySalesImg crazySales);

	void upImgCrazySales(CrazySalesImg crazySales);

	/**
	 * 统计抢购
	 * 返回结果按顺序为今日，昨日，本周，上周，本月，上月的统计结果
	 * @param communityId
	 * @return
	 * @throws Exception
	 */
	List<CrazySalesStatistics> statCrazySales(Integer communityId) throws Exception;

	/**
	 * 统计指定范围的抢购
	 * @param communityId
	 * @param startSecond
	 * @param endSecond
	 * @return
	 * @throws Exception
	 */
	List<CrazySalesStatistics> statCrazySales(Integer communityId, Integer startSecond, Integer endSecond) throws Exception;
}