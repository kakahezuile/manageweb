package com.xj.resource;

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
import com.xj.bean.Complaints;
import com.xj.bean.Messages;
import com.xj.bean.Orders;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.dao.ComplaintsDao;
import com.xj.dao.MessagesDao;
import com.xj.dao.OrdersDao;

@Component
@Scope("prototype")
@Path("/communities/{communityId}/users/{emobIdUser}/complaints")
public class ComplaintsResource {
	
	
	
	private Gson gson = new Gson();
	
	@Autowired
	private ComplaintsDao complaintsDao;
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private MessagesDao messagesDao;
	/**
	 * 添加投诉
	 * @param json
	 * @param communityId
	 * @param emobIdUser
	 * @return
	 */
	@POST
	public String addComplaints(String json , @PathParam("communityId") Integer communityId , @PathParam("emobIdUser") String emobIdUser){
		ResultStatusBean result = new ResultStatusBean();
		result.setStatus("no");
		int resultId = 0;
		if(json != null && !"".equals(json)){
			Complaints complaints = gson.fromJson(json, Complaints.class);
			complaints.setEmobIdFrom(emobIdUser);
			complaints.setCommunityId(communityId);
			complaints.setEmobIdTo(complaints.getEmobIdShop());
			complaints.setEmobIdShop(null);
			complaints.setOccurTime((int)(System.currentTimeMillis() / 1000));
			try {
				resultId = complaintsDao.addComplaint(complaints);
				result.setResultId(resultId);
				result.setStatus("yes");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setMessage("添加投诉时发生错误了");
				result.setStatus("no");
			}
		}else{
			result.setMessage("参数不能为空");
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	/**
	 * 修改投诉内容
	 * @param json
	 * @param complaintId
	 * @return
	 */
	@PUT
	@Path("{complaintId}")
	public String updateComplaints(String json , @PathParam("complaintId") Integer complaintId){
		ResultStatusBean result = new ResultStatusBean();
		if(json != null && !"".equals(json)){
			Complaints complaints = gson.fromJson(json, Complaints.class);
			complaints.setComplaintId(complaintId);
			try {
				boolean resultBoolean = complaintsDao.updateComplaint(complaints);
				if(resultBoolean){
					result.setMessage("修改投诉状态成功");
					result.setStatus("no");
				}else{
					result.setMessage("修改投诉状态失败");
					result.setStatus("no");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setMessage("修改投诉状态时发生错误");
				result.setStatus("no");
			}
		}else{
			result.setMessage("参数不能为空");
			result.setStatus("no");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	/**
	 * 根据 小区id获取投诉列表
	 * @param communityId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GET
	public String getComplaints(@PathParam("communityId") Integer communityId , @QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize){
		Page<Complaints> page = null;
		try {
			page = complaintsDao.getComplaintWithPage(communityId, pageNum, pageSize, 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ResultStatusBean result = new ResultStatusBean();
			result.setMessage("获取社区投诉内容时发生错误了");
			result.setStatus("no");
			return gson.toJson(result);
		}
		return gson.toJson(page).replace("\"null\"", "\"\"");
	}
	/**
	 * 根据投诉id获取投诉信息
	 * @param complaintId
	 * @return
	 */
	@GET
	@Path("/{complaintId}/messages")
	public String getMessages( @PathParam("complaintId") Integer complaintId ){
		ResultStatusBean result = new ResultStatusBean();
		List<Messages> resultList = null;
		
		if(complaintId != null ){
//			Complaints complaint = gson.fromJson(json, Complaints.class);
			try {
				Complaints complaints = complaintsDao.getComplaint(complaintId);
				Integer orderId = complaints.getOrderId();
				Orders orders = ordersDao.getOrderById(orderId);
				resultList = messagesDao.getListAsTime(orders.getEmobIdUser(), orders.getEmobIdShop(), orders.getStartTime(), orders.getEndTime());
				return gson.toJson(resultList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setMessage("查询聊天记录过程中发生错误");
				result.setStatus("no");
				return gson.toJson(result);
			}
		}else{
			result.setMessage("参数不能为空");
			result.setStatus("no");
		}
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
}
