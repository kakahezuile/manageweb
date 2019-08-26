package com.xj.stat.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.Jedis;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.xj.bean.Users;
import com.xj.dao.TryOutDao;
import com.xj.jodis.JodisUtils;
import com.xj.mongo.MongoUtils;
import com.xj.stat.bean.resource.ModuleClick;
import com.xj.stat.bean.resource.PeriodModuleClick;
import com.xj.stat.po.TryOut;
import com.xj.stat.service.ClickService;
import com.xj.utils.DateUtils;

@Service
public class ClickServiceImpl implements ClickService {

	private static final Logger logger = Logger.getLogger(ClickServiceImpl.class);
	
	private static final String PREFIX_DAY = "DAY_Module_Statistics_";
	private static final String PREFIX_WEEK = "WEEK_Module_Statistics_";
	private static final String PREFIX_MONTH = "MONTH_Module_Statistics_";
	
	private static final Date onlineDate = new Date(1433088000000L);//2015-06-01
	private static final Long onlineMilliSecond = 1433088000000L;//2015-06-01
	private static final Integer onlineSecond = 1433088000;//2015-06-01

	@Autowired
	private TryOutDao tryOutDao;

	@Override
	public List<PeriodModuleClick> getDayModuleStatistic(Integer communityId, Integer startSecond, Integer endSecond) {
		if (null == communityId || null == startSecond || null == endSecond) {
			throw new RuntimeException("请提供小区id,开始时间,结束时间!");
		}
		if (startSecond > endSecond) {
			throw new RuntimeException("开始时间不能大于结束时间!");
		}
		
		startSecond = Long.valueOf(DateUtils.getDayBegin(startSecond * 1000L).getTime() / 1000L).intValue();
		Integer currentSecond = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
		if (startSecond > currentSecond) {
			return new ArrayList<PeriodModuleClick>(0);
		}
		if (startSecond < onlineSecond) {
			startSecond = onlineSecond;
		}
		
		endSecond = Long.valueOf(DateUtils.getDayEnd(endSecond * 1000L).getTime() / 1000L).intValue();
		if (endSecond > currentSecond) {
			endSecond = currentSecond;
		}
		
		List<PeriodModuleClick> dailyModuleClicks = getDayModuleStatisticsFromRedis(communityId, startSecond, endSecond);
		List<String> days = DateUtils.getBetweenDates(startSecond * 1000L, endSecond * 1000L);
		String today = DateUtils.formatDate(new Date());
		Set<String> testSet = null;
		for (int i = 0; i < days.size(); i++) {
			String theDay = days.get(i);
			if (theDay.compareTo(today) > 0) {
				continue;
			}
			
			if (!isDayStated(theDay, dailyModuleClicks)) {
				if (null == testSet) {
					testSet = getTestSet();
				}
				
				Integer theDayStartSecond = (int)(DateUtils.getDayBegin(theDay).getTime() / 1000L);
				PeriodModuleClick dailyModuleClick = getDayModuleStatisticsFromMongo(communityId, theDayStartSecond, theDayStartSecond + DateUtils.SECOND_OF_DAY - 1, testSet);
				if (null == dailyModuleClick) {
					continue;
				}
				
				dailyModuleClicks.add(dailyModuleClick);
				
				if (!today.equals(theDay)) {
					sync2Redis(communityId, theDay, PREFIX_DAY, dailyModuleClick.getModules());
				}
			}
		}
		
		return sort(dailyModuleClicks);
	}
	
	@Override
	public List<PeriodModuleClick> getDayModuleStatistic(Integer communityId, Integer startSecond, Integer endSecond,Integer moduleId) {
		if (null == communityId || null == startSecond || null == endSecond) {
			throw new RuntimeException("请提供小区id,开始时间,结束时间!");
		}
		if (startSecond > endSecond) {
			throw new RuntimeException("开始时间不能大于结束时间!");
		}
		
		startSecond = Long.valueOf(DateUtils.getDayBegin(startSecond * 1000L).getTime() / 1000L).intValue();
		Integer currentSecond = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
		if (startSecond > currentSecond) {
			return new ArrayList<PeriodModuleClick>(0);
		}
		if (startSecond < onlineSecond) {
			startSecond = onlineSecond;
		}
		
		endSecond = Long.valueOf(DateUtils.getDayEnd(endSecond * 1000L).getTime() / 1000L).intValue();
		if (endSecond > currentSecond) {
			endSecond = currentSecond;
		}
		
		List<PeriodModuleClick> dailyModuleClicks = getDayModuleStatisticsFromRedis(communityId, startSecond, endSecond,moduleId);
		List<String> days = DateUtils.getBetweenDates(startSecond * 1000L, endSecond * 1000L);
		String today = DateUtils.formatDate(new Date());
		Set<String> testSet = null;
		for (int i = 0; i < days.size(); i++) {
			String theDay = days.get(i);
			if (theDay.compareTo(today) > 0) {
				continue;
			}
			
			if (!isDayStated(theDay, dailyModuleClicks)) {
				if (null == testSet) {
					testSet = getTestSet();
				}
				
				Integer theDayStartSecond = (int)(DateUtils.getDayBegin(theDay).getTime() / 1000L);
				PeriodModuleClick dailyModuleClick = getDayModuleStatisticsFromMongo(communityId, theDayStartSecond, theDayStartSecond + DateUtils.SECOND_OF_DAY - 1, testSet,moduleId);
				if (null == dailyModuleClick) {
					List<ModuleClick> modules = new ArrayList<>();
					modules.add(new ModuleClick("19","福利", 0, 0, 0,0));
					dailyModuleClick = new PeriodModuleClick(theDay, theDay, modules);
				}
				
				dailyModuleClicks.add(dailyModuleClick);
				
				if (!today.equals(theDay)) {
					sync2Redis(communityId, theDay, PREFIX_DAY, dailyModuleClick.getModules(),moduleId);
				}
			}
		}
		
		return sort(dailyModuleClicks);
	}
	
