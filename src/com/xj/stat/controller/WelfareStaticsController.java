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
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.click.ClickAndModuleStatistics;
import com.xj.stat.bean.resource.WelfareStatics;
import com.xj.stat.service.ClickService;
import com.xj.stat.service.NearbyService;
import com.xj.stat.service.WelfareStaticsService;
import com.xj.utils.DateUtils;

/**
 * 福利统计controller
 * @author Administrator
 *
 */
@Scope("prototype")
@Path("/welfareStatics")
@Controller
public class WelfareStaticsController {
	
	
	@Autowired
	private WelfareStaticsService welfareStaticsService;
	
	@Autowired
	private ClickService clickService;
	
	private Gson gson = new Gson();
	
	/**
	 *  根据模块id和小区id获取在该模块在该小区的点击量
	 * @param communityId 小区id
	 * @param amount  0 为目前  -1 为前  1 为后
	 * @param type   1 按日统计  2为按周统计  3为按月统计
	 * @param start
	 * @param end
	 * @return
	 */
	@Path("/modules/{moduleId}/communities/{communityId}")
	@GET
	public String getwelfareStatics(@PathParam("moduleId") Integer moduleId,@PathParam("communityId") Integer communityId,@QueryParam("amount") Integer amount,@QueryParam("type") Integer type, @QueryParam("start")Integer start, @QueryParam("end")Integer end) {
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
			statistics.setModule(welfareStaticsService.statWelfare(communityId, startSecond, endSecond,moduleId));
			statistics.setClick(clickService.getDayModuleStatistic(communityId, startSecond, endSecond,moduleId));
		} else if (null != start && null != end) {//点击上一天，下一天
			start = Long.valueOf(DateUtils.getDayBegin(start * 1000L).getTime() / 1000L).intValue();
			end = Long.valueOf(DateUtils.getDayEnd(end * 1000L).getTime() / 1000L).intValue();
			statistics.setModule(welfareStaticsService.statWelfare(communityId, start, end,moduleId));
			statistics.setClick(clickService.getDayModuleStatistic(communityId, start, end,moduleId));
		} else {//首次进入统计页面
			statistics.setModule(welfareStaticsService.statWelfare(communityId,moduleId));
			statistics.setClick(clickService.getStatistic(communityId,moduleId));
		}
		
		resultStatusBean.setInfo(statistics);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	
	/**
	 * 获取所有福利
	 */
	@Path("/communities/{communityId}/welfares")
	@GET
	public String getAllWelfareStatics(@PathParam("communityId")Integer communityId,@QueryParam("pageSize")Integer pageSize,@QueryParam("pageNum")Integer pageNum){
		 ResultStatusBean resultStatusBean = new ResultStatusBean();
		 List<WelfareStatics> welfareStatics = welfareStaticsService.getAllWelfareStatics(communityId,pageSize,pageNum);
		 Page<WelfareStatics> pageBean = new Page<WelfareStatics>(pageNum, 10, pageSize, 10);
		 pageBean.setPageData(welfareStatics);
		 resultStatusBean.setInfo(pageBean);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		
	}
	/**
	 * 获取指定福利订单信息
	 * @param welfareId
	 * @return
	 */
	@Path("/communities/{communityId}/welfare/{welfareId}")
	@GET
	public String getWelfareDetail(@PathParam("welfareId")Integer welfareId,@QueryParam("pageSize")Integer pageSize,@QueryParam("pageNum")Integer pageNum){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Page<WelfareStatics> pageBean =  welfareStaticsService.getWelfareOrdersDetail(welfareId,pageNum,pageSize);
		resultStatusBean.setInfo(pageBean);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 获取指定的福利信息
	 * @param welfareId
	 * @return
	 */
	@Path("/welfare/{welfareId}/welfareInfo")
	@GET
	public String getWelfareInfo(@PathParam("welfareId")Integer welfareId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		WelfareStatics welfareStatics = welfareStaticsService.getWelfareInfo(welfareId);
		resultStatusBean.setInfo(welfareStatics);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	
	/**
	 * 获取福利购买详情
	 * @param welfareId
	 * @return
	 */
	@Path("/communities/{communityId}/welfareBuys")
	@GET
	public String getWelfareBuys(@PathParam("communityId")Integer communityId,@QueryParam("time")String time,@QueryParam("pageSize")Integer pageSize,@QueryParam("pageNum")Integer pageNum){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		List<WelfareStatics> welfareStatics = welfareStaticsService.getwelfareBuys(communityId,time,pageNum,pageSize);
		resultStatusBean.setInfo(welfareStatics);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	
	
	/**
	 * 获取福利模日块点击详情
	 * @param welfareId
	 * @return
	 */
	@Path("/communities/{communityId}/modules/{moduleId}/clickDetailDay")
	@GET
	public String getWelfareClicks(@PathParam("communityId")Integer communityId,@PathParam("moduleId")Integer moduleId,@QueryParam("time")String time,@QueryParam("pageSize")Integer pageSize,@QueryParam("pageNum")Integer pageNum){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		List<WelfareStatics> welfareStatics = welfareStaticsService.getClickDetailDay(communityId,moduleId,time,pageNum,pageSize);
		resultStatusBean.setInfo(welfareStatics);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
}
