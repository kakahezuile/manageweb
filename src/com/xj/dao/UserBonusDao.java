package com.xj.dao;

import java.util.List;

import com.xj.bean.BonusAndService;
import com.xj.bean.BonusStatistics;
import com.xj.bean.BonusUser;
import com.xj.bean.BonusUserOrder;
import com.xj.bean.Page;
import com.xj.bean.StatisticsBonusUser;
import com.xj.bean.UserBonus;


public interface UserBonusDao extends MyBaseDao{
	
	public Integer addUserBonus(UserBonus userBonus) throws Exception;
	
	public int addThingUserBonus(List<UserBonus> userBonus) throws Exception;
	
	public List<BonusAndService> getBonusAndService(Integer userId) throws Exception;
	
	public List<BonusAndService> getBonusAndService(String emobId) throws Exception;
	
	public boolean updateUserBonus(UserBonus userBonus) throws Exception;
	
	public List<BonusAndService> getBonusAndService(String emobId , String sort) throws Exception;
	
	public List<UserBonus> getUserBonus(Integer userBonusId) throws Exception;
	/**
	 * 按时间统计帮帮卷
	 */
	public Page<BonusStatistics> statisticsTimeBonus(Integer pageNum,Integer pageSize,String bonusName,String bonusPar,Integer startTime,Integer endTime) throws Exception;

	public List<BonusUserOrder> getBonusUser(Integer createTime) throws Exception;

	public List<BonusUser> getOrdersBonus(String serial)throws Exception;

	public StatisticsBonusUser statisticsBonusUser(Integer createTime)throws Exception;
}
