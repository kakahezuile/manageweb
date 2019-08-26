package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Clearing;
import com.xj.dao.ClearingDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("clearingDao")
public class ClearingDaoImpl extends MyBaseDaoImpl implements ClearingDao {

	@Override
	public Integer addClearing(Clearing clearing) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(clearing, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateClearing(Clearing clearing) throws Exception {
		// TODO Auto-generated method stub
		String sql = "UPDATE clearing SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(clearing);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}	
		sql += " WHERE clearing_id = ? ";
		System.out.println(sql);
		list.add(clearing.getClearingId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

}
