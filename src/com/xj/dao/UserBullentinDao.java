package com.xj.dao;

import java.util.List;

import com.xj.bean.UserBulletin;

public interface UserBullentinDao extends MyBaseDao {

	public Integer insert(UserBulletin bulletin) throws Exception;

	public List<UserBulletin> selectUserBullent(Integer bullentinId) throws Exception;
}
