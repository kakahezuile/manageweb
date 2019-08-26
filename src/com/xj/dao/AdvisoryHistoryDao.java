package com.xj.dao;

import com.xj.bean.AdvisoryHistory;
import com.xj.bean.Page;

public interface AdvisoryHistoryDao extends MyBaseDao{
	
	/**
	 * 添加一条
	 * @param advisoryHistory
	 * @return
	 * @throws Exception
	 */
	public Integer addAdvisoryHistory(AdvisoryHistory advisoryHistory) throws Exception;
	
	public boolean updateAdvisoryHistory(AdvisoryHistory advisoryHistory) throws Exception;
	
	public Page<AdvisoryHistory> getAdvisoryHistoryList(Integer communityId , Integer pageNum , Integer pageSize , Integer nvm , Integer startTime , Integer endTime , String name) throws Exception;
}
