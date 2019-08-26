package com.xj.stat.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.xj.stat.bean.parameter.BeanUtil;
import com.xj.stat.bean.parameter.CurrentTimeRange;
import com.xj.stat.bean.parameter.nearby.CrazySales;
import com.xj.stat.bean.resource.nearby.CrazySalesImg;
import com.xj.stat.bean.resource.nearby.CrazySalesShop;
import com.xj.stat.bean.resource.nearby.CrazySalesStatistics;
import com.xj.stat.dao.NearbyDao;

@Repository
public class NearbyDaoImpl extends SqlSessionDaoSupport implements NearbyDao {

	private String ns = "com.xj.stat.sqlmap.mapper.nearby.crazy_sales.";

	@Override
	public List<CrazySalesShop> selectNearbyCrazySalesShop(CrazySales crazySales) {
		return this.getSqlSession().selectList(ns + "selectNearbyCrazySalesShop", crazySales);
	}

	@Override
	public void delectCrazySales(CrazySales crazySales) {
		this.getSqlSession().update(ns + "delectCrazySales", crazySales);
	}

	@Override
	public CrazySalesImg getCrazySales(CrazySales crazySales) {
		return this.getSqlSession().selectOne(ns + "getCrazySales", crazySales);
	}

	@Override
	public void upCrazySales(CrazySalesImg crazySales) {
		this.getSqlSession().update(ns + "upCrazySales", crazySales);
	}

	@Override
	public void upImgCrazySales(CrazySalesImg crazySales) {
		this.getSqlSession().update(ns + "upImgCrazySales", crazySales);
	}

	@Override
	public List<CrazySalesStatistics> statCrazySales(CurrentTimeRange currentTimeRange) {
		return this.getSqlSession().selectList(ns + "currentTimeRangeStatistics", currentTimeRange);
	}

	@Override
	public CrazySalesStatistics statCrazySales(BeanUtil beanUtil) {
		return this.getSqlSession().selectOne(ns + "statistics", beanUtil);
	}
}