package com.xj.stat.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.stat.dao.AppDownloadStatisticsDao;
import com.xj.stat.po.AppDownloadStatistics;
import com.xj.stat.service.AppDownloadStatisticsService;

/**
 * APP扫码下载统计服务
 */
@Service("appDownloadStatisticsService")
public class AppDownloadStatisticsServiceImpl implements AppDownloadStatisticsService {

	@Autowired
	public AppDownloadStatisticsDao appDownloadStatisticsDao;
	
	@Override
	public List<AppDownloadStatistics> day(String communityName, Integer startSecond, Integer endSecond) {
		if (StringUtils.isBlank(communityName) || null == startSecond || null == endSecond) {
			throw new RuntimeException("请提供小区名称,开始时间,结束时间!");
		}
		if (startSecond > endSecond) {
			throw new RuntimeException("开始时间不能大于结束时间!");
		}
		
		return appDownloadStatisticsDao.day(communityName, startSecond, endSecond);
	}
}