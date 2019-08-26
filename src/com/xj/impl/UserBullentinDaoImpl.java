package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.UserBulletin;
import com.xj.dao.UserBullentinDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("userBullentinDao")
public   class UserBullentinDaoImpl  extends MyBaseDaoImpl implements UserBullentinDao{

	@Override
	public Integer insert(UserBulletin bulletin) throws Exception {
		MyReturnKey myReturnKey = new MyReturnKey();
		this.save(bulletin, myReturnKey);
		Integer resultId = myReturnKey.getId();
		return resultId;
	}

	@Override
	public List<UserBulletin> selectUserBullent(Integer bullentinId) throws Exception {
		String sql = "SELECT * FROM user_bulletin WHERE bulletin_id = ? ";
		List<UserBulletin> list = this.getList(sql, new Integer[]{bullentinId}, UserBulletin.class);
		return list;
	}
}
