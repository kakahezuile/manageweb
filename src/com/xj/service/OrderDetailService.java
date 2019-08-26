package com.xj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.dao.OrderDetailDao;

/**
 * @author lence
 * @date 2015-7-15 下午03:52:45
 * @version 1.0
 */
@Service("orderDetailService")
public class OrderDetailService{

	@Autowired
	private OrderDetailDao orderDetailDao;
	
	public List<String> shareLifeCircle(Integer communityId, String serial) throws Exception {
		return orderDetailDao.findOrderInfo(serial);
	}

}
