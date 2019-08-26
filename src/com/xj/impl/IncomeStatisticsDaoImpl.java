package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.IncomeStatistics;
import com.xj.bean.IncomeStatisticsOrders;
import com.xj.bean.OrderDetailBean;
import com.xj.bean.Page;
import com.xj.bean.ShopIncomeStatistics;
import com.xj.dao.IncomeStatisticsDao;

@Repository("incomeStatisticsDao")
public class IncomeStatisticsDaoImpl extends MyBaseDaoImpl implements IncomeStatisticsDao{

	@Override
	public IncomeStatistics getIncome(Integer startTime, Integer endTime,
			String emobId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT round(sum(order_price),2) as order_price , round(sum(case when online = 'yes' then order_price else 0 end),2) as online_price , count(order_id) as order_sum " +
					
					 " , round(sum(case when online = 'no' then order_price else 0 end),2) as theline_price  FROM orders o where emob_id_shop = ? and status = 'ended' and end_time >= ? and end_time <= ? ";
		List<IncomeStatistics> list = this.getList(sql, new Object[]{emobId , startTime , endTime}, IncomeStatistics.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Page<IncomeStatisticsOrders> getOrders(Integer startTime, Integer endTime,
			String emobId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT serial , start_time , order_id , online , order_price FROM orders o where status = 'ended' and end_time >=? and end_time <= ? and emob_id_shop = ? order by start_time desc ";
		Page<IncomeStatisticsOrders> page = this.getData4Page(sql, new Object[]{startTime , endTime , emobId}, pageNum, pageSize, nvm, IncomeStatisticsOrders.class);
		return page;
	}

	@Override
	public List<OrderDetailBean> getOrderDetailList(Integer orderId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT service_id , service_name , count , (price*count) as price FROM order_detail o where order_id = ? ";
		List<OrderDetailBean> list = this.getList(sql, new Object[]{orderId}, OrderDetailBean.class);
		return list;
	}

	@Override
	public Page<OrderDetailBean> getRanking(Integer startTime, Integer endTime,
			String emobId, Integer pageNum, Integer pageSize, Integer nvm)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT od.service_id , od.service_name , count(od.count) as count , 0 as price " +
					
					 "	 FROM order_detail od left join orders o on o.order_id = od.order_id " +
					
					 "  where o.emob_id_shop = ? and o.status = 'ended' and o.end_time >=? and o.end_time <=? group by od.service_id order by count desc";
		Page<OrderDetailBean> page = this.getData4Page(sql, new Object[]{emobId,startTime,endTime}, pageNum, pageSize, nvm, OrderDetailBean.class);
		return page;
	}

	@Override
	public ShopIncomeStatistics getShopIncome(Integer startTime,
			Integer endTime, String emobId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT round(sum((case when status = 'ended' then order_price else 0 end)),2) as order_price , round(sum(case when online = 'yes' and status = 'ended' then order_price else 0 end),2) as online_price ," +
				
					 " round(sum(case when online = 'no' then order_price else 0 end),2) as theline_price , count(order_id) as order_sum , " +
					
					 "        sum(case when status = 'ended' then 1 else 0 end) as ended_sum " +
		
					 "   FROM orders o where emob_id_shop = ?  and end_time >= ? and end_time <= ? ";
		List<ShopIncomeStatistics> list = this.getList(sql, new Object[]{emobId , startTime , endTime}, ShopIncomeStatistics.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