	@Override
	public PeriodModuleClick getWeekModuleStatistic(Integer communityId, Integer weekSecond) {
		if (null == communityId || null == weekSecond) {
			throw new RuntimeException("请提供小区id和统计时间!");
		}
		
		long now = System.currentTimeMillis();
		Date weekBegin = DateUtils.getWeekBegin(weekSecond * 1000L);
		if (weekBegin.getTime() > now) {
			return null;
		}
		
		if (weekBegin.getTime() < onlineMilliSecond) {
			weekBegin = onlineDate;
		}
		
		boolean notThisWeek = !DateUtils.getWeekBegin(now).equals(weekBegin);
		if (notThisWeek) {
			PeriodModuleClick weekModuleClick = getWeekModuleStatisticsFromRedis(communityId, weekBegin);
			if (null != weekModuleClick) {
				return weekModuleClick;
			}
		}
		
		Date weekEnd = DateUtils.getWeekEnd(weekBegin);
		if (weekEnd.getTime() > now) {
			weekEnd.setTime(now);
		}
		
		PeriodModuleClick periodModuleClick = getModuleStatisticsFromMongo(communityId, weekBegin, weekEnd);
		if (null == periodModuleClick) {
			return null;
		}
		
		if (notThisWeek) {
			sync2Redis(communityId, DateUtils.formatDate(weekBegin), PREFIX_WEEK, periodModuleClick.getModules());
		}
		
		return periodModuleClick;
	}
	
	@Override
	public PeriodModuleClick getWeekModuleStatistic(Integer communityId, Integer weekSecond,Integer moduleId) {
		if (null == communityId || null == weekSecond) {
			throw new RuntimeException("请提供小区id和统计时间!");
		}
		
		long now = System.currentTimeMillis();
		Date weekBegin = DateUtils.getWeekBegin(weekSecond * 1000L);
		if (weekBegin.getTime() > now) {
			return null;
		}
		
		if (weekBegin.getTime() < onlineMilliSecond) {
			weekBegin = onlineDate;
		}
		
		boolean notThisWeek = !DateUtils.getWeekBegin(now).equals(weekBegin);
		if (notThisWeek) {
			PeriodModuleClick weekModuleClick = getWeekModuleStatisticsFromRedis(communityId, weekBegin,moduleId);
			if (null != weekModuleClick) {
				return weekModuleClick;
			}
		}
		
		Date weekEnd = DateUtils.getWeekEnd(weekBegin);
		if (weekEnd.getTime() > now) {
			weekEnd.setTime(now);
		}
		
		PeriodModuleClick periodModuleClick = getModuleStatisticsFromMongo(communityId, weekBegin, weekEnd,moduleId);
		if (null == periodModuleClick) {
			return null;
		}
		
		if (notThisWeek) {
			sync2Redis(communityId, DateUtils.formatDate(weekBegin), PREFIX_WEEK, periodModuleClick.getModules(),moduleId);
		}
		
		return periodModuleClick;
	}
	
	@Override
	public PeriodModuleClick getMonthModuleStatistic(Integer communityId, Integer monthSecond) {
		if (null == communityId || null == monthSecond) {
			throw new RuntimeException("请提供小区id和统计时间!");
		}
		
		long now = System.currentTimeMillis();
		Date monthBegin = DateUtils.getMonthBegin(monthSecond * 1000L);
		if (monthBegin.getTime() > now) {
			return null;
		}
		
		if (monthBegin.getTime() < onlineMilliSecond) {
			monthBegin = onlineDate;
		}
		
		boolean notThisMonth = !DateUtils.getMonthBegin(now).equals(monthBegin);
		if (notThisMonth) {
			PeriodModuleClick monthModuleClick = getMonthModuleStatisticsFromRedis(communityId, monthBegin);
			if (null != monthModuleClick) {
				return monthModuleClick;
			}
		}
		
		Date monthEnd = DateUtils.getMonthEnd(monthBegin);
		if (monthEnd.getTime() > now) {
			monthEnd.setTime(now);
		}
		
		PeriodModuleClick periodModuleClick = getModuleStatisticsFromMongo(communityId, monthBegin, monthEnd);
		if (null == periodModuleClick) {
			return null;
		}
		
		if (notThisMonth) {
			sync2Redis(communityId, DateUtils.formatDate(monthBegin), PREFIX_MONTH, periodModuleClick.getModules());
		}
		
		return periodModuleClick;
	}
	
