package com.xj.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.xj.bean.FloorAndUnit;
import com.xj.bean.HomeOwner;
import com.xj.dao.HomeOwnerDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("homeOwnerDao")
public class HomeOwnerDaoImpl extends MyBaseDaoImpl implements HomeOwnerDao {

	@Override
	public Integer addHomeOwner(HomeOwner homeOwner) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(homeOwner, key);
		return key.getId();
	}

	@Override
	public List<HomeOwner> getHomeOwners(Integer communityId) throws Exception {
		String sql = " SELECT home_owner_id , user_floor , ifnull(user_unit,'0') as user_unit , user_room , community_id FROM home_owner WHERE community_id = ? ORDER BY user_floor , user_unit , user_room ";
		return this.getList(sql, new Object[]{communityId}, HomeOwner.class);
	}

	@Override
	public List<FloorAndUnit> getCommunityHome(Integer communityId) throws Exception {
		String sql = "SELECT user_floor, ifnull(user_unit,'0') as user_unit FROM home_owner WHERE community_id = ? group by user_floor,user_unit ORDER BY user_floor,user_unit";
		return this.getList(sql, new Object[]{communityId}, FloorAndUnit.class);
	}

	@Override
	public void importHomeOwners(final List<HomeOwner> homeOwners) throws Exception {
		this.getJdbcTemplate().batchUpdate("INSERT INTO home_owner(community_id,user_floor,user_unit,user_room) values (?, ?, ?, ?)", new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				HomeOwner homeOwner = homeOwners.get(i);
				ps.setInt(1, Integer.valueOf(homeOwner.getCommunityId()));
				ps.setString(2, homeOwner.getUserFloor());
				ps.setString(3, homeOwner.getUserUnit());
				ps.setString(4, homeOwner.getUserRoom());
			}
			@Override
			public int getBatchSize() {
				return homeOwners.size();
			}
		});
	}

	@Override
	public List<String> getFloorsByCommunityId(Integer communityId) {
		return this.getJdbcTemplate().query("SELECT DISTINCT user_floor FROM `home_owner` WHERE community_id = ? ORDER BY user_floor ",new ResultSetExtractor<List<String>>(){

			@Override
			public List<String> extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				List<String> homeOwners = new ArrayList<String>();
				while(rs.next()){
					homeOwners.add(rs.getString("user_floor"));
				}
				return homeOwners;
			}
		},communityId);
	}

	@Override
	public List<String> selectUnitByCommunityIdAndFloor(Integer communityId, String floor) {
		return this.getJdbcTemplate().query(" SELECT DISTINCT user_unit FROM `home_owner` WHERE community_id =? AND user_floor = ?  ORDER BY user_unit ",new ResultSetExtractor<List<String>>(){

			@Override
			public List<String> extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				List<String> homeOwners = new ArrayList<String>();
				while(rs.next()){
					homeOwners.add(rs.getString("user_unit"));
				}
				return homeOwners;
			}
		},communityId,floor);
	}

	@Override
	public List<String> selectRoom(Integer communityId, String floor, String unit) {
		return this.getJdbcTemplate().query("  SELECT DISTINCT user_room FROM `home_owner` WHERE community_id =? AND user_floor = ? AND user_unit =? ORDER BY user_room ",new ResultSetExtractor<List<String>>(){

			@Override
			public List<String> extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				List<String> homeOwners = new ArrayList<String>();
				while(rs.next()){
					homeOwners.add(rs.getString("user_room"));
				}
				return homeOwners;
			}
		},communityId,floor,unit);
	}
}