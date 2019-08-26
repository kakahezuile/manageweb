package com.xj.stat.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.stat.service.CrazySalesStatisticsService;

@Component
@Path("/crazySalesStatistics")
@Scope("prototype")
public class CrazySalesStatisticsController {

	private Gson gson = new Gson();
	
	@Autowired
	private CrazySalesStatisticsService crazySalesStatisticsService;
	
	/**
	 * 抢购总览
	 * @return
	 */
	@GET
	@Path("/summary")
	public String summary() {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo(crazySalesStatisticsService.summary());
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 抢购日览
	 * @param begin
	 * @param end
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/daily")
	public String daily(@QueryParam("begin") Integer begin, @QueryParam("end") Integer end) throws Exception {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo(crazySalesStatisticsService.daily(begin, end));
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 商家的活动情况
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/shop")
	public String shop(@QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize) throws Exception {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo(crazySalesStatisticsService.shop(pageNum, pageSize));
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 商家的活动详情
	 * @param emobId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/shopDetails")
	public String shopDetails(@QueryParam("emobId") String emobId) throws Exception {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo(crazySalesStatisticsService.shopDetails(emobId));
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 小区的活动情况
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/community")
	public String community(@QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize) throws Exception {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo(crazySalesStatisticsService.community(pageNum, pageSize));
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 小区活动详情
	 * @param communityId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/communityDetails")
	public String communityDetails(@QueryParam("communityId") Integer communityId) throws Exception {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setInfo(crazySalesStatisticsService.communityDetails(communityId));
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}