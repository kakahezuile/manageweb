package com.xj.dao;

import java.util.List;

import com.xj.bean.Rule;

public interface RuleDao extends MyBaseDao{

	public Integer addRule(Rule rule) throws Exception;
	
	public boolean updateRule(Rule rule) throws Exception;
	
	public List<Rule> getRuleList(String role) throws Exception;
	
	public List<Rule> getRuleByAdminId(Integer adminId) throws Exception;
	
	public List<Rule> getRuleByAdminId(Integer adminId, String role) throws Exception;
	
}
