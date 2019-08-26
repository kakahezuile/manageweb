package com.xj.stat.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.stat.service.AppDownloadStatisticsService;
import com.xj.utils.DateUtils;

/**
 * APP下载统计
 * @author 王利东
 */
@Component
@Scope("prototype")
@Path("/appDownloadStatistics")
public class AppDownloadStatisticsController {
	
	@Autowired
	private AppDownloadStatisticsService appDownloadStatisticsService;
	
	@GET
	@Path("/day")
	public String getAdminList(@QueryParam("communityName") String communityName, @QueryParam("startDay") String startDay, @QueryParam("endDay") String endDay) {
		Integer startSecond = null;
		Integer endSecond = null;
		if (StringUtils.isBlank(startDay)) {
			startSecond = (int)(DateUtils.getDayBegin().getTime() / 1000L);
		} else {
			startSecond = (int)(DateUtils.parseDate(startDay).getTime() / 1000L);
		}
		if (StringUtils.isBlank(endDay)) {
			endSecond = (int)(System.currentTimeMillis() / 1000L);
		} else {
			endSecond = (int)(DateUtils.parseDate(endDay).getTime() / 1000L);
		}
		
		return new Gson().toJson(appDownloadStatisticsService.day(communityName, startSecond, endSecond)).replace("\"null\"", "\"\"");
	}
}