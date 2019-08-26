package com.xj.resource;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import com.xj.bean.Communities;
import com.xj.bean.Facilities;
import com.xj.bean.FacilitiesClass;
import com.xj.bean.FacilitiesList;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.CommunitiesDao;
import com.xj.dao.FacilitiesDao;
import com.xj.httpclient.utils.LatitudeUtils;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;



/**
 * Created by maxwell on 14/12/11.
 */

@Component
@Scope("prototype")
@Path("/communities/{communityId}/facilities")
public class FacilitiesResource {
    //logger
    private static Logger logger = Logger.getLogger(FacilitiesResource.class);
    //dao
    @Autowired
    private FacilitiesDao facilitiesDao;
    
    @Autowired
    private CommunitiesDao communitiesDao;
    
    private Gson gson = new Gson();

    /**
     * 查询周边设施
     * @param communityId 小区id
     * @return
     */
    @GET
   
    public String findShopsById(@QueryParam("facilityClassId") String sort , @PathParam("communityId") Integer communityId , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize , @QueryParam("keyword") String facilitiesName) {
        logger.info("community id is :"+communityId);
        Page<Facilities> page = null;
        ResultStatusBean resultStatusBean = new ResultStatusBean();
        resultStatusBean.setStatus("no");
        if(sort != null && !"".equals(sort)){
        	 try {
     			page = facilitiesDao.findFacilitiesByTag(communityId, sort, 2, pageNum, pageSize, 10);
     			resultStatusBean.setInfo(page);
     			resultStatusBean.setStatus("yes");
     		} catch (Exception e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     			
     			resultStatusBean.setMessage("error");
     			resultStatusBean.setStatus("no");
     			return gson.toJson(resultStatusBean);
     		}
     		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
        }
        
        if(facilitiesName != null){
        	Page<FacilitiesList> page2 = null;
    		try {
    			page2 = facilitiesDao.selectFacilitiesName(communityId, 1, pageNum, pageSize, 10, facilitiesName);
    			resultStatusBean.setInfo(page2);
    			resultStatusBean.setStatus("yes");
    			return gson.toJson(resultStatusBean);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			
    			resultStatusBean.setMessage("pageNum is not null or pageSize is not null");
    			resultStatusBean.setStatus("no");
    			return gson.toJson(resultStatusBean);
    		}
        }
        
		try {
			page = facilitiesDao.findFacilitiesByTag(communityId, "", 1, pageNum, pageSize, 10);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			resultStatusBean.setMessage("error");
			resultStatusBean.setStatus("no");
			return gson.toJson(resultStatusBean);
		}
//        String resultJson = new Gson().toJson(page);
//        return resultJson.replace("\"null\"", "\"\"");
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
    }
//
//    @GET
//   
//    public String findShopsBySort(@PathParam("communityId") Integer communityId, @QueryParam("facilityClassId") String sort , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize){
//        logger.info("community id is :"+communityId);
//        logger.info("sort is :"+sort);
//        Page<Facilities> page = null;
//        try {
//			page = facilitiesDao.findFacilitiesByTag(communityId, sort, 2, pageNum, pageSize, 10);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			ResultStatusBean resultStatusBean = new ResultStatusBean();
//			resultStatusBean.setMessage("error");
//			resultStatusBean.setStatus("no");
//			return gson.toJson(resultStatusBean);
//		}
//        String resultJson = new Gson().toJson(page);
//        return resultJson.replace("\"null\"", "\"\"");
//    }
    
