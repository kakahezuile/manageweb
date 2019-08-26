package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.XjHelp;
import com.xj.dao.XjHelpDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("xjHelpDao")
public class XjHelpDaoImpl extends MyBaseDaoImpl implements XjHelpDao{

	@Override
	public Integer addXjHelp(XjHelp xjHelp) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(xjHelp, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateHelpByEmobIdFrom(XjHelp xjHelp) throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE xj_help SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(xjHelp);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE emob_id_from = ? and order_id = ? ";
		System.out.println(sql);
	
		list.add(xjHelp.getEmobIdFrom());
		list.add(xjHelp.getOrderId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateHelpByEmobIdTo(XjHelp xjHelp) throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE xj_help SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(xjHelp);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE emob_id_to = ? and order_id = ? ";
		System.out.println(sql);
		
		list.add(xjHelp.getEmobIdTo());
		list.add(xjHelp.getOrderId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

}
