package com.xj.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.xj.bean.AppUpdate;
import com.xj.bean.AppVersion;
import com.xj.bean.Apps;
import com.xj.bean.Communities;
import com.xj.bean.Page;
import com.xj.dao.AppVersionDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("appVersionDao")
public class AppVersionDaoImpl extends MyBaseDaoImpl implements AppVersionDao{

	@Override
	public Integer addAppVersion(AppVersion appVersion) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(appVersion, key);
		return key.getId();
	}

	@Override
	public List<AppVersion> getAppVersions() throws Exception {
		String sql = " SELECT app_version_id , version , create_time FROM app_version ";
		List<AppVersion> list = this.getList(sql, new Object[]{}, AppVersion.class);
		return list;
	}

	@Override
	public Integer insertAppUpdateConfig(AppUpdate update) {
		String  sql = " INSERT INTO `app_update` ( `app_id`, `create_time`, `type`, `status`, `update_user`) VALUES (?, ?, ?, ?, ? ) ";
		return this.getJdbcTemplate().update(sql, update.getAppId(),(int)(System.currentTimeMillis()/1000l),update.getType(),"on",update.getUpdateUser());
	}

	@Override
	public Integer updateAppUpdateConfig(String type, String updateUser,
			String status) {
		String sql = " UPDATE app_update SET `status` = ? WHERE type = ? AND update_user = ? ";
		return this.getJdbcTemplate().update(sql, status,type,updateUser);
			
	}

	@Override
	public Page<Communities> getListCommunityAppVersion(Integer pageNum,
			Integer pageSize) {
		String sql =  "  SELECT community_name, community_id,app_id,max(version) version  from  ( "
				+ "  SELECT  c.community_name,c.community_id,u.app_id,a.version FROM communities c  CROSS JOIN app_update u INNER JOIN app a ON a.id=u.app_id WHERE c.active='是' AND  u.`status` = 'ON' AND u.type='all' AND u.update_user = 'all'  AND  c.status = 'available' "
				+ "	 UNION "
				+ "  SELECT c.community_name,c.community_id,u.app_id,a.version  FROM communities c INNER JOIN app_update u  on u.update_user = c.community_id  INNER JOIN app a ON a.id=u.app_id WHERE c.active='是' AND  u.`status` = 'ON' AND u.type='community' AND c.status = 'available'"
				+ "	 ) t  GROUP BY t.community_id  LIMIT ?,? ";
		 List<Communities> list = this.getJdbcTemplate().query(sql, new ResultSetExtractor<List<Communities>>(){

			@Override
			public List<Communities> extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				List<Communities> communities = new ArrayList<Communities>();
				while(rs.next()){
					Communities community = new Communities();
					community.setCommunityName(rs.getString("community_name"));
					community.setCommunityId(rs.getInt("community_id"));
					community.setAppVersion(rs.getString("version"));
					communities.add(community);
				}
				return communities;
			}
			
		},(pageNum-1)*pageSize,pageSize);
		 
		 int communityCount = this.getJdbcTemplate().queryForInt(" SELECT count(1) FROM communities WHERE `status` = 'available' AND active='是' ");
		 
		Page<Communities> page = new Page<Communities>(pageNum,communityCount, pageSize, 10);
		page.setPageData(list);
		return page;
	}
	
	
	@Override
	public Page<Communities> getListCommunityAppVersionByName(String query,Integer pageNum, Integer pageSize) {
		String sql =  "  SELECT community_name, community_id,app_id,max(version) version  from  ( "
				+ "  SELECT  c.community_name,c.community_id,u.app_id,a.version FROM communities c  CROSS JOIN app_update u INNER JOIN app a ON a.id=u.app_id WHERE c.active='是' AND  u.`status` = 'ON' AND u.type='all' AND u.update_user = 'all' AND c.community_name LIKE '%"+query+"%' "
				+ "	 UNION "
				+ "  SELECT c.community_name,c.community_id,u.app_id,a.version  FROM communities c INNER JOIN app_update u  on u.update_user = c.community_id  INNER JOIN app a ON a.id=u.app_id WHERE c.active='是' AND  u.`status` = 'ON' AND u.type='community' AND c.community_name LIKE '%"+query+"%'  "
				+ "	 ) t  GROUP BY t.community_id ";
		List<Communities> list = this.getJdbcTemplate().query(sql, new ResultSetExtractor<List<Communities>>(){
			
			@Override
			public List<Communities> extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				List<Communities> communities = new ArrayList<Communities>();
				while(rs.next()){
					Communities community = new Communities();
					community.setCommunityName(rs.getString("community_name"));
					community.setCommunityId(rs.getInt("community_id"));
					community.setAppVersion(rs.getString("version"));
					communities.add(community);
				}
				return communities;
			}
		});
		
		Page<Communities> page = new Page<Communities>();
		page.setPageData(list);
		return page;
	}

	@Override
	public List<Apps> getAppversions() {
		
		return	this.getJdbcTemplate().query(" SELECT id,version,detail from app WHERE app_name = 'client' ORDER BY version DESC ", new ResultSetExtractor<List<Apps>>(){

			@Override
			public List<Apps> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				List<Apps> Apps = new ArrayList<Apps>();
				while(rs.next()){
					Apps app = new Apps();
					app.setId(rs.getInt("id"));
					app.setVersion(rs.getString("version"));
					app.setDetail(rs.getString("detail"));
					Apps.add(app);
				}
				return Apps;
			}
		});
	}
}
