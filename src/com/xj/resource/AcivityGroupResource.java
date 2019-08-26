package com.xj.resource;

import java.util.List;


import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import com.xj.bean.Activities;
import com.xj.bean.ActivityMembers;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.ActivitiesDao;
import com.xj.dao.ActivityGroupDao;
import com.xj.httpclient.apidemo.EasemobChatGroups;
import com.xj.service.EmobGroupService;

/**
 * 群成员模块
 * @author Administrator
 *
 */
@Path("/communities/{communityId}/groups/{emobGroupId}/members")
@Component
@Scope("prototype")
public class AcivityGroupResource {
	
	Logger LOGGER = LoggerFactory.getLogger(ActivitiesResource.class);
	
	private Gson gson = new Gson();
	
	@Autowired
	private ActivityGroupDao activityGroupDao;
	
	@Autowired
	private EmobGroupService emobGroupService;
	
	@Autowired
	private ActivitiesDao activitiesDao;
	
	/**
	 * 根据 群id 以及 时间查询 当前时间之后加入群的成员列表
	 * @param emobGroupId
	 * @param createTime
	 * @return
	 */
	@GET
	public String getGroupList(@PathParam("emobGroupId") String emobGroupId ,@QueryParam("q") Integer createTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(activityGroupDao.getGroupList(emobGroupId, createTime));// 查询群成员方法
			resultStatusBean.setCreateTime(activityGroupDao.getMaxCreateTime(emobGroupId));// 最后加入群成员的 加入时间
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("get group list is error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 修改群成员信息
	 * @param json
	 * @param groupId
	 * @return
	 */
	@PUT
	@Path("{groupId}")
	public String updateGroup(String json , @PathParam("groupId") Integer groupId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		ActivityMembers activityGroup = null;
		try {
			activityGroup = gson.fromJson(json, ActivityMembers.class);
			activityGroup.setActivityMemberId(groupId);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("param is error ");
			return gson.toJson(resultStatusBean);
		}
		
		try {
			boolean result = activityGroupDao.updateGroup(activityGroup);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("update group is error ");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 加入群
	 * @param json
	 * @param emobGroupId
	 * @param communityId
	 * @return
	 */
	@POST
	public String addAcitivityGroup(String json , @PathParam("emobGroupId") String emobGroupId , @PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean= new ResultStatusBean();
		resultStatusBean.setStatus("no");
		ActivityMembers activityGroup = null;
		Long unix = System.currentTimeMillis();
		LOGGER.error("addAcitivity ------ start" + unix);
		try {
			activityGroup = gson.fromJson(json, ActivityMembers.class);
			if(emobGroupId != null){
				try {
					String result = emobGroupService.getEmobGroup(emobGroupId);
					if("error".equals(result)){
						resultStatusBean.setStatus("error");
						resultStatusBean.setMessage("加群时发生错误");
						return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
					}else if("no".equals(result)){
						resultStatusBean.setStatus("no");
						resultStatusBean.setMessage("当前群成员已满");
						return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
					}
					ActivityMembers activityGroup2 = activityGroupDao.isEmpty(activityGroup.getEmobUserId(),emobGroupId);
					if(activityGroup2 != null){
						resultStatusBean.setMessage("exist");
						return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			activityGroup.setEmobGroupId(emobGroupId);
			activityGroup.setCreateTime((int)(System.currentTimeMillis() / 1000l));
			activityGroup.setCommunityId(communityId);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("param is error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		try {
			Integer result = activityGroupDao.addActivityGroup(activityGroup);
			LOGGER.error("addAcitivity ------ add Dao " + (unix - System.currentTimeMillis()));
			if(result > 0){
				EasemobChatGroups.addGroup(activityGroup.getEmobUserId(), emobGroupId);
				LOGGER.error("addAcitivity ------ add eamob " + (unix - System.currentTimeMillis()));
				resultStatusBean.setStatus("yes");
				resultStatusBean.setResultId(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("add activityGroup is error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		if(json == null || "".equals(json)){
			resultStatusBean.setMessage("param not is null");
			resultStatusBean.setStatus("no");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 移除群成员
	 * @param emobGroupId
	 * @param emobId
	 * @return
	 */
	@DELETE
	public String deleteActivityGroup(@PathParam("emobGroupId") String emobGroupId , @QueryParam("emobId") String emobId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Activities> list = activitiesDao.getListByEmobId(emobGroupId, emobId);
			if(list != null && list.size() > 0){
				resultStatusBean.setStatus("no");
				resultStatusBean.setMessage("群主不能退出群");
			}else{
				ActivityMembers activityMembers = activityGroupDao.isEmpty(emobId, emobGroupId);
				boolean result = activityGroupDao.deleteGroup(activityMembers.getActivityMemberId());
				if(result){
					EasemobChatGroups.deleteGroup(emobId, emobGroupId);
					resultStatusBean.setStatus("yes");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("delete group is error");
			return gson.toJson(resultStatusBean);
			
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
