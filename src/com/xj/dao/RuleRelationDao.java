package com.xj.dao;

import java.util.List;

import com.xj.bean.RuleRelation;

public interface RuleRelationDao extends MyBaseDao{

	public Integer addRuleRelation(RuleRelation ruleRelation) throws Exception;
	
	public boolean updateRuleRelation(RuleRelation ruleRelation) throws Exception;
	
	public List<RuleRelation> getRelationList(Integer adminId) throws Exception;
	
	public RuleRelation getRelationByAdminIdAndRuleId(Integer adminId , Integer ruleId) throws Exception;
	
	public int getOtherAdminActiveRelation(Integer adminId , Integer ruleId) throws Exception;
	
	public boolean updateRuleRelation(Integer adminId , Integer ruleId , String status) throws Exception;
}
