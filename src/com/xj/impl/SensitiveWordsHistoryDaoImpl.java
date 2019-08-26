package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Page;
import com.xj.bean.SensitiveWordsHistory;
import com.xj.dao.SensitiveWordsHistoryDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("sensitiveWordsHistoryDao")
public class SensitiveWordsHistoryDaoImpl extends MyBaseDaoImpl implements SensitiveWordsHistoryDao {

	@Override
	public Integer addSensitiveWordsHistory(
			SensitiveWordsHistory sensitiveWordsHistory) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(sensitiveWordsHistory, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateSensitiveWordsHistory(
			SensitiveWordsHistory sensitiveWordsHistory) throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE sensitive_words_history SET " ;
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(sensitiveWordsHistory);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false; 
		}
		sql += " WHERE sensitive_words_history_id = ? ";
		list.add(sensitiveWordsHistory.getSensitiveWordsHistoryId());
		Integer result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public Page<SensitiveWordsHistory> getSensitiveWordsHistoryList(
			Integer pageNum, Integer pageSize, Integer nvm) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT sensitive_words_history_id , sensitive_words , group_name , group_id , community_id , community_name , history_time , status , nickname , emob_id , create_time , update_time , uuid FROM sensitive_words_history order by sensitive_words_history_id desc";
		Page<SensitiveWordsHistory> page = this.getData4Page(sql, new Object[]{}, pageNum, pageSize, nvm, SensitiveWordsHistory.class);
		return page;
	}

	@Override
	public boolean deleteSensitiveWordsHistory(
			SensitiveWordsHistory sensitiveWordsHistory) throws Exception {
		// TODO Auto-generated method stub
		Integer result = this.delete(sensitiveWordsHistory);
		return result > 0;
	}

	@Override
	public SensitiveWordsHistory getSensitiveWordsHistory() throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT * FROM sensitive_words_history s where create_time = (select max(create_time) from sensitive_words_history) ";
		List<SensitiveWordsHistory> list = this.getList(sql, new Object[]{}, SensitiveWordsHistory.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Page<SensitiveWordsHistory> getSensitiveWordsHistoryList(
			Integer communityId, Integer pageNum, Integer pageSize, Integer nvm)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT sensitive_words_history_id , sensitive_words , group_name , group_id , community_id , community_name , history_time , status , nickname , emob_id , create_time , update_time , uuid FROM sensitive_words_history WHERE community_id = ? order by sensitive_words_history_id desc";
		Page<SensitiveWordsHistory> page = this.getData4Page(sql, new Object[]{communityId}, pageNum, pageSize, nvm, SensitiveWordsHistory.class);
		return page;
	}
	
	
	

}
