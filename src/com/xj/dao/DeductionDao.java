package com.xj.dao;

import java.util.List;

import com.xj.bean.Clearing;
import com.xj.bean.DeductMoney;
import com.xj.bean.Deduction;
import com.xj.bean.Page;
import com.xj.bean.ReckoningHistory;
import com.xj.bean.ShopsDeduction;

public interface DeductionDao extends MyBaseDao{
	/**
	 * 添加扣款
	 * @param deduction
	 * @return
	 * @throws Exception
	 */
	public Integer addDeduction(Deduction deduction) throws Exception;
	
	/**
	 * 修改扣款信息
	 * @param deduction
	 * @return
	 * @throws Exception
	 */
	public boolean updateDeduction(Deduction deduction) throws Exception;
	
	/**
	 * 分页查询 - 根据emobId 获取扣款信息
	 * @param emobId
	 * @param pageNum
	 * @param pageSize
	 * @param nvm
	 * @return
	 * @throws Exception
	 */
	public Page<Deduction> getDeductionList(String emobId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;

	/**
	 * 分页查询 - 根据emobId 开始时间 结束时间 获取 扣款信息
	 * @param emobId
	 * @param pageNum
	 * @param pageSize
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public Page<DeductMoney> getDeductMoney(String emobId, Integer pageNum, Integer pageSize, Integer startTime, Integer endTime)throws Exception;
	
	/**
	 * 根据emobId 获取所有扣款信息
	 * @param emobId
	 * @return
	 * @throws Exception
	 */
	public List<ShopsDeduction> getShopsDeduction(String emobId) throws Exception;
	
	/**
	 * 根据emobId 月份获取 当月扣款信息
	 * @param emobId
	 * @param months
	 * @return
	 * @throws Exception
	 */
	public List<Deduction> getDeductionList(String emobId , String months) throws Exception;

	public Page<ReckoningHistory> getReckoningHistory(Integer communityId,String sort,Integer pageNum, Integer pageSize)throws Exception;

	public Integer addClearing(Clearing clearing)throws Exception;
	
	public Deduction getDeduction(String emobId , Integer startTime , Integer endTime) throws Exception;
}
