package com.xj.resource;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xj.bean.BonuscoinManager;
import com.xj.bean.OrderJsonParams;
import com.xj.bean.OrderResult;
import com.xj.bean.Orders;
import com.xj.bean.OrdersBean;
import com.xj.bean.OrdersHistory;
import com.xj.bean.Page;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Shops;
import com.xj.bean.XjPayHistory;
import com.xj.dao.BonuscoinManagerDao;
import com.xj.dao.OrdersDao;
import com.xj.dao.ShopsDao;
import com.xj.service.BonuscoinService;
import com.xj.service.OrderService;

/**
 * 
 */
@Component
@Scope("prototype")
@Path("/communities/{communityId}/users/{emobIdUser}/orders")
public class OrdersResource {
    //logger
    Logger logger = Logger.getLogger(OrdersResource.class);
    //dao
    @Autowired
    private OrdersDao ordersDao;
    
    @Autowired
    private OrderService orderService;
    @Autowired
    private BonuscoinService bonuscoinService;
    
    @Autowired
    private BonuscoinManagerDao bonuscoinManagerDao;
    
    @Autowired
    private ShopsDao shopsDao;
    
    private Gson gson = new Gson();
    /**
     * 下订单
     * @param requestJson
     * @param emobIdUser
     * @param communityId
     * @return
     */
    @POST
    public String addOrders(String requestJson , @PathParam("emobIdUser") String emobIdUser , @PathParam("communityId") Integer communityId){
        Gson gson = new Gson();
        ResultStatusBean resultStatusBean = new ResultStatusBean();
        resultStatusBean.setStatus("no");

        try {
        	 logger.info("request json is :"+requestJson);
             //parse request to bean
             OrderJsonParams orderJsonParams = null;
             if(requestJson != null && !"".equals(requestJson)){
            	 orderJsonParams = gson.fromJson(requestJson, OrderJsonParams.class);
            	 resultStatusBean = orderService.isError(orderJsonParams.getOrders());
            	 if(resultStatusBean.getMessage() != null && !"".equals(resultStatusBean.getMessage())){
            		 return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
            	 }
            	 resultStatusBean = orderService.addOrder(orderJsonParams.getOrders() , communityId , emobIdUser);
             }

            
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			
			return gson.toJson(orderService.getResultStatusBean()).replace("\"null\"", "\"\"");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
       
    }

//
//    @PUT
//    @Path("{orderId}")
//    public String modifyOrderState(@PathParam("orderId") String orderId ,String requestJson){
//        Gson gson = new Gson();
//        OrderUpdateBean orderUpdateBean = gson.fromJson(requestJson, OrderUpdateBean.class);
//
//        boolean isUpdateSucess = ordersDao.modifyOrderState(orderId,orderUpdateBean);
//        ResultStatusBean resultStatusBean = new ResultStatusBean();
//        if(isUpdateSucess){
//            resultStatusBean.setStatus("yes");
//            return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
//        }
//        return null;
//    }
    
    /**
     * 修改订单内容
     */
    @PUT
    @Path("{orderId}")
    public String updateOrders(@PathParam("orderId") String orderId , String json){
    	ResultStatusBean resultStatusBean = new ResultStatusBean();
    	try {
			Orders orders = gson.fromJson(json, Orders.class);
			orders.setSerial(orderId);
			if("ended".equals(orders.getStatus()) || "canceled".equals(orders.getStatus()) || "renounce".equals(orders.getStatus())){
				orders.setEndTime((int)(System.currentTimeMillis() / 1000l));

			//2015-07-15添加帮帮币功能
				Orders orderBySerail = ordersDao.getOrderBySerail(orderId);
				Shops shops = shopsDao.getShopsByEmobId(orderBySerail.getEmobIdShop());
				if("2".equals(shops.getSort())){
					Integer bonuscoinCount = bonuscoinService.addBonuscoin(orderBySerail);
					
					BonuscoinManager bonuscoinManager = bonuscoinManagerDao.getBonuscoinManager(new  Integer(shops.getSort()));
					Map<String, Integer> map = new HashMap<String, Integer>();
					map.put("bonuscoinCount", bonuscoinCount);
					map.put("shareBonuscoinCount", bonuscoinManager.getBonuscoinCount());
					resultStatusBean.setInfo(map);
				}
				
			
				List<OrderResult> resultOrders = new  ArrayList<OrderResult>();
				OrderResult orderResult = new OrderResult();
				orderResult.setSerail(orderBySerail.getSerial());
				orderResult.setOrderId(orderBySerail.getOrderId()+"");
				Shops shop = new Shops();
				shop.setEmobId(orderBySerail.getEmobIdShop());
				orderResult.setShop(shop);
				
				resultOrders.add(orderResult);
				
				
				resultStatusBean.setResultOrders(resultOrders);
		
				
			}else{
//				if(orders.getStatus() != null){
//					orders.setStartTime((int)(System.currentTimeMillis() / 1000l));
//				}
			}
			boolean result = ordersDao.updateOrders(orders);
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
    /**
     * 
     * @param action
     * @param emobId
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GET
    @Path("/pay")
    public String getXjPayList(@PathParam("action") String action , @PathParam("emobIdUser") String emobId , @QueryParam("pageSize") Integer pageSize , @QueryParam("pageNum") Integer pageNum){
    	ResultStatusBean resultStatusBean = new ResultStatusBean();
    	try {
    		Integer thisYear = null;
    		Integer thisMonth = null;
			Page<XjPayHistory> page = ordersDao.getXjPayHistory(action, emobId, pageNum, pageSize, 10);
			List<XjPayHistory> list = page.getPageData();
			List<OrdersHistory> listResult = new ArrayList<OrdersHistory>();
			int len = list.size();
			List<XjPayHistory> XjPayHistoryList = new ArrayList<XjPayHistory>();
			for(int i = 0 ; i < len ; i ++){
				XjPayHistory xjPayHistory = list.get(i);
				
				Integer year = xjPayHistory.getOrderYear();
				Integer month = xjPayHistory.getOrderMonth();
				if(i == 0){ 
					thisYear = year;
					thisMonth = month;
				}
				
				if(thisYear.intValue() == year.intValue() && thisMonth.intValue() == month.intValue()){
					XjPayHistoryList.add(xjPayHistory);
				}else{
					if(i != len - 1){
						OrdersHistory ordersHistory = new OrdersHistory();
						ordersHistory.setOrderMonth(thisMonth);
						ordersHistory.setOrderYear(thisYear);
						ordersHistory.setList(XjPayHistoryList);
						XjPayHistoryList = new ArrayList<XjPayHistory>();
						thisYear = xjPayHistory.getOrderYear();
						thisMonth = xjPayHistory.getOrderMonth();
						listResult.add(ordersHistory);
					}
					
				}
			}
			if(XjPayHistoryList != null && XjPayHistoryList.size() > 0){
				OrdersHistory ordersHistory = new OrdersHistory();
				ordersHistory.setOrderMonth(thisMonth);
				ordersHistory.setOrderYear(thisYear);
				ordersHistory.setList(XjPayHistoryList);
				listResult.add(ordersHistory);
				resultStatusBean.setStatus("yes");
				Page<OrdersHistory> page2 = new Page<OrdersHistory>();
				page2.setPageData(listResult);
				page2.setFirst(page.getFirst());
				page2.setLast(page.getLast());
				page2.setNext(page.getNext());
				page2.setNum(page.getNum());
				page2.setPrev(page.getPrev());
				page2.setRowCount(page.getRowCount());
				page2.setBegin(page.getBegin());
				page2.setEnd(page.getEnd());
				page2.setNavCount(page.getNavCount());
				page2.setNavNum(page.getNavNum());
				page2.setPageSize(page.getPageSize());
				resultStatusBean.setInfo(page2);
			}
		
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
		}
    	return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
    }
    /**
     * 根据用户emobid 和店主emobid 获取他们之间未完成的订单
     * @param communityId 
     * @param emobIdUser
     * @param emobIdShop
     * @return
     */
    @GET
    @Path("/notEndOrders")
   public String getNotEndOrders(@PathParam("communityId") int communityId,@PathParam("emobIdUser")String emobIdUser,@QueryParam("q")String emobIdShop){
    	ResultStatusBean resultStatusBean = new ResultStatusBean();
    	resultStatusBean.setStatus("no");
    	try {
			List<OrdersBean> orders = orderService.getNotEndOrders(communityId,emobIdUser,emobIdShop);
			if(orders!=null && orders.size()>0){
				resultStatusBean.setStatus("yes");
				resultStatusBean.setInfo(orders);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			return gson.toJson(resultStatusBean);
			
		}
    	
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
   }
    
}
