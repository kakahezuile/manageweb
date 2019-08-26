package com.xj.resource;

import java.text.SimpleDateFormat;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.Admin;
import com.xj.bean.AdvisoryHistory;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.UsersApp;
import com.xj.dao.AdminDao;
import com.xj.dao.AdvisoryHistoryDao;
import com.xj.service.UserService;

@Component
@Path("/communities/{communityId}/advisoryHistory")
@Scope("prototype")
public class AdvisoryHistoryResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private AdvisoryHistoryDao advisoryHistoryDao;
	
	@Autowired
	private UserService userService;
	 
	@Autowired
	private AdminDao adminDao;
	
	@POST
	public String addAdvisoryHistory(String json , @PathParam("communityId") Integer communityId){ // 添加咨询记录
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			AdvisoryHistory advisoryHistory = gson.fromJson(json, AdvisoryHistory.class);
			advisoryHistory.setCommunityId(communityId);
			Integer time = (int)(System.currentTimeMillis() / 1000l);
			UsersApp app = userService.getUserByEmobId(advisoryHistory.getEmobIdUser());
			advisoryHistory.setUserName(app.getNickname());
			advisoryHistory.setPhone(app.getPhone());
			Admin admin = adminDao.getAdminByEmobId(advisoryHistory.getEmobIdAgent());
			advisoryHistory.setAgentName(admin.getNickname());
			advisoryHistory.setCreateTime(time);
			advisoryHistory.setUpdateTime(time);
			advisoryHistory.setAdvisoryTime(time);
			Integer resultId = advisoryHistoryDao.addAdvisoryHistory(advisoryHistory);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setResultId(resultId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	public String getAdvisoryHistory(@QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize 
			, @QueryParam("startTime") String startTime , @QueryParam("endTime") String endTime
			, @QueryParam("name") String name , @PathParam("communityId") Integer communityId){ // 查询咨询记录
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Integer start = null;
			Integer end = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(startTime != null && endTime != null && !"".equals(startTime) && !"".equals(endTime)){
				if(startTime.length() == 10){
					startTime += " 00:00:01";
				}
				if(endTime.length() == 10){
					endTime += " 23:59:59";
				}
				start = (int)(format.parse(startTime).getTime() / 1000l);
				end = (int)(format.parse(endTime).getTime() / 1000l);
			}
			Page<AdvisoryHistory> page = advisoryHistoryDao.getAdvisoryHistoryList(communityId , pageNum, pageSize, 10, start, end, name);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
}
