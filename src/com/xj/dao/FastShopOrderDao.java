package com.xj.dao;

import java.util.List;

import com.xj.bean.FastShopOrderHistory;
import com.xj.bean.Page;
import com.xj.bean.ShopItemOrderHistory;

public interface FastShopOrderDao extends MyBaseDao {
	
	/**
	 * 分页查询 - 根据环信id 店铺类型 获取快店历史订单
	 * @param emobId
	 * @param sort
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param nvm
	 * @return
	 * @throws Exception
	 */
	public Page<FastShopOrderHistory> getFastShopOrder(String emobId , String sort , String status , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
	/**
	 * 根据订单id 获取历史订单明细
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public List<ShopItemOrderHistory> getShopItemOrderHistory(Integer orderId) throws Exception;
}
