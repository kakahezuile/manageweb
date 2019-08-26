package com.xj.timer;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class ShopVipcardWeekOrder {

	@Autowired
	private DataSource dataSource;

	public void execute() {
		try (Connection connection = dataSource.getConnection();Statement statement = connection.createStatement();) {
			statement.executeUpdate("UPDATE shop_vipcard SET order_day=order_day-1 WHERE order_day>0");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}