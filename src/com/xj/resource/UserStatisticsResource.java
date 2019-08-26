package com.xj.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.UserClike;
import com.xj.service.UserService;
import com.xj.utils.HttpUtil;

@Component
@Scope("prototype")
@Path("/communities/{communityId}/userStatistics")
public class UserStatisticsResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取小区用户
	 */
	@Path("/getListReg")
	@GET
	public String getListReg(@PathParam("communityId") Integer communityId) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(userService.getListReg(communityId));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 获取小区用户	
	 */
	@Path("/getTimeList")
	@GET
	public String getTimeList(@PathParam("communityId") Integer communityId) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(userService.getListUsers(communityId,1434297600,1435507200));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 获取小区用户	
	 */
	@Path("/getShopList")
	@GET
	public String getShopList(@PathParam("communityId") Integer communityId){
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(userService.getListUser(communityId,"shop"));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 获取小区自己人
	 */
	@Path("/getTryOutCommunity")
	@GET
	public String getTryOutCommunity(@PathParam("communityId") Integer communityId){
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(userService.getListTryOutUser(communityId));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 获取自己人
	 */
	@Path("/getTryOut")
	@GET
	public String getTryOut(){
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(userService.getListTryOut());
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 获取小区用户
	 */
	@Path("/getUserList")
	@GET
	public String getUserList(@PathParam("communityId") Integer communityId){
		ResultStatusBean result = new ResultStatusBean();
		try {
			UserClike uc = new UserClike();
			uc.setListUsers(userService.getListUser(communityId,"owner"));
			uc.setListTryOut(userService.getListTryOut());
			uc.setRegisterList(userService.registerList(communityId));
			result.setInfo(uc);
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 统计
	 */
	@Path("/getUserRegister")
	@GET
	public String getUserRegister( @PathParam("communityId") Integer communityId){
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(userService.getUserRegister(communityId));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 统计
	 */
	@Path("/getUserRegisterCommunity/{emobId}")
	@GET
	public String getUserRegisterCommunity( @PathParam("emobId") String emobId){
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(userService.getUserRegisterCommunity(emobId));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 统计
	 */
	@Path("/getUserRegisterEndTime")
	@GET
	public String getUserRegisterEndTime( @PathParam("communityId") Integer communityId, @QueryParam("endTime") String endTime) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			Integer endTimeInt = (int) (new SimpleDateFormat("yyyy-MM-dd 00:00:00").parse(endTime).getTime() / 1000);
			result.setInfo(userService.getUserRegisterTime(communityId, 0, endTimeInt));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 统计
	 */
	@Path("/getUserRegisterTime")
	@GET
	public String getUserRegisterTime( @PathParam("communityId") Integer communityId,@QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime() / 1000L);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime() / 1000L);
			
			result.setInfo(userService.getUserRegisterTime(communityId, startTimeInt, endTimeInt));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 统计
	 */
	@Path("/getUserRegisterTimeGroup")
	@GET
	public String getUserRegisterTimeGroup( @PathParam("communityId") Integer communityId,@QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime,@QueryParam("endList") List<String> endList){
		ResultStatusBean result = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			
			int size = endList.size();
			List<Integer> endList2 = new ArrayList<Integer>(size);
			for (int i = 0; i < size; i++) {
				endList2.add((int) (sdf.parse(endList.get(i)).getTime() / 1000));
			}
			
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime() / 1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime() / 1000);
			
			result.setInfo(userService.getUserRegisterTimeGroup(communityId, startTimeInt, endTimeInt, endList2));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 统计
	 */
	@Path("/getUserRegisterTimeList")
	@GET
	public String getUserRegisterTimeList( @PathParam("communityId") Integer communityId,@QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime) {
		ResultStatusBean result = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			result.setInfo(userService.getUserRegisterTimeList(communityId, startTimeInt, endTimeInt));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * http 跨域统计 访问量
	 */
	@Path("/getVisitor")
	@GET
	public String getVisitor( @PathParam("communityId") Integer communityId,@QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Long startTimeInt = (sdf.parse(startTime).getTime() / 1000L);
			Long endTimeInt = (sdf.parse(endTime).getTime() / 1000L);
			
			return HttpUtil.httpUrl("http://115.28.73.37:9090/api/V1/communities/"+communityId+"/users/statistics/"+startTimeInt+"/"+endTimeInt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * http 跨域统计 访问量
	 */
	@Path("/getLaunches")
	@GET
	public String getLaunches( @PathParam("communityId") Integer communityId,@QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Long startTimeInt = (sdf.parse(startTime).getTime()/1000);
			Long endTimeInt = (sdf.parse(endTime).getTime()/1000);
			
			return HttpUtil.httpUrl("http://115.28.73.37:9090/api/V1/communities/"+communityId+"/launches/statistics/"+startTimeInt+"/"+endTimeInt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * http 跨域统计 访问量
	 */
	@Path("/getLaunchesStatistics")
	@GET
	public String getLaunchesStatistics( @PathParam("communityId") Integer communityId,@QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Long startTimeInt = (sdf.parse(startTime).getTime()/1000);
			Long endTimeInt = (sdf.parse(endTime).getTime()/1000)-1;
			
			return HttpUtil.httpUrl("http://115.28.73.37:9090/api/V1/communities/"+communityId+"/active_user_count/statistics/"+startTimeInt+"/"+endTimeInt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * http 跨域统计 访问量
	 */
	@Path("/getLaunchesDetailsStatistics")
	@GET
	public String getLaunchesDetailsStatistics( @PathParam("communityId") Integer communityId,@QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Long startTimeInt = (sdf.parse(startTime).getTime()/1000);
			Long endTimeInt = (sdf.parse(endTime).getTime()/1000)-1;
			
			return HttpUtil.httpUrl("http://115.28.73.37:9090/api/V1/communities/"+communityId+"/launch_details_daily/statistics/"+startTimeInt+"/"+endTimeInt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * 人品值统计
	 */
	@Path("/getMoralStatistics")
	@GET
	public String getMoralStatistics( @PathParam("communityId") Integer communityId){
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(userService.getMoralStatistics(communityId));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 人品排行
	 */
	@Path("/getMoralList")
	@GET
	public String getMoralList( @PathParam("communityId") Integer communityId){
		ResultStatusBean result = new ResultStatusBean();
		try {
			result.setInfo(userService.getMoralList(communityId));
			result.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus("error");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 当前时间
	 */
	@Path("/getDateTime")
	@GET
	public String getDateTime(){
		ResultStatusBean result = new ResultStatusBean();
		result.setInfo(new Date().getTime());
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
}