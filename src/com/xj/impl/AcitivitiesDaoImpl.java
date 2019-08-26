package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.xj.bean.Activities;
import com.xj.bean.ActivitiesDate;
import com.xj.bean.ActivitiesSimple;
import com.xj.bean.ActivitiesTop;
import com.xj.bean.ActivitiesUserList;
import com.xj.bean.ActivityPhoto;
import com.xj.bean.ActivityUpdateBean;
import com.xj.bean.AddActivity;
import com.xj.bean.Page;
import com.xj.bean.Users;
import com.xj.bean.WebImActivies;
import com.xj.dao.ActivitiesDao;
import com.xj.httpclient.utils.DaoUtils;

@Repository("activitiesDao")
public class AcitivitiesDaoImpl extends MyBaseDaoImpl implements ActivitiesDao {

	private Logger logger = Logger.getLogger(AcitivitiesDaoImpl.class);
	
	public boolean modifyActivityState(Integer activityId, ActivityUpdateBean activityUpdateBean) {
		boolean isUpdadeSucess = false;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = getJdbcTemplate().getDataSource().getConnection();
			preparedStatement = con.prepareStatement("update activities set status = ? where activity_id = ?", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, activityUpdateBean.getStatus());
			preparedStatement.setInt(2, activityId);

			int count = preparedStatement.executeUpdate();
			if (count > 0) {
				isUpdadeSucess = true;
			}
		} catch (Exception e) {
			logger.error(this, e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				logger.error(this, e);
			}
		}
		return isUpdadeSucess;
	}

	public int addAcitivity(Activities activitiesBean) {
		int id = 0;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "insert into activities(activity_title,activity_detail,emob_id_owner,place,create_time,community_id,activity_time,status,emob_group_id,type) values(?,?,?,?,?,?,?,'ongoing',?,?)";
		try {
			con = getJdbcTemplate().getDataSource().getConnection();
			preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, activitiesBean.getActivityTitle());
			preparedStatement.setString(2, activitiesBean.getActivityDetail());
			preparedStatement.setString(3, activitiesBean.getEmobIdOwner());
			preparedStatement.setString(4, activitiesBean.getPlace());
			preparedStatement.setInt(5,
					(int) (System.currentTimeMillis() / 1000L));
			preparedStatement.setInt(6, activitiesBean.getCommunityId());
			preparedStatement.setInt(7, activitiesBean.getActivityTime());
			preparedStatement.setString(8, activitiesBean.getEmobGroupId());
			preparedStatement.setString(9, activitiesBean.getType());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
		} catch (Exception e) {
			logger.error(this, e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				logger.error(this, e);
			}
		}

		return id;
	}

	@Override
	public List<Activities> getListByEmobId(String emobGroupId, String ownerId)
			throws Exception {
		String sql = "SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner , "
				+ " a.status , a.place , a.create_time , a.community_id , a.emob_group_id , a.activity_time , 0 as activity_user_sum  from activities a WHERE a.emob_group_id = ? and a.emob_id_owner = ? ";
		return this.getList(sql, new String[] { emobGroupId, ownerId },
				Activities.class);
	}

	@Override
	public Page<Activities> getListWithPage(Integer communityId,
			String ownerId, Integer pageNum, Integer pageSize, Integer nvm)
			throws Exception {
		String sql = "SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner ,a.bz_praise_sum , a.type ,"
				+ " a.status , a.place , a.create_time , a.community_id , a.emob_group_id , a.activity_time "
				+ " ,count(ag.emob_user_id) as activity_user_sum "
				+ " from activities a left join activity_members ag on ag.emob_group_id = a.emob_group_id "
				+ " WHERE a.community_id = ? AND a.type = 'activity' AND ( a.status = 'ongoing' "
				+ " or (a.status = 'review' and a.emob_id_owner = ?) ) "
				+ " group by a.activity_id order by  a.status , a.activity_id desc";

		Page<Activities> page = this.getData4Page(sql, new Object[] {
				communityId, ownerId }, pageNum, pageSize, nvm,
				Activities.class);
		List<Activities> list = page.getPageData();
		if (CollectionUtils.isEmpty(list)) {
			return page;
		}

		int size = list.size();
		Map<Integer, Activities> map = new HashMap<Integer, Activities>();
		StringBuilder builder = new StringBuilder(
				"SELECT * FROM activity_photo WHERE activity_id IN (");
		for (int i = 0; i < size; i++) {
			Activities activities = list.get(i);
			Integer activityId = activities.getActivityId();
			map.put(activityId, activities);
			builder.append(activityId).append(",");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(")");

		List<ActivityPhoto> photoes = getList(builder.toString(), null,
				ActivityPhoto.class);
		for (int i = 0; i < photoes.size(); i++) {
			ActivityPhoto photo = photoes.get(i);
			Activities activities = map.get(photo.getActivityId());
			List<ActivityPhoto> photoList = activities.getPhotoList();
			if (null == photoList) {
				photoList = new ArrayList<ActivityPhoto>();
				activities.setPhotoList(photoList);
			}
			photoList.add(photo);
		}

		return page;
	}

	@Override
	public Page<ActivitiesSimple> getListWithPageSimple(Integer communityId,
			 Integer pageNum, Integer pageSize, Integer nvm)
			throws Exception {
		String sql = "SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner ,u.nickname,"
				+ "a.create_time , a.activity_time, a.community_id , a.emob_group_id ,"
				+ " COUNT(ag.emob_user_id) AS activity_user_sum"
				+ " FROM activities a"
				+ " LEFT JOIN activity_members ag ON ag.emob_group_id = a.emob_group_id"
				+ " LEFT JOIN users u ON a.emob_id_owner=u.emob_id"
				+ " WHERE a.community_id = ?"
				+ " GROUP BY a.activity_id ORDER BY a.activity_id DESC";

		Page<ActivitiesSimple> page = this.getData4Page(sql, new Object[] {
				communityId }, pageNum, pageSize, nvm,
				ActivitiesSimple.class);
		List<ActivitiesSimple> list = page.getPageData();
		if (CollectionUtils.isEmpty(list)) {
			return page;
		}

		int size = list.size();
		Map<Integer, ActivitiesSimple> map = new HashMap<Integer, ActivitiesSimple>();
		StringBuilder builder = new StringBuilder(
				"SELECT * FROM activity_photo WHERE activity_id IN (");
		for (int i = 0; i < size; i++) {
			ActivitiesSimple activities = list.get(i);
			Integer activityId = activities.getActivityId();
			map.put(activityId, activities);
			builder.append(activityId).append(",");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(")");

		List<ActivityPhoto> photoes = getList(builder.toString(), null,
				ActivityPhoto.class);
		for (int i = 0; i < photoes.size(); i++) {
			ActivityPhoto photo = photoes.get(i);
			ActivitiesSimple activities = map.get(photo.getActivityId());
			List<ActivityPhoto> photoList = activities.getPhotoList();
			if (null == photoList) {
				photoList = new ArrayList<ActivityPhoto>();
				activities.setPhotoList(photoList);
			}
			photoList.add(photo);
		}

		return page;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateActivies(Activities activities) throws Exception {
		String sql = " UPDATE activities SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(activities);
		if (resultObject != null && resultObject[1] != null
				&& ((List<Object>) resultObject[1]).size() > 0) {
			list = (List<Object>) resultObject[1];
			sql += (String) resultObject[0];
		} else {
			return false;
		}
		sql += " WHERE activity_id = ? ";
		list.add(activities.getActivityId());
		return this.updateData(sql, list, null) > 0;
	}

	@Override
	public Page<Activities> getListWithPageByCommunityId(Integer communityId,
			String status, Integer pageNum, Integer pageSize, Integer nvm)
			throws Exception {
		String sql = "";
		if ("-1".equals(status)) { // 待审核的
			sql = "SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner , "
					+ " a.status , a.place , a.create_time , a.community_id , a.emob_group_id , a.activity_time , 0 as activity_user_sum  from activities WHERE status = 'ongoing'";
		} else if ("0".equals(status)) { // 其他的
			sql = "";
		} else if ("".equals(status)) { // 所有的
			sql = "SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner , "
					+ " a.status , a.place , a.create_time , a.community_id , a.emob_group_id , a.activity_time , 0 as activity_user_sum  FROM activities WHERE community_id = ? ";
		}
		List<Object> params = new ArrayList<Object>();
		params.add(communityId);
		Page<Activities> page = this.getData4Page(sql, params.toArray(),
				pageNum, pageSize, nvm, Activities.class);
		return page;
	}

	@Override
	public Page<WebImActivies> getListByWebIm(Integer communityId,
			String status, Integer pageNum, Integer pageSize, Integer nvm)
			throws Exception {
		String sql = " SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner , "
				+ " a.emob_group_id , a.place , a.status , a.create_time , a.community_id , "
				+ " u.nickname as nick_name , u.phone , u.room "
				+ " FROM activities a left join users u on a.emob_id_owner = u.emob_id WHERE a.community_id = ? and a.status != 'rejected' and a.type = 'activity' ";

		return this.getData4Page(sql, new Object[] { communityId }, pageNum,
				pageSize, nvm, WebImActivies.class);
	}

	@Override
	public Page<WebImActivies> getListInWebImByText(String text,
			Integer communityId, String status, Integer pageNum,
			Integer pageSize, Integer nvm) throws Exception {
		String sql = " SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner , "
				+ " a.emob_group_id , a.place , a.status , a.create_time , a.community_id , "
				+ " u.nickname as nick_name , u.phone , u.room "
				+ " FROM activities a left join users u on a.emob_id_owner = u.emob_id WHERE a.community_id = ? and a.status = 'activity' and a.activity_title like '%"
				+ text + "%' and a.status != 'rejected' ";

		return this.getData4Page(sql, new Object[] { communityId }, pageNum,
				pageSize, nvm, WebImActivies.class);
	}

	@Override
	public Page<Activities> getActivitiesByStatusAndText(String text,
			Integer communityId, String status, String type, Integer pageNum,
			Integer pageSize, Integer nvm) throws Exception {
		String sql = " SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner , "
				+ " a.status , a.place , a.create_time , a.community_id , a.emob_group_id , a.activity_time , count(ag.activity_member_id) as activity_user_sum FROM activities a left join activity_members ag on a.emob_group_id = ag.emob_group_id WHERE a.community_id = ? AND a.status = ? AND a.type = ? AND a.activity_title like '%"
				+ text + "%' group by a.activity_id";
		// sql += " order by activity_user_sum desc ";
		Page<Activities> page = this.getData4Page(sql, new Object[] {
				communityId, status, type }, pageNum, pageSize, nvm,
				Activities.class);
		return page;
	}
	
	@Override
	public Page<ActivitiesSimple> getActivitiesByStatusAndTextSimple(String text,
			Integer communityId, String status, String type, Integer pageNum,
			Integer pageSize, Integer nvm) throws Exception {
		String sql = "SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner ,u.nickname,"
				+ "a.create_time , a.activity_time, a.community_id , a.emob_group_id ,"
				+ " COUNT(ag.emob_user_id) AS activity_user_sum"
				+ " FROM activities a"
				+ " LEFT JOIN activity_members ag ON ag.emob_group_id = a.emob_group_id"
				+ " LEFT JOIN users u ON a.emob_id_owner=u.emob_id"
				+" WHERE a.community_id = ? AND a.status = ? AND a.type = ? AND a.activity_title like '%"
				+ text + "%' group by a.activity_id";
		// sql += " order by activity_user_sum desc ";
		Page<ActivitiesSimple> page = this.getData4Page(sql, new Object[] {
				communityId, status, type }, pageNum, pageSize, nvm,
				ActivitiesSimple.class);
		return page;
	}

	@Override
	public List<Activities> getListByGroupId(String groupId) throws Exception {
		String sql = "SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner , "
				+ " a.status , a.place , a.create_time , a.community_id , a.emob_group_id , a.activity_time , 0 as activity_user_sum  from activities a where a.emob_group_id = ? ";
		return this.getList(sql, new Object[] { groupId }, Activities.class);
	}

	/**
	 * 活动统计量头部
	 */

	@Override
	public ActivitiesTop getActivitiesTop(Integer communityId,
			Integer startTime, Integer endTime) throws Exception {

		String sql = "SELECT a.create_time ,COUNT(CASE "
				+ " WHEN  t.id IS NOT NULL  THEN "
				+ " a.emob_user_id "
				+ " ELSE "
				+ " NULL "
				+ " END "
				+ " ) AS add_test_activity, "
				+ " COUNT( "
				+ " CASE "
				+ " WHEN  t.id IS NULL  THEN "
				+ " a.emob_user_id "
				+ " ELSE "
				+ " NULL "
				+ " END "
				+ " ) AS add_activity , (SELECT COUNT(aa.activity_id) FROM activities aa WHERE aa.community_id=? AND"
				+ " 	 aa.create_time > ? "
				+ "	AND aa.create_time < ? AND aa.type='activity') AS activity_num"
				+ " FROM "
				+ " ( "
				+ " SELECT am.create_time,"
				+ " am.emob_user_id "
				+ " FROM "
				+ " activity_members am LEFT JOIN activities ac ON am.emob_group_id=ac.emob_group_id "
				+ " WHERE " + " am.community_id = ? "
				+ " AND am.create_time > ? " + " AND am.create_time < ? "
				+ " AND ac.type='activity' " + " GROUP BY "
				+ " am.emob_user_id " + " ) a "
				+ " LEFT JOIN try_out t ON t.emob_id = a.emob_user_id ";

		// String sql = "SELECT "
		// + " a.activity_time,"
		// + " SUM(a.activites_num) AS activites_num,"
		// + " SUM(a.add_activity) AS add_activity"
		// + " FROM "
		// + " ( "
		// + " SELECT "
		// + " DAY ( "
		// + " from_unixtime(am.create_time) "
		// + " ) AS b, "
		// + " am.create_time AS activity_time, "
		// + " COUNT(am.activity_member_id) AS activites_num, "
		// + " ( "
		// + " SELECT "
		// + " COUNT(ame.activity_member_id) "
		// + " FROM "
		// + " activity_members ame "
		// + " WHERE "
		// + " ame.emob_group_id = a.emob_group_id "
		// + " ) AS add_activity "
		// + " FROM "
		// + " activity_members am "
		// + " LEFT JOIN activities a ON a.emob_group_id = am.emob_group_id "
		// + " WHERE  am.community_id =? "
		// + " AND am.create_time >= ?  AND am.create_time <= ? "
		// + " GROUP BY day(from_unixtime(am.create_time)),a.activity_id) a ";

		List<ActivitiesTop> list = this.getList(sql, new Object[] {
				communityId, startTime, endTime, communityId, startTime,
				endTime }, ActivitiesTop.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 活动统计列表
	 */
	@Override
	public List<ActivitiesTop> getActivitiesList(Integer communityId,
			Integer startTime, Integer endTime, List<Integer> sqlTime)
			throws Exception {

		// String sql =
		// "SELECT  am.create_time AS activity_time,COUNT(am.activity_member_id) AS activites_num,(SELECT COUNT( ame.activity_member_id) FROM activity_members ame"
		// +
		// " WHERE ame.emob_group_id=a.emob_group_id) AS add_activity "
		// +
		// " FROM activity_members am LEFT JOIN activities a ON a.emob_group_id=am.emob_group_id WHERE am.community_id=? AND am.create_time>=? AND am.create_time<=? "
		// + " GROUP BY day(from_unixtime(am.create_time))";
		List<ActivitiesTop> list2 = new ArrayList<ActivitiesTop>();
		for (int i = 0; i < sqlTime.size(); i++) {
			String sql = "SELECT ? as create_time ,COUNT(CASE "
					+ " WHEN  t.id IS NOT NULL  THEN "
					+ " a.emob_user_id "
					+ " ELSE "
					+ " NULL "
					+ " END "
					+ " ) AS add_test_activity, "
					+ " COUNT( "
					+ " CASE "
					+ " WHEN  t.id IS NULL  THEN "
					+ " a.emob_user_id "
					+ " ELSE "
					+ " NULL "
					+ " END "
					+ " ) AS add_activity , (SELECT COUNT(aa.activity_id) FROM activities aa WHERE aa.community_id=? AND"
					+ " 	 aa.create_time > ? "
					+ "	AND aa.create_time < ? AND aa.type='activity') AS activity_num"
					+ " FROM "
					+ " ( "
					+ " SELECT am.create_time,"
					+ " am.emob_user_id "
					+ " FROM "
					+ " activity_members am LEFT JOIN activities ac ON am.emob_group_id=ac.emob_group_id "
					+ " WHERE " + " am.community_id = ? "
					+ " AND am.create_time > ? " + " AND am.create_time < ? "
					+ " AND ac.type='activity' " + " GROUP BY "
					+ " am.emob_user_id " + " ) a "
					+ " LEFT JOIN try_out t ON t.emob_id = a.emob_user_id ";

			List<ActivitiesTop> list = this.getList(sql,
					new Object[] { sqlTime.get(i), communityId, sqlTime.get(i),
							sqlTime.get(i) + (24 * 60 * 60), communityId,
							sqlTime.get(i), sqlTime.get(i) + (24 * 60 * 60) },
					ActivitiesTop.class);
			list2.add(list.get(0));
		}
		// String sql =
		// "SELECT a.activity_time,SUM(a.activites_num) AS activites_num,"
		// + " SUM(a.add_activity) AS add_activity "
		// +
		// " FROM (SELECT 	DAY (from_unixtime(am.create_time)) AS b, am.create_time AS activity_time,COUNT(am.activity_member_id) AS activites_num,"
		// +
		// "(SELECT COUNT( ame.activity_member_id) FROM activity_members ame  WHERE ame.emob_group_id=a.emob_group_id) AS add_activity "
		// +
		// " FROM activity_members am LEFT JOIN activities a ON a.emob_group_id=am.emob_group_id  WHERE am.community_id=? AND am.create_time>=? AND am.create_time<=? "
		// +
		// " GROUP BY day(from_unixtime(am.create_time)),a.activity_id) a GROUP BY a.b";
		// List<ActivitiesTop> list = this.getList(sql, new Object[] {
		// communityId, startTime, endTime,communityId, startTime, endTime },
		// ActivitiesTop.class);
		return list2;
	}

	@Override
	public Page<ActivitiesDate> getActivitiesDateList(Integer communityId,
			Integer pageNum, Integer pageSize, Integer startTime,
			Integer endTime) throws Exception {

		String sql = "SELECT a.emob_group_id,a.activity_id AS activities_id, am.create_time AS activity_time,a.activity_title,COUNT(am.activity_member_id) AS activites_num,"
				+ "(SELECT COUNT( ame.activity_member_id) FROM activity_members ame WHERE ame.emob_group_id=a.emob_group_id) "
				+ " add_activity FROM activity_members am LEFT JOIN activities a ON a.emob_group_id=am.emob_group_id WHERE  am.community_id=? "
				+ " AND am.create_time>=? AND am.create_time<=? GROUP BY a.activity_id ";
		Page<ActivitiesDate> page = this.getData4Page(sql, new Object[] {
				communityId, startTime, endTime }, pageNum, pageSize, 10,
				ActivitiesDate.class);
		return page;
	}

	@Override
	public Page<ActivitiesTop> getActivitiesDetailList(Integer activitiesId,
			Integer pageNum, Integer pageSize, Integer startTime,
			Integer endTime) throws Exception {
		String sql = "SELECT am.create_time AS activity_time,COUNT(am.activity_member_id) AS activites_num,(SELECT COUNT( ame.activity_member_id) FROM activity_members ame WHERE ame.emob_group_id=a.emob_group_id) AS add_activity"
				+ " FROM activity_members am LEFT JOIN activities a ON a.emob_group_id=am.emob_group_id WHERE am.create_time>=? AND am.create_time<=? AND a.activity_id=? "
				+ " GROUP BY day(from_unixtime(am.create_time))";
		Page<ActivitiesTop> page = this.getData4Page(sql, new Object[] {
				startTime, endTime, activitiesId }, pageNum, pageSize, 10,
				ActivitiesTop.class);
		return page;
	}

	@Override
	public Page<WebImActivies> getActivitiesText(String text,
			Integer communityId, String status, Integer pageNum,
			Integer pageSize, Integer nvm) throws Exception {
		String sql = " SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner , "
				+ " a.emob_group_id , a.place , a.status , a.create_time , a.community_id , "
				+ " u.nickname as nick_name , u.phone , u.room "
				+ " FROM activities a left join users u on a.emob_id_owner = u.emob_id WHERE a.community_id = ? and a.activity_title like '%"
				+ text + "%' and a.status =? ORDER BY a.activity_id DESC ";

		Page<WebImActivies> page = this.getData4Page(sql, new Object[] {
				communityId, status }, pageNum, pageSize, nvm,
				WebImActivies.class);
		List<WebImActivies> listWe = page.getPageData();
		List<WebImActivies> listWe2 = new ArrayList<WebImActivies>();
		for (WebImActivies webImActivies : listWe) {
			String sql2 = "SELECT * FROM activity_photo WHERE activity_id=?";
			List<ActivityPhoto> list = this.getList(sql2,
					new Object[] { webImActivies.getActivityId() },
					ActivityPhoto.class);
			webImActivies.setList(list);
			listWe2.add(webImActivies);
		}
		page.setPageData(listWe2);
		return page;
	}

	/**
	 * 活动详情
	 */
	@Override
	public WebImActivies getActivitiesDetail(Integer activitiesId)
			throws Exception {
		String sql = " SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner , "
				+ " a.emob_group_id , a.place , a.status , a.create_time , a.community_id , "
				+ " u.nickname as nick_name , u.phone , u.room "
				+ " FROM activities a left join users u on a.emob_id_owner = u.emob_id WHERE a.activity_id=?";

		List<WebImActivies> listD = this.getList(sql,
				new Object[] { activitiesId }, WebImActivies.class);

		if (listD.size() != 0) {

			WebImActivies webImActivies = listD.get(0);
			String sql2 = "SELECT * FROM activity_photo WHERE activity_id=?";
			List<ActivityPhoto> list = this.getList(sql2,
					new Object[] { webImActivies.getActivityId() },
					ActivityPhoto.class);
			webImActivies.setList(list);
			return webImActivies;
		}

		return null;
	}

	@Override
	public List<Users> getListUser(String emobGroupId) throws Exception {
		String sql = " SELECT u.user_id,u.username,u.emob_id,u.password,u.nickname,u.phone,u.age,u.avatar,u.salt,u.gender,u.idcard,u.client_id,u.equipment,"
				+ "u.idnumber,u.status,u.room,u.create_time,u.signature,u.role,u.community_id,u.user_floor,u.user_unit FROM activity_members a,users u WHERE a.emob_user_id=u.emob_id AND a.emob_group_id=?";
		return this.getList(sql, new Object[] { emobGroupId }, Users.class);
	}

	@Override
	public AddActivity getAddActivity(Integer communityId, Integer startTime,
			Integer endTime) throws Exception {

		String sql = "SELECT COUNT(a.activity_id) AS acitvity_num,a.create_time FROM activities a WHERE a.community_id=? AND a.create_time>? AND a.create_time<? ";

		List<AddActivity> list = this.getList(sql, new Object[] { communityId,
				startTime, endTime }, AddActivity.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<ActivitiesUserList> newActivitiesListDetail(String emobGroupId,
			Integer startTime, Integer endTime) throws Exception {

		String sql = "SELECT u.user_id,u.nickname,u.username,am.create_time FROM activity_members am "
				+ " LEFT JOIN users u ON u.emob_id=am.emob_user_id WHERE am.emob_group_id=? AND am.create_time>? AND am.create_time<? ";
		List<ActivitiesUserList> list = this.getList(sql, new Object[] {
				emobGroupId, startTime, endTime }, ActivitiesUserList.class);
		return list;
	}

	@Override
	public List<ActivitiesDate> newActivitiesList(Integer communityId,
			Integer startTime, Integer endTime) throws Exception {

		String sql = "SELECT a.emob_group_id,a.activity_id AS activities_id, a.create_time AS activity_time,a.activity_title,"
				+ "COUNT(am.activity_member_id) AS activites_num,"
				+ "(SELECT COUNT( ame.activity_member_id) FROM activity_members ame WHERE ame.emob_group_id=a.emob_group_id) "
				+ " add_activity FROM activity_members am LEFT JOIN activities a ON a.emob_group_id=am.emob_group_id WHERE  a.community_id=? "
				+ " AND a.activity_time>=? AND a.activity_time<=? GROUP BY a.activity_id ";
		List<ActivitiesDate> page = this.getList(sql, new Object[] {
				communityId, startTime, endTime }, ActivitiesDate.class);
		return page;

	}

	public boolean updateActivity(String emobGroupId, String activity_title)
			throws Exception {
		String sql = " UPDATE activities SET activity_title = ? WHERE emob_group_id = ? ";
		List<Object> list = new ArrayList<Object>(2);
		list.add(activity_title);
		list.add(emobGroupId);
		return this.updateData(sql, list, null) > 0;
	}

	@Override
	public Activities getActivitiesById(int activityId) throws Exception {
		String sql = " SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner , a.bz_praise_sum , a.type , "
				+ " a.status , a.place , a.create_time , a.community_id , a.emob_group_id , a.activity_time , "
				+ " count(ag.emob_user_id) as activity_user_sum  "
				+ " from activities a left join activity_members ag on ag.emob_group_id = a.emob_group_id  WHERE activity_id = ? ";
		List<Activities> list = this.getList(sql, new Object[] { activityId },
				Activities.class);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public ActivitiesSimple getActivitiesByIdSimple(int activityId) throws Exception {
		String sql = "SELECT a.activity_id , a.activity_title , a.activity_detail , a.emob_id_owner ,u.nickname,"
				+ " a.create_time , a.activity_time, a.community_id, a.emob_group_id ,"
				+ " COUNT(ag.emob_user_id) as activity_user_sum"
				+ " FROM activities a"
				+ " LEFT JOIN activity_members ag on ag.emob_group_id = a.emob_group_id"
				+ " LEFT JOIN users u ON a.emob_id_owner=u.emob_id"
				+ " WHERE activity_id = ?";
		List<ActivitiesSimple> list = this.getList(sql, new Object[] { activityId },
				ActivitiesSimple.class);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public List<ActivityPhoto> getActivitiePhotos(Integer activityId)
			throws Exception {
		String sql = " SELECT * FROM activity_photo WHERE activity_id = ? ";
		return this.getList(sql, new Object[] { activityId },
				ActivityPhoto.class);
	}

	@Override
	public String getActivityThemePhoto(Integer communityId, String type,
			String service) throws Exception {
		String sql = " SELECT `values` FROM `community_item` WHERE type =? AND service = ? AND (community_id =? OR community_id=0) ORDER BY community_id DESC LIMIT 1 ";
		return this.getValue(sql, String.class, type, service, communityId);
	}

}
