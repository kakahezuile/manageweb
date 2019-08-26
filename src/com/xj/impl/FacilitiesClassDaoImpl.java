package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.FacilitiesClass;
import com.xj.dao.FacilitiesClassDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("facilitiesClassDao")
public class FacilitiesClassDaoImpl extends MyBaseDaoImpl implements FacilitiesClassDao{

	@Override
	public Integer insert(FacilitiesClass facilitiesClass) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey myKey = new MyReturnKey();
		this.save(facilitiesClass, myKey);
		return myKey.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateFacilities(FacilitiesClass facilitiesClass) throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE facilities_class SET " ;
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(facilitiesClass);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false; 
		}
		sql += " WHERE facilities_class_id = ? ";
		list.add(facilitiesClass.getFacilitiesClassId());
		Integer result = this.updateData(sql, list, null);
		return result != null && result > 0 ? true : false;
	}

	@Override
	public FacilitiesClass getFacilitiesClass(Integer facilitiesClassId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT * FROM facilities_class WHERE facilities_class_id = ? ";
		List<FacilitiesClass> list = this.getList(sql, new Integer[]{facilitiesClassId}, FacilitiesClass.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<FacilitiesClass> getListFacilitiesClassWithCommunityId(Integer communityId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT * FROM facilities_class where facilities_class_id " +

				 	 " in (select facilities_class_id FROM facilities where community_id = ? group by facilities_class_id)  order by weight desc ";
		List<FacilitiesClass> list = this.getList(sql, new Integer[]{communityId}, FacilitiesClass.class);
		return list;
	}
	
}
