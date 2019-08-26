package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.LifeCircleNumer;
import com.xj.bean.LifeCircleTopVO;
import com.xj.bean.LifePraise;
import com.xj.bean.LifePraise2;
import com.xj.bean.LifePraiseContent;
import com.xj.bean.Page;
import com.xj.dao.LifePraiseDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("lifePraiseDao")
public class LifePraiseDaoImpl extends MyBaseDaoImpl implements LifePraiseDao{

	@Override
	public Integer addLifePraise(LifePraise lifePraise) throws Exception {
		MyReturnKey key = new MyReturnKey();
		if(lifePraise.getLifeCircleId() == null){
			lifePraise.setLifeCircleId(0);
		}
		this.save(lifePraise, key);
		return key.getId();
	}

	@Override
	public LifePraise getLifePraise(String from, String to, Integer start,
			Integer end) throws Exception {
		String sql = " SELECT life_praise_id , emob_id_from , emob_id_to , create_time FROM life_praise WHERE create_time >= ? AND create_time <= ? AND emob_id_from = ? AND emob_id_to = ? ";
		List<LifePraise> list = this.getList(sql, new Object[]{start , end , from , to}, LifePraise.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Page<LifePraiseContent> getLifepraises(String to, Integer pageNum,
			Integer pageSize, Integer nvm) throws Exception {
		String sql = " SELECT l.emob_id_from as emob_id , count(l.life_praise_id) as praise_count , u.nickname " +
					 " , u.avatar FROM life_praise l LEFT JOIN users u ON l.emob_id_from = u.emob_id WHERE l.emob_id_to = ? group by l.emob_id_from order by max(l.create_time) desc ";
		return this.getData4Page(sql, new Object[]{to}, pageNum, pageSize, nvm, LifePraiseContent.class);
	}

	@Override
	public List<LifePraise2> getLifePraises(String to, Integer time)
			throws Exception {
		String sql = " SELECT l.emob_id_from , l.create_time , u.nickname , l.life_circle_id " +
		 " , u.avatar FROM life_praise l LEFT JOIN users u ON l.emob_id_from = u.emob_id WHERE l.emob_id_to = ? AND l.create_time > ? order by l.create_time desc ";
		return this.getList(sql, new Object[]{to , time}, LifePraise2.class);
	}

	@Override
	public LifeCircleNumer getLifeCircleNumber(String to, Integer createTime)
			throws Exception {
		String sql = " SELECT max(create_time) as create_time , count(*) as sum FROM life_praise l WHERE emob_id_to = ? AND create_time > ? ";
		List<LifeCircleNumer> list = this.getList(sql, new Object[]{to , createTime}, LifeCircleNumer.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<LifePraiseContent> getLifePraises(Integer lifeCircleId)
			throws Exception {
		String sql = " SELECT count(l.life_praise_id) as praise_count , u.avatar , u.nickname , l.emob_id_from as emob_id FROM life_praise l left join " +

					 " users u on l.emob_id_from = u.emob_id where l.life_circle_id = ? group by l.emob_id_from "	;
		List<LifePraiseContent> list = this.getList(sql, new Object[]{lifeCircleId}, LifePraiseContent.class);
		return list;
	}

	@Override
	public Integer getUserCharacterValues(Integer start, Integer end, String emobId , Integer communityId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getJdbcTemplate().getDataSource().getConnection();
			
			ps = con.prepareStatement(" select count(*) as count from life_praise where emob_id_to = ? and create_time >= ? and create_time <= ? ");
			ps.setString(1, emobId);
			ps.setInt(2, start);
			ps.setInt(3, end);
			
			rs = ps.executeQuery();
			if(rs != null && rs.next()){
				return rs.getInt("count");
			}
			return 0;
		} finally {
			if (null != rs) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != ps) {
				try {
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != con) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Integer getUserCount(Integer start, Integer end, Integer sum , Integer communityId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getJdbcTemplate().getDataSource().getConnection();
			
			ps = con.prepareStatement(" select count(l.life_praise_id) as count from life_praise l left join users u on l.emob_id_to = u.emob_id where l.create_time >= ? and l.create_time <= ? and u.community_id = ? group by l.emob_id_to having count > ? ");
			ps.setInt(1, start);
			ps.setInt(2, end);
			ps.setInt(3, communityId);
			ps.setInt(4, sum);
			
			rs = ps.executeQuery();
			int num = 1;
			while(rs != null && rs.next()){
				if(rs.getInt("count") > 0){
					num++;
				}
			}
			return num;
		} finally {
			if (null != rs) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != ps) {
				try {
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != con) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<LifeCircleTopVO> getLifeTop(Integer start, Integer end , Integer communityId)
			throws Exception {
		String sql = " select u.nickname , u.avatar , u.nickname , u.emob_id , count(l.life_praise_id) as praise_sum , u.character_values " +
					 "   from life_praise l left join users u on l.emob_id_to = u.emob_id " +
					 " where l.create_time >= ? and l.create_time <= ? and community_id = ? " +
					 " group by l.emob_id_to order by praise_sum desc limit 0,30 ";	
		List<LifeCircleTopVO> list = this.getList(sql, new Object[]{start , end , communityId}, LifeCircleTopVO.class);
		return list;
	}
}