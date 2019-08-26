package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.XjAccounter;
import com.xj.dao.XjAccounterDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("xjAccounterDao")
public class XjAccounterDaoImpl extends MyBaseDaoImpl implements XjAccounterDao{

	@Override
	public Integer addXjAccounter(XjAccounter xjAccounter) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(xjAccounter, key);
		return key.getId();
	}

	@Override
	public boolean updateXjAccounter(XjAccounter xjAccounter) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<XjAccounter> getXjAccountList(String emobId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT accounter_id , accounter_month , accounter_date , accounter_price , emob_id , status FROM xj_accounter WHERE emob_id = ?";
		List<XjAccounter> list = this.getList(sql, new Object[]{emobId}, XjAccounter.class);
		return list;
	}

	@Override
	public XjAccounter getAccount(String emobId, String month) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT accounter_id , accounter_month , accounter_date , accounter_price , emob_id , status FROM xj_accounter WHERE emob_id = ? AND accounter_month = ?";
		List<XjAccounter> list = this.getList(sql, new Object[]{emobId,month}, XjAccounter.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
