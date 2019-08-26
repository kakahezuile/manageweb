package com.xj.stat.service;

import java.util.List;

import com.xj.stat.po.AppDownloadStatistics;

/**
 * APP扫码下载统计服务
 */
public interface AppDownloadStatisticsService {
	
	List<AppDownloadStatistics> day(String communityName, Integer startSecond, Integer endSecond);

}