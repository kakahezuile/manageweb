package com.xj.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.Activities;
import com.xj.bean.ActivityUpdateBean;
import com.xj.bean.ExtNode;
import com.xj.bean.PushTask;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Users;
import com.xj.dao.ActivitiesDao;
import com.xj.httpclient.apidemo.EasemobChatGroups;
import com.xj.thread.PushThread;

@Path("/communities/{communityId}/activities/webIm")
@Scope("prototype")
@Component
public class ActivitiesInWebResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private ActivitiesDao activitiesDao;
	
	@GET
	public String getActivities(@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@PathParam("communityId") Integer communityId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(activitiesDao.getListByWebIm(communityId, "", pageNum, pageSize, 10));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setMessage("error");
			resultStatusBean.setStatus("no");
		}
		return gson.toJson(resultStatusBean);
	}
	
	@Path("{activityId}")
	@PUT
	public String updateActivities(@PathParam("activityId") Integer activityId , String status){
		ActivityUpdateBean activityUpdateBean = new ActivityUpdateBean();
		activityUpdateBean.setStatus(status);
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		boolean updateResult = activitiesDao.modifyActivityState(activityId, activityUpdateBean);
		if(updateResult){
			resultStatusBean.setStatus("yes");
		}else{
			resultStatusBean.setStatus("no");
		}
		return gson.toJson(resultStatusBean);
	}
	
	@Path("/deleteActivity/{activityId}")
	@POST
	public String deleteActivities(@PathParam("activityId") Integer activityId , String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Activities activities = gson.fromJson(json, Activities.class);
		ActivityUpdateBean activityUpdateBean = new ActivityUpdateBean();
		activityUpdateBean.setStatus("rejected");
		boolean updateResult = activitiesDao.modifyActivityState(activityId, activityUpdateBean);
		if(updateResult){
			resultStatusBean.setStatus("yes");
			EasemobChatGroups.deleteGroup(activities.getEmobGroupId());
			
			try {
				List<Users> listUser=activitiesDao.getListUser(activities.getEmobGroupId());
				
				String txt="您所在的群：“"+activities.getActivityTitle()+"” 涉及违规，已经被解散，请知悉。";
				ExtNode ext = new ExtNode();
				ext.setContent(txt);
			
				ext.setCMD_CODE(100);
				ext.setTimestamp(System.currentTimeMillis() / 1000l);
				ext.setTitle("通知公告");
				ext.setNickname("帮帮");
				
				try {
					PushTask pushTask = new PushTask();
					pushTask.setScene("删除群组");
					pushTask.setServiceName(activities.getActivityTitle());
					pushTask.setServiceId(activities.getEmobGroupId());
					pushTask.setTimestamp((int)(System.currentTimeMillis() / 1000l));
					ExecutorService executorService = Executors.newSingleThreadExecutor();
					executorService.execute(new PushThread(ext, listUser, txt));
					executorService.shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			resultStatusBean.setStatus("no");
		}
		return gson.toJson(resultStatusBean);
	}
	
	@Path("/{text}")
	@GET
	public String getActivitiesByText(@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize, @PathParam("text") String text ,
			@PathParam("communityId") Integer communityId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(activitiesDao.getListInWebImByText(text, communityId, "", pageNum, pageSize, 10));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean);
	}
	
	@Path("/getActivities")
	@GET
	public String getActivitiesText(@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize, @QueryParam("text") String text ,
			@PathParam("communityId") Integer communityId,@QueryParam("status")String status) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(activitiesDao.getActivitiesText(text, communityId,status, pageNum, pageSize, 10));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean);
	}
	/**
	 * 统计活动使用量头部
	 */
	@GET
	@Path("/getActivitiesTop")
	public String getActivitiesTop(@PathParam("communityId") Integer communityId,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean result = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			result.setInfo(activitiesDao.getActivitiesTop(communityId,startTimeInt,endTimeInt));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	/**
	 * 活动统计 列表
	 */
	@GET
	@Path("/getActivitiesList")
	public String getActivitiesList(@PathParam("communityId") Integer communityId,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime,@QueryParam("sqlTime") List<String> sqlTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			List<Integer> sqlTime2=new ArrayList<Integer>(sqlTime.size());
			for (int i = 0; i < sqlTime.size(); i++) {
				sqlTime2.add((int) (sdf.parse(sqlTime.get(i)).getTime()/1000));
			}
			
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000) ;
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(activitiesDao.getActivitiesList(communityId,startTimeInt,endTimeInt,sqlTime2));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 统计活动 单天
	 * @param communityId
	 * @param pageNum
	 * @param pageSize
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@GET
	@Path("/getActivitiesDateList")
	public String getActivitiesDateList(@PathParam("communityId") Integer communityId,@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean result = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			result.setInfo(activitiesDao.getActivitiesDateList(communityId,pageNum,pageSize,startTimeInt,endTimeInt));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 单个活动明细
	 */
	@GET
	@Path("/getActivitiesDetailList")
	public String getActivitiesDetailList(@QueryParam("activitiesId") Integer activitiesId,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean result = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			result.setInfo(activitiesDao.getActivitiesDetailList(activitiesId,pageNum,pageSize,startTimeInt,endTimeInt));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 活动详情
	 */
	@GET
	@Path("/getActivitiesDetail")
	public String getActivitiesDetail(@QueryParam("activitiesId") Integer activitiesId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(activitiesDao.getActivitiesDetail(activitiesId));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 *  新加的群
	 */
	@GET
	@Path("/getAddActivity")
	public String getAddActivity(@PathParam("communityId") Integer communityId,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean result = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			result.setInfo(activitiesDao.getAddActivity(communityId,startTimeInt,endTimeInt));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	/**
	 * 新加的群明细
	 */
	@GET
	@Path("/newActivitiesListDetail")
	public String newActivitiesListDetail(@PathParam("communityId") Integer communityId,@QueryParam("emobGroupId") String emobGroupId,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean result = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			result.setInfo(activitiesDao.newActivitiesListDetail(emobGroupId,startTimeInt,endTimeInt));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 新加列表
	 */
	@GET
	@Path("/newActivitiesList")
	public String newActivitiesList(@PathParam("communityId") Integer communityId,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean result = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			result.setInfo(activitiesDao.newActivitiesList(communityId,startTimeInt,endTimeInt));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
}