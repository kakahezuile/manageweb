package com.xj.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.service.StatService;
import com.xj.utils.DateUtil;

@Component
@Scope("prototype")
@Path("/communities/{communityId}/shop/{shopType}/stat")
public class StatSource {

	@Autowired
	private StatService statService;
	
	@GET 
	public String getStat(@PathParam("communityId")Integer communityId,@PathParam("shopType")String shopType,@QueryParam("modules")int modules){
		 Gson gson = new Gson();
        ResultStatusBean resultStatusBean = new ResultStatusBean();
        resultStatusBean.setStatus("no");
        Map<String,Map<String,String>> map = new  HashMap<String, Map<String,String>>();
 		try {
			List<Integer> yesterday = DateUtil.getYesterday();
			Map<String, String> yesterdaydata = statService.statBycommunityAndType(communityId,shopType, yesterday.get(0), yesterday.get(1),modules);
			map.put("yesterdaydata", yesterdaydata);
			
			List<Integer> week = DateUtil.getWeek(0);
			Map<String, String> weekdata = statService.statBycommunityAndType(communityId,shopType, week.get(0), week.get(1),modules);
			map.put("weekdata", weekdata);
			
			List<Integer> lastweek = DateUtil.getWeek(-1);
			Map<String, String> lastweekdata = statService.statBycommunityAndType(communityId,shopType, lastweek.get(0), lastweek.get(1),modules);
			map.put("lastweekdata", lastweekdata);
			
			List<Integer> month = DateUtil.getLastMonth(0);
			Map<String, String> monthdata = statService.statBycommunityAndType(communityId,shopType, month.get(0), month.get(1),modules);
			map.put("monthdata", monthdata);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(map);
		} catch (Exception e) {
			resultStatusBean.setStatus("error");
			e.printStackTrace();
		}	
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
