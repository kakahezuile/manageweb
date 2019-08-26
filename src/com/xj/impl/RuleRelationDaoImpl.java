package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.RuleRelation;
import com.xj.dao.RuleRelationDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("ruleRelationDao")
public class RuleRelationDaoImpl extends MyBaseDaoImpl implements RuleRelationDao{

	@Override
	public Integer addRuleRelation(RuleRelation ruleRelation) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(ruleRelation, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateRuleRelation(RuleRelation ruleRelation) throws Exception {
		String sql = " UPDATE rule_relation SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(ruleRelation);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE rule_relation_id = ? ";
		System.out.println(sql);
		list.add(ruleRelation.getRuleRelationId());
		return this.updateData(sql, list, null) > 0 ;
	}

	@Override
	public List<RuleRelation> getRelationList(Integer adminId) throws Exception {
		String sql = "SELECT * FROM rule_relation WHERE admin_id = ? AND status = 'block'";
		return this.getList(sql, new Object[]{adminId}, RuleRelation.class);
	}

	@Override
	public RuleRelation getRelationByAdminIdAndRuleId(Integer adminId, Integer ruleId) throws Exception {
		String sql = "SELECT * FROM rule_relation WHERE admin_id = ? AND rule_id = ? ";
		List<RuleRelation> list = this.getList(sql, new Object[]{adminId,ruleId}, RuleRelation.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public int getOtherAdminActiveRelation(Integer adminId, Integer ruleId) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getJdbcTemplate().getDataSource().getConnection();
			String sql = "SELECT COUNT(1) FROM rule_relation WHERE admin_id <> ? AND rule_id = ? AND status = 'block'";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, adminId);
			preparedStatement.setInt(2, ruleId);
			
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1);
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean updateRuleRelation(Integer adminId, Integer ruleId , String status) throws Exception {
		List<Object> list = new ArrayList<Object>(3);
		list.add(status);
		list.add(adminId);
		list.add(ruleId);
		
		return this.updateData("UPDATE rule_relation SET status = ? WHERE admin_id = ? AND rule_id = ?", list, null) > 0;
	}
}