package com.xj.stat.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mongodb.util.JSON;
import com.xj.bean.Communities;
import com.xj.bean.OrderEmobId;
import com.xj.bean.StatBean;
import com.xj.dao.CommunitiesDao;
import com.xj.dao.Liveness;
import com.xj.dao.ShopsOrderHistoryDao;
import com.xj.stat.service.StatService;
import com.xj.stat.service.UserService;
import com.xj.stat.utils.DateUtil;
import com.xj.utils.DateUtils;

/**
 *@author lence
 *@date 2015年7月9日上午1:25:43
 */
@RequestMapping("/stat")
@Controller
public class StatUserController {

	@Autowired
	private StatService statService;

	@Autowired
	private CommunitiesDao communitiesDao;

	@Autowired
	private  UserService userService;

	@Autowired
	private com.xj.service.UserService  userService2;

	@Autowired
	private ShopsOrderHistoryDao shopsOrderHistoryDao;
	
	@RequestMapping("/toIndex1.do")
	public String toIndex1(Model model) throws Exception{
		Map<String,Integer> cities = statService.getCities();
		model.addAttribute("cities", cities);
		return "tvstatistics/top";
	}
	
	
	@RequestMapping("/toHome.do")
	public String toIndex3(Model model) throws Exception{
		Map<String,Integer> cities = statService.getCities();
		model.addAttribute("cities", cities);
		return "tvstatistics/top3";
	}
	
	
	
