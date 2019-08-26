package com.xj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.XjTest;
import com.xj.dao.XjTestDao;
import com.xj.exception.ShopItemException;

@Service("testService")
public class TestService {
	
	@Autowired
	private XjTestDao xjTestDao;
	
	public String Test() throws Exception{
		String result = "";
		XjTest xjTest = new XjTest();
		try {
			
			xjTest.setCreateTime((int)(System.currentTimeMillis() / 1000l));
			xjTest.setStrOne("1");
			xjTest.setStrTwo("2");
			xjTestDao.addXjTest(xjTest) ;
			Integer.parseInt("aa");
		} catch (ShopItemException e) {
			// TODO: handle exception
			
			throw new ShopItemException(xjTest);
		
		}
		return result;
	}
}
