package com.xj.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.FacilitiesClass;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.FacilitiesClassDao;

@Component
@Scope("prototype")
@Path("/communities/{communityId}/facilityClasses")
public class FacilitiesClassResource {
	
	private static Logger logger = Logger.getLogger(FacilitiesClassResource.class);
	
	@Autowired
	private FacilitiesClassDao facilitiesClassDao;
	
	private Gson gson = new Gson();
	/**
	 * 根据分类id获取周边设施列表
	 * @param facilityClassId
	 * @return
	 */
	@Path("{facilityClassId}")
	@GET
	public String getFacilitiesClass(@PathParam("facilityClassId") Integer facilityClassId){
		logger.info("getFacilitiesClass");
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			FacilitiesClass facilitiesClass = facilitiesClassDao.getFacilitiesClass(facilityClassId);
			if(facilitiesClass != null){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(facilitiesClass);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 根据小区id获取周边设置分类
	 * @param communityId
	 * @return
	 */
	@GET
	public String getList(@PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<FacilitiesClass> list = facilitiesClassDao.getListFacilitiesClassWithCommunityId(communityId);
			if(list != null){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(list);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 修改周边设施分类
	 * @param facilityClassId
	 * @param json
	 * @return
	 */
	@Path("{facilityClassId}")
	@PUT
	public String update(@PathParam("facilityClassId") Integer facilityClassId , String json){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		FacilitiesClass facilitiesClass = null;
		try {
			facilitiesClass = gson.fromJson(json, FacilitiesClass.class);
			facilitiesClass.setFacilitiesClassId(facilityClassId);
		} catch (Exception e) {
			// TODO: handle exception
			resultStatusBean.setMessage("json param error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		try {
			boolean result = facilitiesClassDao.updateFacilities(facilitiesClass);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setMessage("update error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
	
	/**
	 * 添加周边设施分类
	 * @param json
	 * @param communityId
	 * @return
	 */
	@POST
	public String addFacilitiesClass(String json , @PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		FacilitiesClass facilitiesClass = null;
		try {
			facilitiesClass = gson.fromJson(json, FacilitiesClass.class);
			facilitiesClass.setCommunityId(communityId);
			if(facilitiesClass.getWeight() == null){
				facilitiesClass.setWeight(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			resultStatusBean.setMessage("json param error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		try {
			Integer resultId = facilitiesClassDao.insert(facilitiesClass);
			if(resultId != null){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setResultId(resultId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setMessage("add error");
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
}
