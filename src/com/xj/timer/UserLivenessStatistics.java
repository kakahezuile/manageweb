package com.xj.timer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.xj.bean.Communities;
import com.xj.dao.CommunitiesDao;
import com.xj.dao.Liveness;
import com.xj.service.UserService;
import com.xj.stat.service.StatService;
import com.xj.utils.DateUtils;

public class UserLivenessStatistics {

	@Autowired
	private CommunitiesDao communitiesDao;

	@Autowired
	private StatService statService;

	@Autowired
	private UserService userService;

	public void execute() {
		try {
			List<Communities> l = communitiesDao.getListCommunityQ();
			for (Communities communities : l) {
				Integer communityId= communities.getCommunityId();
				
				List<Integer> today = statService.statUsers(getTodayTime().get(0), getTodayTime().get(1),communityId);
				List<Integer> yesterday = statService.statUsers(getYesterdayTime().get(0), getYesterdayTime().get(1),communityId);
				List<Integer> Week = statService.statUsers(getWeekTime().get(0), getWeekTime().get(1),communityId);
				List<Integer> lastWeek = statService.statUsers(lastWeekTime().get(0), lastWeekTime().get(1),communityId);
				
				Map<String, List<Integer>> map = new  HashMap<String, List<Integer>>();
				map.put("today", today);
				map.put("yesterday", yesterday);
				map.put("Week", Week);
				map.put("lastWeek", lastWeek);
				
				Liveness liveness = new Liveness();
				liveness.setLastWeek(lastWeek.get(3));
				liveness.setLastWeekInstalls(lastWeek.get(1));
				liveness.setYesterday(yesterday.get(3));
				liveness.setYesterdayInstalls(yesterday.get(1));
				liveness.setCommunityId(communityId);
				
				userService.upLiveness(liveness);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<String> getTodayTime() throws Exception{
		ArrayList<String> list = new  ArrayList<String>();
		
		Calendar instance = Calendar.getInstance();
		Date time = instance.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String format = dateFormat.format(time);
		List<String> times = DateUtils.get24Time(format);
		
		String[] split = times.get(0).split("#");
		String[] split1 = times.get(times.size()-1).split("#");
		String startTime = split[1];
		String endTime = split1[2];
		list.add(startTime);
		list.add(endTime);
		return list;
	}
	
	private List<String> getYesterdayTime() throws Exception{
		ArrayList<String> list = new  ArrayList<String>();
		
		Calendar instance = Calendar.getInstance();
		Date time = instance.getTime();
		
		Long longtime = time.getTime()-1000*60*60*24;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String format = dateFormat.format(new Date(longtime));
		List<String> times = DateUtils.get24Time(format);
		
		String[] split = times.get(0).split("#");
		String[] split1 = times.get(times.size()-1).split("#");
		String startTime = split[1];
		String endTime = split1[2];
		list.add(startTime);
		list.add(endTime);
		return list;
	}
	
	/**
	 * 本周一至今
	 * @return
	 * @throws Exception
	 */
	private List<String> getWeekTime() throws Exception{
		ArrayList<String> list = new  ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		//n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
		int n = 0;
		
		int j = cal.get(Calendar.DAY_OF_WEEK);
		if(j==1){
			n = 0-1;
		}
		
		String monday;
		cal.add(Calendar.DATE, n*7);
		//想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//		上周一
		List<String> times = DateUtils.get24Time(monday);
		String[] split = times.get(0).split("#");
		String startTime = split[1];
		
//		上周日
		Long  time = cal.getTime().getTime()+1000*60*60*24*6;
		String sunday = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
		List<String> times2 = DateUtils.get24Time(sunday);
		String[] split1 = times2.get(times.size()-1).split("#");
		String endTime = split1[2];
		
		list.add(startTime);
		list.add(endTime);
		return list;
	}
	
	
	private List<String> lastWeekTime() throws Exception {
		ArrayList<String> list = new  ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		//n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
		int n = -1;
		
		int j = cal.get(Calendar.DAY_OF_WEEK);
		if(j==1){
			n = -1-1;
		}
		
		String monday;
		cal.add(Calendar.DATE, n*7);
		//想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//		上周一
		List<String> times = DateUtils.get24Time(monday);
		String[] split = times.get(0).split("#");
		String startTime = split[1];
		
//		上周日
		Long  time = cal.getTime().getTime()+1000*60*60*24*6;
		String sunday = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
		List<String> times2 = DateUtils.get24Time(sunday);
		String[] split1 = times2.get(times.size()-1).split("#");
		String endTime = split1[2];
		
		list.add(startTime);
		list.add(endTime);
		return list;
	}
}