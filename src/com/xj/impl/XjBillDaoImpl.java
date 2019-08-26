package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.XjBill;
import com.xj.dao.XjBillDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("xjBillDao")
public class XjBillDaoImpl extends MyBaseDaoImpl implements XjBillDao{

	@Override
	public Integer addXjBill(XjBill xjBill) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(xjBill, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateXjBill(XjBill xjBill) throws Exception {
		// TODO Auto-generated method stub
		String sql = " UPDATE xj_bill SET " ;
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(xjBill);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false; 
		}
		sql += " WHERE id = ? ";
		list.add(xjBill.getId());
		Integer result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public XjBill getXjBill(String tradeNo) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT id , bill_id , amt , bill_info , bill_user , bill_time , bill_channel , bill_ret , trade_no FROM xj_bill WHERE trade_no = ? ";
		List<XjBill> list = this.getList(sql, new Object[]{tradeNo}, XjBill.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
	
}
