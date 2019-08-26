package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.SensitiveWords;
import com.xj.dao.SensitiveWordsDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("sensitiveWordsDao")
public class SensitiveWordsDaoImpl extends MyBaseDaoImpl implements SensitiveWordsDao{

	@Override
	public Integer addSensitiveWords(SensitiveWords sensitiveWords)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(sensitiveWords, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateSensitiveWords(SensitiveWords sensitiveWords)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE sensitive_words SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(sensitiveWords);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE sensitive_words_id = ? ";
		
		list.add(sensitiveWords.getSensitiveWordsId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public List<SensitiveWords> getSensitiveWordsList() throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT sensitive_words_id , sensitive_words , create_time , update_time FROM sensitive_words s";
		List<SensitiveWords> list = this.getList(sql, new Object[]{}, SensitiveWords.class);
		return list;
	}

	@Override
	public boolean deleteSensitiveWords(SensitiveWords sensitiveWords)
			throws Exception {
		// TODO Auto-generated method stub
		Integer result = this.delete(sensitiveWords);
		return result > 0;
	}

}
