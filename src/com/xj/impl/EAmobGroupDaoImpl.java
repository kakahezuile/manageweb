package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.EAmobGroup;
import com.xj.dao.EAmobGroupDao;

@Repository("eAmobGroupDao")
public class EAmobGroupDaoImpl extends MyBaseDaoImpl implements EAmobGroupDao{

	@Override
	public EAmobGroup getEAmobGroup(String emobGroupId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT activity_title as group_name FROM activities WHERE emob_group_id = ? ";
		List<EAmobGroup> list = this.getList(sql, new Object[]{emobGroupId}, EAmobGroup.class);
		return list != null && list.size() > 0 ? list.get(0) : null ;
	}

}