	@Override
	public PeriodModuleClick getMonthModuleStatistic(Integer communityId, Integer monthSecond,Integer moduleId) {
		if (null == communityId || null == monthSecond) {
			throw new RuntimeException("请提供小区id和统计时间!");
		}
		
		long now = System.currentTimeMillis();
		Date monthBegin = DateUtils.getMonthBegin(monthSecond * 1000L);
		if (monthBegin.getTime() > now) {
			return null;
		}
		
		if (monthBegin.getTime() < onlineMilliSecond) {
			monthBegin = onlineDate;
		}
		
		boolean notThisMonth = !DateUtils.getMonthBegin(now).equals(monthBegin);
		if (notThisMonth) {
			PeriodModuleClick monthModuleClick = getMonthModuleStatisticsFromRedis(communityId, monthBegin,moduleId);
			if (null != monthModuleClick) {
				return monthModuleClick;
			}
		}
		
		Date monthEnd = DateUtils.getMonthEnd(monthBegin);
		if (monthEnd.getTime() > now) {
			monthEnd.setTime(now);
		}
		
		PeriodModuleClick periodModuleClick = getModuleStatisticsFromMongo(communityId, monthBegin, monthEnd,moduleId);
		if (null == periodModuleClick) {
			return null;
		}
		
		if (notThisMonth) {
			sync2Redis(communityId, DateUtils.formatDate(monthBegin), PREFIX_MONTH, periodModuleClick.getModules(),moduleId);
		}
		
		return periodModuleClick;
	}
	
	@Override
	public List<PeriodModuleClick> getStatistic(Integer communityId) {
		Date yesterdayBegin = DateUtils.getDayBegin(DateUtils.getOffsetDate(-1, DateUtils.OFFSET_DAY));
		Integer todayEnd = Long.valueOf(DateUtils.getDayEnd().getTime() / 1000L).intValue();
		
		List<PeriodModuleClick> dayModuleStatistic = sort(getDayModuleStatistic(communityId, Long.valueOf(yesterdayBegin.getTime() / 1000L).intValue(), todayEnd));
		
		List<PeriodModuleClick> statistics = new ArrayList<PeriodModuleClick>(6);
		statistics.add(dayModuleStatistic.get(1));//排序后，日期小的会在前
		statistics.add(dayModuleStatistic.get(0));
		statistics.add(getWeekModuleStatistic(communityId, todayEnd));
		statistics.add(getWeekModuleStatistic(communityId, Long.valueOf(DateUtils.getOffsetDate(-1, DateUtils.OFFSET_WEEK).getTime() / 1000L).intValue()));
		statistics.add(getMonthModuleStatistic(communityId, todayEnd));
		statistics.add(getMonthModuleStatistic(communityId, Long.valueOf(DateUtils.getOffsetDate(-1, DateUtils.OFFSET_MONTH).getTime() / 1000L).intValue()));
		
		return statistics;
	}
	
