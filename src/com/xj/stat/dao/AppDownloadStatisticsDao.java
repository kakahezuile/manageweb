package com.xj.stat.dao;

import java.util.List;

import com.xj.stat.po.AppDownloadStatistics;

/**
 * App扫码下载统计数据访问接口
 * @author wangld
 */
public interface AppDownloadStatisticsDao {

	List<AppDownloadStatistics> day(String communityName, Integer startSecond, Integer endSecond);

}