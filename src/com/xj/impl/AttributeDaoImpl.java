package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;


import com.xj.bean.Attribute;
import com.xj.dao.AttributeDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("attributeDao")
public class AttributeDaoImpl extends MyBaseDaoImpl implements AttributeDao {

	@Override
	public Integer addAttribute(Attribute attribute) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(attribute, key);
		return key.getId();
	}

	@Override
	public List<Attribute> getAttributes(Integer serviceId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT attr_id , attr_name , service_id , price FROM attribute WHERE service_id = ? ";
		List<Attribute> list = this.getList(sql, new Object[]{serviceId}, Attribute.class);
		return list;
	}

	@Override
	public boolean deleteAttribute(Attribute attribute) throws Exception {
		// TODO Auto-generated method stub
		int result = this.delete(attribute);
		return result > 0;
	}

}
