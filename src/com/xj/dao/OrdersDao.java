package com.xj.dao;

import java.util.List;

import com.xj.bean.OrderUpdateBean;
import com.xj.bean.Orders;
import com.xj.bean.OrdersBean;
import com.xj.bean.Page;
import com.xj.bean.XjPayHistory;


public interface OrdersDao extends MyBaseDao{
	/**
	 * 保存订单信息
	 * @param ordersBean
	 * @return
	 */
    public String addOrder(OrdersBean ordersBean);
    
    /**
     * 修改订单信息
     * @param orderId
     * @param orderUpdateBean
     * @return
     */
    public boolean modifyOrderState(String orderId, OrderUpdateBean orderUpdateBean);
    
    /**
     * 根据订到号获取订单信息
     * @param orderId 订单号
     * @return 订单信息
     * @throws Exception
     */
    public Orders getOrderById(Integer orderId) throws Exception;
    
    public Orders getOrderBySerail(String serail) throws Exception;
    
    public Integer getCountByEmobIdShop(String emobIdShop) throws Exception;
    
    public boolean updateOrders(Orders orders) throws Exception;
    
    public boolean updateOrdersByOrderId(Orders orders) throws Exception;
    
    public Page<XjPayHistory> getXjPayHistory(String action , String emobId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
    
    public String getOrdersNumbers(String emobId , Integer startTime , Integer endTime) throws Exception;

    
	public int updateUsersShare(String serial);

	/**
	 * 获取用户的未完成订单
	 * @param communityId
	 * @param emobIdUser
	 * @param emobIdShop
	 * @return
	 */
	public List<OrdersBean> getNotEndOrders(int communityId, String emobIdUser,
			String emobIdShop)throws Exception;
}
