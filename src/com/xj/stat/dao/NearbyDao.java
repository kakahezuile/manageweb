package com.xj.stat.dao;

import java.util.List;

import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.bean.parameter.CurrentTimeRange;
import com.xj.stat.bean.parameter.nearby.CrazySales;
import com.xj.stat.bean.resource.nearby.CrazySalesImg;
import com.xj.stat.bean.resource.nearby.CrazySalesShop;
import com.xj.stat.bean.resource.nearby.CrazySalesStatistics;

/**
 *@author lence
 *@date  2015年7月9日上午12:47:18
 */
public interface NearbyDao {

	List<CrazySalesShop> selectNearbyCrazySalesShop(CrazySales crazySales);

	void delectCrazySales(CrazySales crazySales);

	CrazySalesImg getCrazySales(CrazySales crazySales);

	void upCrazySales(CrazySalesImg crazySales);

	void upImgCrazySales(CrazySalesImg crazySales);

	/**
	 * 统计当前时间范围的抢购信息
	 * 返回结果按顺序为今日，本周，本月的统计结果
	 * @param currentTimeRange
	 * @return
	 */
	List<CrazySalesStatistics> statCrazySales(CurrentTimeRange currentTimeRange);
	
	/**
	 * 统计指定时间范围的抢购信息
	 * @param beanUtil
	 * @return
	 */
	CrazySalesStatistics statCrazySales(BeanUtil beanUtil);
}