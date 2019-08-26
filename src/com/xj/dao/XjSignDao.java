package com.xj.dao;

import com.xj.bean.XjSign;

public interface XjSignDao extends MyBaseDao {

	public Integer addXjSign(XjSign xjSign) throws Exception;
	
	public XjSign getSign(String nonce) throws Exception;
}
