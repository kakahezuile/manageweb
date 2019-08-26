package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.ActivityMembers;
import com.xj.dao.ActivityGroupDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("activityGroupDao")
public class ActivityGroupDaoImpl extends MyBaseDaoImpl implements ActivityGroupDao {

	@Override
	public Integer addActivityGroup(ActivityMembers activityGroup) throws Exception {
		MyReturnKey myReturnKey = new MyReturnKey();
		this.save(activityGroup, myReturnKey);
		return myReturnKey.getId();
	}

	@Override
	public List<ActivityMembers> getGroupList(String embGroupId , Integer createTime) throws Exception {
		String sql = " SELECT a.activity_member_id , a.emob_group_id , "+
			" a.emob_user_id , a.emob_user_id as emob_id , u.nickname , u.avatar , u.room , a.create_time , a.group_status , a.community_id ,"+
			" u.phone , u.idnumber , u.idcard , u.gender , u.age , u.signature , c.community_name , u.user_floor , u.user_unit"+
			" FROM activity_members a left join users u on a.emob_user_id = u.emob_id " +
			" left join communities c on u.community_id = c.community_id"+
			" WHERE a.group_status = 'block' AND a.emob_group_id = ? ";
		if (createTime != null && createTime != 0) {
			sql += " AND a.create_time > ? ";
			return this.getList(sql, new Object[]{embGroupId, createTime}, ActivityMembers.class);
		}
		
		return this.getList(sql, new Object[]{embGroupId}, ActivityMembers.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateGroup(ActivityMembers activityGroup) throws Exception {
		String sql = " UPDATE activity_members SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(activityGroup);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE activity_member_id = ? ";
		System.out.println(sql);
		list.add(activityGroup.getActivityMemberId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public boolean deleteGroup(Integer activityGroupId) throws Exception {
		ActivityMembers activityGroup = new ActivityMembers();
		activityGroup.setActivityMemberId(activityGroupId);
		Integer result = this.delete(activityGroup);
		return result > 0;
	}

	@Override
	public ActivityMembers isEmpty(String emobId , String emobGroupId) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = this.getJdbcTemplate().getDataSource().getConnection();
			
			preparedStatement = connection.prepareStatement("SELECT activity_member_id FROM activity_members WHERE emob_user_id = ? and emob_group_id = ?");
			preparedStatement.setString(1, emobId);
			preparedStatement.setString(2, emobGroupId);
			
			resultSet = preparedStatement.executeQuery();
			
			ActivityMembers activityMembers = null;
			if (resultSet.next()) {
				activityMembers = new ActivityMembers();
				activityMembers.setActivityMemberId(resultSet.getInt(1));
			}
			
			return activityMembers;
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Integer getMaxCreateTime(String emobGroupId) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = this.getJdbcTemplate().getDataSource().getConnection();
			preparedStatement = connection.prepareStatement("SELECT max(create_time) FROM activity_members WHERE emob_group_id = ?");
			preparedStatement.setString(1, emobGroupId);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return 0;
	}

}
