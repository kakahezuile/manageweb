package com.xj.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ExtNode;
import com.xj.bean.LifeCircle;
import com.xj.bean.LifeCircleBean;
import com.xj.bean.LifeCircleDetail;
import com.xj.bean.LifeCircleHotTop;
import com.xj.bean.LifeCircleTips;
import com.xj.bean.LifeCircleVO;
import com.xj.bean.LifePraise;
import com.xj.bean.LifePraiseContent;
import com.xj.bean.Orders;
import com.xj.bean.Page;
import com.xj.bean.PushTask;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopType;
import com.xj.bean.Shops;
import com.xj.bean.SinglePraise;
import com.xj.bean.Users;
import com.xj.bean.UsersApp;
import com.xj.dao.LifePraiseDao;
import com.xj.dao.MyUserDao;
import com.xj.dao.OrdersDao;
import com.xj.dao.ShopTypeDao;
import com.xj.dao.ShopsDao;
import com.xj.httpclient.utils.MyDateUtiles;
import com.xj.service.CirclePraiseService;
import com.xj.service.LifeCircleService;
import com.xj.service.OrderDetailService;
import com.xj.thread.PushThread;

@Path("/communities/{communityId}/circles")
@Component
@Scope("prototype")
public class CircleResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private LifeCircleService lifeCircleService;
	
	@Autowired
	private CirclePraiseService circlePraiseService;
	
	@Autowired
	private LifePraiseDao lifePraiseDao;
	
	@Autowired
	private MyUserDao myUserDao;
	
	@Autowired
	private  OrderDetailService orderDetailService;
	
	@Autowired
	private ShopsDao shopsDao;
	
	@Autowired
	private  OrdersDao ordersDao;
	
	@Autowired
	private ShopTypeDao shopTypeDao;
	
	@SuppressWarnings({ "unchecked"})
	private Object getCircles(Object object) throws Exception{
		
		
		if(object instanceof List){
			List<LifeCircle> list = (List<LifeCircle>) object;
			list = (List<LifeCircle>)getList(list);
			return list;
		}else if(object instanceof Page){
			Page<LifeCircle> page = (Page<LifeCircle>) object;
			List<LifeCircle> list = page.getPageData();
			list = (List<LifeCircle>)getList(list);
			page.setPageData(list);
			return page;
		}else if(object instanceof LifeCircle){
			LifeCircle lifeCircle = (LifeCircle)getList(object);
			return lifeCircle;
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	private Object getList(Object object) throws Exception{
		List<ShopType> shopTypes = shopTypeDao.getTypeList();
		Map<Integer, String> map = new HashMap<Integer, String>();
		if(shopTypes != null){
			Iterator<ShopType> iterator = shopTypes.iterator();
			ShopType shopType = null;
			while(iterator.hasNext()){
				shopType = iterator.next();
				map.put(shopType.getShopTypeId(), shopType.getShopTypeName());
			}
		}
		LifeCircle lifeCircle = null;
		if(object instanceof List){
			List<LifeCircle> list = (List<LifeCircle>) object;
			if(list != null && list.size() > 0){
				int len = list.size();
				int type = 0;
				for(int i = 0 ; i < len ; i++){
					lifeCircle = list.get(i);
					type = lifeCircle.getType();
					if(type == 2){
						list.get(i).setTypeContent("分享了"+map.get(type)+"购物");
						list.get(i).setExtContent("我买了这些商品，你也试试吧！");
					}
					
				}
			}
			return list;
		}else if(object instanceof LifeCircle){
			lifeCircle = (LifeCircle) object;
			int type = lifeCircle.getType();
			lifeCircle.setTypeContent(map.get(type));
			return lifeCircle;
		}
		
		return null;
	}
	@GET
	@Path("{emobId}/tips") // tips - 提示
	public String getLifeCircleTop(@PathParam("communityId") Integer communityId , @PathParam("emobId") String emobId , @QueryParam("time") Integer time ){ // 生活圈头部最新评论列表
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			LifeCircleTips lifeCircleTips = lifeCircleService.getTips(communityId , emobId, time);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(lifeCircleTips);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getLifeCircleTop error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("{emobId}/hot")
	public String getHot(@PathParam("emobId") String emobId , @PathParam("communityId") Integer communityId){ // 人品值排行榜
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			int start = MyDateUtiles.getWeekStartTime(new Date(), "last", 1);
	    	Date d = new Date();
	    	d.setTime(start * 1000l);
	    	int end = (int)(MyDateUtiles.getWeekEndTime(d) / 1000l);
	    	LifeCircleHotTop lifeCircleHotTop = lifeCircleService.getHotTop(start, end, emobId , communityId);
	    	resultStatusBean.setStatus("yes");
	    	resultStatusBean.setInfo(lifeCircleHotTop);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getHot error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("{emobId}/numbers")
	public String getLifeCircleNumber(@QueryParam("time") Integer time , @PathParam("communityId") Integer communityId , @PathParam("emobId") String emobId){ // 首页生活圈 数字
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Integer num = lifeCircleService.getLifeCircleNum(communityId, emobId, time);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(num);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getLifeCircleNumber error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@SuppressWarnings("unchecked")
	@GET
	public String getListCircle(@PathParam("communityId") Integer communityId , @QueryParam("pageNum") Integer pageNum 
			, @QueryParam("pageSize") Integer pageSize , @QueryParam("q") String emobId,@DefaultValue("1.0.0")  @QueryParam("appVersionId") String appVersionID){ // 获取生活圈列表
		if(StringUtils.isNotBlank(appVersionID)){
			appVersionID = appVersionID.replaceAll("\\.", "");
		}
//		appVersionID = appVersionID.substring(1, appVersionID.length()-1);
		Integer appVersion = new  Integer(appVersionID);
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Page<LifeCircle> page = lifeCircleService.getLifeCircles(communityId, emobId, pageNum, pageSize,appVersion);
			page = (Page<LifeCircle>)getCircles(page);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getListCircle error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@DELETE
	@Path("{lifeCircleId}")
	public String deleteCircle(@PathParam("lifeCircleId") Integer lifeCircleId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			boolean result = lifeCircleService.deleteLifeCircle(lifeCircleId);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("deleteCircle error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("{emobId}/single")
	public String getLifeCircleSelf(@PathParam("communityId") Integer communityId , @PathParam("emobId") String emobId , @QueryParam("q") Integer value 
			, @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize){ 
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		if(value != null){ // 获取圈 单条详细内容
			try {
				LifeCircle lifeCircle = lifeCircleService.getLifeCircle(communityId ,value);
				
				if(lifeCircle != null){
					resultStatusBean.setStatus("yes");
					resultStatusBean.setInfo(getCircles(lifeCircle));
				}else{
					resultStatusBean.setMessage("当前生活圈已被删除");
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				resultStatusBean.setStatus("error");
				resultStatusBean.setMessage("getLifeCircle error");
				return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
			}
		}else{ // 根据emobId获取生活圈内容列表
			try {
				if(pageNum != null && pageSize != null){
					if(pageNum == 1){
						LifeCircleBean lifeCircleBean = lifeCircleService.getCircleSelf(communityId , emobId, pageNum, pageSize);
						resultStatusBean.setInfo(lifeCircleBean);
					}else if(pageNum > 1){
						Page<LifeCircleVO> list = lifeCircleService.getLifeCircleVo(emobId, pageNum, pageSize);
						resultStatusBean.setInfo(list);
					}
					
					resultStatusBean.setStatus("yes");
				}
			} catch (Exception e) {
				e.printStackTrace();
				resultStatusBean.setStatus("error");
				resultStatusBean.setMessage("getLifeCircleSelf error");
				return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
			}
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@POST
	public String addLifeCircle(String json , @PathParam("communityId") Integer communityId){ // 发布生活圈
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			int time = (int)(System.currentTimeMillis() / 1000l);
			LifeCircle lifeCircle = gson.fromJson(json, LifeCircle.class);
			lifeCircle.setCommunityId(communityId);
			lifeCircle.setCreateTime(time);
			lifeCircle.setUpdateTime(time);
			Integer resultId = lifeCircleService.addLifeCircleService(lifeCircle);
			if(resultId > 0){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setResultId(resultId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("add Life error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@POST
	@Path("{emobId}")
	public String addLifeCircleContent(String json , @PathParam("emobId") String emobId , @PathParam("communityId") Integer communityId){ // 评论
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			LifeCircleDetail lifeCircleDetail = gson.fromJson(json, LifeCircleDetail.class);
			int time = (int)(System.currentTimeMillis() / 1000l);
			lifeCircleDetail.setCreateTime(time);
			lifeCircleDetail.setUpdateTime(time);
			lifeCircleDetail.setPraiseSum(0);
			lifeCircleDetail.setEmobIdFrom(emobId);
			int resultId = lifeCircleService.addLifeCircleDetail(lifeCircleDetail);
			if(resultId > 0){
				List<Users> users = new ArrayList<Users>();
				Users user = new Users();
				UsersApp usersApp = myUserDao.getUserByEmobId(lifeCircleDetail.getEmobIdTo());
				user.setEmobId(lifeCircleDetail.getEmobIdTo());
				user.setEquipment(usersApp.getEquipment());
//				users.add(lifeCircleDetail.getEmobIdTo());
				users.add(user);
				resultStatusBean.setStatus("yes");
				resultStatusBean.setResultId(resultId);
				ExtNode ext = new ExtNode();
				usersApp = myUserDao.getUserByEmobId(emobId);
	        	ext.setCMD_CODE(121);
	        	ext.setTitle("新评论");
	        	String content = lifeCircleDetail.getDetailContent();
	        	if(content.length() > 14){
	        		content = content.substring(0,14);
	        	}
	        	if(usersApp != null){
	        		ext.setContent("评论了你的生活圈");
	        	}
	        	ext.setCommunityId(communityId);
	        	ext.setNickname(usersApp.getNickname());
	        	ExecutorService executorService = Executors.newSingleThreadExecutor();
	        	PushTask pushTask = new PushTask();
				pushTask.setTimestamp((int)(System.currentTimeMillis() / 1000l));
			
				pushTask.setScene("新评论");
				pushTask.setServiceName("circleAddDetailContent");
////				pushTaskDao.addPushTask(pushTask);
	        	executorService.execute(new PushThread(ext, users, content));
	        	executorService.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("addLifeCircleContent error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@POST
	@Path("{emobId}/praise")
	public String addLifeCirclePraise( @PathParam("emobId") String emobId , String json , @PathParam("communityId") Integer communityId){ //  status == 1  为发布者点赞  status == 2 为评论者点赞
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			LifePraise lifePraise = gson.fromJson(json, LifePraise.class);
			lifePraise.setEmobIdFrom(emobId);
			lifePraise.setCreateTime((int)(System.currentTimeMillis() / 1000l));
			boolean result = circlePraiseService.addCirclePraise(lifePraise, lifePraise.getStatus());
			if(result){
				resultStatusBean.setStatus("yes");
				List<Users> users = new ArrayList<Users>();
				Users user = new Users();
				user.setEmobId(lifePraise.getEmobIdTo());

	        	UsersApp userApp = myUserDao.getUserByEmobId(lifePraise.getEmobIdTo());
	        	user.setEquipment(userApp.getEquipment());
//				users.add(lifeCircleDetail.getEmobIdTo());
				users.add(user);
			
				ExtNode ext = new ExtNode();
	        	ext.setCMD_CODE(122);
	        	ext.setTitle("新的赞");
	        	userApp = myUserDao.getUserByEmobId(emobId);
	        	ext.setContent("赞了你的人品");
	        	ext.setNickname(userApp.getNickname());
	        	ext.setCommunityId(communityId);
	        	ExecutorService executorService = Executors.newSingleThreadExecutor();
	        	PushTask pushTask = new PushTask();
				pushTask.setTimestamp((int)(System.currentTimeMillis() / 1000l));
			
				pushTask.setScene("新的赞");
				pushTask.setServiceName("circleAddDetailContent");
////				pushTaskDao.addPushTask(pushTask);
	        	executorService.execute(new PushThread(ext, users, ext.getContent()));
	        	executorService.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("addLifeCircleContent error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("{emobId}/praises")
	public String getLifeCirclePraises(@PathParam("communityId") Integer communityId , @PathParam("emobId") String emobId , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize){ // 根据emobId获取点赞列表
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			if(pageNum != null){
				if(pageNum == 1){
					SinglePraise singlePraise = circlePraiseService.getSinglePraise(communityId , emobId, pageNum, pageSize, 10);
					resultStatusBean.setInfo(singlePraise);
				
				}else if(pageNum > 1){
					Page<LifePraiseContent> page = lifePraiseDao.getLifepraises(emobId, pageNum, pageSize, 10);
					resultStatusBean.setInfo(page);
				}
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getLifeCirclePraises error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	

	
	
	
	
	
	/**
	 * 2015-7-15新增
	 * http://120.24.232.121:8082/BonusAPI/api/v1/{serial}/share
	 * 订单分享到生活圈
	 * @return  订单的图片及其他信息
	 * @throws Exception 
	 */
	@GET
	@Path("{serial}/share")
	public String shareLifeCircle(String json,@PathParam("communityId") Integer communityId,@PathParam("serial") String serial){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			List<String> shareLifeCircle = orderDetailService.shareLifeCircle(communityId,serial);
			Orders orders = ordersDao.getOrderBySerail(serial);
			if(orders!=null){
				resultStatusBean.setEmobId(orders.getEmobIdShop());
				resultStatusBean.setInfo(shareLifeCircle);
				resultStatusBean.setStatus("yes");
			}else{
				resultStatusBean.setStatus("no");
				resultStatusBean.setMessage("订单信息有误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("getCircleBlackList error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	
	/**
	 * 2015-07-15新增
	 * http://120.24.232.121:8082/BonusAPI/api/v1/{emobId}/{serial}/share
	 * 分享到生活圈
	 * @return  分享是否成功及其他信息
	 * @throws Exception 
	 */
	@POST
	@Path("{emobId}/{serial}/share")
	public String addLifeCircleWithType(String json , @PathParam("communityId") Integer communityId,@PathParam("emobId") String emobId,@PathParam("serial") String serial){ // 发布生活圈
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			int time = (int)(System.currentTimeMillis() / 1000l);
			LifeCircle lifeCircle = gson.fromJson(json, LifeCircle.class);
			lifeCircle.setEmobId(emobId);
			lifeCircle.setCommunityId(communityId);
			lifeCircle.setCreateTime(time);
			lifeCircle.setUpdateTime(time);
			
			Shops shops = shopsDao.getShopsByEmobId(lifeCircle.getEmobIdShop());
			lifeCircle.setType(new Integer(shops.getSort()));
			
			Orders orders = ordersDao.getOrderBySerail(serial);
			orders.setShare("no");
			
			ordersDao.updateOrders(orders);
			boolean lifeCircleShare = lifeCircleService.lifeCircleShare(serial,lifeCircle);
			if(lifeCircleShare == true){
				resultStatusBean.setStatus("yes");
			}else{
				resultStatusBean.setStatus("no");
				resultStatusBean.setMessage("没有上传图片或图片保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("add Life error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
