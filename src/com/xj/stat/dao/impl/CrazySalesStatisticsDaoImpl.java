package com.xj.stat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xj.bean.Page;
import com.xj.impl.MyBaseDaoImpl;
import com.xj.stat.dao.CrazySalesStatisticsDao;
import com.xj.stat.po.CrazySalesCommunityStatistics;
import com.xj.stat.po.CrazySalesShopStatistics;
import com.xj.stat.po.CrazySalesStatistics;
import com.xj.utils.DateUtils;

@Repository
public class CrazySalesStatisticsDaoImpl extends MyBaseDaoImpl implements CrazySalesStatisticsDao {

	/**
	 * 周边商家下载量标识
	 */
	private static final Integer crazySaleShopFlag = 100003;
	
	/**
	 * 下载量今日，昨日，本周，上周，本月，上月统计
	 */
	private static final String downloadSql = "SELECT count(1) AS download FROM app_download where community_id=" + crazySaleShopFlag + " and create_time>=? UNION ALL "
		+ "SELECT count(1) AS download FROM app_download where community_id=" + crazySaleShopFlag + " AND create_time>=? AND create_time<? UNION ALL "
		+ "SELECT count(1) AS download FROM app_download where community_id=" + crazySaleShopFlag + " AND create_time>=? UNION ALL "
		+ "SELECT count(1) AS download FROM app_download where community_id=" + crazySaleShopFlag + " AND create_time>=? AND create_time<? UNION ALL "
		+ "SELECT count(1) AS download FROM app_download where community_id=" + crazySaleShopFlag + " AND create_time>=? UNION ALL "
		+ "SELECT count(1) AS download FROM app_download where community_id=" + crazySaleShopFlag + " AND create_time>=? AND create_time<?";
	/**
	 * 注册量今日，昨日，本周，上周，本月，上月统计
	 */
	private static final String registerSql = "SELECT count(1) AS register FROM shops s LEFT JOIN users u ON s.emob_id=u.emob_id WHERE s.sort=13 AND s.status<>'suspend' AND u.create_time>? UNION ALL "
		+ "SELECT count(1) AS register FROM shops s LEFT JOIN users u ON s.emob_id=u.emob_id WHERE s.sort=13 AND s.status<>'suspend' AND u.create_time>=? AND u.create_time<? UNION ALL "
		+ "SELECT count(1) AS register FROM shops s LEFT JOIN users u ON s.emob_id=u.emob_id WHERE s.sort=13 AND s.status<>'suspend' AND u.create_time>? UNION ALL "
		+ "SELECT count(1) AS register FROM shops s LEFT JOIN users u ON s.emob_id=u.emob_id WHERE s.sort=13 AND s.status<>'suspend' AND u.create_time>? AND u.create_time<? UNION ALL "
		+ "SELECT count(1) AS register FROM shops s LEFT JOIN users u ON s.emob_id=u.emob_id WHERE s.sort=13 AND s.status<>'suspend' AND u.create_time>? UNION ALL "
		+ "SELECT count(1) AS register FROM shops s LEFT JOIN users u ON s.emob_id=u.emob_id WHERE s.sort=13 AND s.status<>'suspend' AND u.create_time>=? AND u.create_time<?";
	/**
	 * 发布活动量和出价总额今日，昨日，本周，上周，本月，上月统计
	 */
	private static final String pubAndPriceSql = "SELECT COUNT(1) AS pub,SUM(price) AS money FROM crazy_sales_community WHERE create_time>=? UNION ALL "
		+ "SELECT COUNT(1) AS pub,SUM(price) AS money FROM crazy_sales_community WHERE create_time>=? AND create_time<? UNION ALL "
		+ "SELECT COUNT(1) AS pub,SUM(price) AS money FROM crazy_sales_community WHERE create_time>=? UNION ALL "
		+ "SELECT COUNT(1) AS pub,SUM(price) AS money FROM crazy_sales_community WHERE create_time>=? AND create_time<? UNION ALL "
		+ "SELECT COUNT(1) AS pub,SUM(price) AS money FROM crazy_sales_community WHERE create_time>=? UNION ALL "
		+ "SELECT COUNT(1) AS pub,SUM(price) AS money FROM crazy_sales_community WHERE create_time>=? AND create_time<?";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, Object>> summary() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(6);
		
		Map<String, Object> today = new HashMap<String, Object>();
		today.put("date", "今天");
		result.add(today);
		
		Map<String, Object> yesterday = new HashMap<String, Object>();
		yesterday.put("date", "昨天");
		result.add(yesterday);
		
		Map<String, Object> week = new HashMap<String, Object>();
		week.put("date", "本周");
		result.add(week);
		
		Map<String, Object> lastWeek = new HashMap<String, Object>();
		lastWeek.put("date", "上周");
		result.add(lastWeek);
		
