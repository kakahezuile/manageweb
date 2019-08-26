package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.StandardEntry;
import com.xj.dao.StandardEntryDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("standardEntryDao")
public class StandardEntryDaoImpl extends MyBaseDaoImpl implements StandardEntryDao{

	@Override
	public Integer addStandardEntry(StandardEntry standardEntry)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(standardEntry, key);
		return key.getId();
	}

	@Override
	public boolean deleteStandardEntry(Integer entryId) throws Exception {
		// TODO Auto-generated method stub
		StandardEntry standardEntry = new StandardEntry();
		standardEntry.setEntryId(entryId);
		Integer result = this.delete(standardEntry);
		return result > 0;
	}

	@Override
	public List<StandardEntry> getStandardEntryList(Integer communityId,
			String sort) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT s.entry_id , s.entry_sum , s.standard_id , s.sort , s.community_id , s.create_time , (s.entry_sum*x.price) as price FROM standard_entry s left join xj_standard x on x.standard_id = s.standard_id WHERE s.community_id = ? AND s.sort = ? order by price  ";
		List<StandardEntry> list = this.getList(sql, new Object[]{communityId , sort}, StandardEntry.class);
		return list;
	}

}
