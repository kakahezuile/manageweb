package com.xj.dao;

import java.util.List;

import com.xj.bean.Page;
import com.xj.bean.PayAmount;
import com.xj.bean.PayMessage;
import com.xj.bean.PayTurnover;

public interface PayMessageDao extends MyBaseDao {
	
	public Integer addPayMessage(PayMessage payMessage) throws Exception;
	
	public boolean updatePayMessage(PayMessage payMessage) throws Exception;
	
	public List<PayMessage> getPayMessageList() throws Exception;
	
	public PayMessage getPayMessageByType(Integer type) throws Exception;
	
	/**
	 * 运营缴费统计的头部
	 */
	public PayAmount getPayTop( Integer startTime,Integer endTime) throws Exception;
	/**
	 * 运营缴费统计的列表
	 */
	public Page<PayAmount> getPayList(Integer pageNum,Integer pageSize,Integer startTime, Integer endTime) throws Exception;

	public PayTurnover getPayTurnoverTop(Integer startTime, Integer endTime)throws Exception;

	public Page<PayTurnover> getPayTurnoverList(Integer pageNum,
			Integer pageSize, Integer startTime, Integer endTime)throws Exception;
}
