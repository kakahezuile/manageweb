package com.xj.stat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.xj.bean.Page;
import com.xj.jodis.JodisUtils;
import com.xj.mongo.MongoUtils;
import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.bean.parameter.CurrentTimeRange;
import com.xj.stat.bean.resource.WelfareStatics;
import com.xj.stat.dao.WelfareStaticsDao;
import com.xj.stat.service.WelfareStaticsService;
import com.xj.utils.DateUtils;

import redis.clients.jedis.Jedis;

/**
 * 福利统计service实现类
 * @author Administrator
 */
@Service
public class WelfareStaticsServiceImpl implements WelfareStaticsService {

	private static final Logger logger = Logger.getLogger(WelfareStaticsServiceImpl.class);
	
	@Autowired
	private WelfareStaticsDao welfareStaticsDao;

	@Override
	public List<WelfareStatics> statWelfare(Integer communityId, Integer startSecond, Integer endSecond,Integer moduleId) {
		
		List<Date> dates = DateUtils.findBetweenDates(new Date(startSecond * 1000L), new Date(endSecond * 1000L));
		List<WelfareStatics> statistics = new ArrayList<WelfareStatics>(dates.size());
		long todayBegin = DateUtils.getDayBegin().getTime();
		for (Date date : dates) {
			if (DateUtils.getDayBegin(date).getTime() > todayBegin) {
				continue;
			}
			
			statistics.add(stat(communityId, date, STATISTICS_DAY));//TODO 这里会多次打开关闭redis连接，需要改进
		}
		return statistics;
	}

	/**
	 * 统计小区福利购买量
	 */
	@Override
	public List<WelfareStatics> statWelfare(Integer communityId,Integer moduleId) {
		List<WelfareStatics> statistics = welfareStaticsDao.statWelfareOrders(new CurrentTimeRange(communityId));
		
		statistics.add(1, stat(communityId, DateUtils.getOffsetDate(-1, DateUtils.OFFSET_DAY), STATISTICS_DAY));
		statistics.add(3, stat(communityId, DateUtils.getOffsetDate(-1, DateUtils.OFFSET_WEEK), STATISTICS_WEEK));
		statistics.add(stat(communityId, DateUtils.getOffsetDate(-1, DateUtils.OFFSET_MONTH), STATISTICS_MONTH));
		
		return statistics;
	}
	
	private WelfareStatics stat(Integer communityId, Date day, int type) {
		String key = null;
		boolean notToday = true;//notToday -> 不是今天
		Integer end = null;
		if (type == STATISTICS_DAY) {
			day = DateUtils.getDayBegin(day);
			end = Long.valueOf(DateUtils.getDayEnd(day).getTime() / 1000L).intValue();
			notToday = !day.equals(DateUtils.getDayBegin(new Date()));
			
			key = "WelfareStatistics_DAY_" + communityId + "_" + DateUtils.formatDate(day);
		} else if (type == STATISTICS_WEEK) {
			day = DateUtils.getWeekBegin(day);
			end = Long.valueOf(DateUtils.getWeekEnd(day).getTime() / 1000L).intValue();
			notToday = !day.equals(DateUtils.getWeekBegin(new Date()));
			
			key = "WelfareStatistics_WEEK_" + communityId + "_" + DateUtils.formatDate(day);
		} else if (type == STATISTICS_MONTH) {
			day = DateUtils.getMonthBegin(day);
			end = Long.valueOf(DateUtils.getMonthEnd(day).getTime() / 1000L).intValue();
			notToday = !day.equals(DateUtils.getMonthBegin(new Date()));
			
			key = "WelfareStatistics_MONTH_" + communityId + "_" + DateUtils.formatDate(day);
		}
		
		if (notToday) {
			try (Jedis jedis = JodisUtils.getResource()) {
				String statistics = jedis.get(key);
				if (StringUtils.isNotBlank(statistics)) {
					WelfareStatics welfareStatics = new WelfareStatics();
					welfareStatics.setBuyNum(statistics);
					return welfareStatics;
				}
			} catch (Exception e) {
				logger.error("Redis：获取统计数据出错!", e);
			}
		}
		
		WelfareStatics welfareStatics = welfareStaticsDao.statWelfareOrders(new BeanUtil(communityId, Long.valueOf(day.getTime() / 1000L).intValue(), end));
		if (notToday) {
			JodisUtils.set(key, welfareStatics.getBuyNum());
		}
		
		return welfareStatics;
	}

	@Override
	public List<WelfareStatics> getAllWelfareStatics(Integer communityId, Integer pageSize, Integer pageNum) {
		return welfareStaticsDao.getAllWelfareStatics(communityId,pageNum,pageSize);
	}

	@Override
	public Page<WelfareStatics> getWelfareOrdersDetail(Integer welfareId, Integer pageNum, Integer pageSize) {
		List<WelfareStatics> welfareStatics = welfareStaticsDao.getWelfareOrdersDetail(welfareId,pageNum,pageSize);
		Page<WelfareStatics> pageBean = new Page<WelfareStatics>(pageNum, 10, pageSize, 10);
		pageBean.setPageData(welfareStatics);
		return pageBean;
	}

	@Override
	public WelfareStatics getWelfareInfo(Integer welfareId) {
		return welfareStaticsDao.getWelfareInfo(welfareId);
	}

	@Override
	public List<WelfareStatics> getClickDetailDay(Integer communityId,Integer moduleId, String time,Integer pageNum,Integer pageSize) {
		Integer start = (int)(DateUtils.getDayBegin(time).getTime() / 1000L);
		Integer end = (int)(DateUtils.getDayEnd(time).getTime() / 1000L);
		
		DBCollection collection = MongoUtils.getCollection("hourly_events");
		DBObject conditon = new BasicDBObject("service_id", moduleId+"");
		conditon.put("communityId", communityId+"");
		conditon.put("emob_id", new BasicDBObject("$ne", ""));
		DBObject hour = new BasicDBObject("$gte", start);
		hour.put("$lte", end);
		conditon.put("hour", hour);
		DBCursor find = collection.find(conditon).sort(new BasicDBObject("hour", -1));//.skip((pageNum-1)*pageSize).limit(pageSize);
		Map<String,Long> map = new HashMap<String, Long>();
		while(find.hasNext()){
			DBObject next = find.next();
			String user = (String)next.get("emob_id");
			Long createTime = (Long)next.get("hour");
			if(StringUtils.isNoneBlank(user)){
				map.put(user, createTime);
			}
		}
		Object[] users =  map.keySet().toArray();
		List<WelfareStatics> welfareStatics = welfareStaticsDao.getClickUserDetailDay(users);
		
		for (WelfareStatics welfareStatics2 : welfareStatics) {
			welfareStatics2.setCreateTime(Long.toString(map.get(welfareStatics2.getEmobId())));
		}
		return welfareStatics;
		
	}

	@Override
	public List<WelfareStatics> getwelfareBuys(Integer communityId, String time, Integer pageNum, Integer pageSize) {
		Integer start = (int)(DateUtils.getDayBegin(time).getTime() / 1000L);
		Integer end = (int)(DateUtils.getDayEnd(time).getTime() / 1000L);
		
		return welfareStaticsDao.getwelfareBuys(communityId,start,end,pageNum,pageSize);
	}
}