package com.xj.resource.welfare;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.service.welfare.WelfareOrderService;

@Path("/communities/{communityId}/welfareOrders")
@Component
@Scope("prototype")
public class WelfareOrderResource {

	private Gson gson = new Gson();
	
	@Autowired
	private WelfareOrderService welfareOrderService;
	
	@GET
	public String getWelfares(@QueryParam("welfareId") Integer welfareId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(welfareOrderService.getWelfareOrders(welfareId));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("获取福利订单列表失败:" + e.getMessage());
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@POST
	@Path("/cancel/{welfareOrderId}")
	public String cancelOrder(@PathParam("welfareOrderId") Integer welfareOrderId) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setStatus(welfareOrderService.refund(welfareOrderId) ? "yes" : "no");
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("订单退款失败:" + e.getMessage());
		}
		
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@POST
	@Path("/notify/{welfareOrderId}")
	public String notify(@PathParam("welfareOrderId") Integer welfareOrderId, @FormParam("type") int type, @FormParam("message") String message) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setStatus(welfareOrderService.notify(welfareOrderId, type, message) ? "yes" : "no");
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("发送福利通知消息失败：" + e.getMessage());
		}
		
		return gson.toJson(resultStatusBean);
	}
	
	@POST
	@Path("/notifyAll")
	public String notifyAll(@FormParam("welfareId") Integer welfareId, @FormParam("type") int type, @FormParam("status") String status, @FormParam("message") String message) {
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setStatus(welfareOrderService.notifyAll(welfareId, type, status, message) ? "yes" : "no");
		} catch (Exception e) {
			e.printStackTrace();
			
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("发送福利通知消息失败：" + e.getMessage());
		}
		
		return gson.toJson(resultStatusBean);
	}
}