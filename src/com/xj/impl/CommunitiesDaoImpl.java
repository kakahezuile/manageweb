package com.xj.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.xj.bean.Communities;
import com.xj.bean.CommunityAndCity;
import com.xj.bean.CommunityFeature;
import com.xj.bean.IdCount;
import com.xj.bean.PartnerPermission;
import com.xj.bean.PublicizePhotos;
import com.xj.bean.PublishPrice;
import com.xj.bean.tvstatistics.CommunitiesSequence;
import com.xj.dao.CommunitiesDao;
import com.xj.dao.ReturnKeyList;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("communitiesDao")
public class CommunitiesDaoImpl extends MyBaseDaoImpl implements CommunitiesDao {

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateCommunities(Communities communities) throws Exception {
		Object resultObject[] = DaoUtils.reflect(communities);
		if (resultObject == null || resultObject[1] == null
				|| ((List<Object>) resultObject[1]).size() == 0) {
			return false;
		}

		String sql = "UPDATE communities SET ";
		sql += (String) resultObject[0];
		sql += " WHERE community_id = ? ";

		List<Object> list = (List<Object>) resultObject[1];
		list.add(communities.getCommunityId());

		return this.updateData(sql, list, null) > 0;
	}

	@Override
	public boolean deleteCommunities(Integer communityId) throws Exception {
		String sql = "DELETE FROM communities WHERE community_id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(communityId);

		return this.updateData(sql, params, null) > 0;
	}

	@Override
	public int addCommunities(Communities communities) throws Exception {
		MyKey myKey = new MyKey();
		this.save(communities, myKey);
		return myKey.getMyKey();
	}

	class MyKey implements ReturnKeyList {

		private Integer myKey;

		public Integer getMyKey() {
			return this.myKey;
		}

		@Override
		public void getKeyList(List<Map<String, Object>> keyList) {
			myKey = ((Long) keyList.get(0).get("GENERATED_KEY")).intValue();
		}

	}

	@Override
	public List<Communities> getCommunitiesByCityId(Integer cityId)
			throws Exception {
		String sql = "SELECT community_id , community_name , population , communities_desc , longitude , latitude , shop_services , facility_services , city_id , create_time FROM communities WHERE city_id = ? ";
		return this.getList(sql, new Integer[] { cityId }, Communities.class);
	}

	@Override
	public List<Communities> getCommunityList() throws Exception {
		String sql = "SELECT community_id , community_name , population , communities_desc , longitude , latitude , shop_services , facility_services , city_id , create_time FROM communities WHERE `status` = 'available' AND active = '是'  order by community_id asc";
		return this.getList(sql, null, Communities.class);
	}