    /**
     * 添加周边设施
     */
    @POST
    public String addFacilities(String json ,@PathParam("communityId") Integer communityId){
    	ResultStatusBean reStatusBean = new ResultStatusBean();
    	if(json != null && !"".equals(json)){
//    			System.out.println(json+"-------周边设施信息");
    		try {
    			Facilities facilities = gson.fromJson(json, Facilities.class);
    			if(facilities.getCommunityId() == null){
    				facilities.setCommunityId(communityId);
    			}
    			facilities.setCreateTime((int)(System.currentTimeMillis() / 1000l));
    			Communities communities = communitiesDao.getCommunities(communityId);
    			Float latitude = communities.getLatitude();
    			Float longitude = communities.getLongitude();
    			Double temp = LatitudeUtils.LantitudeLongitudeDist(latitude, longitude, facilities.getLatitude(), facilities.getLongitude());
    			DecimalFormat df = new DecimalFormat("#.00");
				String distanceStr = df.format(temp);
				facilities.setDistance(distanceStr);
				Integer result = facilitiesDao.addFacilities(facilities);
				if(result > 0){
					reStatusBean.setMessage("添加周边设施成功了");
		    		reStatusBean.setStatus("yes");
		    		reStatusBean.setResultId(result);
				}else{
					reStatusBean.setMessage("添加周边设施失败");
		    		reStatusBean.setStatus("no");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				reStatusBean.setMessage("添加周边设施时发生错误啦~~~");
	    		reStatusBean.setStatus("no");
	    		return gson.toJson(reStatusBean);
			}
    	}else{
    		reStatusBean.setMessage("参数不能为空");
    		reStatusBean.setStatus("no");
    	}
    	
    	return gson.toJson(reStatusBean).replace("\"null\"", "\"\"");
    }
    
    /**
     * 修改周边设施
     */
    @PUT
    @Path("{facilityId}")
    public String updateFacilities(@PathParam("facilityId") Long facilityId , String json){
    	ResultStatusBean resultStatusBean = new ResultStatusBean();
    	if(json != null && !"".equals(json)){
    		Facilities facilities = gson.fromJson(json, Facilities.class);
    		facilities.setFacilityId(facilityId);
    		try {
				boolean result = facilitiesDao.updateFacilities(facilities);
				if(result){
					resultStatusBean.setStatus("yes");
					resultStatusBean.setMessage("修改周边设施成功");
				}else{
					resultStatusBean.setStatus("no");
					resultStatusBean.setMessage("修改周边设施失败");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resultStatusBean.setStatus("no");
				resultStatusBean.setMessage("添加周边设施时发生错误了");
			}
    	}else{
    		resultStatusBean.setStatus("no");
    		resultStatusBean.setMessage("参数不能为空");
    	}
    	
    	return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
    }
    /**
     * 根据周边设施分类 获取周边设施
     * @param pageNum
     * @param pageSize
     * @param facilitiesClassId
     * @param communityId
     * @return
     */
    @GET
    @Path("/byFacilitiesClassId/{facilitiesClassId}")
    public String getFacilitiesByClassId(@QueryParam("pageNum") Integer pageNum 
    		, @QueryParam("pageSize") Integer pageSize 
    		, @PathParam("facilitiesClassId") Integer facilitiesClassId , @PathParam("communityId") Integer communityId){
    	Page<Facilities> page = null;
    	ResultStatusBean resultStatusBean = new ResultStatusBean();
    	resultStatusBean.setStatus("no");
    	if(pageNum == null || pageSize == null){
    		
    		resultStatusBean.setStatus("no");
    		resultStatusBean.setMessage("分页参数不能为空");
    		return gson.toJson(resultStatusBean);
    	}
    	try {
			page = facilitiesDao.findeFacilitiesByClassId(communityId, facilitiesClassId, pageNum, pageSize, 10);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
    	return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
    }
    
    /**
     * 统计周边
     */
    @GET
    @Path("/countFacilitesTop")
    public String countFacilitesTop(@PathParam("communityId") Integer communityId){
    	ResultStatusBean resultStatusBean = new ResultStatusBean();
    	try {
			Map<String,Integer> map=facilitiesDao.conntFacilitiesTop(communityId);
		
			resultStatusBean.setInfo(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean);
    }
    
   
    /**
     * 按类别搜索
     */
    @GET
    @Path("/selectFacilitiesType")
    public String selectFacilitiesType(@PathParam("communityId") Integer communityId ,@QueryParam("type") String type , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize, @QueryParam("facilitiesName") String facilitiesName) {
    	logger.info("community id is :"+communityId);
    	
    	ResultStatusBean resultStatusBean = new ResultStatusBean();
    	Page<FacilitiesList> page = null;
    	try {
    		page = facilitiesDao.selectFacilitiesType(communityId, type, pageNum, pageSize, 10, facilitiesName);
    		resultStatusBean.setInfo(page);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    		resultStatusBean.setMessage("error");
    		resultStatusBean.setStatus("no");
    		return gson.toJson(resultStatusBean);
    	}
    	return gson.toJson(resultStatusBean);
    }
    /**
     * 按类别搜索
     */
    @GET
    @Path("/selectFacilits")
    public String selectFacilitiesName(@PathParam("communityId") Integer communityId , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize, @QueryParam("facilitiesName") String facilitiesName) {
    	logger.info("community id is :"+communityId);
    	
    	ResultStatusBean resultStatusBean = new ResultStatusBean();
    	Page<FacilitiesList> page = null;
    	try {
    		page = facilitiesDao.selectFacilitiesName(communityId, 1, pageNum, pageSize, 10, facilitiesName);
    		resultStatusBean.setInfo(page);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    		resultStatusBean.setMessage("error");
    		resultStatusBean.setStatus("no");
    		return gson.toJson(resultStatusBean);
    	}
    	return gson.toJson(resultStatusBean);
    }
    /**
     * 查看 周边的类型
     */
    @GET
    @Path("/getFacilitiesType")
    public String getFacilitiesType(){
    	
    	ResultStatusBean resultStatusBean = new ResultStatusBean();
    	try {
    		List<FacilitiesClass> fc = facilitiesDao.getFacilitiesType();
    		resultStatusBean.setInfo(fc);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    		resultStatusBean.setMessage("error");
    		resultStatusBean.setStatus("no");
    		return gson.toJson(resultStatusBean);
    	}
    	return gson.toJson(resultStatusBean);
    	
    }
    
}
