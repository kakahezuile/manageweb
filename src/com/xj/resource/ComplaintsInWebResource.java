package com.xj.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.xj.bean.ComplaintTop;
import com.xj.bean.ComplaintsUserMsg;
import com.xj.bean.Messages;
import com.xj.bean.Orders;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopUserMsg;
 
import com.xj.dao.OrderComplainsDao;
import com.xj.dao.OrdersDao;
import com.xj.httpclient.apidemo.EasemobChatMessage;
import com.xj.httpclient.utils.ListSort;
import com.xj.mongo.MessageMongo;


@Component
@Path("/communities/{communityId}/complaints/webIm")
@Scope("prototype")
public class ComplaintsInWebResource {
	
	@Autowired
	private OrderComplainsDao orderComplainsDao;
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private MessageMongo messageMongo;

	
	private Gson gson = new Gson();
	
	@GET
	@Path("/getEmobIdList")
	public String getEmobIdList(@PathParam("communityId") Integer communityId ,@QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize , @QueryParam("q") String sort){ // 获取投诉列表
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Page<ComplaintsUserMsg> page = null;
			if(sort == null){
				page = orderComplainsDao.getComplaintsUserMsg(pageNum, pageSize, 10);
			}else{
				page = orderComplainsDao.getComplaintsUserMsg(communityId,sort,pageNum, pageSize, 10);
			}
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setMessage("getList is error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/{emobIdShop}")
	public String getEmobToShopEmob( @PathParam("emobIdShop") String emobIdShop){ // 获取被投诉者的所有信息
		ShopUserMsg shopUserMsg = null;
		try {
			shopUserMsg = orderComplainsDao.getShopUserMsg(emobIdShop);
			Integer sum = ordersDao.getCountByEmobIdShop(emobIdShop);
			shopUserMsg.setSumOrder(sum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ResultStatusBean resultStatusBean = new ResultStatusBean();
			resultStatusBean.setStatus("no");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(shopUserMsg).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/history/{emobIdUser}/{emobIdShop}") // 查看交易双方聊天记录
	public String getUserMessageHistory(@PathParam("emobIdUser") String emobIdUser 
			, @PathParam("emobIdShop") String emobIdShop , @QueryParam("occurTime") Integer occurTime){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		Date newDate = new Date();  // 当前系统时间
		Date complaintDate = new Date(occurTime*1000l); // 投诉时间
		try {
			if(isYeaterday(complaintDate, newDate) == -1){
				
				ObjectNode objectNode = EasemobChatMessage.getChatMessages(null , null , emobIdUser , emobIdShop); // 获取用户对商家说的话
				ObjectNode objectNode2 = EasemobChatMessage.getChatMessages(null , null , emobIdShop , emobIdUser); // 获取商家对用户说的话
				List<Messages> listFrom = EasemobChatMessage.getUserMessageBean(objectNode);
				List<Messages> listTo = EasemobChatMessage.getUserMessageBean(objectNode2);
				List<Messages> list = new ArrayList<Messages>();
				list.addAll(listFrom);
				list.addAll(listTo);
				ListSort<Messages> listSort = new ListSort<Messages>();
				listSort.Sort(list, "getTimestamp", ""); // 根据发送时间将聊天信息进行排序
				
				return gson.toJson(list).replace("\"null\"", "\"\"");
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			List<Messages> listFrom = messageMongo.getListMessage(emobIdShop, emobIdUser, null , null);
			List<Messages> listTo = messageMongo.getListMessage(emobIdUser, emobIdShop, null , null );
			List<Messages> list = new ArrayList<Messages>();
			list.addAll(listFrom);
			list.addAll(listTo);
			ListSort<Messages> listSort = new ListSort<Messages>();
			listSort.Sort(list, "getTimestamp", ""); // 根据发送时间将聊天信息进行排序
			return gson.toJson(list).replace("\"null\"", "\"\"");
//		/	page = messagesDao.getListAsTimeWithPage(emobIdUser, emobIdShop, startTime, endTime, pageNum, pageSize, 10); // 获取当前服务中双方聊天记录
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			resultStatusBean.setStatus("no");
			resultStatusBean.setMessage("getListAsTimeWithPage error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		
	}
	
	@GET
	@Path("/history")
	public String getHistory(@QueryParam("emobIdUser") String emobIdUser , @QueryParam("emobIdShop") String emobIdShop 
			, @QueryParam("occurTime") Integer occurTime , @QueryParam("status") String status){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		List<Messages> listResult = new ArrayList<Messages>();
		boolean type = false;
		try {
			if("this".equals(status)){
				type = false;
			}else if("next".equals(status)){
				type = true;
			}else if("last".equals(status)){
				type = false;
			}
			List<Messages> listFrom = messageMongo.getListMessage(emobIdUser, emobIdShop, new Long(occurTime * 1000l),type);
			List<Messages> listTo = messageMongo.getListMessage(emobIdShop, emobIdUser, new Long(occurTime * 1000l),type);
			listResult.addAll(listFrom);
			listResult.addAll(listTo);
			ListSort<Messages> listSort = new ListSort<Messages>();
			listSort.Sort(listResult, "getTimestamp", ""); // 根据发送时间将聊天信息进行排序
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(listResult);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	
	@GET
	@Path("/history/{orderId}/{emobIdUser}/{emobIdShop}") // 查看交易实录
	public String getHitory(@PathParam("orderId") Integer orderId , @PathParam("emobIdUser") String emobIdUser 
			, @PathParam("emobIdShop") String emobIdShop , @QueryParam("occurTime") Integer occurTime){
		Integer startTime = 0;
		Integer endTime = 0;
	
		Integer sum = 0;
		try {
			sum = orderComplainsDao.getUserToShopCount(emobIdUser, emobIdShop); // 获取当前投诉双方有几次交易记录
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			ResultStatusBean resultStatusBean = new ResultStatusBean();
			resultStatusBean.setStatus("no");
			resultStatusBean.setMessage("getUserToShopCount error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		try {
			Orders orders = ordersDao.getOrderById(orderId);
			startTime = orders.getStartTime();
			endTime = orders.getEndTime();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(sum > 1){
			try {
				Orders orders = orderComplainsDao.getThisOrderLastOrder(orderId, emobIdUser, emobIdShop); // 获取当前订单的上一个订单内容  为获取上一个订单结束时间
				if(orders != null){
					startTime = orders.getEndTime();
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ResultStatusBean resultStatusBean = new ResultStatusBean();
				resultStatusBean.setStatus("no");
				resultStatusBean.setMessage("getThisOrderLastOrder error");
				return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
			}
		}else{
			startTime = 0;
		}
		Date newDate = new Date();  // 当前系统时间
		Date complaintDate = new Date(occurTime*1000l); // 投诉时间
		try {
			if(isYeaterday(complaintDate, newDate) == -1){
				
				ObjectNode objectNode = EasemobChatMessage.getChatMessages( startTime * 1000l, endTime * 1000l , emobIdUser , emobIdShop); // 获取用户对商家说的话
				ObjectNode objectNode2 = EasemobChatMessage.getChatMessages( startTime * 1000l, endTime * 1000l , emobIdShop , emobIdUser); // 获取商家对用户说的话
				List<Messages> listFrom = EasemobChatMessage.getUserMessageBean(objectNode);
				List<Messages> listTo = EasemobChatMessage.getUserMessageBean(objectNode2);
				List<Messages> list = new ArrayList<Messages>();
				list.addAll(listFrom);
				list.addAll(listTo);
				ListSort<Messages> listSort = new ListSort<Messages>();
				listSort.Sort(list, "getTimestamp", ""); // 根据发送时间将聊天信息进行排序
				
				return gson.toJson(list).replace("\"null\"", "\"\"");
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			List<Messages> listFrom = messageMongo.getListMessage(emobIdShop, emobIdUser, startTime * 1000l, endTime * 1000l);
			List<Messages> listTo = messageMongo.getListMessage(emobIdUser, emobIdShop, startTime * 1000l, endTime * 1000l);
			List<Messages> list = new ArrayList<Messages>();
			list.addAll(listFrom);
			list.addAll(listTo);
			ListSort<Messages> listSort = new ListSort<Messages>();
			listSort.Sort(list, "getTimestamp", ""); // 根据发送时间将聊天信息进行排序
			return gson.toJson(list).replace("\"null\"", "\"\"");
//		/	page = messagesDao.getListAsTimeWithPage(emobIdUser, emobIdShop, startTime, endTime, pageNum, pageSize, 10); // 获取当前服务中双方聊天记录
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ResultStatusBean resultStatusBean = new ResultStatusBean();
			resultStatusBean.setStatus("no");
			resultStatusBean.setMessage("getListAsTimeWithPage error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		
	}
	
	@GET
	@Path("/getComplain") 
	public String getComplain(@QueryParam("complaint_id") Integer complaint_id){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			ComplaintTop com=	orderComplainsDao.getComplaints(complaint_id);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(com);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	@GET
	@Path("/getComplainUser") 
	public String getComplainUser(@QueryParam("userName") String  userName,@QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize){	
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Page<ComplaintsUserMsg> page = orderComplainsDao.getComplaintsUser(userName,pageNum, pageSize, 10);
			if(page != null){
				return gson.toJson(page);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultStatusBean.setMessage("getList is error");
			return gson.toJson(resultStatusBean);
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	
	/** 
     * @author 东权
     * @param oldTime 较小的时间 
     * @param newTime 较大的时间 (如果为空   默认当前时间 ,表示和当前时间相比) 
     * @return -1 ：同一天.    0：昨天 .   1 ：至少是前天. 
     * @throws ParseException 转换异常 
     */  
    private int isYeaterday(Date oldTime,Date newTime) throws ParseException{  
        if(newTime==null){  
            newTime=new Date();  
        }  
               //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        String todayStr = format.format(newTime);  
        Date today = format.parse(todayStr);  
        //昨天 86400000=24*60*60*1000 一天  
        if((today.getTime()-oldTime.getTime())>0 && (today.getTime()-oldTime.getTime())<=86400000) {  
            return 0;  
        }  
        else if((today.getTime()-oldTime.getTime())<=0){ //至少是今天  
            return -1;  
        }  
        else{ //至少是前天  
            return 1;  
        }  
          
    }  
	
}
