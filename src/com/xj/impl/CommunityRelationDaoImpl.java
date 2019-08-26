package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Communities;
import com.xj.bean.CommunityService;
import com.xj.bean.CommunityRelationAndName;
import com.xj.dao.CommunityRelationDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("communityRelationDao")
public class CommunityRelationDaoImpl extends MyBaseDaoImpl implements CommunityRelationDao{

	@Override
	public Integer addRelation(CommunityService communityRelation) throws Exception {
		MyReturnKey myReturnKey = new MyReturnKey();
		return this.save(communityRelation, myReturnKey);
	}

	@Override
	public List<CommunityService> getRelationList(Integer communityId) throws Exception {
		String sql = "SELECT * FROM community_service WHERE community_id = ? AND relation_status = 'normal' ";
		return this.getList(sql, new Integer[]{communityId}, CommunityService.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateRelation(CommunityService communityRelation) throws Exception {
		String sql = " UPDATE community_service SET " ;
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(communityRelation);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false; 
		}
		sql += " WHERE community_service_id = ? ";
		list.add(communityRelation.getCommunityServiceId());
		Integer result = this.updateData(sql, list, null);
		return result != null && result > 0 ? true : false;
	
	}

	@Override
	public CommunityService isEmpty(Integer communityId, Integer categoryId)
			throws Exception {
		String sql = " SELECT * FROM community_service WHERE community_id = ? AND service_id = ?  ";
		List<CommunityService> list = this.getList(sql, new Object[]{communityId , categoryId}, CommunityService.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public boolean updateRelationByCommunityIdAndCategoryId(
			Integer communityId, Integer categoryId) throws Exception {
		String sql = "UPDATE community_service SET relation_status = 'normal' WHERE community_id = ? AND service_id = ?  ";
		List<Object> list = new ArrayList<Object>();
		list.add(communityId);
		list.add(categoryId);
		Integer result = this.updateData(sql, list, null);
		return result > 0;
	}
	
	@Override
	public boolean updateRelationByCommunityIdAndCategoryId(
			Integer communityId, Integer categoryId , Integer appVersionId) throws Exception {
		Integer e=(int)(System.currentTimeMillis() / 1000l);
		
		String sql = "UPDATE community_service SET relation_status = 'normal',update_time="+e+" WHERE community_id = ? AND service_id = ? AND app_version_id = ? ";
		List<Object> list = new ArrayList<Object>();
		list.add(communityId);
		list.add(categoryId);
		list.add(appVersionId);
		Integer result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public List<CommunityRelationAndName> getCommunityRelationAndNameList(
			Integer communityId) throws Exception {
		String sql = " SELECT c.community_service_id , c.update_time , c.community_id , c.service_id , c.relation_status , s.service_name , s.img_name FROM community_service c "+
					 " left join services s on c.service_id = s.service_id WHERE c.community_id = ? AND c.relation_status = 'normal' order by c.service_level  ";
		List<CommunityRelationAndName> list = this.getList(sql, new Object[]{communityId}, CommunityRelationAndName.class);
		return list;
	}
	
	@Override
	public List<CommunityRelationAndName> getCommunityRelationAndNameList(
			Integer communityId , Integer appVersionId) throws Exception {
		String sql = " SELECT c.community_service_id , c.update_time , c.community_id , c.service_id , c.relation_status , s.service_name , s.img_name FROM community_service c "+
					 " left join services s on c.service_id = s.service_id WHERE c.community_id = ? AND c.app_version_id = ? AND c.relation_status = 'normal' order by c.service_level  ";
		List<CommunityRelationAndName> list = this.getList(sql, new Object[]{communityId,appVersionId}, CommunityRelationAndName.class);
		return list;
	}

	@Override
	public CommunityService isEmpty(Integer communityId, Integer categoryId,
			Integer appVersionId) throws Exception {
		String sql = " SELECT * FROM community_service WHERE community_id = ? AND service_id = ? AND app_version_id = ?  ";
		List<CommunityService> list = this.getList(sql, new Object[]{communityId , categoryId , appVersionId}, CommunityService.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<CommunityRelationAndName> getCommunityRelationAndNameList(
			Integer communityId, Integer appVersionId, Integer time)
			throws Exception {
		String sql = " SELECT c.community_service_id , c.update_time , c.community_id , c.service_id , c.relation_status , s.service_name , s.img_name FROM community_service c "+
		 			 " left join services s on c.service_id = s.service_id WHERE c.community_id = ? AND c.app_version_id = ? AND c.update_time > ?  AND c.relation_status = 'normal' order by s.service_level  ";
		List<CommunityRelationAndName> list = this.getList(sql, new Object[]{communityId,appVersionId,time}, CommunityRelationAndName.class);
		return list;
	}

	@Override
	public Communities getCommunity(Integer communityId) throws Exception {
		String sql = " SELECT * FROM communities WHERE community_id = ?   ";
		List<Communities> list = this.getList(sql, new Object[]{communityId}, Communities.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Integer getMaxTime(Integer communityId) throws Exception {
		PreparedStatement ps = null;
		ResultSet res = null;
		Connection con = null;
		
		try {
			con = this.getJdbcTemplate().getDataSource().getConnection();
			
			ps = con.prepareStatement("SELECT max(update_time) as update_time FROM community_service WHERE community_id = ?");
			ps.setInt(1, communityId);
			
			res = ps.executeQuery();
			if(res != null && res.next()){
				return res.getInt("update_time");
			}
			
			return 0;
		} finally {
			if(res != null){
				try {
					res.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(ps != null){
				try {
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(con != null){
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
