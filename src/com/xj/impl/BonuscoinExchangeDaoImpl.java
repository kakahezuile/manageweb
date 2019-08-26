package com.xj.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.BonuscoinExchange;
import com.xj.dao.BonuscoinExchangeDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("bonuscoinExchangeDao")
public class BonuscoinExchangeDaoImpl extends MyBaseDaoImpl implements BonuscoinExchangeDao{

	@Override
	public Integer addBonuscoinExchange(BonuscoinExchange bonuscoinExchange)
			throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(bonuscoinExchange, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateBonuscoinExchange(BonuscoinExchange bonuscoinExchange)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "UPDATE bonuscoin_exchange SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(bonuscoinExchange);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}	
		sql += " WHERE exchange_id = ? ";
		System.out.println(sql);
		list.add(bonuscoinExchange.getExchangeId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}
	
	@Override
	public BonuscoinExchange getBonuscoinExchange(Integer communityId)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT exchange_id, community_id, bonuscoin, exchange,service_id FROM bonuscoin_exchange WHERE community_id = ? ";
		List<BonuscoinExchange> list = this.getList(sql, new Object[]{communityId}, BonuscoinExchange.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
