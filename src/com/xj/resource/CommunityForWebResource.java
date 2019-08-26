package com.xj.resource;


import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.Communities;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Services;
import com.xj.dao.CommunitiesDao;
import com.xj.dao.ServiceCategoryDao;

@Component
@Path("/communities/webIm/communityService")
@Scope("prototype")
public class CommunityForWebResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private CommunitiesDao communitiesDao;
	
	@Autowired
	private ServiceCategoryDao serviceCategoryDao;
	
	@GET
	public String getCommunityList(){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Communities> list = communitiesDao.getCommunityList();
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setMessage("get list is error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
	
	@Path("isStartService/{communityId}")
	@GET
	public String getCommunityListIsStart(@PathParam("communityId") Integer communityId , @QueryParam("appVersionId") Integer appVersionId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Services> list = serviceCategoryDao.getIsHaveCategoryList(communityId,appVersionId);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setMessage("get list is error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
	
	@Path("isStopService/{communityId}")
	@GET
	public String getAllServiceCategory(@PathParam("communityId") Integer communityId , @QueryParam("appVersionId") Integer appVersionId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Services> listStart = serviceCategoryDao.getIsHaveCategoryList(communityId,appVersionId);
			List<Services> listAll = serviceCategoryDao.getCategoryList();

			Iterator<Services> it = listAll.iterator();
			Services serviceCategory = null;
			Services serviceCategoryTemp = null;
			Iterator<Services> itStart = null;

			
			while(it.hasNext()){
				serviceCategory = it.next();
				itStart = listStart.iterator();
				while(itStart.hasNext()){
					serviceCategoryTemp = itStart.next();
					if(serviceCategoryTemp.getServiceId() == serviceCategory.getServiceId()){
						listAll.remove(serviceCategory);
						it = listAll.iterator();
						break;
					}
				}
			}
			

			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(listAll);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setMessage("get list is error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
	
	
	@Path("/upCommunity")
	@GET
	public String upCommunity(){
		
		return "";
		
	}
	
}
