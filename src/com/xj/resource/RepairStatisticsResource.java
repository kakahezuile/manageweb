package com.xj.resource;

import java.text.SimpleDateFormat;
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
import com.xj.bean.CommentsLists;
import com.xj.bean.ComplaintsDetail;
import com.xj.bean.ComplaintsList;
import com.xj.bean.Page;
import com.xj.bean.RepairStatisticsCenter;
import com.xj.bean.RepairStatisticsComplaints;
import com.xj.bean.RepairStatisticsHistory;
import com.xj.bean.RepairStatisticsHistoryCenter;
import com.xj.bean.RepairStatisticsTop;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Subpackage;
import com.xj.dao.RepairStatisticsDao;

@Path("/communities/{communityId}/repairStatistics")
@Scope("prototype")
@Component
public class RepairStatisticsResource {

	private Gson gson = new Gson();

	@Autowired
	private RepairStatisticsDao repairStatisticsDao;

	/**
	 * 物业服务 头部数据
	 */

	@GET
	@Path("/top")
	public String getRepairStatisticsTop(
			@QueryParam("startTime") String startTime,
			@QueryParam("sort") String sort,
			@QueryParam("endTime") String endTime,@PathParam("communityId") Integer communityId ) { // 维修首页 top数据获取
		  Integer startTimeInt = 0;  
		  Integer endTimeInt = 0;  
	       try {  
	    	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
	           Date date = sdf.parse(startTime);  
	           startTimeInt = (int) (date.getTime()/1000);  
	           
	           SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
	           Date date2 = sdf2.parse(endTime); 
	           endTimeInt = (int) (date2.getTime()/1000);  
	       }  
	       catch (Exception e) {  
	           e.printStackTrace();  
	       }  

		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			RepairStatisticsTop repairStatisticsTop = repairStatisticsDao
					.getRepairStatisticsTop(communityId,startTimeInt, endTimeInt,sort); // 头部数据对象
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(repairStatisticsTop);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");

			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
//	@GET
//	@Path("/top")
//	public String getRepairStatisticsTop(
//			@QueryParam("startTime") Integer startTime,
//			@QueryParam("endTime") Integer endTime) { // 维修首页 top数据获取
//		ResultStatusBean resultStatusBean = new ResultStatusBean();
//		resultStatusBean.setStatus("no");
//		try {
//			RepairStatisticsTop repairStatisticsTop = repairStatisticsDao
//			.getRepairStatisticsTop(startTime, endTime); // 头部数据对象
//			resultStatusBean.setStatus("yes");
//			resultStatusBean.setInfo(repairStatisticsTop);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			resultStatusBean.setStatus("error");
//			return gson.toJson(resultStatusBean);
//		}
//		return gson.toJson(resultStatusBean);
//	}

	
	
	/**
	 *  获取师傅列表
	 */
	
	@GET
	@Path("/center")
	public String getRepairStatisticsCenter(@PathParam("communityId") Integer communityId ,@QueryParam("sort") String sort) { // 获取师傅列表
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<RepairStatisticsCenter> list = repairStatisticsDao
					.getReaiStatisticsCenterList(communityId,sort);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
	/**
	 *  历史维修列表
	 */
	
	@GET
	@Path("/getHistoryMaintainList")
	public String getHistoryMaintainList(@PathParam("communityId") Integer communityId ,@QueryParam("sort") String sort,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime, @QueryParam("pageNum") Integer pageNum) { 
		
		  Integer startTimeInt = 0;  
		  Integer endTimeInt = 0;  
	       try {  
	    	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
	           Date date = sdf.parse(startTime);  
	            startTimeInt = (int) (date.getTime()/1000);  
	           
	           
	           SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
	           Date date2 = sdf2.parse(endTime); 
	           endTimeInt = (int) (date2.getTime()/1000);  
	       }  
	       catch (Exception e) {  
	           e.printStackTrace();  
	       } 
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
//			Page<RepairStatisticsHistory> page = repairStatisticsDao
//			.getHistoryMaintainList(startTime,endTime,shopId,pageNum,pageSize,nvm);
			List<RepairStatisticsHistory> page = repairStatisticsDao
			.getHistoryMaintainList2(communityId,startTimeInt,endTimeInt,pageNum,20,sort);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
	/**
	 *  历史维修列表详情
	 */
	
	@GET
	@Path("/getHistoryMaintainListDetail")
	public String getHistoryMaintainListDetail(@PathParam("communityId") Integer communityId ,@QueryParam("shopId")Integer shopId,@QueryParam("sort") String sort,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime, @QueryParam("pageNum") Integer pageNum) { 
		
		Integer startTimeInt = 0;  
		Integer endTimeInt = 0;  
		try {  
			   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
	           Date date = sdf.parse(startTime);  
	           startTimeInt = (int) (date.getTime()/1000);  
	           
	           SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
	           Date date2 = sdf2.parse(endTime); 
	           endTimeInt = (int) (date2.getTime()/1000);  
		}  
		catch (Exception e) {  
			e.printStackTrace();  
		} 
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
//			Page<RepairStatisticsHistory> page = repairStatisticsDao
//			.getHistoryMaintainList(startTime,endTime,shopId,pageNum,pageSize,nvm);
			Page<RepairStatisticsHistoryCenter> page = repairStatisticsDao
			.getHistoryMaintainListDetail(communityId,startTimeInt,endTimeInt,pageNum,20,shopId,sort);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}

	@GET
	@Path("/historyList")
	public String getHistoryList(@PathParam("communityId") Integer communityId ,@QueryParam("startTime") Integer startTime,
			@QueryParam("endTime") Integer endTime) { // 获取维修历史列表
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<RepairStatisticsHistory> list = repairStatisticsDao
					.getHistoryList(communityId,startTime, endTime);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
	
	/**
	 * 获取师傅的头部
	 */
	@GET
	@Path("/getComplaints")
	public String getComplaints(@PathParam("communityId") Integer communityId ,@QueryParam("sort") String sort,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime,@QueryParam("shopId")Integer shopId){
		
		  Integer startTimeInt = 0;  
		  Integer endTimeInt = 0;  
	       try {  
	           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
	           Date date = sdf.parse(startTime);  
	           startTimeInt = (int) (date.getTime()/1000);  
	           
	           SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
	           Date date2 = sdf2.parse(endTime); 
	           endTimeInt = (int) (date2.getTime()/1000);  
	       }  
	       catch (Exception e) {  
	           e.printStackTrace();  
	       }  

		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			RepairStatisticsComplaints rc=	 repairStatisticsDao
					.getComplaints(communityId,startTimeInt, endTimeInt,shopId,sort);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(rc);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean);
	}
	
	/**
	 * 获取师傅投诉
	 */
	@GET
	@Path("/getComplaintsList")
	public String getComplaintsList(@PathParam("communityId") Integer communityId ,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime,@QueryParam("pageNum") Integer pageNum){
		
		Integer startTimeInt = 0;  
		Integer endTimeInt = 0;  
		try {  
			   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
	           Date date = sdf.parse(startTime);  
	           startTimeInt = (int) (date.getTime()/1000);  
	           
	           SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
	           Date date2 = sdf2.parse(endTime); 
	           endTimeInt = (int) (date2.getTime()/1000);  
		}  
		catch (Exception e) {  
			e.printStackTrace();  
		} 
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
//			Page<RepairStatisticsHistory> page = repairStatisticsDao
//			.getHistoryMaintainList(startTime,endTime,shopId,pageNum,pageSize,nvm);
			Page<ComplaintsList> page = repairStatisticsDao
			.getComplaintsList(communityId,startTimeInt,endTimeInt,pageNum);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		
		return gson.toJson(resultStatusBean);
	}
	/**
	 * 获取师傅投诉
	 */
	@GET
	@Path("/getCommentsList")
	public String getCommentsList(@PathParam("communityId") Integer communityId ,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime,@QueryParam("pageNum") Integer pageNum){
		
		Integer startTimeInt = 0;  
		Integer endTimeInt = 0;  
		try {  
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
			Date date = sdf.parse(startTime);  
			startTimeInt = (int) (date.getTime()/1000);  
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
			Date date2 = sdf2.parse(endTime); 
			endTimeInt = (int) (date2.getTime()/1000);  
		}  
		catch (Exception e) {  
			e.printStackTrace();  
		} 
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
//			Page<RepairStatisticsHistory> page = repairStatisticsDao
//			.getHistoryMaintainList(startTime,endTime,shopId,pageNum,pageSize,nvm);
			Page<CommentsLists> page = repairStatisticsDao
			.getCommentsList(communityId,startTimeInt,endTimeInt,pageNum);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		
		return gson.toJson(resultStatusBean);
	}
	/**
	 * 获取师傅投诉明细
	 */
	@GET
	@Path("/getCommentsListDetail")
	public String getCommentsListDetail(@PathParam("communityId") Integer communityId ,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime,@QueryParam("pageNum") Integer pageNum,@QueryParam("shopId") Integer shopId){
		
		Integer startTimeInt = 0;  
		Integer endTimeInt = 0;  
		try {  
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
			Date date = sdf.parse(startTime);  
			startTimeInt = (int) (date.getTime()/1000);  
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
			Date date2 = sdf2.parse(endTime); 
			endTimeInt = (int) (date2.getTime()/1000);  
		}  
		catch (Exception e) {  
			e.printStackTrace();  
		} 
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
//			Page<RepairStatisticsHistory> page = repairStatisticsDao
//			.getHistoryMaintainList(startTime,endTime,shopId,pageNum,pageSize,nvm);
			Page<ComplaintsDetail> page = repairStatisticsDao
			.getCommentsListDetail(communityId,startTimeInt,endTimeInt,pageNum,shopId);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		
		return gson.toJson(resultStatusBean);
	}
	
	/**
	 * 查看分包次数
	 */
	@GET
	@Path("/getSubpackage")
	public String getSubpackage(@PathParam("communityId") Integer communityId ,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime,@QueryParam("pageNum") Integer pageNum){
		
		Integer startTimeInt = 0;  
		Integer endTimeInt = 0;  
		try {  
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
			Date date = sdf.parse(startTime);  
			startTimeInt = (int) (date.getTime()/1000);  
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
			Date date2 = sdf2.parse(endTime); 
			endTimeInt = (int) (date2.getTime()/1000);  
		}  
		catch (Exception e) {  
			e.printStackTrace();  
		} 
		
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
//			Page<RepairStatisticsHistory> page = repairStatisticsDao
//			.getHistoryMaintainList(startTime,endTime,shopId,pageNum,pageSize,nvm);
			Page<Subpackage> page = repairStatisticsDao
			.getSubpackage(communityId,startTimeInt,endTimeInt,pageNum);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
		
		return gson.toJson(resultStatusBean);
	}
	
}
