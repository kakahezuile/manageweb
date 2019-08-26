package com.xj.dao;

import java.util.List;

import com.xj.bean.StandardEntry;

public interface StandardEntryDao extends MyBaseDao{
	
	public Integer addStandardEntry(StandardEntry standardEntry) throws Exception;
	
	public boolean deleteStandardEntry(Integer entryId) throws Exception;
	
	public List<StandardEntry> getStandardEntryList(Integer communityId , String sort) throws Exception;
}
