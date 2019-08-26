package com.xj.stat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.xj.jodis.JodisUtils;
import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.bean.parameter.CurrentTimeRange;
import com.xj.stat.bean.parameter.nearby.CrazySales;
import com.xj.stat.bean.resource.nearby.CrazySalesImg;
import com.xj.stat.bean.resource.nearby.CrazySalesShop;
import com.xj.stat.bean.resource.nearby.CrazySalesStatistics;
import com.xj.stat.dao.NearbyDao;
import com.xj.stat.service.NearbyService;
import com.xj.utils.DateUtils;

@Service
public class NearbyServiceImpl implements NearbyService {

	private static final Logger logger = Logger.getLogger(NearbyServiceImpl.class);

	@Autowired
	private NearbyDao nearbyDao;
	
	@Override
	public List<CrazySalesShop> selectNearbyCrazySalesShop(CrazySales crazySales) {
		return nearbyDao.selectNearbyCrazySalesShop(crazySales);
	}

	@Override
	public void delectCrazySales(CrazySales crazySales) {
		nearbyDao.delectCrazySales(crazySales);
	}

	@Override
	public CrazySalesImg getCrazySales(CrazySales crazySales) {
		return nearbyDao.getCrazySales(crazySales);
	}

	@Override
	public void upCrazySales(CrazySalesImg crazySales) {
		nearbyDao.upCrazySales(crazySales);
	}

	@Override
	public void upImgCrazySales(CrazySalesImg crazySales) {
		nearbyDao.upImgCrazySales(crazySales);
	}

	@Override
	public List<CrazySalesStatistics> statCrazySales(Integer communityId) throws Exception {
		List<CrazySalesStatistics> statistics = nearbyDao.statCrazySales(new CurrentTimeRange(communityId));
		
		statistics.add(1, stat(communityId, DateUtils.getOffsetDate(-1, DateUtils.OFFSET_DAY), STATISTICS_DAY));
		statistics.add(3, stat(communityId, DateUtils.getOffsetDate(-1, DateUtils.OFFSET_WEEK), STATISTICS_WEEK));
		statistics.add(stat(communityId, DateUtils.getOffsetDate(-1, DateUtils.OFFSET_MONTH), STATISTICS_MONTH));
		
		return statistics;
	}

	@Override
	public List<CrazySalesStatistics> statCrazySales(Integer communityId, Integer startSecond, Integer endSecond) throws Exception {
		List<Date> dates = DateUtils.findBetweenDates(new Date(startSecond * 1000L), new Date(endSecond * 1000L));
		List<CrazySalesStatistics> statistics = new ArrayList<CrazySalesStatistics>(dates.size());
		long todayBegin = DateUtils.getDayBegin().getTime();
		for (Date date : dates) {
			if (DateUtils.getDayBegin(date).getTime() > todayBegin) {
				continue;
			}
			
			statistics.add(stat(communityId, date, STATISTICS_DAY));//TODO 这里会多次打开关闭redis连接，需要改进
		}
		
		return statistics;
	}
	
	private CrazySalesStatistics stat(Integer communityId, Date day, int type) {
		String key = null;
		boolean notToday = true;//notToday -> 不是今天
		Integer end = null;
		if (type == STATISTICS_DAY) {
			day = DateUtils.getDayBegin(day);
			end = Long.valueOf(DateUtils.getDayEnd(day).getTime() / 1000L).intValue();
			notToday = !day.equals(DateUtils.getDayBegin(new Date()));
			
			key = "CrazySalesStatistics_DAY_" + communityId + "_" + DateUtils.formatDate(day);
		} else if (type == STATISTICS_WEEK) {
			day = DateUtils.getWeekBegin(day);
			end = Long.valueOf(DateUtils.getWeekEnd(day).getTime() / 1000L).intValue();
			notToday = !day.equals(DateUtils.getWeekBegin(new Date()));
			
			key = "CrazySalesStatistics_WEEK_" + communityId + "_" + DateUtils.formatDate(day);
		} else if (type == STATISTICS_MONTH) {
			day = DateUtils.getMonthBegin(day);
			end = Long.valueOf(DateUtils.getMonthEnd(day).getTime() / 1000L).intValue();
			notToday = !day.equals(DateUtils.getMonthBegin(new Date()));
			
			key = "CrazySalesStatistics_MONTH_" + communityId + "_" + DateUtils.formatDate(day);
		}
		
		if (notToday) {
			try (Jedis jedis = JodisUtils.getResource()) {
				String statistics = jedis.get(key);
				if (StringUtils.isNotBlank(statistics)) {
					String[] split = statistics.split("_");
					
					CrazySalesStatistics crazySalesStatistics = new CrazySalesStatistics();
					crazySalesStatistics.setBuyNum(split[0]);
					crazySalesStatistics.setUseNum(split[1]);
					crazySalesStatistics.setUserNum(split[2]);
					
					return crazySalesStatistics;
				}
			} catch (Exception e) {
				logger.error("Redis：获取统计数据出错!", e);
			}
		}
		
		CrazySalesStatistics crazySalesStatistics = nearbyDao.statCrazySales(new BeanUtil(communityId, Long.valueOf(day.getTime() / 1000L).intValue(), end));
		if (notToday) {
			JodisUtils.set(key, crazySalesStatistics.getBuyNum() + "_" + crazySalesStatistics.getUseNum() + "_" + crazySalesStatistics.getUserNum());
		}
		
		return crazySalesStatistics;
	}
}