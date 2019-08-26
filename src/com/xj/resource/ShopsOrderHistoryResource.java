package com.xj.resource;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.ShopsOrderHistoryDao;
import com.xj.utils.DateUtils;
import com.xj.utils.HttpUtil;

@Component
@Scope("prototype")
@Path("/communities/{communityId}/users/{emobId}/orderHistories")
public class ShopsOrderHistoryResource {
	
	@Autowired
	private ShopsOrderHistoryDao shopsOrderHistoryDao;
	
	private Gson gson = new Gson();
	
	/**
	 * 获取历史订单记录
	 * @param emobId
	 * @param sort
	 * @param action
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GET
	public String getShopsOrderHistoryList(@PathParam("emobId") String emobId, @QueryParam("q") String sort, @QueryParam("action") String action, @QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(shopsOrderHistoryDao.getShopsOrdersHistoryList(emobId, sort, StringUtils.isBlank(action) ? null : action , pageNum, pageSize, 10));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 使用量 合计明细
	 */
	@GET
	@Path("/getQuickShopList")
	public String getQuickShopList(@PathParam("communityId") Integer communityId,@QueryParam("sort") String sort,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getQuickShopList(communityId, sort, pageNum, pageSize, startTimeInt, endTimeInt));
			resultStatusBean.setStatus("yes");
			
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 使用量头部统计
	 */
	@GET
	@Path("/getQuickShopTop")
	public String getQuickShopTop(@PathParam("communityId") Integer communityId,@QueryParam("sort") String sort,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getQuickShopTop(communityId,sort, startTimeInt, endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 使用量头部统计
	 */
	@GET
	@Path("/getQuickShopTopH")
	public String getQuickShopTopH(@PathParam("communityId") Integer communityId,@QueryParam("sort") String sort,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getQuickShopTopH(communityId, sort, startTimeInt, endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 评论 头部
	 */
	@GET
	@Path("/getQuickShopCommentsTop")
	public String getQuickShopCommentsTop(@PathParam("communityId") Integer communityId,@QueryParam("sort") String sort,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getQuickShopCommentsTop(communityId, sort, startTimeInt, endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 评论与投诉列表
	 */
	@GET
	@Path("/getQuickShopCommentsList")
	public String getQuickShopCommentsList(@PathParam("communityId") Integer communityId,@QueryParam("sort") String sort,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getQuickShopCommentsList(communityId,sort, pageNum, pageSize, startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 营业额 详情页面
	 */
	@GET
	@Path("/getQuickShopOrderDetail")
	public String getQuickShopOrderDetail(@QueryParam("shopId") String shopId,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getQuickShopOrderDetail(shopId, pageNum, pageSize,startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 评价详情页面
	 */
	@GET
	@Path("/getQuickShopCommentsDetail")
	public String getQuickShopCommentsDetail(@QueryParam("shopId") String shopId,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getQuickShopCommentsDetail(shopId, pageNum, pageSize,startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 投诉详情页面
	 */
	@GET
	@Path("/getComplaintsDetailList")
	public String getComplaintsDetailList(@QueryParam("shopId") String shopId,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getComplaintsDetailList(shopId, pageNum, pageSize,startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 使用量 详情页面
	 */
	@GET
	@Path("/getQuickShopDetailList")
	public String getQuickShopDetailList(@QueryParam("shopId") String shopId,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getQuickShopDetailList(shopId, pageNum, pageSize,startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 使用量 时间段分组统计
	 */
	@GET
	@Path("/getUseAmountList")
	public String getUseAmountList(@PathParam("communityId") Integer communityId,	@QueryParam("sort") String sort,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getUseAmountList(communityId,sort, pageNum, pageSize,startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 营业额 时间段 分组统计
	 */
	@GET
	@Path("/getTurnoverList")
	public String getTurnoverList(@PathParam("communityId") Integer communityId,
			@QueryParam("sort") String sort,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getTurnoverList(communityId, sort, pageNum, pageSize, startTimeInt, endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 服务时间 统计
	 */
	@GET
	@Path("/getTimeQuantumList")
	public String getTimeQuantumList(@PathParam("communityId") Integer communityId,@QueryParam("sort") String sort,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getTimeQuantumList(communityId,sort, pageNum, pageSize,startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 服务时间头部 
	 */
	@GET
	@Path("/getTimeQuantumTop")
	public String getTimeQuantumTop(@PathParam("communityId") Integer communityId,@QueryParam("sort") String sort,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getTimeQuantumTop(communityId,sort,startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 结款
	 */
	@GET
	@Path("/getPayOff")
	public String getPayOff(@PathParam("communityId") Integer communityId,@QueryParam("sort") String sort,@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime,@QueryParam("myTime") String myTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getPayOff2(communityId,sort,pageNum,pageSize,startTimeInt,endTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 扣款申请记录
	 */
	@GET
	@Path("/getDeductMoney")
	public String getDeductMoney(@QueryParam("emobId") String emobId,@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getDeductMoney(emobId,pageNum,pageSize,startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 交易记录
	 */
	@GET
	@Path("/getTradingRecord")
	public String getTradingRecord(@QueryParam("shopId") String shopId,@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize,@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getTradingRecord(shopId,pageNum,pageSize,startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 交易人员明细
	 */
	@GET
	@Path("/getUserShop")
	public String getUserShop(@PathParam("communityId") String communityId,@QueryParam("sort") String sort,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			Date time = new SimpleDateFormat("yyyy-MM-dd 00:00:00").parse(startTime);
			Integer start = (int)(DateUtils.getDayBegin(time).getTime() / 1000L);
			Integer end = (int)(DateUtils.getDayEnd(time).getTime() / 1000L);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getUserShop(communityId,sort,start,end));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	/**
	 * 交易人员明细
	 */
	@GET
	@Path("/getUserShopWU")
	public String getUserShopWU(@PathParam("communityId") String communityId,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Integer startTimeInt = (int) (sdf.parse(startTime).getTime()/1000);
			Integer endTimeInt = (int) (sdf.parse(endTime).getTime()/1000);
			
			resultStatusBean.setInfo(shopsOrderHistoryDao.getUserShopWU(communityId,startTimeInt,endTimeInt));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	/**
	 * 访问量统计
	 */
	@GET
	@Path("/getClickAmount")
	public String getClickAmount(@QueryParam("url") String url){
		return HttpUtil.httpUrl(url);
	}
	
	/**
	 * 购买排行
	 */
	@GET
	@Path("/getBuyRanking")
	public String getBuyRanking(@PathParam("communityId") Integer communityId,@QueryParam("type") String type){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(shopsOrderHistoryDao.getBuyRanking(communityId,type));
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/getShopDelie")
	public String getShopDelie(@PathParam("communityId") Integer communityId,@QueryParam("sort") String sort)throws Exception {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(shopsOrderHistoryDao.getShopDelie(communityId,sort));
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}