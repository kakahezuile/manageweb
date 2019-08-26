package com.xj.resource.consult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.consult.StatisticsUserDao;

@Component
@Path("/consult/{communityId}/statisticsUser")
@Scope("prototype")
public class StatisticsUserResource {
	private Gson gson = new Gson();
	
	@Autowired
	private StatisticsUserDao statisticsUserDao;
	
	@Path("/getUserRegister")
    @GET
	public String getUserRegister( @PathParam("communityId") Integer communityId, @QueryParam("startTime") Integer startTime , @QueryParam("endTime") Integer endTime ){
		ResultStatusBean result = new ResultStatusBean();
		result.setStatus("yes");
		try {
			statisticsUserDao.selectStatisticsUser(communityId,startTime,endTime);
			result.setStatus("yes");
		//	result.setInfo(ur);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
			return gson.toJson(result);
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
}
