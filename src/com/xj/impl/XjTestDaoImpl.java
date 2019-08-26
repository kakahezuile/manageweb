package com.xj.impl;

import org.springframework.stereotype.Repository;

import com.xj.bean.XjTest;
import com.xj.dao.XjTestDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("xjTestDao")
public class XjTestDaoImpl extends MyBaseDaoImpl implements XjTestDao{

	@Override
	public Integer addXjTest(XjTest xjTest) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(xjTest, key);
		return key.getId();
	}

}
