package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.BonuscoinManager;
import com.xj.dao.BonuscoinManagerDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("bonuscoinManagerDao")
public class BonuscoinManagerDaoImpl extends MyBaseDaoImpl implements BonuscoinManagerDao {

	@Override
	public Integer addBonuscoinManager(BonuscoinManager bonuscoinManager)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(bonuscoinManager, key);
		return key.getId();
	}

	@Override
	public BonuscoinManager getBonuscoinManager(Integer shopTypeId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT bonuscoin_manager_id, shop_type_id, bonuscoin_count FROM bonuscoin_manager WHERE shop_type_id = ? ";
		List<BonuscoinManager> list = this.getList(sql, new Object[]{shopTypeId}, BonuscoinManager.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateBonuscoinManager(BonuscoinManager bonuscoinManager)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE bonuscoin_manager SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(bonuscoinManager);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE bonuscoin_manager_id = ? ";
		
		list.add(bonuscoinManager.getBonuscoinManagerId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}
	
}
