package com.xj.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.xj.bean.LifeCircle;
import com.xj.bean.Users;
import com.xj.dao.TryOutDao;
import com.xj.stat.po.TryOut;

/**
 * @author lence
 * @date 2015年7月9日上午1:08:21
 * 
 */
@Repository
public class TryOutDaoImpl extends SqlSessionDaoSupport implements TryOutDao {

	private String ns = "com.xj.stat.sqlmap.mapper.TryOutMapper.";

	public List<TryOut> getAllTryOut() {
		return this.getSqlSession().selectList(ns + "queryAll");
	}

	public List<String> getTryOutByEmobIds(Object[] array) {
		return this.getSqlSession().selectList(ns + "getTryOutByEmobIds", array);
	}

	@Override
	public List<String> selectTryOut(Integer communityId) {
		return this.getSqlSession().selectList(ns + "selectTryOut", communityId);
	}

	@Override
	public List<Users> getTryOuts(Integer communityId, Integer pageNum,
			Integer pageSize) {
		Map<String ,Integer> map = new HashMap<String,Integer>();
		map.put("communityId", communityId);
		map.put("start", (pageNum-1)*pageSize);
		map.put("size", pageSize);
		
		return this.getSqlSession().selectList(ns + "getTryOuts", map);
	}

	@Override
	public List<LifeCircle> getLifeCircles(String emobId,
			Integer pageNum, Integer pageSize) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("emobId", emobId);
		map.put("start", (pageNum-1)*pageSize);
		map.put("size", pageSize);
		return this.getSqlSession().selectList(ns + "getLifeCircles", map);
	}

	@Override
	public Integer addTryOut(TryOut to) {
		return this.getSqlSession().insert(ns + "insertSelective",to);
	}

	@Override
	public Integer getTryOutNum(Integer communityId) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("communityId", communityId);
		return this.getSqlSession().selectOne(ns + "getTryOutNum", map);
	}

	@Override
	public int getLifeCirclesSum(String emobId) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("emobId", emobId);
		return this.getSqlSession().selectOne(ns + "getLifeCirclesSum", map);
	}

	@Override
	public Users getNavyByEmobId(String emobId) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("emobId", emobId);
		return this.getSqlSession().selectOne(ns + "getNavyByEmobId", map);
	}

	@Override
	public List<Users> searchNavy(String query) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("query1", query);
		map.put("query2", query);
		return this.getSqlSession().selectList(ns + "searchNavy", query);
	}
}