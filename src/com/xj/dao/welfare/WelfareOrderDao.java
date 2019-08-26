package com.xj.dao.welfare;

import java.util.List;

import com.xj.bean.welfare.WelfareOrder;

public interface WelfareOrderDao {

	/**
	 * 获取福利订单信息
	 * @param welfareId
	 * @return
	 * @throws Exception
	 */
	List<WelfareOrder> getWelfareOrders(Integer welfareId) throws Exception;

	/**
	 * 获取订单信息
	 * @param welfareOrderId
	 * @return
	 * @throws Exception
	 */
	WelfareOrder getWelfareOrder(Integer welfareOrderId) throws Exception;

	/**
	 * 福利订单退款
	 * @param welfareOrderId
	 * @return
	 */
	boolean refund(Integer welfareOrderId);

	/**
	 * 获取指定福利下，指定状态的订单用户
	 * @param welfareId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	String[] getWelfareOrderUsers(Integer welfareId, String status) throws Exception;

}