package com.xj.stat.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Users;
import com.xj.service.UserService;
import com.xj.stat.service.ClickService;
import com.xj.utils.DateUtils;

/**
 * 流失用户统计
 * @author 王利东
 */
@Scope("prototype")
@Path("/runoffUserStatistics/communities/{communityId}")
@Controller
public class RunoffUserStatisticsController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClickService clickService;
	
	private Gson gson = new Gson();
	
	@GET
	public String runoffUser(@PathParam("communityId") Integer communityId, @QueryParam("year") Integer year, @QueryParam("month") Integer month, @QueryParam("comparisonYear") Integer comparisonYear, @QueryParam("comparisonMonth") Integer comparisonMonth) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		if (null == communityId || null == year || null == month) {
			resultStatusBean.setStatus("no");
			resultStatusBean.setMessage("请选择小区，年份，月份!");
			return gson.toJson(resultStatusBean);
		}
		
		String targetMonth = year + "-" + (month.intValue() < 10 ? ("0" + month) : String.valueOf(month)) + "-" + "01";
		Date targetMonthStart = DateUtils.parseDate(targetMonth);
		Date targetMonthEnd = DateUtils.getMonthEnd(targetMonth);

		Date comparisonMonthBegin = null;
		Date comparisonMonthEnd = null;
		if (null == comparisonYear || null == comparisonMonth) {
			int _nextMonth = month == 12 ? 1 : month + 1;
			String nextMonth = (year + (month == 12 ? 1 : 0)) + "-" + (_nextMonth < 10 ? ("0" + _nextMonth) : String.valueOf(_nextMonth)) + "-" + "01";
			
			comparisonMonthBegin = DateUtils.getMonthBegin(nextMonth);
			comparisonMonthEnd = DateUtils.getMonthEnd(nextMonth);
		} else {
			String _comparisonMonth = comparisonYear + "-" + (comparisonMonth < 10 ? ("0" + comparisonMonth) : String.valueOf(comparisonMonth)) + "-" + "01";
			
			comparisonMonthBegin = DateUtils.getMonthBegin(_comparisonMonth);
			comparisonMonthEnd = DateUtils.getMonthEnd(_comparisonMonth);
		}
		
		try {
			List<Users> users = userService.getSetupUsersWithoutTest(communityId, (int)(targetMonthStart.getTime() / 1000L), (int)(targetMonthEnd.getTime() / 1000L));
			Map<String, Integer> userClickMap = clickService.getClickUser(communityId, (int)(comparisonMonthBegin.getTime() / 1000L), (int)(comparisonMonthEnd.getTime() / 1000L), users);//TODO clickUsers应该是users的子集
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("users", users);
			result.put("userClickMap", userClickMap);
			resultStatusBean.setInfo(result);
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("no");
			resultStatusBean.setMessage(e.getMessage());
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}