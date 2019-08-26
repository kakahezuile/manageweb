package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;


import com.xj.bean.CommunityUserService;
import com.xj.dao.CommunityUserServiceDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("communityUserServiceDao")
public class CommunityUserServiceDaoImpl extends MyBaseDaoImpl implements CommunityUserServiceDao {

	@Override
	public Integer addCommunityUserService(
			CommunityUserService communityUserService) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(communityUserService, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateCommunityUserService(
			CommunityUserService communityUserService) throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE community_user_service SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(communityUserService);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE user_service_id = ? ";
	
		list.add(communityUserService.getUserServiceId());
		int result = this.updateData(sql, list, null);
		return result > 0 ;
	}

	@Override
	public List<CommunityUserService> getCommunityUserServiceList(
			String emobId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT c.user_service_id  , c.service_id , c.status , s.service_name , s.img_name FROM community_user_service c "+
		 			 
					 " left join services s on c.service_id = s.service_id WHERE c.emob_id = ? AND c.status = 'normal'  ";
		List<CommunityUserService> list = this.getList(sql, new Object[]{emobId}, CommunityUserService.class);
		return list;
	}

	@Override
	public CommunityUserService getCommunityUserService(String emobId,
			Integer serviceId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT c.user_service_id , c.service_id , c.status , c.emob_id , '0' as img_name , '0' as service_name FROM community_user_service c WHERE c.emob_id = ? AND c.service_id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(emobId);
		params.add(serviceId);
		List<CommunityUserService> list = this.getList(sql, params.toArray(), CommunityUserService.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