		Map<String, Object> month = new HashMap<String, Object>();
		month.put("date", "本月");
		result.add(month);
		
		Map<String, Object> lastMonth = new HashMap<String, Object>();
		lastMonth.put("date", "上月");
		result.add(lastMonth);
		
		long todayStartTime = DateUtils.getDayBegin().getTime() / 1000L;		// 今天起始时间(秒)
		long yesterdayStartTime = DateUtils.getYesterdayStartTime() / 1000L;	// 昨天起始时间(秒)
		long weekStartTime = DateUtils.getWeekBegin().getTime() / 1000L;		// 本周起始时间(秒)
		long lastWeekStartTime = DateUtils.getLastWeekStartTime() / 1000L;		// 上周起始时间(秒)
		long monthStartTime = DateUtils.getMonthBegin().getTime() / 1000L;		// 本月起始时间(秒)
		long lastMonthStartTime = DateUtils.getLastMonthStartTime() / 1000L;	// 上月起始时间(秒)
		
		Connection connection = null;
		PreparedStatement downloadStatement = null;
		PreparedStatement registerStatement = null;
		PreparedStatement pubAndPriceStatement = null;
		ResultSet downloadResultSet = null;
		ResultSet registerResultSet = null;
		ResultSet pubAndPriceResultSet = null;
		try {
			connection = this.getJdbcTemplate().getDataSource().getConnection();
			
			downloadStatement = setParameter(connection.prepareStatement(downloadSql), todayStartTime, yesterdayStartTime, weekStartTime, lastWeekStartTime, monthStartTime, lastMonthStartTime);
			downloadResultSet = setResult(downloadStatement.executeQuery(), result, "download");
			
			registerStatement = setParameter(connection.prepareStatement(registerSql), todayStartTime, yesterdayStartTime, weekStartTime, lastWeekStartTime, monthStartTime, lastMonthStartTime);
			registerResultSet = setResult(registerStatement.executeQuery(), result, "register");
			
			pubAndPriceStatement = setParameter(connection.prepareStatement(pubAndPriceSql), todayStartTime, yesterdayStartTime, weekStartTime, lastWeekStartTime, monthStartTime, lastMonthStartTime);
			pubAndPriceResultSet = setResult(pubAndPriceStatement.executeQuery(), result, "pub", "money");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(downloadResultSet);
			close(registerResultSet);
			close(pubAndPriceResultSet);
			close(downloadStatement);
			close(registerStatement);
			close(pubAndPriceStatement);
			close(connection);
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CrazySalesStatistics> daily(Integer begin, Integer end) throws Exception {
		if (begin > end) {
			Integer temp = end;
			end = begin;
			begin = temp;
		}
		
		String beginDate = DateUtils.formatDate(new Date(begin * 1000L));
		String endDate = DateUtils.formatDate(new Date(end * 1000L));
		
		return this.getList("SELECT * FROM crazy_sales_statistics WHERE date>=? AND date <=?", new Object[] {beginDate, endDate}, CrazySalesStatistics.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<CrazySalesShopStatistics> shop(Integer pageNum, Integer pageSize) throws Exception {
		String sql = "SELECT a.shop_emob_id,a.community,a.activity,a.price,b.sales,b.used,sp.shop_name FROM "
				+ "(SELECT t.emob_id AS shop_emob_id,count(1) as community,SUM(t.total) as activity,SUM(t.price) as price FROM "
				+ "(SELECT s.emob_id,c.community_id,count(1) as total,SUM(c.price) as price FROM crazy_sales s LEFT JOIN crazy_sales_community c ON s.crazy_sales_id=c.crazy_sales_id GROUP BY s.emob_id,c.community_id) t "
				+ "GROUP BY t.emob_id) a LEFT JOIN "
				+ "(SELECT u.shop_emob_id,COUNT(1) as sales,SUM(CASE WHEN u.status='used' THEN 1 ELSE 0 END) AS used FROM crazy_sales_user u GROUP BY u.shop_emob_id ) b ON a.shop_emob_id=b.shop_emob_id "
				+ "LEFT JOIN shops sp ON a.shop_emob_id=sp.emob_id";
		
		return this.getData4Page(sql, null, pageNum, pageSize, 10, CrazySalesShopStatistics.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, Object>> shopDetails(String emobId) {
		String sql = "SELECT s.title,cs.community_name,c.price,s.total,count(1) as sales,SUM(CASE WHEN u.status='used' THEN 1 ELSE 0 END) AS used "
			+ "FROM crazy_sales s LEFT JOIN crazy_sales_community c ON s.crazy_sales_id=c.crazy_sales_id LEFT JOIN communities cs ON c.community_id=cs.community_id LEFT JOIN crazy_sales_user u ON s.crazy_sales_id=u.crazy_sales_id "
			+ "WHERE s.emob_id=? GROUP BY c.community_id,c.price ORDER BY c.price DESC";
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			connection = this.getJdbcTemplate().getDataSource().getConnection();
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, emobId);
			
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Map<String, Object> data = new HashMap<String, Object>();
				
				data.put("communityName", resultSet.getString("community_name"));
				data.put("title", resultSet.getString("title"));
				data.put("price", resultSet.getDouble("price"));
				data.put("total", resultSet.getInt("total"));
				data.put("sales", resultSet.getInt("sales"));
				data.put("used", resultSet.getInt("used"));
				
				result.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<CrazySalesCommunityStatistics> community(Integer pageNum, Integer pageSize) throws Exception {
		String sql = "SELECT cs.community_id,cs.community_name,shops,activity,price,sales,used FROM communities cs LEFT JOIN "
			+ "(SELECT b.community_id,COUNT(1) as shops,SUM(b.total) as activity,SUM(b.price) as price,SUM(b.sales) as sales,SUM(b.used) as used FROM "
			+ "(SELECT s.emob_id,a.community_id,COUNT(1) as total,SUM(a.price) as price,SUM(a.sales) as sales,SUM(a.used) as used FROM crazy_sales s LEFT JOIN "
			+ "(SELECT c.crazy_sales_id,c.community_id,c.price,count(1) as sales,SUM(CASE WHEN u.status='used' THEN 1 ELSE 0 END) AS used FROM crazy_sales_community c LEFT JOIN crazy_sales_user u ON c.crazy_sales_id=u.crazy_sales_id AND c.community_id=u.community_id "
			+ "GROUP BY c.crazy_sales_id,c.community_id ORDER BY c.community_id) a ON s.crazy_sales_id=a.crazy_sales_id GROUP BY s.emob_id,a.community_id) b GROUP BY b.community_id) t ON cs.community_id=t.community_id ORDER BY t.price DESC";
		
		return this.getData4Page(sql, null, pageNum, pageSize, 10, CrazySalesCommunityStatistics.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, Object>> communityDetails(Integer communityId) {
		String sql = "SELECT a.crazy_sales_id,a.shop_name,a.title,a.price,a.total,COUNT(u.code) AS sales,SUM(CASE WHEN u.status='used' THEN 1 ELSE 0 END) AS used FROM "
			+ "(SELECT s.crazy_sales_id,sh.shop_name,s.title,c.price,s.total FROM crazy_sales s LEFT JOIN shops sh ON s.emob_id=sh.emob_id LEFT JOIN crazy_sales_community c ON s.crazy_sales_id=c.crazy_sales_id WHERE c.community_id=?) a "
			+ "LEFT JOIN crazy_sales_user u ON a.crazy_sales_id=u.crazy_sales_id GROUP BY a.crazy_sales_id";
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			connection = this.getJdbcTemplate().getDataSource().getConnection();
			
			statement = connection.prepareStatement(sql);
			statement.setInt(1, communityId);
			
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Map<String, Object> data = new HashMap<String, Object>();
				
				data.put("crazySalesId", resultSet.getInt("crazy_sales_id"));
				data.put("shopName", resultSet.getString("shop_name"));
				data.put("title", resultSet.getString("title"));
				data.put("price", resultSet.getDouble("price"));
				data.put("total", resultSet.getInt("total"));
				data.put("sales", resultSet.getInt("sales"));
				data.put("used", resultSet.getInt("used"));
				
				result.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
		
		return result;
	}

	private ResultSet setResult(ResultSet resultSet, List<Map<String, Object>> result, String... fields) throws SQLException {
		for (int i = 0; i < result.size(); i++) {
			resultSet.next();
			
			Map<String, Object> map = result.get(i);
			for (int j = 0; j < fields.length; j++) {
				String field = fields[j];
				Object value = resultSet.getObject(field);
				map.put(field, null == value ? 0 : value);
			}
		}
		
		return resultSet;
	}

	private PreparedStatement setParameter(PreparedStatement preparedStatement, long todayStartTime, long yesterdayStartTime, long weekStartTime, long lastWeekStartTime, long monthStartTime, long lastMonthStartTime) throws SQLException {
		preparedStatement.setLong(1, todayStartTime);
		preparedStatement.setLong(2, yesterdayStartTime);
		preparedStatement.setLong(3, todayStartTime);
		preparedStatement.setLong(4, weekStartTime);
		preparedStatement.setLong(5, lastWeekStartTime);
		preparedStatement.setLong(6, weekStartTime);
		preparedStatement.setLong(7, monthStartTime);
		preparedStatement.setLong(8, lastMonthStartTime);
		preparedStatement.setLong(9, monthStartTime);
		
		return preparedStatement;
	}

	private void close(Connection connection) {
		if (null != connection) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void close(PreparedStatement preparedStatement) {
		if (null != preparedStatement) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void close(ResultSet resultSet) {
		if (null != resultSet) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}