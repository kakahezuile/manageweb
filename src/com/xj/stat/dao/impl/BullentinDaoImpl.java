package com.xj.stat.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.xj.bean.Page;
import com.xj.bean.StatPushMessageBean;
import com.xj.stat.dao.BullentinDao;
import com.xj.stat.po.Bulletin;
@Repository
public class BullentinDaoImpl extends SqlSessionDaoSupport implements BullentinDao  {
	String ns = "com.xj.stat.sqlmap.mapper.BulletinMapper.";

	@Override
	public List<Bulletin> getAllBulletinByCommunityId(Integer communityId,
			Page<StatPushMessageBean> pb) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("communityId", communityId);
		map.put("start", pb.getStartRow());
		map.put("pageSize", pb.getPageSize());
		
		List<Bulletin> Bulletins =  this.getSqlSession().selectList(ns+"getAllBulletinByCommunityId", map);
		return Bulletins;
	}

	@Override
	public int getAllCountByCommunityId(Integer communityId) {
		return this.getSqlSession().selectOne(ns+"getAllCountByCommunityId", communityId);
	}

}
