package com.xj.stat.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.stat.service.ClickService;

@Component
@Path("/moduleClickStatistics")
@Scope("prototype")
public class ClickController {

	private Gson gson = new Gson();
	
	@Autowired
	private ClickService clickService;
	
	@GET
	@Path("/day")
	public String getDayModuleStatistic(@QueryParam("communityId") Integer communityId, @QueryParam("startSecond") Integer startSecond, @QueryParam("endSecond") Integer endSecond) {
		return gson.toJson(clickService.getDayModuleStatistic(communityId, startSecond, endSecond)).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/week")
	public String getWeekModuleStatistic(@QueryParam("communityId") Integer communityId, @QueryParam("time") Integer time) {
		return gson.toJson(clickService.getWeekModuleStatistic(communityId, time)).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/month")
	public String getMonthModuleStatistic(@QueryParam("communityId") Integer communityId, @QueryParam("time") Integer time) {
		return gson.toJson(clickService.getMonthModuleStatistic(communityId, time)).replace("\"null\"", "\"\"");
	}
}