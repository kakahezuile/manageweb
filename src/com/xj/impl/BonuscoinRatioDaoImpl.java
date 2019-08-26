package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.BonuscoinRatio;
import com.xj.dao.BonuscoinRatioDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("bonuscoinRatioDao")
public class BonuscoinRatioDaoImpl extends MyBaseDaoImpl implements BonuscoinRatioDao {

	@Override
	public Integer addBonuscoinRatio(BonuscoinRatio bonuscoinRatio)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(bonuscoinRatio, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateBonuscoinRatio(BonuscoinRatio bonuscoinRatio)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE bonuscoin_ratio SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(bonuscoinRatio);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE ratios_id = ? ";
		System.out.println(sql);
		list.add(bonuscoinRatio.getRatiosId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public BonuscoinRatio getBonuscoinRatio(Integer communityId, Integer sort)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT ratios_id, community_id, sort, consume, exchange, proportion, create_time, update_time, admin_id FROM bonuscoin_ratio WHERE community_id = ? AND sort = ?";
		List<BonuscoinRatio> list = this.getList(sql, new Object[]{communityId , sort}, BonuscoinRatio.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
	
}
