package com.xj.stat.dao.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.xj.stat.dao.UserDao;
import com.xj.stat.po.Users;

/**
 *@author lence
 *@date  2015年7月9日上午12:49:21
 *
 */
@Repository
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

	private String ns = "com.xj.stat.sqlmap.mapper.UsersMapper.";
	
	public Users getUsers(String str) {
		return  this.getSqlSession().selectOne(ns+"getUsers",str);
	}
	
	public List<Users> getUsersByArray(Object[] str,Integer communityId) {
		Map<String,Object> map=new HashMap<String,Object>() ;
		map.put("str", str);
		map.put("communityId", communityId);
		return  this.getSqlSession().selectList(ns+"getUsersByArray",map);
	}

	public List<Users> findUsersByCommunityId(int  id) {
		
		return this.getSqlSession().selectList(ns+"findUsersByCommunityId",id);
	}

	public List<Users> findUsersByCommunityIdWithNull(int id) {
		return this.getSqlSession().selectList(ns+"findUsersByCommunityIdWithNull",id);
	}

	public List<String> findAllInstallUserIdByCommunity(Integer communityid) {
		
		return this.getSqlSession().selectList(ns+"findAllInstallUserIdByCommunity",communityid);
	}

	public List<String> findAllRegisService(Integer communityid) {
		return this.getSqlSession().selectList(ns+"findAllRegisService",communityid);
		
	}

	public List<String> getUsersByEmobIdsWithVisitor(Object[] str) {
		
		return  this.getSqlSession().selectList(ns+"getUsersByArrayWithVisitor",str);
	}

	@Override
	public Integer findAllInstallUserIdByCommunityAndTime(String endDay,
			Integer communityId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("endDay", Integer.parseInt(endDay));
		map.put("communityId", communityId);
		
		return this.getSqlSession().selectOne(ns+"findAllInstallUserIdByCommunityAndTime",map);
	}

	@Override
	public Integer findAllRegisServiceByCommunityAndTime(String endDay,
			Integer communityId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("endDay", Integer.parseInt(endDay));
		map.put("communityId", communityId);
		return this.getSqlSession().selectOne(ns+"findAllRegisServiceByCommunityAndTime",map);
	}

	@Override
	public List<String> getInstallActiveUsers(Set<String> set,
			Integer communityId, String endDay) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("emobId", set.toArray());
		map.put("communityId", communityId);
		map.put("endDay", endDay);
		return this.getSqlSession().selectList(ns+"getInstallActiveUsers",map);
	}

	@Override
	public List<String> getRegisterActiveUsers(Set<String> set,
			Integer communityId, String endDay) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("emobId", set.toArray());
		map.put("communityId", communityId);
		map.put("endDay", endDay);
		return this.getSqlSession().selectList(ns+"getRegisterActiveUsers",map);
	}

	@Override
	public List<com.xj.bean.Users> findUserByIds(List<String> emobIds) {
		return this.getSqlSession().selectList(ns+"findUserByIds",emobIds);
	}

	@Override
	public Set<String> getAllTest(Integer communityId) {
		List<String> selectList = this.getSqlSession().selectList(ns+"getAllTest",communityId);
		return new HashSet<String>(selectList);
	}

}
