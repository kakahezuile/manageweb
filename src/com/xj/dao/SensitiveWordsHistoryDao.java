package com.xj.dao;

import com.xj.bean.Page;
import com.xj.bean.SensitiveWordsHistory;

public interface SensitiveWordsHistoryDao extends MyBaseDao{
	
	public Integer addSensitiveWordsHistory(SensitiveWordsHistory sensitiveWordsHistory) throws Exception;
	
	public boolean updateSensitiveWordsHistory(SensitiveWordsHistory sensitiveWordsHistory) throws Exception;
	
	public Page<SensitiveWordsHistory> getSensitiveWordsHistoryList(Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
	public Page<SensitiveWordsHistory> getSensitiveWordsHistoryList(Integer communityId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception;
	
	public boolean deleteSensitiveWordsHistory(SensitiveWordsHistory sensitiveWordsHistory) throws Exception;
	
	public SensitiveWordsHistory getSensitiveWordsHistory() throws Exception;
}
