package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.DateSpecification;
import com.xj.dao.DateSpecificationDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("dateSpecificationDao")
public class DateSpecificationDaoImpl extends MyBaseDaoImpl implements DateSpecificationDao{

	@Override
	public Integer addDateSpecification(DateSpecification dateSpecification)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(dateSpecification, key);
		return key.getId();
	}

	@Override
	public DateSpecification getSpecification() throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT * FROM date_specification ";
		List<DateSpecification> list = this.getList(sql, new Object[]{}, DateSpecification.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateDateSpecification(DateSpecification dateSpecification)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE date_specification SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(dateSpecification);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE specification_id = ? ";
	
		list.add(dateSpecification.getSpecificationId());
		int result = this.updateData(sql, list, null);
		return result > 0 ;
	}

}
