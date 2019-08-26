package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.AdvisoryHistory;
import com.xj.bean.Page;
import com.xj.dao.AdvisoryHistoryDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("advisoryHistoryDao")
public class AdvisoryHistoryDaoImpl extends MyBaseDaoImpl implements AdvisoryHistoryDao{

	@Override
	public Integer addAdvisoryHistory(AdvisoryHistory advisoryHistory)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(advisoryHistory, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateAdvisoryHistory(AdvisoryHistory advisoryHistory)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "UPDATE advisory_history SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(advisoryHistory);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}	
		sql += " WHERE advisory_history_id = ? ";
		System.out.println(sql);
		list.add(advisoryHistory.getAdvisoryHistoryId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public Page<AdvisoryHistory> getAdvisoryHistoryList(Integer communityId , Integer pageNum,
			Integer pageSize, Integer nvm, Integer startTime , Integer endTime , String name) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT status,advisory_history_id , emob_id_user , emob_id_agent , user_name , agent_name , advisory_time , phone , create_time , update_time FROM advisory_history a WHERE community_id = ? ";
		List<Object> list = new ArrayList<Object>();
		list.add(communityId);
		if(startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
			list.add(startTime);
			list.add(endTime);
			sql += " AND advisory_time >= ? AND advisory_time <= ? ";
		}
		if(name != null && !"".equals(name)){
			sql += " AND (user_name like '%"+name+"%' OR agent_name like '%"+name+"%')";
		}
		sql+="  ORDER BY advisory_history_id DESC";
		Page<AdvisoryHistory> page = this.getData4Page(sql, list.toArray(), pageNum, pageSize, nvm, AdvisoryHistory.class);
		return page;
	}

}
