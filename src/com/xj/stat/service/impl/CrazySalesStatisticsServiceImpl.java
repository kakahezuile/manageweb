package com.xj.stat.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.Page;
import com.xj.stat.dao.CrazySalesStatisticsDao;
import com.xj.stat.po.CrazySalesCommunityStatistics;
import com.xj.stat.po.CrazySalesShopStatistics;
import com.xj.stat.po.CrazySalesStatistics;
import com.xj.stat.service.CrazySalesStatisticsService;

@Service
public class CrazySalesStatisticsServiceImpl implements CrazySalesStatisticsService {

	@Autowired
	private CrazySalesStatisticsDao crazySalesStatisticsDao;

	@Override
	public List<Map<String, Object>> summary() {
		return crazySalesStatisticsDao.summary();
	}

	@Override
	public List<CrazySalesStatistics> daily(Integer begin, Integer end) throws Exception {
		if (null == begin || null == end) {
			throw new RuntimeException("开始日期和结束日期为空!");
		}
		
		return crazySalesStatisticsDao.daily(begin, end);
	}

	@Override
	public Page<CrazySalesShopStatistics> shop(Integer pageNum, Integer pageSize) throws Exception {
		if (null == pageNum || null == pageSize) {
			throw new RuntimeException("分页信息丢失");
		}
		
		return crazySalesStatisticsDao.shop(pageNum, pageSize);
	}

	@Override
	public List<Map<String, Object>> shopDetails(String emobId) {
		if (StringUtils.isBlank(emobId)) {
			throw new RuntimeException("店家环信ID为空!");
		}
		
		return crazySalesStatisticsDao.shopDetails(emobId);
	}

	@Override
	public Page<CrazySalesCommunityStatistics> community(Integer pageNum, Integer pageSize) throws Exception {
		if (null == pageNum || null == pageSize) {
			throw new RuntimeException("分页信息丢失");
		}
		
		return crazySalesStatisticsDao.community(pageNum, pageSize);
	}

	@Override
	public List<Map<String, Object>> communityDetails(Integer communityId) {
		if (null == communityId) {
			throw new RuntimeException("小区ID为空!");
		}
		
		return crazySalesStatisticsDao.communityDetails(communityId);
	}
}