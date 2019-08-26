package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.XjStandard;
import com.xj.dao.XjStandardDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("xjStandardDao")
public class XjStandardDaopImpl extends MyBaseDaoImpl implements XjStandardDao {

	@Override
	public Integer addXjStandard(XjStandard xjStandard) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(xjStandard, key);
		return key.getId();
	}

	@Override
	public XjStandard getXjStandard(Integer communityId, String sort)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT standard_id , sort , price , detail , community_id FROM xj_standard WHERE community_id = ? AND sort = ?  ";
		List<XjStandard> list = this.getList(sql, new Object[]{communityId , sort}, XjStandard.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateXjStandard(XjStandard xjStandard) throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE xj_standard SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(xjStandard);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE standard_id = ? ";
		System.out.println(sql);
		list.add(xjStandard.getStandardId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

}
