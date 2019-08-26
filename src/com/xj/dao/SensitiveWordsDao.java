package com.xj.dao;

import java.util.List;

import com.xj.bean.SensitiveWords;

public interface SensitiveWordsDao extends MyBaseDao {
	
	public Integer addSensitiveWords(SensitiveWords sensitiveWords) throws Exception;
	
	public boolean updateSensitiveWords(SensitiveWords sensitiveWords) throws Exception;
	
	public List<SensitiveWords> getSensitiveWordsList() throws Exception;
	
	public boolean deleteSensitiveWords(SensitiveWords sensitiveWords) throws Exception;
}
