package com.xj.dao;

import com.xj.bean.XjBill;

public interface XjBillDao extends MyBaseDao {

	public Integer addXjBill(XjBill xjBill) throws Exception;
	
	public boolean updateXjBill(XjBill xjBill) throws Exception;
	
	public XjBill getXjBill(String tradeNo) throws Exception;
}
