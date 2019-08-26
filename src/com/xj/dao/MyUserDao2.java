package com.xj.dao;

import java.util.List;

import com.xj.bean.Orders;
import com.xj.bean.TryOut;
import com.xj.bean.Users;
import com.xj.bean.XjIp;
import com.xj.stat.po.UserVo;

/**
 * @author lence
 * @date 2015-7-15 下午01:42:03
 * @version 1.0
 */
public interface MyUserDao2 extends MyUserDao {

	/**
	 * 更新用户帮帮币数量
	 * @param emobIdUser
	 * @param bonusCount
	 */
	int updateUserBonusCoin(String emobIdUser, Integer bonusCount);
	
	/**
	 * 获取所有的水军
	 * @return
	 * @throws Exception
	 */
	List<TryOut> getAllTryOut()throws Exception;

	/**
	 * 获取指定时间内的已完成订单
	 * @param communityId
	 * @param shopType
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception 
	 */
	List<Orders> getAllEndedOrdersInTime(Integer communityId, String shopType, Integer startTime, Integer endTime) throws Exception;

	List<Users> findUsersByCommunityIdWithNull(int communityid)throws Exception;

	Integer getCommissionRate(Integer communityId)throws Exception;

	List<XjIp> getuserIP() throws Exception;
	
	UserVo getUserVoByEmobId(String key) throws Exception;

	List<Users> findUsersByCommunityIdWithNull2(int communityid)throws Exception;
}
