package com.xj.stat.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.xj.impl.MyBaseDaoImpl;
import com.xj.stat.dao.AppDownloadStatisticsDao;
import com.xj.stat.po.AppDownloadStatistics;
import com.xj.utils.DateUtils;

/**
 * App扫码下载统计数据访问接口实现类
 * @author wangld
 */
@Repository("appDownloadStatisticsDao")
public class AppDownloadStatisticsDaoImpl extends MyBaseDaoImpl implements AppDownloadStatisticsDao {

	private static final Integer onlineSecond = 1433088000;//2015-06-01
	
	@Override
	public List<AppDownloadStatistics> day(String communityName, Integer startSecond, Integer endSecond) {
		startSecond = Long.valueOf(DateUtils.getDayBegin(startSecond * 1000L).getTime() / 1000L).intValue();
		Integer currentSecond = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
		if (startSecond > currentSecond) {
			return new ArrayList<AppDownloadStatistics>(0);
		}
		if (startSecond < onlineSecond) {
			startSecond = onlineSecond;
		}
		
		endSecond = Long.valueOf(DateUtils.getDayEnd(endSecond * 1000L).getTime() / 1000L).intValue();
		if (endSecond > currentSecond) {
			endSecond = currentSecond;
		}
		
		//TRUNCATE((create_time+28800)/86400,0) 将创建时间转换成距离1970-01-01日的天数，比如1970-01-02距离1970-01-01的天数为1
		String sql = "SELECT a.type,a.terrace,a.community_id as communityId,b.community_name as communityName,TRUNCATE((a.create_time+28800)/86400,0) as time,count(1) as result FROM app_download a LEFT JOIN communities b ON a.community_id=b.community_id WHERE b.community_name LIKE ? AND a.create_time>=? AND a.create_time<? GROUP BY a.type,a.terrace,a.community_id,TRUNCATE((a.create_time+28800)/86400,0) ORDER BY a.community_id, time DESC";
		
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<List<AppDownloadStatistics>>(){
			@Override
			public List<AppDownloadStatistics> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<AppDownloadStatistics> results = new ArrayList<AppDownloadStatistics>();
				while(rs.next()){
					AppDownloadStatistics result = new AppDownloadStatistics();
					
					result.setType(rs.getString("type"));
					result.setTerrace(rs.getString("terrace"));
					result.setCommunityId(rs.getInt("communityId"));
					result.setCommunityName(rs.getString("communityName"));
					result.setTime(DateUtils.formatDate(new Date((rs.getInt("time") * 86400 - 28800) * 1000L)));
					result.setResult(rs.getInt("result"));
					
					results.add(result);
				}
				return results;
			}
			
		}, "%" + communityName + "%", startSecond, endSecond);
	}
}