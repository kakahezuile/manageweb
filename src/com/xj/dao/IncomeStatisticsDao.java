package com.xj.dao;

import java.util.List;

import com.xj.bean.IncomeStatistics;
import com.xj.bean.IncomeStatisticsOrders;
import com.xj.bean.OrderDetailBean;
import com.xj.bean.Page;
import com.xj.bean.ShopIncomeStatistics;

public interface IncomeStatisticsDao extends MyBaseDao {
	
	/**
	 * 添加一条收入统计内容
	 * @param startTime
	 * @param endTime
	 * @param emobId
	 * @return
	 * @throws Exception
	 */
	public IncomeStatistics getIncome(Integer startTime , Integer endTime , String emobId) throws Exception;
	
	/**
	 * 分页查询 - 根据 时间、环信id 获取收入统计列表
	 * @param startTime
	 * @param endTime
	 * @param emobId
	 * @param pageNum
	 * @param pageSize
	 * @param nvm
	 * @return
	 * @throws Exception
	 */
	public Page<IncomeStatisticsOrders> getOrders(Integer startTime , Integer endTime , String emobId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
	/**
	 * 根据订单id 获取订单明细
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public List<OrderDetailBean> getOrderDetailList(Integer orderId) throws Exception;
	
	
	public Page<OrderDetailBean> getRanking(Integer startTime , Integer endTime , String emobId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
	public ShopIncomeStatistics getShopIncome(Integer startTime , Integer endTime , String emobId) throws Exception;
}