	/**
	 * 获取小区在时间段内的点击情况，结果按日分组
	 * @param communityId
	 * @param startSecond
	 * @param endSecond
	 * @return
	 * 
	 * 服务ID_服务名称_用户点击量_水军点击量_点击用户数_点击水军数_统计日期
	 *                             /\   1432907000   6_送水_10_23_6_15_2015-05-29
	 *                            /||\  1432906000   ......
	 *                           / || \ 1432905000   ......
	 *  DAY_Module_Statistics_1    ||   1432904000   ......
	 *                             ||   1432903000   ......
	 *                             ||   1432902000   ......
	 *                             ||   1432901000   ......
	 */
	private List<PeriodModuleClick> getDayModuleStatisticsFromRedis(Integer communityId, Integer startSecond, Integer endSecond) {
		try (Jedis jedis = JodisUtils.getResource()) {
			List<PeriodModuleClick> dayModuleStatisticsList = new ArrayList<PeriodModuleClick>();
			
			Set<String> set = jedis.zrangeByScore(PREFIX_DAY + communityId, startSecond, endSecond);
			if (set.size() == 0) {
				return dayModuleStatisticsList;
			}
			
			PeriodModuleClick dailyModuleClick = null;
			String currentDay = null;
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				String[] values = iterator.next().split("_");
				
				String day = values[6];
				if (!day.equals(currentDay)) {
					currentDay = day;
					dailyModuleClick = new PeriodModuleClick();
					dailyModuleClick.setBegin(day);
					dailyModuleClick.setEnd(day);
					
					dayModuleStatisticsList.add(dailyModuleClick);
				}
				dailyModuleClick.addModule(new ModuleClick(values[0], values[1], _integer(values[2]), _integer(values[3]), _integer(values[4]), _integer(values[5])));
			}
			return dayModuleStatisticsList;
		}
	}
	/**
	 * 获取小区在时间段内的点击情况，结果按日分组
	 * @param communityId
	 * @param startSecond
	 * @param endSecond
	 * @param moduleId  模块名
	 * @return
	 * 
	 * 服务ID_服务名称_用户点击量_水军点击量_点击用户数_点击水军数_统计日期
	 *                             /\   1432907000   6_送水_10_23_6_15_2015-05-29
	 *                            /||\  1432906000   ......
	 *                           / || \ 1432905000   ......
	 *  DAY_Module_Statistics_1    ||   1432904000   ......
	 *                             ||   1432903000   ......
	 *                             ||   1432902000   ......
	 *                             ||   1432901000   ......
	 */
	private List<PeriodModuleClick> getDayModuleStatisticsFromRedis(Integer communityId, Integer startSecond, Integer endSecond,Integer moduleId) {
		try (Jedis jedis = JodisUtils.getResource()) {
			List<PeriodModuleClick> dayModuleStatisticsList = new ArrayList<PeriodModuleClick>();
			
			Set<String> set = jedis.zrangeByScore(PREFIX_DAY + communityId+"_"+moduleId, startSecond, endSecond);
			if (set.size() == 0) {
				return dayModuleStatisticsList;
			}
			
			PeriodModuleClick dailyModuleClick = null;
			String currentDay = null;
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				String[] values = iterator.next().split("_");
				
				String day = values[6];
				if (!day.equals(currentDay)) {
					currentDay = day;
					dailyModuleClick = new PeriodModuleClick();
					dailyModuleClick.setBegin(day);
					dailyModuleClick.setEnd(day);
					
					dayModuleStatisticsList.add(dailyModuleClick);
				}
				dailyModuleClick.addModule(new ModuleClick(values[0], values[1], _integer(values[2]), _integer(values[3]), _integer(values[4]), _integer(values[5])));
			}
			return dayModuleStatisticsList;
		}
	}
	
	private Integer _integer(String value) {
		if (StringUtils.isBlank(value)) {
			return Integer.valueOf(0);
		}
		
		return Integer.valueOf(value);
	}
	
	/**
	 * 获取小区在某一周内的点击情况
	 * @param communityId
	 * @param date 一周内的某一时间，周一到周日，周一为第一天
	 * @return
	 * 
	 * 服务ID_服务名称_用户点击量_水军点击量_点击用户数_点击水军数
	 *                              /\   1432907000   6_送水_10_23_6_15
	 *                             /||\  ..........   ......
	 *                            / || \ ..........   ......
	 *  WEEK_Module_Statistics_1    ||   ..........   ......
	 *                              ||   ..........   ......
	 *                              ||   ..........   ......
	 *                              ||   ..........   ......
	 */
	private PeriodModuleClick getWeekModuleStatisticsFromRedis(Integer communityId, Date date) {
		Date weekBegin = DateUtils.getWeekBegin(date);
		Date weekEnd = DateUtils.getWeekEnd(date);
		
		try (Jedis jedis = JodisUtils.getResource()) {
			Set<String> set = jedis.zrangeByScore(PREFIX_WEEK + communityId, Long.valueOf(weekBegin.getTime() / 1000L).intValue(), Long.valueOf(weekEnd.getTime() / 1000L).intValue());
			if (set.size() == 0) {
				return null;
			}
			
			PeriodModuleClick periodModuleClick = new PeriodModuleClick();
			periodModuleClick.setBegin(DateUtils.formatDate(weekBegin));
			periodModuleClick.setEnd(DateUtils.formatDate(weekEnd));
			
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				String[] values = iterator.next().split("_");
				
				ModuleClick moduleClick = new ModuleClick();
				moduleClick.setServiceId(values[0]);
				moduleClick.setServiceName(values[1]);
				moduleClick.setUserClick(_integer(values[2]));
				moduleClick.setTestClick(_integer(values[3]));
				moduleClick.setUserCount(_integer(values[4]));
				moduleClick.setTestCount(_integer(values[5]));
				
				periodModuleClick.addModule(moduleClick);
			}
			
			return periodModuleClick;
		}
	}
	
	/**
	 * 获取小区在某一周内的点击情况
	 * @param communityId
	 * @param date 一周内的某一时间，周一到周日，周一为第一天
	 * @return
	 * 
	 * 服务ID_服务名称_用户点击量_水军点击量_点击用户数_点击水军数
	 *                              /\   1432907000   6_送水_10_23_6_15
	 *                             /||\  ..........   ......
	 *                            / || \ ..........   ......
	 *  WEEK_Module_Statistics_1    ||   ..........   ......
	 *                              ||   ..........   ......
	 *                              ||   ..........   ......
	 *                              ||   ..........   ......
	 */
	private PeriodModuleClick getWeekModuleStatisticsFromRedis(Integer communityId, Date date,Integer moduleId) {
		Date weekBegin = DateUtils.getWeekBegin(date);
		Date weekEnd = DateUtils.getWeekEnd(date);
		
		try (Jedis jedis = JodisUtils.getResource()) {
			Set<String> set = jedis.zrangeByScore(PREFIX_WEEK + communityId+"_"+moduleId, Long.valueOf(weekBegin.getTime() / 1000L).intValue(), Long.valueOf(weekEnd.getTime() / 1000L).intValue());
			if (set.size() == 0) {
				return null;
			}
			
			PeriodModuleClick periodModuleClick = new PeriodModuleClick();
			periodModuleClick.setBegin(DateUtils.formatDate(weekBegin));
			periodModuleClick.setEnd(DateUtils.formatDate(weekEnd));
			
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				String[] values = iterator.next().split("_");
				
				ModuleClick moduleClick = new ModuleClick();
				moduleClick.setServiceId(values[0]);
				moduleClick.setServiceName(values[1]);
				moduleClick.setUserClick(_integer(values[2]));
				moduleClick.setTestClick(_integer(values[3]));
				moduleClick.setUserCount(_integer(values[4]));
				moduleClick.setTestCount(_integer(values[5]));
				
				periodModuleClick.addModule(moduleClick);
			}
			
			return periodModuleClick;
		}
	}
	
	/**
	 * 获取小区在某一月内的点击情况
	 * @param communityId
	 * @param date 一月内的某一时间
	 * @return
	 * 
	 * 服务ID_服务名称_用户点击量_水军点击量_点击用户数_点击水军数
	 *                               /\   1432907000   6_送水_10_23_6_15
	 *                              /||\  ..........   ......
	 *                             / || \ ..........   ......
	 *  MONTH_Module_Statistics_1    ||   ..........   ......
	 *                               ||   ..........   ......
	 *                               ||   ..........   ......
	 *                               ||   ..........   ......
	 */
	private PeriodModuleClick getMonthModuleStatisticsFromRedis(Integer communityId, Date date) {
		Date monthBegin = DateUtils.getMonthBegin(date);
		Date monthEnd = DateUtils.getMonthEnd(date);
		
		try (Jedis jedis = JodisUtils.getResource()) {
			Set<String> set = jedis.zrangeByScore(PREFIX_MONTH + communityId, Long.valueOf(monthBegin.getTime() / 1000L).intValue(), Long.valueOf(monthEnd.getTime() / 1000L).intValue());
			if (set.size() == 0) {
				return null;
			}
			
			PeriodModuleClick periodModuleClick = new PeriodModuleClick();
			periodModuleClick.setBegin(DateUtils.formatDate(monthBegin));
			periodModuleClick.setEnd(DateUtils.formatDate(monthEnd));
			
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				String[] values = iterator.next().split("_");
				
				ModuleClick moduleClick = new ModuleClick();
				moduleClick.setServiceId(values[0]);
				moduleClick.setServiceName(values[1]);
				moduleClick.setUserClick(_integer(values[2]));
				moduleClick.setTestClick(_integer(values[3]));
				moduleClick.setUserCount(_integer(values[4]));
				moduleClick.setTestCount(_integer(values[5]));
				
				periodModuleClick.addModule(moduleClick);
			}
			
			return periodModuleClick;
		}
	}
	
	/**
	 * 获取小区在某一月内的点击情况
	 * @param communityId
	 * @param date 一月内的某一时间
	 * @return
	 * 
	 * 服务ID_服务名称_用户点击量_水军点击量_点击用户数_点击水军数
	 *                               /\   1432907000   6_送水_10_23_6_15
	 *                              /||\  ..........   ......
	 *                             / || \ ..........   ......
	 *  MONTH_Module_Statistics_1    ||   ..........   ......
	 *                               ||   ..........   ......
	 *                               ||   ..........   ......
	 *                               ||   ..........   ......
	 */
	private PeriodModuleClick getMonthModuleStatisticsFromRedis(Integer communityId, Date date,Integer moduleId) {
		Date monthBegin = DateUtils.getMonthBegin(date);
		Date monthEnd = DateUtils.getMonthEnd(date);
		
		try (Jedis jedis = JodisUtils.getResource()) {
			Set<String> set = jedis.zrangeByScore(PREFIX_MONTH + communityId+"_"+moduleId, Long.valueOf(monthBegin.getTime() / 1000L).intValue(), Long.valueOf(monthEnd.getTime() / 1000L).intValue());
			if (set.size() == 0) {
				return null;
			}
			
			PeriodModuleClick periodModuleClick = new PeriodModuleClick();
			periodModuleClick.setBegin(DateUtils.formatDate(monthBegin));
			periodModuleClick.setEnd(DateUtils.formatDate(monthEnd));
			
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				String[] values = iterator.next().split("_");
				
				ModuleClick moduleClick = new ModuleClick();
				moduleClick.setServiceId(values[0]);
				moduleClick.setServiceName(values[1]);
				moduleClick.setUserClick(_integer(values[2]));
				moduleClick.setTestClick(_integer(values[3]));
				moduleClick.setUserCount(_integer(values[4]));
				moduleClick.setTestCount(_integer(values[5]));
				
				periodModuleClick.addModule(moduleClick);
			}
			
			return periodModuleClick;
		}
	}
	
	/**
	 * 获取小区在时间段内的点击情况，结果按日分组
	 * @param communityId
	 * @param startSecond
	 * @param endSecond
	 * @param testSet 水军集合
	 * @return
	 * 
	 * http://115.28.73.37:9090/api/V1/communities/1/modules/statistics/1441900800/1441987199
	 */
	private PeriodModuleClick getDayModuleStatisticsFromMongo(Integer communityId, Integer startSecond, Integer endSecond, Set<String> testSet) {
		BasicDBList dayModuleStatisticsList = (BasicDBList)((BasicDBObject) MongoUtils.getDB().doEval("communityModuleDayClickStatistic('" + communityId + "', " + startSecond + ", " + endSecond + ")").get("retval")).get("_firstBatch");
		if (dayModuleStatisticsList.size() > 0) {
			BasicDBObject dayModuleStatistics = (BasicDBObject)dayModuleStatisticsList.get(0);
			
			Long time = ((Double) ((BasicDBObject) dayModuleStatistics.get("_id")).get("time")).longValue() * DateUtils.MILLISECOND_OF_DAY;
			
			String day = DateUtils.formatDate(new Date(time));
			return new PeriodModuleClick(day, day, resolveModuleStatisticsResult(testSet, (BasicDBList)dayModuleStatistics.get("info")));
		}
		
		return null;
	}
	
	/**
	 * 获取小区在时间段内的指定模块的点击情况，结果按日分组
	 * @param communityId
	 * @param startSecond
	 * @param endSecond
	 * @param testSet 水军集合
	 * @param moduleId   模块名
	 * @return
	 * 
	 * http://115.28.73.37:9090/api/V1/communities/1/modules/statistics/1441900800/1441987199
	 */
	private PeriodModuleClick getDayModuleStatisticsFromMongo(Integer communityId, Integer startSecond, Integer endSecond, Set<String> testSet,Integer moduleId) {
		BasicDBList dayModuleStatisticsList = (BasicDBList)((BasicDBObject) MongoUtils.getDB().doEval("communityModuleDayClickStaticWithModuleId('" + communityId + "', " + startSecond + ", " + endSecond + ",'"+moduleId+"')").get("retval")).get("_firstBatch");
		if (dayModuleStatisticsList.size() > 0) {
			BasicDBObject dayModuleStatistics = (BasicDBObject)dayModuleStatisticsList.get(0);
			
			Long time = ((Double) ((BasicDBObject) dayModuleStatistics.get("_id")).get("time")).longValue() * DateUtils.MILLISECOND_OF_DAY;
			
			String day = DateUtils.formatDate(new Date(time));
			return new PeriodModuleClick(day, day, resolveModuleStatisticsResult(testSet, (BasicDBList)dayModuleStatistics.get("info")));
		}
		
		return null;
	}
	
	/**
	 * 获取一个时间范围内的点击情况
	 * @param communityId
	 * @param begin
	 * @param end
	 * @return
	 */
	private PeriodModuleClick getModuleStatisticsFromMongo(Integer communityId, Date begin, Date end) {
		BasicDBList moduleStatisticsList = (BasicDBList)((BasicDBObject) MongoUtils.getDB().doEval("communityModuleClickStatistic('" + communityId + "', " + begin.getTime() / 1000L + ", " + end.getTime() / 1000L + ")").get("retval")).get("_firstBatch");
		
		List<ModuleClick> moduleClickList = resolveModuleStatisticsResult(getTestSet(), moduleStatisticsList);
		if (null == moduleClickList) {
			return null;
		}
		
		PeriodModuleClick periodModuleClick = new PeriodModuleClick();
		periodModuleClick.setBegin(DateUtils.formatDate(begin));
		periodModuleClick.setEnd(DateUtils.formatDate(end));
		periodModuleClick.setModules(moduleClickList);
		
		return periodModuleClick;
	}
	
	/**
	 * 获取一个时间范围内的点击情况
	 * @param communityId
	 * @param begin
	 * @param end
	 * @param moduleId 模块id
	 * @return
	 */
	private PeriodModuleClick getModuleStatisticsFromMongo(Integer communityId, Date begin, Date end,Integer moduleId) {
		BasicDBList moduleStatisticsList = (BasicDBList)((BasicDBObject) MongoUtils.getDB().doEval("communityModuleClickStatisticWithModule('" + communityId + "', " + begin.getTime() / 1000L + ", " + end.getTime() / 1000L + ",'"+moduleId+"')").get("retval")).get("_firstBatch");
		
		List<ModuleClick> moduleClickList = resolveModuleStatisticsResult(getTestSet(), moduleStatisticsList);
		if (null == moduleClickList) {
			return null;
		}
		
		PeriodModuleClick periodModuleClick = new PeriodModuleClick();
		periodModuleClick.setBegin(DateUtils.formatDate(begin));
		periodModuleClick.setEnd(DateUtils.formatDate(end));
		periodModuleClick.setModules(moduleClickList);
		
		return periodModuleClick;
	}
	
	private List<ModuleClick> resolveModuleStatisticsResult(Set<String> testSet, BasicDBList statisticsInfo) {
		if (statisticsInfo.size() == 0) {
			return null;
		}
		
		List<ModuleClick> moduleClickList = new ArrayList<ModuleClick>();
		for (Iterator<Object> statisticsInfoIterator = statisticsInfo.iterator(); statisticsInfoIterator.hasNext();) {
			BasicDBObject moduleStatistics = (BasicDBObject)statisticsInfoIterator.next();
			
			int testClick = 0;
			Set<String> tests = new HashSet<String>();
			Set<String> users = new HashSet<String>();
			BasicDBList moduleUserClickList = (BasicDBList)moduleStatistics.get("userClick");
			for (Iterator<Object> moduleUserClickIterator = moduleUserClickList.iterator(); moduleUserClickIterator.hasNext();) {
				BasicDBObject clickInfo = (BasicDBObject)moduleUserClickIterator.next();
				String emobId = (String)clickInfo.get("emobId");
				Integer click = (Integer)clickInfo.get("click");
				
				if (testSet.contains(emobId)) {
					tests.add(emobId);
					testClick += click;
				} else {
					users.add(emobId);
				}
			}
			
			ModuleClick moduleClick = new ModuleClick();
			moduleClick.setServiceId((String)moduleStatistics.get("serviceId"));
			moduleClick.setServiceName((String)moduleStatistics.get("serviceName"));
			moduleClick.setUserClick((Integer)moduleStatistics.get("total") - testClick);
			moduleClick.setTestClick(Integer.valueOf(testClick));
			moduleClick.setUserCount(Integer.valueOf(users.size()));
			moduleClick.setTestCount(Integer.valueOf(tests.size()));
			
			moduleClickList.add(moduleClick);
		}
		
		return moduleClickList;
	}
	
	private void sync2Redis(final Integer communityId, final String date, final String prefix, final List<ModuleClick> moduleClickList) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try (Jedis jedis = JodisUtils.getResource()) {
					int dateSecond = Long.valueOf(DateUtils.parseDate(date).getTime() / 1000L).intValue();
					String key = prefix + communityId;
					
					for (int i = 0; i < moduleClickList.size(); i++) {
						ModuleClick click = moduleClickList.get(i);
						
						if (PREFIX_DAY.equals(prefix)) {
							//服务ID_服务名称_用户点击量_水军点击量_点击用户数_点击水军数_统计日期
							jedis.zadd(key, dateSecond, click.getServiceId() + "_" + click.getServiceName() + "_" + click.getUserClick() + "_" + click.getTestClick() + "_" + click.getUserCount() + "_" + click.getTestCount() + "_" + date);
						} else {
							//服务ID_服务名称_用户点击量_水军点击量_点击用户数_点击水军数
							jedis.zadd(key, dateSecond, click.getServiceId() + "_" + click.getServiceName() + "_" + click.getUserClick() + "_" + click.getTestClick() + "_" + click.getUserCount() + "_" + click.getTestCount());
						}
					}
				} catch (Exception e) {
					logger.error("Redis：模块点击统计数据同步出错!", e);
				}
			}
		}).start();
	}
	
	private void sync2Redis(final Integer communityId, final String date, final String prefix, final List<ModuleClick> moduleClickList,final Integer moduleId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try (Jedis jedis = JodisUtils.getResource()) {
					int dateSecond = Long.valueOf(DateUtils.parseDate(date).getTime() / 1000L).intValue();
					String key = prefix + communityId+"_"+moduleId;
					for (int i = 0; i < moduleClickList.size(); i++) {
						ModuleClick click = moduleClickList.get(i);
						if (PREFIX_DAY.equals(prefix)) {
							//服务ID_服务名称_用户点击量_水军点击量_点击用户数_点击水军数_统计日期
							jedis.zadd(key, dateSecond, click.getServiceId() + "_" + click.getServiceName() + "_" + click.getUserClick() + "_" + click.getTestClick() + "_" + click.getUserCount() + "_" + click.getTestCount() + "_" + date);
						} else {
							//服务ID_服务名称_用户点击量_水军点击量_点击用户数_点击水军数
							jedis.zadd(key, dateSecond, click.getServiceId() + "_" + click.getServiceName() + "_" + click.getUserClick() + "_" + click.getTestClick() + "_" + click.getUserCount() + "_" + click.getTestCount());
						}
					}
				} catch (Exception e) {
					logger.error("Redis：模块点击统计数据同步出错!", e);
				}
			}
		}).start();
	}
	
	/**
	 * 获取水军环信ID集合
	 * @return
	 */
	private Set<String> getTestSet() {
		List<TryOut> allTryOut = tryOutDao.getAllTryOut();
		
		Set<String> testSet = new HashSet<String>();
		for (int j = 0; j < allTryOut.size(); j++) {
			testSet.add(allTryOut.get(j).getEmobId());
		}
		
		return testSet;
	}
	
	private boolean isDayStated(String day, List<PeriodModuleClick> dailyModuleClicks) {
		for (int i = 0; i < dailyModuleClicks.size(); i++) {
			if (day.equals(dailyModuleClicks.get(i).getBegin())) {
				return true;
			}
		}
		return false;
	}
	
	private List<PeriodModuleClick> sort(List<PeriodModuleClick> dailyModuleClicks) {
		Collections.sort(dailyModuleClicks, new Comparator<PeriodModuleClick>() {
			@Override
			public int compare(PeriodModuleClick o1, PeriodModuleClick o2) {
				int result = o1.getBegin().compareTo(o2.getBegin());
				if (result == 0) {
					result = o1.getEnd().compareTo(o2.getEnd());
				}
				
				return result;
			}
		});
		
		return dailyModuleClicks;
	}
	
	@Override
	public Object getStatistic(Integer communityId, Integer moduleId) {
		Date yesterdayBegin = DateUtils.getDayBegin(DateUtils.getOffsetDate(-1, DateUtils.OFFSET_DAY));
		Integer todayEnd = Long.valueOf(DateUtils.getDayEnd().getTime() / 1000L).intValue();
		
		List<PeriodModuleClick> dayModuleStatistic = sort(getDayModuleStatistic(communityId, Long.valueOf(yesterdayBegin.getTime() / 1000L).intValue(), todayEnd,moduleId));
		
		List<PeriodModuleClick> statistics = new ArrayList<PeriodModuleClick>(6);
		statistics.add(dayModuleStatistic.get(1));//排序后，日期小的会在前
		statistics.add(dayModuleStatistic.get(0));
		statistics.add(getWeekModuleStatistic(communityId, todayEnd,moduleId));
		statistics.add(getWeekModuleStatistic(communityId, Long.valueOf(DateUtils.getOffsetDate(-1, DateUtils.OFFSET_WEEK).getTime() / 1000L).intValue(),moduleId));
		statistics.add(getMonthModuleStatistic(communityId, todayEnd,moduleId));
		statistics.add(getMonthModuleStatistic(communityId, Long.valueOf(DateUtils.getOffsetDate(-1, DateUtils.OFFSET_MONTH).getTime() / 1000L).intValue(),moduleId));
		
		return statistics;
	}
	
	@Override
	public List<PeriodModuleClick> getSubtotalStatics(List<PeriodModuleClick> statistic) {
		for (PeriodModuleClick periodModuleClick : statistic) {
			List<ModuleClick> modules = periodModuleClick.getModules();
			int totalCount = 0;
			Map<String,ModuleClick> map = new TreeMap<String,ModuleClick>();
			for (ModuleClick moduleClick : modules) {
				map.put(moduleClick.getServiceName(), moduleClick);
				totalCount+=moduleClick.getUserClick();
			}
			periodModuleClick.setMap(map);
			periodModuleClick.setModules(null);
			periodModuleClick.setTotalCount(totalCount+"");
		}
		return statistic;
	}
	
	@Override
	public Map<String, Integer> getClickUser(Integer communityId, Integer startTime, Integer endTime, List<Users> users) {
		if (CollectionUtils.isEmpty(users)) {
			return new HashMap<String, Integer>();
		}
		
		DBObject condition = new BasicDBObject();
		condition.put("communityId", String.valueOf(communityId));
		condition.put("day", new BasicDBObject("$gte",startTime));
		condition.put("day", new BasicDBObject("$lt",endTime));
		
		BasicDBList values = new BasicDBList();
		for (int i = 0; i < users.size(); i++) {
			values.add(String.valueOf(users.get(i).getUserId()));
		}
		condition.put("user_id", new BasicDBObject("$in", values));
		
		Map<String, Integer> userClickMap = new HashMap<String, Integer>();
		DB db = MongoUtils.getDB();
		try (DBCursor dbCursor = db.getCollection("daily_users").find(condition)) {
			while (dbCursor.hasNext()) {
				DBObject result = dbCursor.next();
				
				String emobId = (String)result.get("emob_id");
				if (null != emobId && !"".equals(emobId)) {
					Integer count = userClickMap.get(emobId);
					if (null == count) {
						count = (Integer)result.get("click");
					} else {
						count = count + (Integer)result.get("click");
					}
					
					userClickMap.put(emobId, count);
				}
			}
		}
		
		DBObject condition1 = new BasicDBObject();
		condition1.put("communityId", String.valueOf(communityId));
		condition1.put("hour", new BasicDBObject("$gte",startTime));
		condition1.put("hour", new BasicDBObject("$lt",endTime));
		condition1.put("user_id", new BasicDBObject("$in", values));
		
		try (DBCursor dbCursor1 = db.getCollection("hourly_events").find(condition1)) {
			while (dbCursor1.hasNext()) {
				DBObject result = dbCursor1.next();
				
				String emobId = (String)result.get("emob_id");
				if (null != emobId && !"".equals(emobId)) {
					Integer count = userClickMap.get(emobId);
					if (null == count) {
						count = (Integer)result.get("click");
					} else {
						count = count + (Integer)result.get("click");
					}
					
					userClickMap.put(emobId, count);
				}
			}
		}
		
		return userClickMap;
	}
}

