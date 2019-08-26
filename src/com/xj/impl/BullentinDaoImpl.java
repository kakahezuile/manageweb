package com.xj.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.xj.bean.Bulletin;
import com.xj.bean.BulletinUser;
import com.xj.bean.Page;
import com.xj.dao.BullentinDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("bullentinDao")
public class BullentinDaoImpl extends MyBaseDaoImpl implements BullentinDao {

	@Override
	public Integer insert(Bulletin bulletin) throws Exception {
		MyReturnKey myReturnKey = new MyReturnKey();
		this.save(bulletin, myReturnKey);
		Integer resultId = myReturnKey.getId();
		return resultId;
	}

	@Override
	public List<Bulletin> getBullentinList(Integer communityId)
			throws Exception {
		String sql = "SELECT * FROM bulletin WHERE community_id = ? ";
		List<Bulletin> list = this.getList(sql, new Integer[] { communityId },
				Bulletin.class);
		return list;
	}

	@Override
	public Page<BulletinUser> getBullentinListWithPage(Integer communityId,
			Integer pageNum, Integer pageSize, Integer nvm) throws Exception {
		String sql = "SELECT b.id,b.bulletin_text,b.create_time,b.community_id,b.admin_id,b.theme,b.sender_object,a.username AS user_name "
				+ "FROM bulletin b  LEFT JOIN admin a ON a.admin_id=b.admin_id WHERE b.community_id = ? order by id desc";
		Page<BulletinUser> page = this.getData4Page(sql,
				new Integer[] { communityId }, pageNum, pageSize, nvm,
				BulletinUser.class);
		return page;
	}

	@Override
	public BulletinUser getBulletin(Integer bulletinId) throws Exception {
		String sql = "SELECT b.id,b.bulletin_text,b.create_time,b.community_id,b.admin_id,b.theme,b.sender_object,a.username AS user_name "
				+ "FROM bulletin b  LEFT JOIN admin a ON a.admin_id=b.admin_id WHERE b.id = ?";
		List<BulletinUser> list = this.getList(sql,
				new Integer[] { bulletinId }, BulletinUser.class);
		if (list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Page<BulletinUser> getTimeBullentinListWithPage(Integer communityId,
			Integer pageNum, Integer pageSize, Integer nvm, Integer startTime,
			Integer endTimeInt) throws Exception {
		String sql = "SELECT b.id,b.bulletin_text,b.create_time,b.community_id,b.admin_id,b.theme,b.sender_object,a.username AS user_name "
				+ "FROM bulletin b  LEFT JOIN admin a ON a.admin_id=b.admin_id WHERE b.community_id = ? AND b.create_time>? AND b.create_time<? order by id desc";
		Page<BulletinUser> page = this.getData4Page(sql, new Object[] {
				communityId, startTime, endTimeInt }, pageNum, pageSize, nvm,
				BulletinUser.class);
		return page;
	}

	@Override
	public Bulletin getBullentinByMessageId(String key) {
		String sql = "SELECT theme,bulletin_text,message_id,create_time FROM bulletin  WHERE message_id =? ORDER BY create_time DESC LIMIT 1 ";
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<Bulletin>(){
			
			@Override
			public Bulletin extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(!rs.next()){
					return null;
				}
				Bulletin bulletin = new Bulletin();
				bulletin.setBulletinText(rs.getString("bulletin_text"));
				bulletin.setMessageId(rs.getLong("message_id"));
				bulletin.setTheme(rs.getString("theme"));
				bulletin.setCreateTime(rs.getInt("create_time"));
				return bulletin;
			}
		},key);
	}

}
