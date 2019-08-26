package com.xj.stat.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.bean.resource.ClickMobule;
import com.xj.stat.bean.resource.TopUser;
import com.xj.stat.service.StatService;
import com.xj.stat.service.StatisticsUserService;
import com.xj.stat.utils.DateUtil;
import com.xj.utils.DateUtils;

/**
 * @author lence
 * @date 2015年7月9日上午1:25:43
 */
@Component
@Path("/statisticsUser")
@Scope("prototype")
public class StatisticsUserController {

	@Autowired
	private StatService statService;

	@Autowired
	private StatisticsUserService statisticsUserService;

	private Gson gson = new Gson();

	@Path("/statisticsModuleNum")
	@POST
	public String statisticsModuleNum(String json) throws Exception {
		BeanUtil beanUtil = gson.fromJson(json, BeanUtil.class);
		
		List<Integer> thisWeek = DateUtil.getWeek(0);
		List<Integer> lastWeek = DateUtil.getWeek(-1);
		List<Integer> thisMonth = DateUtil.getLastMonth(0);
		List<Integer> lastMonth = DateUtil.getLastMonth(-1);
		String[] yesterdayTime = getYesterdayTime();
		List<Integer> total=DateUtil.getOnlineTime("2015-6-1");
		// 昨日数据
		beanUtil.setStartTime(Integer.parseInt(yesterdayTime[0]));
		beanUtil.setEndTime(Integer.parseInt(yesterdayTime[1]));
		List<ClickMobule> esterdayList = statService.statisticsModuleNum(beanUtil);
		
		// 本周数据
		beanUtil.setStartTime(thisWeek.get(0));
		beanUtil.setEndTime(thisWeek.get(1));
		List<ClickMobule> thisWeekList = statService.statisticsModuleNum(beanUtil);
		
		// 上周数据
		beanUtil.setStartTime(lastWeek.get(0));
		beanUtil.setEndTime(lastWeek.get(1));
		List<ClickMobule> lastWeekList = statService.statisticsModuleNum(beanUtil);
		
		// 本月数据
		beanUtil.setStartTime(thisMonth.get(0));
		beanUtil.setEndTime(thisMonth.get(1));
		List<ClickMobule> thisMonthList = statService.statisticsModuleNum(beanUtil);
		
		// 上月数据
		beanUtil.setStartTime(lastMonth.get(0));
		beanUtil.setEndTime(lastMonth.get(1));
		List<ClickMobule> lastMonthList = statService.statisticsModuleNum(beanUtil);
		
		// 全部
		beanUtil.setStartTime(total.get(0));
		beanUtil.setEndTime(total.get(1));
		List<ClickMobule> totalList = statService.statisticsModuleNum(beanUtil);
		
		Map<String,Object> map=new HashMap<String,Object>();	
		map.put("to_day", esterdayList);
		map.put("this_week", thisWeekList);
		map.put("last_week", lastWeekList);
		map.put("this_month", thisMonthList);
		map.put("last_month", lastMonthList);
		map.put("total_id", totalList);
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo(map);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@Path("/statisticsUserNum")
	@POST
	public String statisticsUserNum(String json) throws Exception {
		BeanUtil beanUtil = gson.fromJson(json, BeanUtil.class);
		List<Integer> thisWeek = DateUtil.getWeek(0);
		List<Integer> lastWeek = DateUtil.getWeek(-1);
		List<Integer> thisMonth = DateUtil.getLastMonth(0);
		List<Integer> lastMonth = DateUtil.getLastMonth(-1);
		String[] todayTime = getTodayTime();
		String[] yesterdayTime = getYesterdayTime();
		Set<String> set = new HashSet<String>();
		Set<String> s = statService.thisDay(beanUtil.getCommunityId());

		// 今日数据
		beanUtil.setStartTime(Integer.parseInt(todayTime[0]));
		beanUtil.setEndTime(Integer.parseInt(todayTime[1]));
		TopUser todayTop = statService.statisticsUserNum(s, beanUtil);

		// 昨日数据
		beanUtil.setStartTime(Integer.parseInt(yesterdayTime[0]));
		beanUtil.setEndTime(Integer.parseInt(yesterdayTime[1]));
		TopUser yesterdayTop = statService.statisticsUserNum(set, beanUtil);

		// 本周数据
		beanUtil.setStartTime(thisWeek.get(0));
		beanUtil.setEndTime(thisWeek.get(1));
		TopUser thisWeekTop = statService.statisticsUserNum(s, beanUtil);

		// 上周数据
		beanUtil.setStartTime(lastWeek.get(0));
		beanUtil.setEndTime(lastWeek.get(1));
		TopUser lastWeekTop = statService.statisticsUserNum(set, beanUtil);

		// 本月数据
		beanUtil.setStartTime(thisMonth.get(0));
		beanUtil.setEndTime(thisMonth.get(1));
		TopUser thisMonthTop = statService.statisticsUserNum(s, beanUtil);

		// 上月数据
		beanUtil.setStartTime(lastMonth.get(0));
		beanUtil.setEndTime(lastMonth.get(1));
		TopUser lastMonthTop = statService.statisticsUserNum(set, beanUtil);

		List<TopUser> topUserList = new ArrayList<TopUser>();
		topUserList.add(todayTop);
		topUserList.add(yesterdayTop);
		topUserList.add(thisWeekTop);
		topUserList.add(lastWeekTop);
		topUserList.add(thisMonthTop);
		topUserList.add(lastMonthTop);

		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo(topUserList);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@Path("/statisticsUserNum2")
	@POST
	public String statisticsUserNum2(String json) throws Exception {
		BeanUtil beanUtil = gson.fromJson(json, BeanUtil.class);
		List<Integer> thisWeek = DateUtil.getWeek(0);
		List<Integer> lastWeek = DateUtil.getWeek(-1);
		List<Integer> thisMonth = DateUtil.getLastMonth(0);
		List<Integer> lastMonth = DateUtil.getLastMonth(-1);
		String[] yesterdayTime = getYesterdayTime();
		
		statService.thisDay(beanUtil.getCommunityId());
		
		// 昨日数据
		beanUtil.setStartTime(Integer.parseInt(yesterdayTime[0]));
		beanUtil.setEndTime(Integer.parseInt(yesterdayTime[1]));
		List<String> esterdayList= statisticsUserService.selectUserEmob(beanUtil);
		
		// 本周数据
		beanUtil.setStartTime(thisWeek.get(0));
		beanUtil.setEndTime(thisWeek.get(1));
		List<String> thisWeekList= statisticsUserService.selectUserEmob(beanUtil);
		
		// 上周数据
		beanUtil.setStartTime(lastWeek.get(0));
		beanUtil.setEndTime(lastWeek.get(1));
		List<String> lastWeekList= statisticsUserService.selectUserEmob(beanUtil);
		
		// 本月数据
		beanUtil.setStartTime(thisMonth.get(0));
		beanUtil.setEndTime(thisMonth.get(1));
		List<String> thisMonthList= statisticsUserService.selectUserEmob(beanUtil);
		
		// 上月数据
		beanUtil.setStartTime(lastMonth.get(0));
		beanUtil.setEndTime(lastMonth.get(1));
		List<String> lastMonthList= statisticsUserService.selectUserEmob(beanUtil);
		
		Map<String,Object> map=new HashMap<String,Object>();	
		map.put("to_day", esterdayList);
		map.put("this_week", thisWeekList);
		map.put("last_week", lastWeekList);
		map.put("this_month", thisMonthList);
		map.put("last_month", lastMonthList);
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo(map);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@Path("/thisStatisticsUserNum")
	@POST
	public String thisStatisticsUserNum(String json) throws Exception {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo(statService.thisDay2(gson.fromJson(json, BeanUtil.class).getCommunityId()));
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	private String[] getTodayTime() throws Exception {
		List<String> times = DateUtils.get24Time(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		return new String[] {times.get(0).split("#")[1], times.get(times.size() - 1).split("#")[2]};
	}

	/**
	 * 上一天
	 * @return
	 * @throws Exception
	 */
	private String[] getYesterdayTime() throws Exception {
		List<String> times = DateUtils.get24Time(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)));
		
		return new String[] {times.get(0).split("#")[1], times.get(times.size() - 1).split("#")[2]};
	}
}