//db.system.js.save({
//	_id: 'communityModuleDayClickStatistic',
//	comment: '小区模块日点击统计',
//	value: function(communityId, start, end) {
//		var result = db.hourly_events.aggregate([{
//			$match: {
//				communityId:communityId,
//				hour:{
//					$gte:start,
//					$lte:end
//				}
//			}
//		}, {
//			$group:{
//				_id:{
//					time:{
//						$subtract:[{
//							$divide:[{
//								$add: ["$hour", 28800]//北京位于东八区，时间上是+8:00，28800=60*60*8,
//							}, 86400]
//						}, {
//							$mod:[{
//								$divide:[{
//									$add: ["$hour", 28800]
//								}, 86400]
//							}, 1]
//						}]
//					},
//					serviceId:"$service_id",
//					serviceName:"$service_name"
//				},
//				total:{$sum:"$click"},
//				userClick:{
//					$addToSet:{
//						emobId:"$emob_id",
//						click:"$click",
//						time: "$hour"
//					}
//				}
//			}
//		}, {
//			$group:{
//				_id:{time:"$_id.time"},
//				info:{
//					$addToSet:{
//						total:"$total",
//						serviceId:"$_id.serviceId",
//						serviceName:"$_id.serviceName",
//						userClick:"$userClick"
//					}
//				}
//			}
//		}]);
//		return result;
//	}
//});