	@RequestMapping("/toIndex.do")
	public String toIndex(Model model,Integer communityId,HttpServletRequest request,String leftNum) throws Exception{
//		List<String> ips = statService.getuserIP();
//		String ipAddr = getIpAddr(request);

//		Calendar instance = Calendar.getInstance();
//		Date time = instance.getTime();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String format = dateFormat.format(time);
//		List<String> get24Time = DateUtils.get24Time(format);
		
		
		List<Integer> today = statService.statUsers(getTodayTime().get(0), getTodayTime().get(1),communityId);
		List<Integer> yesterday = statService.statUsers(getYesterdayTime().get(0), getYesterdayTime().get(1),communityId);
		List<Integer> Week = statService.statUsers(getWeekTime().get(0), getWeekTime().get(1),communityId);
		List<Integer> lastWeek = statService.statUsers(lastWeekTime().get(0), lastWeekTime().get(1),communityId);
		
		Map<String, List<Integer>> map = new  HashMap<String, List<Integer>>();
		map.put("today", today);
		map.put("yesterday", yesterday);
		map.put("Week", Week);
		map.put("lastWeek", lastWeek);
		
		List<String> installUsers = userService.getAllInstallUserByCommunity(communityId);
		List<String> registUsers = userService.getAllRegisService(communityId);
		
		Map<String,Integer> cities = statService.getCities();
		
		model.addAttribute("tootalInstall", installUsers.size());
		model.addAttribute("tootalRegist", registUsers.size());
		model.addAttribute("map", map);
		model.addAttribute("communityId", communityId);
		model.addAttribute("leftNum", leftNum);
		model.addAttribute("cities", cities);
		
		return "tvstatistics/index1";
		
	}
	@RequestMapping("/toIndex3.do")
	public String toIndex3(Model model,Integer communityId,HttpServletRequest request,String leftNum) throws Exception{
//		    List<String> ips = statService.getuserIP();
//		    String ipAddr = getIpAddr(request);
			
//			Calendar instance = Calendar.getInstance();
//			Date time = instance.getTime();
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			String format = dateFormat.format(time);
//			List<String> get24Time = DateUtils.get24Time(format);
			
			List<Integer> today = statService.statUsers(getTodayTime().get(0), getTodayTime().get(1),communityId);
			List<Integer> yesterday = statService.statUsers(getYesterdayTime().get(0), getYesterdayTime().get(1),communityId);
			List<Integer> Week = statService.statUsers(getWeekTime().get(0), getWeekTime().get(1),communityId);
			List<Integer> lastWeek = statService.statUsers(lastWeekTime().get(0), lastWeekTime().get(1),communityId);
			
			Map<String, List<Integer>> map = new  HashMap<String, List<Integer>>();
			map.put("today", today);
			map.put("yesterday", yesterday);
			map.put("Week", Week);
			map.put("lastWeek", lastWeek);
			
			List<String> installUsers = userService.getAllInstallUserByCommunity(communityId);
			List<String> registUsers = userService.getAllRegisService(communityId);
			
			model.addAttribute("tootalInstall", installUsers.size());
			model.addAttribute("tootalRegist", registUsers.size());
			model.addAttribute("map", map);
			model.addAttribute("communityId", communityId);
			model.addAttribute("leftNum", leftNum);
			return "tvstatistics/index2";
		
	}
	@RequestMapping("/toIndex2.do")
	public void toIndex2(Model model,HttpServletRequest request) throws Exception{
//		List<String> ips = statService.getuserIP();
		
		List<Communities> l=   communitiesDao.getListCommunityQ();
		for (Communities communities : l) {
			Integer communityId=communities.getCommunityId();
//			Calendar instance = Calendar.getInstance();
//			Date time = instance.getTime();
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			String format = dateFormat.format(time);
//			List<String> get24Time = DateUtils.get24Time(format);
			
			List<Integer> today = statService.statUsers(getTodayTime().get(0), getTodayTime().get(1),communityId);
			List<Integer> yesterday = statService.statUsers(getYesterdayTime().get(0), getYesterdayTime().get(1),communityId);
			List<Integer> Week = statService.statUsers(getWeekTime().get(0), getWeekTime().get(1),communityId);
			List<Integer> lastWeek = statService.statUsers(lastWeekTime().get(0), lastWeekTime().get(1),communityId);
			
			Map<String, List<Integer>> map = new  HashMap<String, List<Integer>>();
			map.put("today", today);
			map.put("yesterday", yesterday);
			map.put("Week", Week);
			map.put("lastWeek", lastWeek);
			
			Liveness liveness=new Liveness();
			liveness.setLastWeek(lastWeek.get(3));
			liveness.setLastWeekInstalls(lastWeek.get(1));
			liveness.setYesterday(yesterday.get(3));
			liveness.setYesterdayInstalls(yesterday.get(1));
			liveness.setCommunityId(communityId);
			userService2.upLiveness(liveness);
		}
	}
	
//	@RequestMapping("/statisticsUser.do")
//	public void statisticsUser(HttpServletRequest request,PrintWriter pw,Model model) throws Exception{
//		List<Communities> l=   communitiesDao.getListCommunityQ();
//		
//		for (Communities communities : l) {
//			Integer communityId=communities.getCommunityId();
//			Calendar instance = Calendar.getInstance();
//			Date time = instance.getTime();
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			String format = dateFormat.format(time);
//			List<String> get24Time = DateUtils.get24Time(format);
//		//	List<Integer> today = statService.statUsers2(getTodayTime().get(0), getTodayTime().get(1),communityId);
//		}
//	}
	
//	private List<String> getTodayTime2() throws Exception{
//		ArrayList<String> list = new  ArrayList<String>();
//		
//		Calendar instance = Calendar.getInstance();
//		Date time = instance.getTime();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String format = dateFormat.format(time);
//		List<String> times = DateUtils.get24Time(format);
//		
//		String[] split = times.get(0).split("#");
//		String[] split1 = times.get(times.size()-1).split("#");
//		String startTime = split[1];
//		String endTime = split1[2];
//		list.add(startTime);
//		list.add(endTime);
//		return list;
//	}
	
