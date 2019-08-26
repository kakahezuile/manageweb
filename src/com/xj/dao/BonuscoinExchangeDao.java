package com.xj.dao;

import com.xj.bean.BonuscoinExchange;

public interface BonuscoinExchangeDao extends MyBaseDao {
	
	public Integer addBonuscoinExchange(BonuscoinExchange bonuscoinExchange) throws Exception; // 添加比例
	
	public boolean updateBonuscoinExchange(BonuscoinExchange bonuscoinExchange) throws Exception; // 修改比例
	
	public BonuscoinExchange getBonuscoinExchange(Integer communityId) throws Exception; // 根据小区获取比例
}
