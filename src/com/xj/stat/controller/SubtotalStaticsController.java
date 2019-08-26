package com.xj.stat.controller;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.click.ClickAndModuleStatistics;
import com.xj.stat.bean.resource.PeriodModuleClick;
import com.xj.stat.service.ClickService;
import com.xj.stat.service.NearbyService;
import com.xj.utils.DateUtils;

/**
 * 分类汇总统计
 * @author Administrator
 *
 */
@Scope("prototype")
@Path("/subtotalStatics")
@Controller
public class SubtotalStaticsController {

	private Gson gson = new Gson();
	
	
	@Autowired
	private ClickService clickService;
	/**
	 * 根据小区id获取小区各个模块的点击点击比例及点击次数
	 * @param communityId
	 * @param amount    
	 * @param type	
	 * @param start
	 * @param end
	 * @return
	 */
	@Path("/communities/{communityId}")
	@GET
	public String getSubtotalStatics(@PathParam("communityId") Integer communityId,@QueryParam("amount") Integer amount,@QueryParam("type") Integer type, @QueryParam("start")Integer start, @QueryParam("end")Integer end){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		ClickAndModuleStatistics statistics = new ClickAndModuleStatistics();
		if (null != amount && null != type) {   //今天，本周，本月统计
			Date date = DateUtils.getOffsetDate(amount, type);
			Integer startSecond = null;
			Integer endSecond = null;
			if (NearbyService.STATISTICS_DAY == type.intValue()) {
				startSecond = Long.valueOf(DateUtils.getDayBegin(date).getTime() / 1000L).intValue();
				endSecond = Long.valueOf(DateUtils.getDayEnd(date).getTime() / 1000L).intValue();
			} else if (NearbyService.STATISTICS_WEEK == type.intValue()) {
				startSecond = Long.valueOf(DateUtils.getWeekBegin(date).getTime() / 1000L).intValue();
				endSecond = Long.valueOf(DateUtils.getWeekEnd(date).getTime() / 1000L).intValue();
			} else if (NearbyService.STATISTICS_MONTH == type.intValue()) {
				startSecond = Long.valueOf(DateUtils.getMonthBegin(date).getTime() / 1000L).intValue();
				endSecond = Long.valueOf(DateUtils.getMonthEnd(date).getTime() / 1000L).intValue();
			}
			statistics.setClick(clickService.getDayModuleStatistic(communityId, startSecond, endSecond));
		} else if (null != start && null != end) {//点击上一天，下一天
			start = Long.valueOf(DateUtils.getDayBegin(start * 1000L).getTime() / 1000L).intValue();
			end = Long.valueOf(DateUtils.getDayEnd(end * 1000L).getTime() / 1000L).intValue();
			statistics.setClick(clickService.getDayModuleStatistic(communityId, start, end));
		} else {//首次进入统计页面
			List<PeriodModuleClick> list = clickService.getSubtotalStatics(clickService.getStatistic(communityId));
			statistics.setClick(list);
		}
		
		resultStatusBean.setInfo(statistics);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