	@RequestMapping("/getStat.do")
	public void getStat(HttpServletRequest request,PrintWriter pw,Model model,Integer communityId) throws Exception{
		
		Map<String, List<Integer>> map = new  HashMap<String, List<Integer>>();
		
		Liveness liveness=	userService2.getLiveness(communityId);
		if(liveness==null){
			//Calendar instance = Calendar.getInstance();
			//Date time = instance.getTime();
			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//String format = dateFormat.format(time);
			//List<String> get24Time = DateUtils.get24Time(format);
			
			List<Integer> today = statService.statUsers(getTodayTime().get(0), getTodayTime().get(1),communityId);
			List<Integer> yesterday = statService.statUsers(getYesterdayTime().get(0), getYesterdayTime().get(1),communityId);
			List<Integer> Week = statService.statUsers(getWeekTime().get(0), getWeekTime().get(1),communityId);
			List<Integer> lastWeek = statService.statUsers(lastWeekTime().get(0), lastWeekTime().get(1),communityId);
			
			map.put("today", today);
			map.put("yesterday", yesterday);
			map.put("Week", Week);
			map.put("lastWeek", lastWeek);
			Liveness liveness2=new Liveness();
			liveness2.setLastWeek(lastWeek.get(3));
			liveness2.setLastWeekInstalls(lastWeek.get(1));
			liveness2.setYesterday(yesterday.get(3));
			liveness2.setYesterdayInstalls(yesterday.get(1));
			liveness2.setCommunityId(communityId);
			userService2.upLiveness(liveness2);
		}else{
			List<Integer> today =new ArrayList<Integer>();
			List<Integer> yesterday =new ArrayList<Integer>();
			List<Integer> Week =new ArrayList<Integer>();
			List<Integer> lastWeek=new ArrayList<Integer>();
			today.add(0);
			today.add(0);
			today.add(0);
			today.add(0);
			yesterday.add(0);
			yesterday.add(liveness.getYesterdayInstalls());
			yesterday.add(0);
			yesterday.add(liveness.getYesterday());
			Week.add(0);
			Week.add(0);
			Week.add(0);
			Week.add(0);
            lastWeek.add(0);			
            lastWeek.add(liveness.getLastWeekInstalls());			
            lastWeek.add(0);			
            lastWeek.add(liveness.getLastWeek());			
			map.put("today", today);
			map.put("yesterday", yesterday);
			map.put("Week", Week);
			map.put("lastWeek", lastWeek);
			
		}
		
		
		
		
//		model.addAttribute("map", map);
//		return "tvstatistics/index";
		String serialize = JSON.serialize(map);
		pw.print(serialize);
		
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
	/**
	 * 上一天
	 * @return
	 * @throws Exception
	 */
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
	private   List<String> getWeekTime() throws Exception{
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
//		System.out.println("本周一："+monday);
//		上周一
		List<String> times = DateUtils.get24Time(monday);
		String[] split = times.get(0).split("#");
		String startTime = split[1];
		
//		上周日
		Long  time = cal.getTime().getTime()+1000*60*60*24*6;
		String sunday = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
//		System.out.println("本周日："+sunday);
		List<String> times2 = DateUtils.get24Time(sunday);
//		System.out.println(times2);
		String[] split1 = times2.get(times.size()-1).split("#");
		String endTime = split1[2];
		
		list.add(startTime);
		list.add(endTime);
		return list;
	}
	
	
	private   List<String> lastWeekTime() throws Exception{
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
//		System.out.println("上周一："+monday);
//		上周一
		List<String> times = DateUtils.get24Time(monday);
		String[] split = times.get(0).split("#");
		String startTime = split[1];
		
//		上周日
		Long  time = cal.getTime().getTime()+1000*60*60*24*6;
		String sunday = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
//		System.out.println("上周日："+sunday);
		List<String> times2 = DateUtils.get24Time(sunday);
//		System.out.println(times2);
		String[] split1 = times2.get(times.size()-1).split("#");
		String endTime = split1[2];
		
		list.add(startTime);
		list.add(endTime);
		return list;
	}

	/********************************周边统计******************************************/
	 /**
	  * 统计周边 
	  * @throws Exception
	  */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getCircumDetails.do")
	public void getCircumDetails(PrintWriter pw) throws Exception{
		List<Integer> todayTimes = DateUtil.getTodayTimes();
		Map<String, Integer> todayData = statService.getData(3, 17, todayTimes.get(0), todayTimes.get(1));
		
		List<Integer> yesterdayTimes = DateUtil.getYesterday();
		Map<String, Integer> yesterdayData = statService.getData(3, 17, yesterdayTimes.get(0), yesterdayTimes.get(1));
		
		List<Integer> weekTimes = DateUtil.getWeek(0);
		Map<String, Integer> weekData = statService.getData(3, 17, weekTimes.get(0), weekTimes.get(1));
		
		List<Integer> lastWeekTimes = DateUtil.getWeek(-1);
		Map<String, Integer> lastWeekData = statService.getData(3, 17, lastWeekTimes.get(0), lastWeekTimes.get(1));
		
		List<Integer> lastMonth = DateUtil.getLastMonth(-1);
		Map<String, Integer> lastMonthData = statService.getData(3, 17, lastMonth.get(0), lastMonth.get(1));
		
		List<Integer> onlineTime = DateUtil.getOnlineTime("2015-06-01");
		Map<String, Integer> onlineTimeData = statService.getData(3, 17, onlineTime.get(0), onlineTime.get(1));
		
		List<Integer> thisMonthTime = DateUtil.getThisMonthTime();
		Map<String, Integer> thisMonthTimeData = statService.getData(3, 17, thisMonthTime.get(0), thisMonthTime.get(1));
		
		Map<String,Map> data = new  HashMap<String, Map>();
		
		data.put("today", todayData);
		data.put("yesterday", yesterdayData);
		data.put("week", weekData);
		data.put("lastWeek", lastWeekData);
		data.put("thisMonthTimeData", thisMonthTimeData);
		data.put("lastMonthData", lastMonthData);
		data.put("onlineTimeData", onlineTimeData);
		
		JSONObject fromObject = JSONObject.fromObject(data);
		pw.print(fromObject);
		
	
	}
	
	public Map<String,Integer> getDate(Map<String, Integer> map){
		Map<String,Integer> data = new  HashMap<String, Integer>();
		Integer num = 0;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			num += entry.getValue();
		}
		data.put("userNum", map.size());
		data.put("clickNum", num);
		
		return data;
	}

