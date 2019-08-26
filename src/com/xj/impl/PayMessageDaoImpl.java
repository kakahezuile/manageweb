package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Page;
import com.xj.bean.PayAmount;
import com.xj.bean.PayMessage;
import com.xj.bean.PayTurnover;
import com.xj.dao.PayMessageDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("payMessageDao")
public class PayMessageDaoImpl extends MyBaseDaoImpl implements PayMessageDao{

	@Override
	public Integer addPayMessage(PayMessage payMessage) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(payMessage, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updatePayMessage(PayMessage payMessage) throws Exception {
		String sql = " UPDATE pay_message SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(payMessage);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE message_id = ? ";
		System.out.println(sql);
		list.add(payMessage.getMessageId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public List<PayMessage> getPayMessageList() throws Exception {
		String sql = "SELECT * FROM pay_message";
		List<PayMessage> list = this.getList(sql, new Object[]{} , PayMessage.class);
		return list;
	}

	@Override
	public PayMessage getPayMessageByType(Integer type) throws Exception {
		String sql = "SELECT * FROM pay_message WHERE message_type = ? ";
		List<PayMessage> list = this.getList(sql, new Object[]{type}, PayMessage.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 *  运营 缴费头部
	 */
	@Override
	public PayAmount getPayTop(Integer startTime,Integer endTime) throws Exception {
		String sql = "SELECT COUNT(p.pay_id) AS order_pay,p.create_time  FROM xj_pay p WHERE p.create_time>? AND p.create_time<?";
		List<PayAmount> list = this.getList(sql, new Object[]{startTime,endTime}, PayAmount.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 *  运营 缴费 列表
	 */

	@Override
	public Page<PayAmount> getPayList(Integer pageNum, Integer pageSize,
			Integer startTime, Integer endTime) throws Exception {
		String sql = "SELECT COUNT(p.pay_id) AS order_pay,p.create_time FROM xj_pay p WHERE p.create_time>? AND p.create_time<? GROUP BY day(from_unixtime(p.create_time))";
		Page<PayAmount> page = this.getData4Page(sql, new Object[] {
				startTime, endTime }, pageNum, pageSize, 10,
				PayAmount.class);
		return page;
	}
	/**
	 * 运营 缴费金额统计
	 */
	@Override
	public PayTurnover getPayTurnoverTop(Integer startTime, Integer endTime)
			throws Exception {
		String sql = "SELECT COUNT(p.pay_id) AS pay_amount,SUM(p.pay_sum) AS pay_sum,p.pay_type AS type  FROM xj_pay p WHERE p.create_time>? AND p.create_time<?";
		List<PayTurnover> list = this.getList(sql, new Object[]{startTime,endTime}, PayTurnover.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 运营缴费列表
	 * @throws Exception 
	 */
	@Override
	public Page<PayTurnover> getPayTurnoverList(Integer pageNum,
			Integer pageSize, Integer startTime, Integer endTime) throws Exception {
		String sql = "SELECT COUNT(p.pay_id) AS pay_amount,SUM(p.pay_sum) AS pay_sum,p.pay_type AS type  FROM xj_pay p WHERE p.create_time>? AND p.create_time<? GROUP BY p.pay_type";
		Page<PayTurnover> page = this.getData4Page(sql, new Object[] {
				startTime, endTime }, pageNum, pageSize, 10,
				PayTurnover.class);
		return page;
	}

}
