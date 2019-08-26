package com.xj.timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xj.bean.Communities;
import com.xj.dao.CommunitiesDao;
import com.xj.stat.po.StatisticsTime;
import com.xj.stat.service.StatService;
import com.xj.stat.service.StatisticsTimeService;

public class UserClickStatistics {

	@Autowired
	private CommunitiesDao communitiesDao;

	@Autowired
	private StatisticsTimeService statisticsTimeService;

	@Autowired
	private StatService statService;

	public void execute() {
		try {
			List<Communities> l = communitiesDao.getListCommunityQ();
			List<StatisticsTime> statisticsTimeList = statisticsTimeService.selectStatisticsTime();
			StatisticsTime statisticsTime = statisticsTimeList.get(0);
			List<Date> dateList = findDates(new Date(statisticsTime.getStatisticsUserTime() * 1000l), new Date());
			for (int i = 1; i < dateList.size() - 2; i++) {
				for (Communities communities : l) {
					statService.statUsers2((dateList.get(i).getTime() / 1000) + "", (((dateList.get(i).getTime() + (24 * 60 * 60 * 1000)) / 1000) - 1) + "", communities.getCommunityId());
				}
				statisticsTime.setStatisticsUserTime((int) (dateList.get(i).getTime() / 1000));
				statisticsTimeService.insertStatisticsTime(statisticsTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> dates = new ArrayList<Date>();
		dates.add(dBegin);
		
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(dBegin);
		
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(dEnd);
		
		while (dEnd.after(calBegin.getTime())) {// 测试此日期是否在指定日期之后
			calBegin.add(Calendar.DAY_OF_MONTH, 1);// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			dates.add(calBegin.getTime());
		}
		
		return dates;
	}
}