	/**********************统计通知
	 * @throws Exception *********************************/
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getNotificationDetails.do")
	public void getNotificationDetails(PrintWriter pw) throws Exception{
		List<Integer> todayTimes = DateUtil.getTodayTimes();
		Map<String, Integer> todayData = statService.getData(3, 0, todayTimes.get(0), todayTimes.get(1));
		
		List<Integer> yesterdayTimes = DateUtil.getYesterday();
		Map<String, Integer> yesterdayData = statService.getData(3, 0, yesterdayTimes.get(0), yesterdayTimes.get(1));
		
		List<Integer> weekTimes = DateUtil.getWeek(0);
		Map<String, Integer> weekData = statService.getData(3, 0, weekTimes.get(0), weekTimes.get(1));
		
		List<Integer> lastWeekTimes = DateUtil.getWeek(-1);
		Map<String, Integer> lastWeekData = statService.getData(3, 0, lastWeekTimes.get(0), lastWeekTimes.get(1));
		
		List<Integer> lastMonth = DateUtil.getLastMonth(-1);
		Map<String, Integer> lastMonthData = statService.getData(3, 0, lastMonth.get(0), lastMonth.get(1));
		
		List<Integer> onlineTime = DateUtil.getOnlineTime("2015-06-01");
		Map<String, Integer> onlineTimeData = statService.getData(3, 0, onlineTime.get(0), onlineTime.get(1));
		
		List<Integer> thisMonthTime = DateUtil.getThisMonthTime();
		Map<String, Integer> thisMonthTimeData = statService.getData(3, 0, thisMonthTime.get(0), thisMonthTime.get(1));
		
		
		Map<String,Map> data = new  HashMap<String, Map>();
		
		data.put("today", todayData);
		data.put("yesterday", yesterdayData);
		data.put("week", weekData);
		data.put("lastWeek", lastWeekData);
		data.put("thisMonthTimeData", thisMonthTimeData);
		data.put("lastMonthData", lastMonthData);
		data.put("onlineTimeData", onlineTimeData);
		
		JSONObject fromObject = JSONObject.fromObject(data);
		pw.print(fromObject);
	}
	
	

	
	
	
	
	/***************统计各小区安装注册情况***********************/
	
	@RequestMapping("/toPartnerHome.do")
	public String toPartnerHome(Model model) throws Exception{
		//Calendar instance = Calendar.getInstance();
		//Date time = instance.getTime();
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//String format = dateFormat.format(time);
		//List<String> get24Time = DateUtils.get24Time(format);
		List<Integer> communityIds = new  ArrayList<Integer>();
		for (Integer communityId : communityIds) {
			StatBean sb = new StatBean();
			List<String> installUsers = userService.getAllInstallUserByCommunity(communityId);
			List<String> registUsers = userService.getAllRegisService(communityId);
			sb.setInstallUsers(installUsers.size());
			sb.setCommunityId(communityId);
			sb.setRegistUsers(registUsers.size());
		}
		
		
		
		model.addAttribute("communityId", 0);
		return "tvstatistics/index1";
		
	}
	
	

	/**************************统计快店销售情况**********************************/
	
	
	@Autowired
	private com.xj.service.StatService statService1;
	
