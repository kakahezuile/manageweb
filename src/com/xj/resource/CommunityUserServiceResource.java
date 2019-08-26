package com.xj.resource;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import com.xj.bean.CommunityUserService;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Services;
import com.xj.dao.CommunityUserServiceDao;
import com.xj.dao.ServiceCategoryDao;

@Path("/communities/{communityId}/communityUserService")
@Component
@Scope("prototype")
public class CommunityUserServiceResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private ServiceCategoryDao serviceCategoryDao;
	
	@Autowired
	private CommunityUserServiceDao communityUserServiceDao;
	
	@GET
	@Path("isStartService/{emobId}")
	public String getCommunityListIsStart(@PathParam("emobId") String emobId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Services> list = serviceCategoryDao.getIsHaveCategoryList(emobId);
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
	
	@Path("isStopService/{emobId}")
	@GET
	public String getAllServiceCategory(@PathParam("emobId") String emobId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<Services> listStart = serviceCategoryDao.getIsHaveCategoryList(emobId);
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
	@Path("{emobId}")
	@POST
	public String addUserRelation(@PathParam("emobId") String emobId , String json){ // 为当前用户添加服务
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		
		try {
			CommunityUserService communityUserService = gson.fromJson(json, CommunityUserService.class);
			communityUserService.setEmobId(emobId);
			communityUserService.setStatus("normal");
			boolean result = false;
			CommunityUserService communityUserService2 = communityUserServiceDao.getCommunityUserService(emobId, communityUserService.getServiceId());
			if(communityUserService2 != null && communityUserService2.getUserServiceId() > 0){ // 修改
				communityUserService.setUserServiceId(communityUserService2.getUserServiceId());
				result = communityUserServiceDao.updateCommunityUserService(communityUserService);
			}else{ // 添加
				Integer resultId = communityUserServiceDao.addCommunityUserService(communityUserService);
				if(resultId > 0){
					result = true;
				}
			}
			if(result){
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
	@Path("{emobId}")
	public String updateCommunityUserService(@PathParam("emobId") String emobId , String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			CommunityUserService communityUserService = gson.fromJson(json, CommunityUserService.class);
			
			CommunityUserService communityUserService2 = communityUserServiceDao.getCommunityUserService(emobId, communityUserService.getServiceId());
			communityUserService.setUserServiceId(communityUserService2.getUserServiceId());
			boolean result = communityUserServiceDao.updateCommunityUserService(communityUserService);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
}
