package com.xj.stat.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.dao.StatisticsUserDao;
import com.xj.stat.po.StatisticsUser;


@Repository
public class StatisticsUserDaoImpl extends SqlSessionDaoSupport implements StatisticsUserDao {

	private final String ns = "com.xj.stat.sqlmap.mapper.statistics.statistics_user.";

	@Override
	public Integer insertStatisticsUser(StatisticsUser statisticsUser) {
		return this.getSqlSession().insert(ns + "insert", statisticsUser);
	}

	@Override
	public List<String> selectUserEmob(BeanUtil beanUtil) {
		return this.getSqlSession().selectList(ns + "selectUserEmob", beanUtil);
	}
}