package com.xj.dao;

import java.util.List;

import com.xj.bean.ActivityMembers;



public interface ActivityGroupDao extends MyBaseDao {
	/**
	 * 添加活动参与者
	 * @param activityGroup
	 * @return
	 * @throws Exception
	 */
	public Integer addActivityGroup(ActivityMembers activityGroup) throws Exception;
	/**
	 * 获取活动成员列表  根据环信提供的groupid
	 * @param emobGroupId
	 * @param createTime
	 * @return
	 * @throws Exception
	 */
	public List<ActivityMembers> getGroupList(String emobGroupId , Integer createTime) throws Exception;
	/**
	 * 修改活动成员内容
	 * @param activityGroup
	 * @return
	 * @throws Exception
	 */
	public boolean updateGroup(ActivityMembers activityGroup) throws Exception;
	
	public boolean deleteGroup(Integer activityGroupId) throws Exception;
	
	public ActivityMembers isEmpty(String emobId , String emobGroupId) throws Exception;
	
	public Integer getMaxCreateTime(String emobGroupId) throws Exception;
}