//db.system.js.save({
//	_id: 'communityModuleWeekClickStatistic',
//	comment: '小区模块周点击统计',
//	value: function(communityId, start, end) {
//		var result = db.hourly_events.aggregate([{
//			$match: {
//				communityId:communityId,
//				hour:{
//					$gte:start,
//					$lte:end
//				}
//			}
//		}, {
//			$group:{
//				_id:{
//					time:{
//						$subtract:[{
//							$divide:[{
//								$add: ["$hour", 28800]//北京位于东八区，时间上是+8:00，28800=60*60*8,
//							}, 604800]
//						}, {
//							$mod:[{
//								$divide:[{
//									$add: ["$hour", 28800]
//								}, 604800]
//							}, 1]
//						}]
//					},
//					serviceId:"$service_id",
//					serviceName:"$service_name"
//				},
//				total:{$sum:"$click"},
//				userClick:{
//					$addToSet:{
//						emobId:"$emob_id",
//						click:"$click",
//						time: "$hour"
//					}
//				}
//			}
//		}, {
//			$group:{
//				_id:{time:"$_id.time"},
//				info:{
//					$addToSet:{
//						total:"$total",
//						serviceId:"$_id.serviceId",
//						serviceName:"$_id.serviceName",
//						userClick:"$userClick"
//					}
//				}
//			}
//		}]);
//		return result;
//	}
//});
//
//db.system.js.save({
//	_id: 'communityModuleClickStatistic',
//	comment: '小区模块点击统计',
//	value: function(communityId, start, end) {
//		var result = db.hourly_events.aggregate([{
//			$match: {
//				communityId:communityId,
//				hour:{
//					$gte:start,
//					$lte:end
//				}
//			}
//		}, {
//			$group:{
//				_id:{
//					serviceId:"$service_id",
//					serviceName:"$service_name"
//				},
//				serviceId: {$first: "$service_id"},
//				serviceName: {$first: "$service_name"},
//				total:{$sum:"$click"},
//				userClick:{
//					$addToSet:{
//						emobId:"$emob_id",
//						click:"$click",
//						time: "$hour"
//					}
//				}
//			}
//		}]);
//		return result;
//	}
//});