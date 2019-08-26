package com.xj.dao;


import com.xj.bean.RuleItem;

public interface RuleItemDao extends MyBaseDao{
	public Integer addRuleItem(RuleItem ruleItem) throws Exception;
	
	public RuleItem getRuleItem(Integer adminId) throws Exception;
	
	public boolean updateRuleItem(RuleItem ruleItem) throws Exception;
	
	public RuleItem getRuleByAminAndCommunityId(Integer adminId , Integer communityId) throws Exception;
	
	public boolean deleteRuleByAdminIdAndCommunityId(Integer adminId , Integer communityId) throws Exception;
}
