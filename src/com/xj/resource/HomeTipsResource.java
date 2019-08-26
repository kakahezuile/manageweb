package com.xj.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.HomeTips;
import com.xj.bean.ResultStatusBean;
import com.xj.service.HomeTipsService;

@Path("/communities/{communityId}/home")
@Component
@Scope("prototype")
public class HomeTipsResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private HomeTipsService homeTipsService;
	/**
	 * 首页 最新内容更新 数字提示
	 * @param communityId
	 * @param emobId
	 * @param time
	 * @return
	 */
	@Path("{emobId}/tips")
	@GET
	public String getHomeTips(@PathParam("communityId") Integer communityId , @PathParam("emobId") String emobId , @QueryParam("time") Integer time){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			resultStatusBean.setStatus("yes");
			HomeTips homeTips = homeTipsService.getTips(communityId, emobId, time);
			resultStatusBean.setInfo(homeTips);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getHomeTips error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
