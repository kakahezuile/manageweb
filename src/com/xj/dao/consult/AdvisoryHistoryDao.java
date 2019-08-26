package com.xj.dao.consult;

import java.util.List;

import com.xj.bean.AdvisoryHistory;
import com.xj.bean.Page;
import com.xj.dao.MyBaseDao;

public interface AdvisoryHistoryDao extends MyBaseDao  {

	/**
	 * 添加一条
	 * @param advisoryHistory
	 * @return
	 * @throws Exception
	 */
	public Integer addAdvisoryHistory(AdvisoryHistory advisoryHistory) throws Exception;
	
	public boolean updateAdvisoryHistory(AdvisoryHistory advisoryHistory) throws Exception;
	
	public Page<AdvisoryHistory> getAdvisoryHistoryList(Integer communityId , Integer pageNum , Integer pageSize , Integer nvm , Integer startTime , Integer endTime , String name) throws Exception;

	public List<AdvisoryHistory> getGroup(Integer communityId)throws Exception;
}
