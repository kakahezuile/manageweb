package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.XjSign;
import com.xj.dao.XjSignDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("xjSignDao")
public class XjSignDaoImpl extends MyBaseDaoImpl implements XjSignDao{

	@Override
	public Integer addXjSign(XjSign xjSign) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(xjSign, key);
		return key.getId();
	}

	@Override
	public XjSign getSign(String nonce) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT * FROM xj_sign WHERE nonce = ? ";
		List<XjSign> list = this.getList(sql, new Object[]{nonce}, XjSign.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
