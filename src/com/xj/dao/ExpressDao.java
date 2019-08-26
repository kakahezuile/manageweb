package com.xj.dao;

import com.xj.bean.Express;
import com.xj.bean.ExpressTop;
import com.xj.bean.Page;

/**
 * 快递dao
 * @author Administrator
 *
 */
public interface ExpressDao extends MyBaseDao{
	
	public Integer addExpress(Express express) throws Exception;
	
	public boolean updateExpress(Express express) throws Exception;
	
	/**
	 * 查询登记的快递列表
	 */
	public Page<Express> findAllByType(Integer communityId ,Integer type , Integer pageNum ,Integer pageSize , Integer nvm ) throws Exception;
	
	public Express isEmpty(String expressNo) throws Exception;
	
	public Page<Express> findAllByText(Integer communityId , Integer type , String text , Integer pageNum ,Integer pageSize , Integer nvm  ) throws Exception;

	/**
	 * 快递统计头部
	 */
	public ExpressTop getExpressTop(Integer startTime, Integer endTime) throws Exception;

	/**
	 *  快递统计列表
	 */
	public Page<ExpressTop> getExpressList(Integer pageNum, Integer pageSize, Integer startTime, Integer endTime) throws Exception;

	/**
	 * 按时间统计快递信息
	 */
	public Page<Express> getExpressDetail(Integer type,Integer pageNum,Integer pageSize,Integer startTime, Integer endTime) throws Exception;
}
