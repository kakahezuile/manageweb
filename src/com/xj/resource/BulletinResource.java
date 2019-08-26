package com.xj.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.Bulletin;
import com.xj.bean.BulletinUser;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.UserBulletin;
import com.xj.bean.UsersApp;
import com.xj.dao.BullentinDao;
import com.xj.dao.UserBullentinDao;
import com.xj.service.UserService;

@Scope("prototype")
@Component
@Path("/communities/{communityId}/bulletin")
public class BulletinResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private BullentinDao bullentinDao;
	@Autowired
	private UserBullentinDao userBullentinDao;
	
	@Autowired
	private UserService userService;
	
	@POST
	public String getBullentinList(@PathParam("communityId") Integer communityId , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize){
		Page<BulletinUser> page = null;
		try {
			page = bullentinDao.getBullentinListWithPage(communityId, pageNum, pageSize, 10);
		} catch (Exception e) {
			ResultStatusBean resultStatusBean = new ResultStatusBean();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getBullentList IS ERROR");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(page);
	}
	
	@POST
	@Path("/getTimeBullentinList")
	public String getTimeBullentinList(@PathParam("communityId") Integer communityId , @QueryParam("pageNum") Integer pageNum , 
			@QueryParam("pageSize") Integer pageSize,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		  Page<BulletinUser> page = null;
		  Integer startTimeInt = 0;  
		  Integer endTimeInt =0;  
	       try {  
	    	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	           Date date = sdf.parse(startTime);  
	        startTimeInt = (int) (date.getTime()/1000);  
	           
	           
	           SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");  
	           Date date2 = sdf2.parse(endTime);
	           endTimeInt = (int) (date2.getTime()/1000);  
	       }  
	       catch (Exception e) {  
	           e.printStackTrace();  
	       } 
	       
		try {
			page = bullentinDao.getTimeBullentinListWithPage(communityId, pageNum, pageSize, 10,startTimeInt,endTimeInt);
		} catch (Exception e) {
			ResultStatusBean resultStatusBean = new ResultStatusBean();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getBullentList IS ERROR");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(page);
	}
	
	@POST
	@Path("/add")
	public String addBulletin(String json , @PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
//			System.out.println(json + "---------------json");
			Bulletin bulletin = gson.fromJson(json, Bulletin.class);
			bulletin.setCommunityId(communityId);
			bulletin.setCreateTime((int)(System.currentTimeMillis() / 1000));
//			System.out.println(bulletin.getBulletinText());
			Integer resultId = bullentinDao.insert(bulletin);
			resultStatusBean.setResultId(resultId);
			resultStatusBean.setMessage("发布成功了");
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("发布失败了");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	
	@POST
	@Path("/inform")
	public String inform(String json , @PathParam("communityId") Integer communityId,@QueryParam("emobId") List<String> emobId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			Bulletin bulletin = gson.fromJson(json, Bulletin.class);
			bulletin.setCommunityId(communityId);
			bulletin.setCreateTime((int)(System.currentTimeMillis() / 1000));
//			System.out.println(bulletin.getBulletinText());
			Integer resultId = bullentinDao.insert(bulletin);
			
			for (String emob : emobId) {
				UserBulletin userBulletin=new UserBulletin(); 
				userBulletin.setEmobId(emob);
				userBulletin.setBulletinId(resultId);
				
				userBullentinDao.insert(userBulletin);
			}
			resultStatusBean.setResultId(resultId);
			resultStatusBean.setMessage("发布成功了");
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("发布失败了");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@POST
	@Path("/detail")
	public String detail( @PathParam("communityId") Integer communityId,@QueryParam("bulletinId") Integer bulletinId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		
		try {
			BulletinUser bulletinUser=	bullentinDao.getBulletin(bulletinId);
			if(bulletinUser!=null){
			
			 List<UserBulletin> listUser=	userBullentinDao.selectUserBullent(	bulletinUser.getId());
			 List<UsersApp> listUserApp=new ArrayList<UsersApp>();
				for (UserBulletin userBulletin : listUser) {
					UsersApp ua=	userService.getUserByEmobId(userBulletin.getEmobId());
					listUserApp.add(ua);
			    }
				bulletinUser.setListUser(listUserApp);
			};
			
			resultStatusBean.setInfo(bulletinUser);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
