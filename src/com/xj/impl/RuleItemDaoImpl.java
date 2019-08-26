package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;


import com.xj.bean.RuleItem;
import com.xj.dao.RuleItemDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("ruleItemDao")
public class RuleItemDaoImpl extends MyBaseDaoImpl implements RuleItemDao {

	@Override
	public Integer addRuleItem(RuleItem ruleItem) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(ruleItem, key);
		return key.getId();
	}

	@Override
	public RuleItem getRuleItem(Integer adminId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT * FROM rule_item WHERE admin_id = ? ";
		List<RuleItem> list = this.getList(sql, new Object[]{adminId}, RuleItem.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateRuleItem(RuleItem ruleItem) throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE rule_item SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(ruleItem);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE rule_item_id = ? ";
		System.out.println(sql);
		list.add(ruleItem.getRuleItemId());
		int result = this.updateData(sql, list, null);
		return result > 0 ;
	
	}
	
	@Override
	public RuleItem getRuleByAminAndCommunityId(Integer adminId, Integer communityId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM rule_item WHERE admin_id = ? AND community_id = ? ";
		List<RuleItem> list = this.getList(sql, new Object[]{adminId , communityId}, RuleItem.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public boolean deleteRuleByAdminIdAndCommunityId(Integer adminId,
			Integer communityId) throws Exception {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM rule_item WHERE admin_id = ? AND community_id != ?";
		List<Object> list = new ArrayList<Object>();
		list.add(adminId);
		list.add(communityId);
		Integer result = this.updateData(sql, list, null);
		return result > 0;
	}


}
