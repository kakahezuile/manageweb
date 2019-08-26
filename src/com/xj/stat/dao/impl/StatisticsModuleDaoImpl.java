package com.xj.stat.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.bean.resource.ClickMobule;
import com.xj.stat.dao.StatisticsModuleDao;
import com.xj.stat.po.StatisticsModule;

@Repository
public class StatisticsModuleDaoImpl extends SqlSessionDaoSupport implements StatisticsModuleDao {

	private String ns = "com.xj.stat.sqlmap.mapper.statistics.statistics_module.";

	@Override
	public Integer insertStatisticsModule(StatisticsModule statisticsModule) {
		return this.getSqlSession().insert(ns + "insert", statisticsModule);
	}

	@Override
	public List<String> selectUserEmob(BeanUtil beanUtil) {
		return this.getSqlSession().selectList(ns + "selectUserEmob", beanUtil);
	}

	@Override
	public List<ClickMobule> statisticsModuleNum(BeanUtil beanUtil) {
		return this.getSqlSession().selectList(ns + "statisticsModuleNum", beanUtil);
	}
}