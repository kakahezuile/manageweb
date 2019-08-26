package com.xj.timer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xj.utils.DateUtils;

public class CrazySalesStatistics {

	private static Logger logger = Logger.getLogger(CrazySalesStatistics.class);

	private static final Integer crazySaleShopFlag = 100003;

	@Autowired
	private DataSource dataSource;
	
	public void execute() {
		logger.info("抢购活动统计任务开始---------" + System.currentTimeMillis());
		
		String downloadSql = "SELECT count(1) AS download FROM app_download where community_id=" + crazySaleShopFlag + " AND create_time>=? AND create_time<?";
		String registerSql = "SELECT count(1) AS register FROM shops s LEFT JOIN users u ON s.emob_id=u.emob_id WHERE s.sort=13 AND s.status<>'suspend' AND u.create_time>=? AND u.create_time<?";
		String pubAndPriceSql = "SELECT COUNT(1) AS pub,SUM(price) AS money FROM crazy_sales_community WHERE create_time>=? AND create_time<?";
		
		long todayStartTime = DateUtils.getDayBegin().getTime() / 1000L;		// 今天起始时间(秒)
		long yesterdayStartMilliTime = DateUtils.getYesterdayStartTime();	// 昨天起始时间(毫秒)
		long yesterdayStartTime = yesterdayStartMilliTime / 1000L;			// 昨天起始时间(秒)
		
		Connection connection = null;
		PreparedStatement downloadStatement = null;
		PreparedStatement registerStatement = null;
		PreparedStatement pubAndPriceStatement = null;
		PreparedStatement saveStatement = null;
		ResultSet downloadResultSet = null;
		ResultSet registerResultSet = null;
		ResultSet pubAndPriceResultSet = null;
		try {
			connection = dataSource.getConnection();
			
			downloadStatement = connection.prepareStatement(downloadSql);
			downloadStatement.setLong(1, yesterdayStartTime);
			downloadStatement.setLong(2, todayStartTime);
			
			downloadResultSet = downloadStatement.executeQuery();
			downloadResultSet.next();
			int download = downloadResultSet.getInt("download");
			
			registerStatement = connection.prepareStatement(registerSql);
			registerStatement.setLong(1, yesterdayStartTime);
			registerStatement.setLong(2, todayStartTime);
			
			registerResultSet = registerStatement.executeQuery();
			registerResultSet.next();
			int register = registerResultSet.getInt("register");
			
			pubAndPriceStatement = connection.prepareStatement(pubAndPriceSql);
			pubAndPriceStatement.setLong(1, yesterdayStartTime);
			pubAndPriceStatement.setLong(2, todayStartTime);
			
			pubAndPriceResultSet = pubAndPriceStatement.executeQuery();
			pubAndPriceResultSet.next();
			int pub = pubAndPriceResultSet.getInt("pub");
			double money = pubAndPriceResultSet.getDouble("money");
			
			saveStatement = connection.prepareStatement("INSERT INTO crazy_sales_statistics(date, download, register, pub, money) values (?, ?, ?, ?, ?)");
			saveStatement.setString(1, DateUtils.formatDate(new Date(yesterdayStartMilliTime)));
			saveStatement.setInt(2, download);
			saveStatement.setInt(3, register);
			saveStatement.setInt(4, pub);
			saveStatement.setDouble(5, money);
			
			saveStatement.execute();
		} catch (SQLException e) {
			logger.error("抢购活动统计任务异常", e);
		} finally {
			close(downloadResultSet);
			close(registerResultSet);
			close(pubAndPriceResultSet);
			close(saveStatement);
			close(downloadStatement);
			close(registerStatement);
			close(pubAndPriceStatement);
			close(connection);
		}
		
		logger.info("抢购活动统计任务结束");
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