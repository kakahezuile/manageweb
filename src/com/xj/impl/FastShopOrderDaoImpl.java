package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.FastShopOrderHistory;
import com.xj.bean.Page;
import com.xj.bean.ShopItemOrderHistory;
import com.xj.dao.FastShopOrderDao;

@Repository("fastShopOrderDao")
public class FastShopOrderDaoImpl extends MyBaseDaoImpl implements FastShopOrderDao{

	@Override 
	public Page<FastShopOrderHistory> getFastShopOrder(String emobId,
			String sort, String status, Integer pageNum, Integer pageSize,
			Integer nvm) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT o.order_id , o.serial , o.end_time , round((o.order_price - ifnull(b.bonus_par,0)),2) as order_price " +

			" FROM orders o LEFT JOIN shops s "+

			" ON o.emob_id_shop = s.emob_id " +

			" LEFT JOIN user_bonus us on o.serial = us.serial "+

			" LEFT JOIN bonus b on us.bonus_id = b.bonus_id " +

			" WHERE s.sort = ? AND o.emob_id_user = ? AND o.status = ? AND ifnull(o.order_detail,'') != 'delete' ORDER BY o.end_time desc ";
		List<Object> list = new ArrayList<Object>();
		list.add(sort);
		list.add(emobId);
		list.add(status);
		Page<FastShopOrderHistory> page = this.getData4Page(sql, list.toArray(), pageNum, pageSize, nvm, FastShopOrderHistory.class);
		return page;
	}

	@Override
	public List<ShopItemOrderHistory> getShopItemOrderHistory(Integer orderId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT si.status , si.purchase , o.service_name , si.current_price , si.origin_price , s.shop_name , s.shop_id , s.emob_id as shop_emob_id , round(avg(c.score),0) as score , si.service_img , o.count , o.service_id " +
					 "	 FROM order_detail o " +
					 " 	 LEFT JOIN shop_item si " +
					 "     ON o.service_id = si.service_id "+
					 "   LEFT JOIN shops s " +
					 "     ON si.shop_id = s.shop_id " +
					 "	 LEFT JOIN comments c " +
					 " 	   ON s.emob_id = c.emob_id_to " +
					 " 	WHERE o.order_id = ? " +
					 " GROUP BY o.order_detail_id " ;
		List<Object> params = new ArrayList<Object>();
		params.add(orderId);
		List<ShopItemOrderHistory> list = this.getList(sql, params.toArray(), ShopItemOrderHistory.class);
		return list;
	}

}