	@RequestMapping("/teamwork/stat.do")
	public String getStat(Integer communityId,String shopType,int modules,Model model,String communityName){
        Map<String,Map<String,String>> map = new  HashMap<String, Map<String,String>>();
 		try {
			List<Integer> yesterday = DateUtil.getYesterday();
			Map<String, String> yesterdaydata = statService1.statBycommunityAndType(communityId,shopType, yesterday.get(0), yesterday.get(1),modules);
			map.put("yesterdaydata", yesterdaydata);
			
			List<Integer> week = DateUtil.getWeek(0);
			Map<String, String> weekdata = statService1.statBycommunityAndType(communityId,shopType, week.get(0), week.get(1),modules);
			map.put("weekdata", weekdata);
			
			List<Integer> lastweek = DateUtil.getWeek(-1);
			Map<String, String> lastweekdata = statService1.statBycommunityAndType(communityId,shopType, lastweek.get(0), lastweek.get(1),modules);
			map.put("lastweekdata", lastweekdata);
			
			List<Integer> month = DateUtil.getLastMonth(0);
			Map<String, String> monthdata = statService1.statBycommunityAndType(communityId,shopType, month.get(0), month.get(1),modules);
			map.put("monthdata", monthdata);
			
			
//			获取分成率
			Integer rate = statService1.getCommissionRate(communityId);
			model.addAttribute("rate", rate);
			model.addAttribute("map",map);
//			request.getSession().setAttribute("community_id", communityId);
			model.addAttribute("communityId",communityId);
		} catch (Exception e) {
		    e.printStackTrace();
			model.addAttribute("communityId",communityId);
		}
		
		return "teamwork/teamwork-statistics2";
	}
	
	
	/**
	 * 获取详情
	 * @throws Exception
	 */
	@RequestMapping("/communities/{communityId}/shop/{shopType}/getDetail.do")
	public String getDetail(@PathVariable("communityId")Integer communityId,@PathVariable("shopType")String shopType,String time,Model model){
	
		try {
			List<Integer> times = null;
			if("yesterday".equals(time)){
				times = DateUtil.getYesterday();
			}else if("lastweek".equals(time)){
				times = DateUtil.getWeek(-1);
			}else if("week".equals(time)){
				times = DateUtil.getWeek(0);
			}else if ("month".equals(time)){
				times = DateUtil.getLastMonth(0);
			}else{
				int paramMonth = Integer.parseInt(time);
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
				String format = dateFormat.format(new Date());
				int thisMonth = Integer.parseInt(format);
				if(paramMonth<=thisMonth){
					times = DateUtil.getLastMonth(paramMonth-thisMonth);
				}else{
					times = DateUtil.getLastMonth(paramMonth-thisMonth-12);
				}
				
				
			}
//			List<Orders> orderList = statService1.getOrderDetailByCondition(communityId,shopType,times.get(0),times.get(1));
			List<OrderEmobId> orderList=	shopsOrderHistoryDao.getOrderEmobId(communityId, shopType, times.get(0), times.get(1));
//			获取分成率
			Integer rate = statService1.getCommissionRate(communityId);
			model.addAttribute("rate", rate);
			model.addAttribute("orderList", orderList);
			model.addAttribute("communityId",communityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "teamwork/teamwork-detail2";
	}

	
	
	@RequestMapping("/teamwork/getMore.do")
	public String getMore(int modules,String shopType,Integer community_id,Model model){
		try {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		String format = dateFormat.format(new Date());
		Map<String,Map<String,String>> map = new  HashMap<String, Map<String,String>>();
		int m = new  Integer(format);
		   List<String> mapList=new ArrayList<String>();
			for (int i = 0; i < 5; i++) {
				
				List<Integer> month = DateUtil.getLastMonth(-i);
				Map<String, String> monthdata = statService1.statBycommunityAndType(community_id,shopType, month.get(0), month.get(1),modules);
				if(StringUtils.isNotBlank(monthdata.get("clickNum")) && Integer.parseInt(monthdata.get("clickNum"))!=0){
					map.put(m-i+"", monthdata);
					mapList.add(m-i+"");
				}
			}	
			model.addAttribute("communityId",community_id);
			model.addAttribute("map", map);
			model.addAttribute("mapList", mapList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "teamwork/teamwork-more2";
	}
	
	public String getIpAddr(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	}   
	
}
