package com.xj.stat.dao;

import java.util.List;
import java.util.Set;

import com.xj.stat.po.Users;

/**
 *@author lence
 *@date  2015年7月9日上午12:47:18
 *
 */
public interface UserDao {
	Users getUsers(String str);
	
	List<Users> getUsersByArray(Object[] str,Integer communityId);

	List<Users> findUsersByCommunityId(int  id);

	List<Users> findUsersByCommunityIdWithNull(int communityid);

	List<String> findAllInstallUserIdByCommunity(Integer communityid);

	List<String> findAllRegisService(Integer communityid);

	List<String> getUsersByEmobIdsWithVisitor(Object[] str);

	/**
	 * 获取指定时间内的安装用户量
	 * @param endDay
	 * @param communityId
	 * @return
	 */
	Integer findAllInstallUserIdByCommunityAndTime(String endDay,
			Integer communityId);

	/**
	 * 获取指定小区内的注册量
	 * @param endDay
	 * @param communityId
	 * @return
	 */
	Integer findAllRegisServiceByCommunityAndTime(String endDay,
			Integer communityId);
	
	
	/**
	 * 获取安装活跃用户
	 * @param set
	 * @param communityId
	 * @param endDay
	 * @return
	 */
	List<String> getInstallActiveUsers(Set<String> set,
			Integer communityId, String endDay);

	/**
	 * 获取注册活跃用户
	 * @param set
	 * @param communityId
	 * @param endDay
	 * @return
	 */
	List<String> getRegisterActiveUsers(Set<String> set, Integer communityId, String endDay);

	/**
	 * 根据用户EmobId集合，获取用户
	 * @param emobIds
	 * @return
	 */
	List<com.xj.bean.Users> findUserByIds(List<String> emobIds);

	/**
	 * 获取小区所有的水军
	 * @param communityId
	 * @return
	 */
	Set<String> getAllTest(Integer communityId);

}
