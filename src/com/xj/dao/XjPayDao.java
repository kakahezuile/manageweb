package com.xj.dao;

import com.xj.bean.Page;
import com.xj.bean.PayAndUser;
import com.xj.bean.PayTop;
import com.xj.bean.XjPay;

public interface XjPayDao extends MyBaseDao {
	
	public Integer addXjPay(XjPay xjPay) throws Exception;
	
	public boolean updateXjPay(XjPay xjPay) throws Exception;
	
	public Page<PayAndUser> getPayList(Integer type , Integer status , Integer pageNum , Integer pageSize , Integer navNum) throws Exception;
	
	public PayTop getPayTop(Integer type , Integer status) throws Exception;
	
	public XjPay getXjPay(Integer payId) throws Exception;
}
