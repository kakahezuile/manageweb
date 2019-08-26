package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Rule;
import com.xj.dao.RuleDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("ruleDao")
public class RuleDaoImpl extends MyBaseDaoImpl implements RuleDao {

	@Override
	public Integer addRule(Rule rule) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(rule, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateRule(Rule rule) throws Exception {
		String sql = " UPDATE rule SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(rule);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE rule_id = ? ";
		list.add(rule.getRuleId());
		return this.updateData(sql, list, null) > 0 ;
	}

	@Override
	public List<Rule> getRuleList(String role) throws Exception {
		String sql = "";
		List<Object> params = new ArrayList<Object>();
		if("other".equals(role)){
			sql += "SELECT * FROM rule WHERE 1 = 1 ";
		}else{
			sql += "SELECT * FROM rule WHERE role = ? ";
			params.add(role);
		}
		
		return this.getList(sql , params.toArray(), Rule.class);
	}

	@Override
	public List<Rule> getRuleByAdminId(Integer adminId) throws Exception {
		String sql = "SELECT r.rule_id , r.rule_name , r.div_id , r.nav_id , r.model_name , r.create_time , r.role  FROM rule_relation rr left join rule r on rr.rule_id = r.rule_id where rr.admin_id = ? and rr.status = 'block' order by r.rule_id "	;
		return this.getList(sql, new Object[]{adminId}, Rule.class);
	}

	@Override
	public List<Rule> getRuleByAdminId(Integer adminId, String role) throws Exception {
		String sql = "SELECT r.rule_id , r.rule_name , r.div_id , r.nav_id , r.model_name , r.create_time , r.role  FROM rule_relation rr "+
	 	" left join rule r on rr.rule_id = r.rule_id where rr.admin_id = ? and r.role = ? and rr.status = 'block' order by r.rule_id "	;
		return this.getList(sql, new Object[]{adminId,role}, Rule.class);
	}
}