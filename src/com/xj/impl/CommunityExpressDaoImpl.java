package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.CommunityExpress;
import com.xj.dao.CommunityExpressDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("communityExpressDao")
public class CommunityExpressDaoImpl extends MyBaseDaoImpl implements CommunityExpressDao{

	@Override
	public Integer addCommunityExpress(CommunityExpress communityExpress)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(communityExpress, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateCommunityExpress(CommunityExpress communityExpress)
			throws Exception {
		String sql = "UPDATE community_express SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(communityExpress);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE community_express_id = ? ";
		System.out.println(sql);
		list.add(communityExpress.getCommunityExpressId());
		int result = this.updateData(sql, list, null);
		
		
	
		return result > 0;
	}

	@Override
	public List<CommunityExpress> getCommunityExpressList(Integer communityId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT community_express_id , express_address , community_id , express_level , create_time , update_time FROM community_express WHERE community_id = ? ";
		List<CommunityExpress> list = this.getList(sql, new Object[]{communityId}, CommunityExpress.class);
		return list;
	}

	@Override
	public CommunityExpress getCommunityExpressByLevel(Integer communityId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT community_express_id , express_address , community_id , express_level , create_time , update_time FROM community_express WHERE community_id = ? ORDER BY express_level DESC limit 1 ";
		List<CommunityExpress> list = this.getList(sql, new Object[]{communityId}, CommunityExpress.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
