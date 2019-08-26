package com.xj.resource;

import java.util.Iterator;
import java.util.List;

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
import com.google.gson.reflect.TypeToken;
import com.xj.bean.Communities;
import com.xj.bean.CommunityService;
import com.xj.bean.CommunityRelationAndName;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.CommunityRelationDao;

@Path("/communities/{communityId}/services")
@Scope("prototype")
@Component
public class CommunityRelationInWebResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private CommunityRelationDao communityRelationDao;

	@GET
	@Path("/all")
	public String getCommunityRelationList(@PathParam("communityId") Integer communityId ){  // 获取当前小区内的所有服务
		List<CommunityService> list = null;
		try {
			list = communityRelationDao.getRelationList(communityId);
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gson.toJson(list);
	}
	
	/**
	 * 根据app版本以及小区id 获取小区模块
	 * @param communityId
	 * @param appVersionId
	 * @param time - 
	 * @return
	 */
	@GET
	
	public String getRelationNameList(@PathParam("communityId") Integer communityId , @QueryParam("appVersionId") Integer appVersionId , @QueryParam("time") Integer time){
		List<CommunityRelationAndName> list = null;
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		
		try {
			if(appVersionId != null && !"".equals(appVersionId)){
				if(time != null && time != 0){
					list = communityRelationDao.getCommunityRelationAndNameList(communityId,appVersionId);
				}else{
					list = communityRelationDao.getCommunityRelationAndNameList(communityId,appVersionId);
				}
				
			}else{
				list = communityRelationDao.getCommunityRelationAndNameList(communityId);
			}
			int retime = communityRelationDao.getMaxTime(communityId);
			resultStatusBean.setCreateTime(retime); // 传最新变动时间   -- app通过变动时间 是否更改做 刷新操作
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
			
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@POST
	public String addCommunityRelation(@PathParam("communityId") Integer communityId , String json){ // 为当前小区添加服务
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		
		try {
			List<CommunityService> list = gson.fromJson(json, new TypeToken<List<CommunityService>>(){}.getType());
			Iterator<CommunityService> it = list.iterator();
			CommunityService communityRelation = null;
			while(it.hasNext()){
				communityRelation = it.next();
				communityRelation.setCommunityId(communityId);
				CommunityService communityRelation2 = communityRelationDao.isEmpty(communityId, communityRelation.getServiceId() , communityRelation.getAppVersionId());
				if(communityRelation2 != null){
					communityRelation.setRelationStatus("normal");
					communityRelation.setUpdateTime((int)(System.currentTimeMillis() / 1000l));
					communityRelationDao.updateRelationByCommunityIdAndCategoryId(communityId, communityRelation.getServiceId() , communityRelation.getAppVersionId());
				}else{
					communityRelation.setRelationStatus("normal");
					int unixTime = (int)(System.currentTimeMillis() / 1000l);
					communityRelation.setCreateTime(unixTime);
					communityRelation.setUpdateTime(unixTime);
					communityRelationDao.addRelation(communityRelation);
				}
				
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO: handle exception
			resultStatusBean.setMessage("param is error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		
		
		return gson.toJson(resultStatusBean);
	}
	
	@PUT
	@Path("{communityRelationId}")
	public String updateAddcommunityRelation(@PathParam("communityRelationId") Integer communityRelationId , String json){ // 修改小区服务关系
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		CommunityService communityRelation = null;
		resultStatusBean.setStatus("no");
		try {
			communityRelation = gson.fromJson(json, CommunityService.class);
			communityRelation.setCommunityServiceId(communityRelationId);
			communityRelation.setUpdateTime((int)(System.currentTimeMillis() / 1000l));
			boolean result = communityRelationDao.updateRelation(communityRelation);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setMessage("param is error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
	@GET
	@Path("/getCommunity")
	public String getCommunity(@PathParam("communityId") Integer communityId ){ 
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			
			Communities c=	communityRelationDao.getCommunity(communityId);
			resultStatusBean.setInfo(c);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setMessage("param is error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
}
