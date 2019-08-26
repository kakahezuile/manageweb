package com.xj.dao;

import java.util.List;

import com.xj.bean.BlackList;

public interface BlackListDao extends MyBaseDao {
	/**
	 * 添加黑名单
	 * @param blackList
	 * @return
	 * @throws Exception
	 */
	public Integer addBlackList(BlackList blackList) throws Exception;
	
	/**
	 * 根绝小区id  以及用户环信id 、黑名单类型 获取黑名单列表
	 * @param communityId
	 * @param emobIdFrom
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<BlackList> getBlackList(Integer communityId , String emobIdFrom , String status) throws Exception;
	
	/**
	 * 从黑名单中移除
	 * @param blackList
	 * @return
	 * @throws Exception
	 */
	public boolean deleteBlackList(BlackList blackList) throws Exception;
	
	/**
	 * 根据小区id 、 A用户环信id  、B用户环信id 、 黑名单类型 获取 黑名单列表 
	 * @param communityId
	 * @param emobIdFrom
	 * @param emobIdTo
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<BlackList> getBlackList(Integer communityId , String emobIdFrom , String emobIdTo , String status) throws Exception;
}