	@Override
	public CommunityAndCity getCommunityAndCity(Integer userId)
			throws Exception {
		String sql = " SELECT c.community_name , ct.city  FROM users u left join communities c on u.community_id = c.community_id left join city ct on   c.city_id = ct.city_id where u.user_id = ? ";
		List<CommunityAndCity> list = this.getList(sql, new Object[] { userId }, CommunityAndCity.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Communities getCommunities(Integer communityId) throws Exception {
		String sql = "SELECT community_id , community_name  FROM communities WHERE community_id = ? AND `status` = 'available' AND active = '是'  ";
		List<Communities> list = this.getList(sql, new Object[] { communityId }, Communities.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<Communities> getListCommunity(Integer communityId) throws Exception {
		String sql = "SELECT community_id , community_name  FROM communities  where community_id=? WHERE `status` = 'available' AND active = '是'  ";
		return this.getList(sql, new Object[] { communityId }, Communities.class);
	}

	@Override
	public List<CommunityFeature> getCommunityFeature(Integer communityId)
			throws Exception {
		String sql = "SELECT * FROM community_feature f WHERE f.community_id=?";
		return this.getList(sql, new Object[] { communityId }, CommunityFeature.class);

	}

	@Override
	public List<Communities> getListCommunityEmobId(String emobId) throws Exception {
		String sql = "SELECT c.community_id , c.community_name    FROM communities c LEFT JOIN partner_permission p ON p.community_id=c.community_id  where p.emob_id=?";
		return this.getList(sql, new Object[] { emobId }, Communities.class);
	}

	@Override
	public List<Communities> getListCommunityQ() throws Exception {
		String sql = "SELECT c.community_id , c.community_name  FROM communities c WHERE c.`status` = 'available' AND c.active = '是' AND community_id<100 ";
		return this.getList(sql, new Object[] {}, Communities.class);
	}

	@Override
	public Integer addPartnerPermission(PartnerPermission partnerPermission) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(partnerPermission, key);
		return key.getId();
	}

	@Override
	public Integer delPartnerPermission(String emobId, Integer communityId)
			throws Exception {
		String sql = "SELECT * FROM partner_permission where emob_id=?";
		List<PartnerPermission> list = this.getList(sql, new Object[] { emobId }, PartnerPermission.class);
		Integer i = 0;
		for (PartnerPermission partnerPermission : list) {
			i++;
			this.delete(partnerPermission);
		}

		return i;
	}

	@Override
	public boolean addPublicizePhotos(PublicizePhotos publicizePhotos) throws Exception {
		String sql = " SELECT * FROM publicize_photos WHERE  community_id=? ORDER BY create_time DESC LIMIT 1  ";
		List<PublicizePhotos> list = this.getList(sql, new Object[] { publicizePhotos.getCommunityId() }, PublicizePhotos.class);
		int id = -1;
		if (CollectionUtils.isNotEmpty(list)) {
			PublicizePhotos photos = list.get(0);
			photos.setImgUrl(publicizePhotos.getImgUrl());
			id = this.update(photos);
		} else {
			MyKey myKey = new MyKey();
			this.save(publicizePhotos, myKey);
			id = myKey.getMyKey();
		}
		return id > 0;
	}

	@Override
	public PublicizePhotos getPublicizePhotos(Integer communityId) throws Exception {
		String sql = "SELECT * FROM publicize_photos WHERE  community_id=? ORDER BY create_time DESC LIMIT 1 ";
		List<PublicizePhotos> list = this.getList(sql,
				new Object[] { communityId }, PublicizePhotos.class);
		if (list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<CommunitiesSequence> communitiesSequence() throws Exception {
		String sql = "SELECT * FROM communities_sequence ORDER BY sequence DESC ";
		return this.getList(sql, new Object[] {}, CommunitiesSequence.class);
	}

	@Override
	public Integer addPublishPrice(PublishPrice publishPrice) throws Exception {
		String sql = "SELECT * FROM publish_price WHERE  community_id=?";

		List<Object> list3 = new ArrayList<Object>();
		this.getList(sql, new Object[] { publishPrice.getCommunityId() },
				PublishPrice.class);
		String sql2 = "UPDATE publish_price SET status='end' ";

		sql2 += " WHERE community_id = ? ";
		list3.add(publishPrice.getCommunityId());
		this.updateData(sql2, list3, null);

		MyKey myKey = new MyKey();
		this.save(publishPrice, myKey);
		int id = myKey.getMyKey();
		return id;
	}

	@Override
	public PublishPrice getPublishPrice(Integer communityId) throws Exception {
		String sql = "SELECT * FROM publish_price WHERE  community_id=? and status='ongoing'";
		List<PublishPrice> list = this.getList(sql,
				new Object[] { communityId }, PublishPrice.class);
		if (list.size() == 0) {
			return null;
		}
		return list.get(0);

	}

	@Override
	public Map<String, Integer> getCities() {
		String sql = " SELECT DISTINCT d.id,d.`name` FROM communities c INNER JOIN district d ON c.city_id = d.id WHERE c.`status` = 'available' AND c.active = '是' ";
		return this.getJdbcTemplate().query(sql,
				new ResultSetExtractor<Map<String, Integer>>() {
					@Override
					public Map<String, Integer> extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						Map<String, Integer> map = new HashMap<String, Integer>();
						while (rs.next()) {
							map.put(rs.getString("name"), rs.getInt("id"));
						}
						return map;
					}
				});
	}

	/**
	 * 获取城市 中的小区信息
	 * 
	 * @throws Exception
	 */
	@Override
	public List<Communities> getCityCommunities(Integer cityId)
			throws Exception {
		String sql = "SELECT c.community_id , c.community_name  FROM communities c WHERE c.city_id = ? AND c.`status` = 'available' AND c.active = '是'  ";
		return this.getList(sql, new Object[] { cityId }, Communities.class);
	}

	@Override
	public List<Integer> getCommunityIdList() {
		String sql = " SELECT community_id FROM communities WHERE `status` = 'available' AND active = '是'  ";
		return this.getJdbcTemplate().query(sql,
				new ResultSetExtractor<List<Integer>>() {
					@Override
					public List<Integer> extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						List<Integer> list = new ArrayList<Integer>();
						while (rs.next()) {
							list.add(rs.getInt("community_id"));
						}
						return list;
					}
				});
	}

	@Override
	public List<IdCount> getCommunityUpdate(Integer lastQuitTime)
			throws Exception {
		String sql = "SELECT lc.community_id AS id ,count(1) AS count"
				+ " FROM life_circle_detail lcd LEFT JOIN life_circle lc ON lcd.life_circle_id=lc.life_circle_id"
				+ " WHERE lcd.emob_id_from NOT IN (SELECT emob_id FROM try_out)"
				+ " AND lc.life_circle_id IS NOT NULL"
				+ " AND lcd.update_time>?" + " GROUP BY lc.community_id";
		return this.getList(sql, new Object[] { lastQuitTime }, IdCount.class);
	}

	@Override
	public Map<String, Float> getCommunityLocation(Integer communityId) {
		String sql = " SELECT latitude,longitude FROM communities WHERE community_id = ? ";
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<Map<String, Float>>(){

			@Override
			public Map<String, Float> extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				if(rs.next()){
					Map<String, Float> map = new HashMap<String, Float>();
					map.put("latitude", rs.getFloat("latitude"));
					map.put("longitude", rs.getFloat("longitude"));
					return map;
				}
				return null;
			}
		},communityId);
	}

	@Override
	public String getHomePic(Integer communityId) {
		String sql= " SELECT `values` FROM community_item WHERE service = 'home' AND  type = 'photo' AND community_id in  (0 ,?) ORDER BY community_id DESC  LIMIT 1  ";
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<String>(){

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(!rs.next()){
					return null;
				}
				return rs.getString("values");
			}
		},communityId);
	}

	@Override
	public void saveOrUpdateHomePic(Integer communityId, String posterFile) {
		String sql = " UPDATE `community_item` SET  `values`=? WHERE `community_id`=? AND service = 'home' AND type = 'photo' ";
		int update = this.getJdbcTemplate().update(sql, posterFile,communityId);
		if(update<1){
			 sql = " INSERT INTO `community_item` (`community_id`, `service`, `type`, `values`) VALUES ( ?, 'home', 'photo', ?) ";
			 this.getJdbcTemplate().update(sql, communityId,posterFile);
		}
	}
}