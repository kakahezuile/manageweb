package com.xj.resource2;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import com.xj.bean.FastShopOrderHistory;
import com.xj.bean.Orders;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopItemOrderHistory;
import com.xj.dao.FastShopOrderDao;
import com.xj.dao.OrdersDao;

@Component
@Scope("prototype")
@Path("/v2/communities/{communityId}/fastShop")
public class FastShopOrderResource2 {
	
	private Gson gson = new Gson();
	
	@Autowired
	private FastShopOrderDao fastShopOrderDao;
	
	@Autowired
	private OrdersDao ordersDao;
	
	@GET
	public String getOrderHistory(@QueryParam("q") String emobId ,@QueryParam("pageNum") Integer pageNum , @QueryParam("pageSize") Integer pageSize){ // 快店历史订单
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Page<FastShopOrderHistory> page = fastShopOrderDao.getFastShopOrder(emobId, "2", "ended", pageNum, pageSize, 10);
			List<FastShopOrderHistory> list = page.getPageData();
			int len = list.size();
			List<ShopItemOrderHistory> listShopItem = null;
			for(int i = 0 ; i < len ; i++){
				FastShopOrderHistory fastShopOrderHistory = list.get(i);
				Integer orderId = fastShopOrderHistory.getOrderId();
				listShopItem = fastShopOrderDao.getShopItemOrderHistory(orderId);
				list.get(i).setList(listShopItem);
			}
			page.setPageData(list);
			resultStatusBean.setStatus("yes");
			resultStatusBean.setInfo(page);
		} catch (Exception e) { 
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	/**
	 * 修改订单历史
	 * @param orderId
	 * @return
	 */
	@PUT
	@Path("{orderId}")
	public String updateOrder(@PathParam("orderId") Integer orderId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		try {
			Orders orders = new Orders();
			orders.setOrderId(orderId);
			orders.setOrderDetail("delete");
			boolean result = ordersDao.updateOrdersByOrderId(orders);
			if(result){
				resultStatusBean.setStatus("yes");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
}
