package com.xj.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xj.dao.OrderDetailDao;

/**
 * @author lence
 * @date 2015-7-15 下午03:51:41
 * @version 1.0
 */
@Repository("orderDetailDao")
public class OrderDetailDaoImpl extends MyBaseDaoImpl implements OrderDetailDao {

	@Override
	public List<String> findOrderInfo(String serial) throws Exception {
	String sql = "SELECT s.service_img as serviceImg"+
		" FROM orders o LEFT JOIN order_detail d ON o.order_id = d.order_id "+
		" LEFT JOIN shop_item s ON s.service_id = d.service_id WHERE "+
		" o.serial =? ";
		//String[] params = {serial};
		List<String> list  = new  ArrayList<String>();
		List<Map<String,Object>> queryForList = this.getJdbcTemplate().queryForList(sql, serial);
		for (Map<String, Object> map : queryForList) {
			list.add((String)map.get("serviceImg"));
		}
		
		return list;
	}
}